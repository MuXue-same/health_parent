package com.lxh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lxh.entity.MessageConstant;
import com.lxh.entity.Order;
import com.lxh.entity.RedisMessageConstant;
import com.lxh.entity.Result;
import com.lxh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
        public Result submit(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        if (code==null || !code.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        map.put("orderType",Order.ORDERTYPE_WEIXIN);
        Result result = null;
        try {
            result = orderService.submit(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if (result.isFlag()){
            System.out.println("发送预约成功短信!");
        }

        return result;
    }

    @RequestMapping("/findById")
    public Result findById(String id){

        try {
            Map order = orderService.findById(id);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

    }
}
