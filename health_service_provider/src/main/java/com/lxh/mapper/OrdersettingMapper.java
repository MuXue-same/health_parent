package com.lxh.mapper;

import com.lxh.entity.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrdersettingMapper {

    void insert(@Param("order") OrderSetting orderSetting);


    List<OrderSetting> selectDate(@Param("dateBegin") String dateBegin,@Param("dateEnd") String dateEnd);

    OrderSetting findByOrderDate(@Param("orderDate") Date orderDate);
}