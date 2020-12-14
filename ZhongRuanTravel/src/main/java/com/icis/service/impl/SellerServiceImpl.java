package com.icis.service.impl;

import com.icis.dao.SellerDao;
import com.icis.dao.impl.SellerDaoImpl;
import com.icis.pojo.Seller;
import com.icis.service.SellerService;

public class SellerServiceImpl implements SellerService {
    private SellerDao sd=new SellerDaoImpl();
    @Override
    public Seller getSellerById(Integer sid) {
        return sd.getSellerById(sid);
    }
}
