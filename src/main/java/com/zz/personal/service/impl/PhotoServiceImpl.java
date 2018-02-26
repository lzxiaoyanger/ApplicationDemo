package com.zz.personal.service.impl;

import com.zz.personal.dao.entity.Photo;
import com.zz.personal.dao.entity.PhotoExample;
import com.zz.personal.dao.mapper.PhotoMapper;
import com.zz.personal.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService{

    @Autowired
    private PhotoMapper photoMapper;


    @Override
    public List<Photo> getOnePhoto(Integer photoId) {
        PhotoExample example = new PhotoExample();
        PhotoExample.Criteria criteria = example.createCriteria();
        criteria.andPhotoIdEqualTo(photoId);
        return photoMapper.selectByExample(example);

    }
}
