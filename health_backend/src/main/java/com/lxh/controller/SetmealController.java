package com.lxh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lxh.entity.*;
import com.lxh.service.SetmealService;
import com.lxh.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult page =setmealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
       return page;
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile.getOriginalFilename());
        String s = UUID.randomUUID().toString();
        String[] split = imgFile.getOriginalFilename().split("\\.");
        String imgName = s+"."+split[split.length-1];
        System.out.println(imgName);
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),imgName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,imgName);
    }

    @PostMapping("/insertSetmeal")
    public Result insertSetmeal(@RequestBody Setmeal setmeal,String[] checkgroupIds){
        try {
            setmealService.insertSetmeal(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
}
