package com.lxh.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lxh.entity.OrderSetting;
import com.lxh.mapper.OrdersettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (!CollectionUtils.isEmpty(orderSettingList)){
            for (OrderSetting orderSetting : orderSettingList) {

                ordersettingMapper.insert(orderSetting);
            }


        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        System.out.println(date);
        String dateBegin = date + "-1";//2019-3-1
        String dateEnd = date + "-31";//2019-3-31
        List<OrderSetting> orderSettings = ordersettingMapper.selectDate(dateBegin,dateEnd);
        List<Map> mapList = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map orderMap = new HashMap();
            orderMap.put("date",orderSetting.getOrderDate().getDate());//获得日期 （几号）
            orderMap.put("number",orderSetting.getNumber());//可预约人数
            orderMap.put("reservations",orderSetting.getReservations());//已预约人 数
            mapList.add(orderMap);

        }

        return mapList;
    }

    @Override
    public void update(OrderSetting orderSetting) {
        ordersettingMapper.insert(orderSetting);
    }
}
