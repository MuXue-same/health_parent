package com.lxh.service;

import com.lxh.entity.CheckGroup;
import com.lxh.entity.CheckItem;
import com.lxh.entity.PageResult;

import java.util.List;

public interface CheckgroupService {
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void insertCheckgroup(CheckGroup checkGroup, String[] checkitemids);

    void deleteCheckgroup(int groupid);

    void updateCheckgroup(CheckGroup checkGroup, String[] checkitemids);

    List<CheckGroup> findAll();
}
