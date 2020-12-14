package com.hentai.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class test1 {

    public static void main(String[] args) {
//        File[] files = orderByDate("E:\\shixun3\\imgserver\\");
//        List<String> list = new ArrayList<>();
//        File f=new File("E:\\shixun3\\imgserver\\2019lifan");
//        for (File file : f.listFiles()) {
//            System.out.println(file.getName());
//            list.add(file.getName());
//        }
//        System.out.println(list);
        for (int i1 = 0; i1 < 10; i1++) {
            Random r=new Random(0);
            //50-100
            int i=r.nextInt(100-50+1)+50;//[0,51)=50-100
            System.out.println(i);
        }


    }

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
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            System.out.println(new Date(files[i].lastModified()));
        }
        return files;
    }
}
