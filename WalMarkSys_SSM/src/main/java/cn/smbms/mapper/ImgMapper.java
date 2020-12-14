package cn.smbms.mapper;

import cn.smbms.pojo.Img;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgMapper {

    int addImg(Img img)throws Exception;

    /**
     *
     *
     * @return
     */
    List<Img> getImgList() throws Exception;

    Img getImgByUserId(int userId) throws Exception;

    int updateImgByUserId(Img img);

}
