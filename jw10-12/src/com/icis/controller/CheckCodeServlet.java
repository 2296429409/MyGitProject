package com.icis.controller;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkcode")
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置图片高宽并创建
        int w=100;
        int h=50;
        BufferedImage bufferedImage =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //创建画笔
        Graphics2D graphics = bufferedImage.createGraphics();
        //画背景
        graphics.setColor(Color.RED);
        graphics.fillRect(0,0,w,h);
        //画边框
        graphics.setColor(Color.CYAN);
        graphics.drawRect(0,0,w-1,h-1);
        //获取随机字
        String str=
                "0123456789";
        Random random = new Random();
        //画字
        graphics.setColor(Color.blue);
        char[] chars = new char[4];
        for (int i = 0; i < 4; i++) {
            int ran = random.nextInt(str.length());
            char ch=str.charAt(ran);
            chars[i]=ch;
            graphics.drawString(String.valueOf(ch),20+w/5*i,25);
        }

//        //画遮掩线条
//        graphics.setColor(Color.cyan);
//        for (int i = 0; i < 9; i++) {
//            int x1=random.nextInt(w);
//            int x2=random.nextInt(w);
//            int y1=random.nextInt(h);
//            int y2=random.nextInt(h);
//            graphics.drawLine(x1,x2,y1,y2);
//        }
        //向前端传递图片
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
        request.getSession().setAttribute("code",new String(chars));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
