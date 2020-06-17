package com.lxh.mapper;

import com.lxh.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> findByMemberIdAndSetmealIdAndOrderDate(@Param("memberId") Integer memberId, @Param("setmealId") Integer setmealId, @Param("orderDate") Date orderDate);

    Map findById4Detail(Integer id);
}