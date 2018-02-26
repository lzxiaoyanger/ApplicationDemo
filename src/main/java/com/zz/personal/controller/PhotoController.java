package com.zz.personal.controller;


import com.zz.personal.dao.entity.Photo;
import com.zz.personal.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService service;

    @RequestMapping("/getPhoto")
    public List<Photo> getPhoto(@RequestParam("id") Integer photoId){
        return service.getOnePhoto(photoId);
    }
}
