package com.hentai.service.impl;

import com.hentai.mapper.ImgMapper;
import com.hentai.pojo.Videoav;
import com.hentai.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgServiceImpl implements ImgService {
    @Autowired
    private ImgMapper im;

    @Override
    public Videoav getavformat(Integer i) {
        return im.getavformat(i);
    }
}
