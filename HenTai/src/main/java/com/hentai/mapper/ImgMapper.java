package com.hentai.mapper;

import com.hentai.pojo.Videoav;
import org.apache.ibatis.annotations.Select;

public interface ImgMapper {
    @Select("select * from videoav where id=#{i}")
    Videoav getavformat(Integer i);
}
