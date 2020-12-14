package cn.smbms.controller;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.BillService;
import cn.smbms.service.ProviderService;
import cn.smbms.service.UserService;
import cn.smbms.tools.ExportExcelUtil;
import cn.smbms.tools.ParseExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private UserService userService;

    //刷新及查询订单列表的方法
    @RequestMapping("/bill.do")
    public String billList(@RequestParam(required=true,defaultValue="1") Integer page,String queryProductName, String queryProviderId, String queryIsPayment, Model model,String id) {
        //当前页  页大小
        PageHelper.startPage(page, 8);

        //添加"id"字段下拉搜索记录 by夏小虎
        List<Bill> billList = billService.getBillList(queryProductName, queryProviderId, queryIsPayment,id);
        //供应商列表
        List<Provider> providerList = billService.getProviderList();

        PageInfo<Bill> pageInfo=new PageInfo<Bill>(billList);

       // System.out.println(pageInfo);

        //具体订单列表
        model.addAttribute("billList", billList);
        //供应商列表
        model.addAttribute("providerList", providerList);
        //搜索框商品名回显
        model.addAttribute("queryProductName", queryProductName);
        //搜索框商品id
        model.addAttribute("queryProviderId", queryProviderId);
        //搜索框支付状态
        model.addAttribute("queryIsPayment", queryIsPayment);

        //分页集合
        model.addAttribute("pageInfo",pageInfo);
        return "billlist";
    }

    //供应商 json格式的集合
    @ResponseBody
    @RequestMapping("/providerList.do")
    public List<Provider> getproviderlist() {
        List<Provider> providerList = billService.getProviderList();
        return providerList;
    }

    //添加订单列表的方法
    @RequestMapping("/addBill.do")
    public String addBill(Bill bill,HttpServletRequest request) {
        User userSession = (User) request.getSession().getAttribute("userSession");
        bill.setCreatedBy(userSession.getId());
        System.out.println(bill);
        Integer i = billService.addBill(bill);
        //跳转到刷新列表的方法
        return "redirect:bill.do";
    }


    //查看详情
    @RequestMapping("/viewBill.do")
    public String viewBill(String billid, Model model) {
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill", bill);
        return "billview";
    }

    //修改时候数据回显
    @RequestMapping("/getBillById.do")
    public String getBillById(String billid, Model model) {
        Bill bill = billService.getBillById(billid);
        model.addAttribute("bill", bill);
        return "billmodify";
    }

    //修改数据
    @RequestMapping("/modifyBill.do")
    public String modifyBill(Bill bill) {
        Integer i = billService.updateBill(bill);
        return "redirect:bill.do";
    }


    //删除数据
    @ResponseBody
    @RequestMapping("/delBill.do")
    public  HashMap<String, String> delBill( String billid) {
        System.out.println(billid);
        HashMap<String, String> resultMap = new HashMap<>();

        if (!StringUtils.isNullOrEmpty(billid)) {
            Integer i = billService.delBill(billid);
            if (i > 0) {//删除成功
                resultMap.put("delResult", "true");
            } else {//删除失败
                resultMap.put("delResult", "false");
            }
        } else {
            resultMap.put("delResult", "notexit");
        }
        return resultMap;

    }

    /**
     * 导入表格
     * 谢毓武
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Map fileUpload(MultipartFile file) {
        Map<String,String> resultMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        try {
            Class  billClass = Bill.class;
//            Bill refectBill = (Bill) billClass.newInstance();
            Field[] fields = billClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("id") || field.getName().equals("providerId") || field.getName().equals("modifyBy")
                        || field.getName().equals("modifyDate") || field.getName().equals("createdBy")) {
                    continue;
                }
                list.add(field.getName());
            }
            ParseExcelUtil.setPojoFileds(list);
            System.out.println(ParseExcelUtil.getPojoFileds());
            String originalFilename = file.getOriginalFilename();
            if (!file.isEmpty()&& file.getSize()>0) {
                InputStream is = file.getInputStream();
                String json = ParseExcelUtil.readExcel(is,originalFilename,1,0);
                System.out.println(json);
                List<Bill> bills = JSON.parseObject(json,new TypeReference<List<Bill>>(){});
                System.out.println(bills);
                List<Bill> billList = new ArrayList<>();
                List<Provider> providerList = providerService.queryAll();
                List<User> userList = userService.getAllUser();
                for (Bill b : bills) {
                    for (Provider provider : providerList) {
                        if (b.getProviderName().equals(provider.getProName())) {
                            b.setProviderId(provider.getId());
                        }
                    }
                    for (User user : userList) {
                        if (b.getUserName().equals(user.getUserName())) {
                            b.setCreatedBy(user.getId());
                        }
                    }
                    Integer i = billService.addBill(b);
                    if (i>0) {
                        resultMap.put("result","成功");
                    }else {
                        resultMap.put("result","请确定表格是否填有数据！");
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }


    /**
     * 导出
     * 谢毓武
     */
    @RequestMapping("/getExcel")
    public void getExcel(@RequestParam(required=true,defaultValue="1") Integer page,String queryProductName, String queryProviderId, String queryIsPayment, HttpServletResponse response) {
        //当前页  页大小
        PageHelper.startPage(page, 8);
        List<Bill> billList = billService.getBillList(queryProductName, queryProviderId, queryIsPayment,"");
        List<User> userList = userService.getAllUser();
        for (Bill bill : billList) {
            for (User user : userList) {
                if (bill.getCreatedBy()==user.getId()) {
                    bill.setUserName(user.getUserName());
                }
            }
        }
        ExportExcelUtil<Bill> util = new ExportExcelUtil<Bill>();
        // 准备数据
        String[] columnNames = {"订单编码", "商品名称", "商品描述", "商品单位",
                "商品数量", "总金额", "是否支付", "创建时间",
                "供应商名称", "用户名称"};

        try {
            String fileName = "订单信息.xlsx";
            // 表示以附件的形式把文件发送到客户端
            String myFileName = new Date().getTime()+".xlsx";
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
            //response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "ISO8859-1"));//设定输出文件头
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(myFileName,"UTF-8"));
            String[] delFileds = {"id","providerId","modifyBy","modifyDate","createdBy"};
            String version = null;
            if (myFileName.endsWith(".xls")) {
                version = ExportExcelUtil.EXCEL_FILE_2003;
            }else {
                version = ExportExcelUtil.EXCEl_FILE_2007;
            }
            util.exportExcel("信息导出", columnNames, billList, new FileOutputStream(fileName), version,delFileds);
            FileInputStream fis = new FileInputStream("订单信息.xlsx");
            byte[] buff = new byte[8*1024];
            int len;
            ServletOutputStream os = response.getOutputStream();
            while ((len=fis.read(buff))!=-1) {
                os.write(buff);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-----------");
        System.out.println(billList);
    }

    //联想查询
    @RequestMapping("getdata")
    @ResponseBody
    public Object getdata(String queryProductName) {
        return billService.getdata(queryProductName);
        /*List<String> str = new ArrayList<>();
        List<Bill> billList = billService.getBillList(queryProductName,null,null,"");
        for (int i = 0; i < billList.size(); i++) {
            str.add(billList.get(i).getProductName());
            if (i==5)
                break;
        }
        return str;*/
    }

}
