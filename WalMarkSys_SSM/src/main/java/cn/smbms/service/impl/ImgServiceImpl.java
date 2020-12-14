package cn.smbms.service.impl;

import cn.smbms.mapper.ImgMapper;
import cn.smbms.pojo.Img;
import cn.smbms.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImgServiceImpl implements ImgService {

    @Autowired
    private ImgMapper imgMapper;


    @Override
    public boolean addImg(Img img) {
        boolean falg = false;
        try {
            int i = imgMapper.addImg(img);
            if (i>0) {
                falg = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return falg;
    }

    @Override
    public List<Img> getImgList() {
        List<Img> imgList = null;
        try {
            imgList = imgMapper.getImgList();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return imgList;
    }

    @Override
    public Img getImgByUserId(int userId){
        Img img = null;
        try {
            img = imgMapper.getImgByUserId(userId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    @Override
    public boolean updateImgByUserId(Img img) {
        boolean falg = false;
        try {
            int i = imgMapper.updateImgByUserId(img);
            System.out.println(i);
            if (i>0) {
                falg = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return falg;
    }
}
