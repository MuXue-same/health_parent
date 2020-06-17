package com.lxh.service;

import com.lxh.entity.Order;
import com.lxh.entity.Result;

import java.util.Map;

public interface OrderService {
    Result submit(Map map);

    Map findById(String id);
}
