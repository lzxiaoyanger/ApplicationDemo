package com.zz.personal.service;

import com.zz.personal.dao.entity.Photo;

import java.util.List;

public interface PhotoService {

    List<Photo> getOnePhoto(Integer photoId);
}
