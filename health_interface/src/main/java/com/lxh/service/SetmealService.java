package com.lxh.service;

import com.lxh.entity.PageResult;
import com.lxh.entity.Setmeal;

import java.util.List;

public interface SetmealService {
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void insertSetmeal(Setmeal setmeal, String[] checkgroupIds);

    List<Setmeal> findAll();

    Setmeal findById(int id);
}
