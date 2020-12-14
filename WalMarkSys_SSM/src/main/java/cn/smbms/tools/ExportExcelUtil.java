package cn.smbms.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.commons.lang3.StringUtils;

public class ExportExcelUtil<T> {

    // 2007 版本以上 最大支持1048576行
    public final static String EXCEl_FILE_2007 = "2007";
    // 2003 版本 最大支持65536 行
    public final static String EXCEL_FILE_2003 = "2003";


    /**
     * 通过版本类判断是
     *
     * @param sheetName 表格标题名
     * @param headers   表格头部标题集合
     * @param dataset   数据集合
     * @param out       输出流
     * @param version   指定生成Excel文件的版本
     */
    public void exportExcel(String sheetName, String[] headers, Collection<T> dataset, OutputStream out, String version,String[] delFileds) {
        Workbook workbook = null;
        if (StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        exportExcel(workbook, sheetName, headers, dataset, out, "yyyy-MM-dd",delFileds);
    }

    /**
     * 通用Excel导出方法,利用反射机制遍历对象的所有字段，将数据写入Excel文件中 <br>
     * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
     *
     * @param sheetName 表格标题名
     * @param headers   表格头部标题集合
     * @param dataset   需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
     *                  JavaBean属性的数据类型有基本数据类型及String,Date
     * @param out       与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern   如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
     */
    public void exportExcel(Workbook workbook, String sheetName, String[] headers, Collection<T> dataset, OutputStream out, String pattern,String[] delFileds) {
        // 生成一个表格
        Sheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        //设置宽度
        //sheet.setDefaultColumnWidth(20);
        sheet.setDefaultColumnWidth(18);
        //sheet.trackAllColumnsForAutoSizing();
//        sheet.autoSizeColumn(0);
        // 生成一个样式
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//内容居中
        // 生成一个字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);//加粗
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        CellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        // 生成另一个字体
        Font font2 = workbook.createFont();
        // font2.setBold(true);//加粗
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 产生表格标题行
        Row row = sheet.createRow(0);
        Cell cellHeader;
        for (int i = 0; i < headers.length; i++) {
            cellHeader = row.createCell(i);
            cellHeader.setCellStyle(style);
            cellHeader.setCellValue(headers[i]);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        T t;
        Field[] fields;
        Field field;
        //HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        String fieldName;
        String getMethodName;
        Cell cell;
        Class tCls;
        Method getMethod;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            t = (T) it.next();
            // 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            fields = t.getClass().getDeclaredFields();
            int c = 0;
            Map<String,Integer> initMap = new HashMap<>();
            initMap.put("initV",1);
            for (int i = 0; i < fields.length; i++) {
                int d = i;
                if (checkDelFileds(delFileds,fields[i].getName())) {
                    c=Math.abs(i-initMap.get("initV"));
                        continue;
                }
//                for (String delFiled : delFileds) {
//                    if (fields[i].getName().equals(delFiled)) {
////                    c=0;
////                    c++;
////                    System.out.println(initMap.get("initV"));
//                        c=Math.abs(i-initMap.get("initV"));
//                        continue;
//                    }
//                }
                //-------------
//                if (fields[i].getName().equals("id") || fields[i].getName().equals("providerId") || fields[i].getName().equals("modifyBy")
//                        || fields[i].getName().equals("modifyDate") || fields[i].getName().equals("createdBy")) {
////                    c=0;
////                    c++;
////                    System.out.println(initMap.get("initV"));
//                    c=Math.abs(i-initMap.get("initV"));
//                    continue;
//                }
//                if (c>3) {
//                    d= i-2;
//                }
                d=i-c;
                initMap.put("initV",d);
                cell = row.createCell(d);
                cell.setCellStyle(style2);
                field = fields[i];
                fieldName = field.getName();
                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    String s = "";
                    if (value instanceof Integer) {
                        if (field.getName().equals("isPayment")) {
                            if ((Integer) value==1) {
                                textValue = "未付款";
                                s = "未付款";
                            }else {
                                System.out.println("=========");
                                textValue = "已付款";
                                s = "已付款";
                            }
//                            cell.setCellValue(v.trim());
                        }
                        else {
                            System.out.println("你是逗比？");
                            cell.setCellValue((Integer) value);
                        }
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {

                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        System.out.println(s+"------------------------------->");
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else if (field.getName().equals("isPayment")) {
                            cell.setCellValue(s);
                        } else {
                            // richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(textValue);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源啊啊啊
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否匹配舍掉的字段
     * @param delFileds 舍掉的
     * @param v
     * @return
     */
    private static boolean checkDelFileds(String[] delFileds,String v) {
        for (String delFiled : delFileds) {
            if (v.equals(delFiled)) {
                return true;
            }
        }
        return false;
    }
}

