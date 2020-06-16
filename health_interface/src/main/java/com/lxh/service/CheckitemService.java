package com.lxh.service;

import com.lxh.entity.CheckItem;
import com.lxh.entity.PageResult;
import com.lxh.entity.Result;

import java.util.List;

public interface CheckitemService {
    void insertCheckItem(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void deleteCheckItem(int id);

    void updateCheckItem(CheckItem checkItem);

    boolean decg(CheckItem checkItem);

    List<CheckItem> findAll();

    List<Integer> findById(String groupid);
}
