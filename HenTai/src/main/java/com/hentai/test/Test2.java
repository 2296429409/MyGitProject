package com.hentai.test;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class Test2 {
    public static void main(String[] args) throws IOException {
        File f=new File("G:\\2020\\imgserver\\img\\up");
        for (File file : f.listFiles()) {
            Thumbnails.of("G:\\2020\\imgserver\\img\\up\\"+file.getName())
                    .size(500, 500)
                    .toFile("G:\\2020\\imgserver\\img\\xup\\"+file.getName());
        }

    }
}
