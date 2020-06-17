package com.lxh.controller;

import com.aliyuncs.exceptions.ClientException;
import com.lxh.entity.MessageConstant;
import com.lxh.entity.RedisMessageConstant;
import com.lxh.entity.Result;
import com.lxh.util.SMSUtils;
import com.lxh.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        Integer integer = ValidateCodeUtils.generateValidateCode(4);

        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,integer.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300,integer.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


}
