package com.lxh.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lxh.entity.MessageConstant;
import com.lxh.entity.OrderSetting;
import com.lxh.entity.Result;
import com.lxh.service.OrderSettingService;
import com.lxh.util.POIUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            if (!CollectionUtils.isEmpty(strings)){
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] string : strings) {
                    OrderSetting o = new OrderSetting(new Date(string[0]),Integer.parseInt(string[1]));
                    orderSettingList.add(o);
                }
                orderSettingService.add(orderSettingList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> data = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }
    @RequestMapping("/update")
    public Result update(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.update(orderSetting);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }


}
