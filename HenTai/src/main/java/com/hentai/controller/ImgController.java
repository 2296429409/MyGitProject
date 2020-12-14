package com.hentai.controller;

import com.hentai.pojo.Videoav;
import com.hentai.service.ImgService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("img")
public class ImgController {
    static final Random ran=new Random();


    @Autowired
    ImgService is;
    @RequestMapping("imgUp")
    public String imgUp(MultipartFile file) throws IOException {
        System.out.println("上传图片....");
        if (file==null) {
            return "redirect:erro.html";
        }
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename = " + originalFilename);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if ((suffix.equalsIgnoreCase(".jpg")||suffix.equalsIgnoreCase(".jpeg")||suffix.equalsIgnoreCase(".jpe")||suffix.equalsIgnoreCase(".jfif")||suffix.equalsIgnoreCase(".bmp")||suffix.equalsIgnoreCase(".dib")||suffix.equalsIgnoreCase(".png")||suffix.equalsIgnoreCase(".gif"))){
//            String newName= UUID.randomUUID().toString().replace("-","")+suffix;
            String newName= new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())+new Random().nextInt()+suffix;
            File f=new File("G:\\2020\\imgserver\\img\\up\\"+newName);
            file.transferTo(f);
            //缩略图
            Thumbnails.of("G:\\2020\\imgserver\\img\\up\\"+newName)
                    .size(500, 500)
                    .toFile("G:\\2020\\imgserver\\img\\xup\\"+newName);

            return "redirect:../pages/img/test.html";
        }else
        return "redirect:../pages/img/error.html";

    }
    @RequestMapping("/getimgarr")
    @ResponseBody
    public List<String> getimgarr(){
        System.out.println("查看图片....");
        List<String> list = new ArrayList<>();
//        File f=new File("E:\\shixun3\\imgserver\\");
        for (File file : orderByDate("G:\\2020\\imgserver\\img\\up")) {
//            System.out.println(file.getName());
            list.add(file.getName());
        }
        return list;
    }




/*文件排序*/
    public static File[] orderByDate(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return -1;
                else if (diff == 0)
                    return 0;
                else
                    return 1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
//        for (int i = 0; i < files.length; i++) {
//            System.out.println(files[i].getName());
//            System.out.println(new Date(files[i].lastModified()));
//        }
        return files;
    }


    @RequestMapping("/getvideoarr")
    @ResponseBody
    public List<String> getvideoarr(){
        System.out.println("lifan推荐....");
        List<String> list = new ArrayList<>();
        File f=new File("G:\\2020\\imgserver\\2019lifan");
        for (File file : f.listFiles()) {
//            System.out.println(file.getName());
            list.add(file.getName());
        }
        return list;
    }

    @RequestMapping("/getavformat")
    @ResponseBody
    public List<Videoav> getavformat(){
        System.out.println("av推荐....");
        List<Videoav> l=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Videoav format = is.getavformat(ran.nextInt(10)+1);
            System.out.println("format = " + format);
            int number = ran.nextInt(format.getMax()-format.getMin()+1)+format.getMin();
            format.setNumber(number);
            l.add(format);
        }
        return l;
    }





    @GetMapping
    @DeleteMapping
    @PostMapping
    @PutMapping
    public List<String> get(@PathVariable("uid")Integer id){









 /*     DefaultNumber
        1 headname.add("snis");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/snisDefaultNumber/snisDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/s/sni/snis00DefaultNumber/snis00DefaultNumber_dmb_w.mp4
        2 headname.add("ipx");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/ipxDefaultNumber/ipxDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/i/ipx/ipx00DefaultNumber/ipx00DefaultNumber_dm_w.mp4
        3 headname.add("ssni");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/ssniDefaultNumber/ssniDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/s/ssn/ssni00DefaultNumber/ssni00DefaultNumber_dmb_w.mp4
        4 headname.add("mide");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/mideDefaultNumber/mideDefaultNumberpl.jpg                   002-861
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/m/mid/mide00DefaultNumber/mide00DefaultNumber_mhb_w.mp4  -861
        5 headname.add("mizd");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/mizdDefaultNumber/mizdDefaultNumberpl.jpg                   001-999
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/m/miz/mizd00DefaultNumber/mizd00DefaultNumber_dmb_w.mp4  001-999
        6 headname.add("cjod");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/cjodDefaultNumber/cjodDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/c/cjo/cjod00DefaultNumber/cjod00DefaultNumber_dmb_w.mp4
        8 headname.add("jufe");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/jufeDefaultNumber/jufeDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/j/juf/jufe00DefaultNumber/jufe00DefaultNumber_dmb_w.mp4         1-237
        9 headname.add("pred");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/predDefaultNumber/predDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/p/pre/pred00DefaultNumber/pred00DefaultNumber_mhb_w.mp4          160-260
        10 headname.add("miaa");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/miaaDefaultNumber/miaaDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/m/mia/miaa00DefaultNumber/miaa00DefaultNumber_dmb_w.mp4          001-350
        11 headname.add("shkd");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/shkdDefaultNumber/shkdDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/s/shk/shkd00DefaultNumber/shkd00DefaultNumber_dmb_w.mp4          500-914
        12 headname.add("1sdde");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/1sdde634/1sdde634pl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/1/1sd/1sdde634/1sdde634dmb_w.mp4
        13 headname.add("1stars");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/1stars260/1stars260pl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/1/1st/1stars260/1stars260_mhb_w.mp4
        14 headname.add("mvsd");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/mvsdDefaultNumber/mvsdDefaultNumberpl.jpg
        //video: https://cc3001.dmm.co.jp/litevideo/freepv/m/mvs/mvsd00DefaultNumber/mvsd00DefaultNumber_dm_w.mp4           200-422
        15 headname.add("pppd");
        //img:   http://pics.dmm.co.jp/mono/movie/adult/pppd876/pppd876pl.jpg
        //video: http://cc3001.dmm.co.jp/litevideo/freepv/p/ppp/pppd00876/pppd00876_dmb_w.mp4
        headname.add("");
        //img:
        //video:
        headname.add("");
        //img:
        //video:
        headname.add("");
        //img:
        //video:
        headname.add("");
        //img:
        //video:
        headname.add("");
        //img:
        //video:
*/




        return null;
    }





}
