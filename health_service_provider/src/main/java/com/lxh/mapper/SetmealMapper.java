package com.lxh.mapper;

import com.github.pagehelper.Page;
import com.lxh.entity.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealMapper {


    Page<Setmeal> findPage(@Param("queryString") String queryString);

    void insertSetmeal(@Param("setmeal") Setmeal setmeal);

    void insertSetChe(@Param("setId") int setId,@Param("checkgroupIds") String[] checkgroupIds);

    List<Setmeal> findAll();

    Setmeal findById(@Param("id") int id);
}