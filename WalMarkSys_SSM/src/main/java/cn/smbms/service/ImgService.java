package cn.smbms.service;

import cn.smbms.pojo.Img;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImgService {

    boolean addImg(Img img);

    /**
     *
     *
     * @return
     */
    List<Img> getImgList();

    Img getImgByUserId(int userId);

    boolean updateImgByUserId(Img img);

}
