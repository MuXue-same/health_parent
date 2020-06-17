package com.lxh.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lxh.entity.*;
import com.lxh.mapper.MemberMapper;
import com.lxh.mapper.OrderMapper;
import com.lxh.mapper.OrdersettingMapper;
import com.lxh.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersettingMapper ordersettingMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result submit(Map map) {
        ;Date orderDate =null;
        try {
            orderDate = DateUtils.parseString2Date((String) map.get("orderDate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        OrderSetting orderSetting = ordersettingMapper.findByOrderDate(orderDate);
        if (orderSetting==null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (reservations>=number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findByTelephone(telephone);
        if (member!=null){
            Integer memberId = member.getId();
            Integer setmealId = (Integer) map.get("setmealId");
            List<Order> orderList = orderMapper.findByMemberIdAndSetmealIdAndOrderDate(memberId,setmealId,orderDate);
            if (CollectionUtils.isEmpty(orderList)){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {

            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberMapper.insert(member);
        }

        Order order = new Order();
        order.setMemberId(member.getId());//设置会员ID
        order.setOrderDate(orderDate);//预约日期
        order.setOrderType((String) map.get("orderType"));//预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);//到诊状态
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));//套餐ID
        orderMapper.insert(order);

        orderSetting.setReservations(orderSetting.getReservations()+1);
        ordersettingMapper.insert(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map findById(String id) {
        Map map = orderMapper.findById4Detail(Integer.parseInt(id));
        return map;

    }
}
