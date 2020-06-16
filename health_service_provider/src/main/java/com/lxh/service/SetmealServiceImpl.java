package com.lxh.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lxh.entity.PageResult;
import com.lxh.entity.RedisConstant;
import com.lxh.entity.Setmeal;
import com.lxh.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealMapper.findPage(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void insertSetmeal(Setmeal setmeal, String[] checkgroupIds) {
        setmealMapper.insertSetmeal(setmeal);
        if (checkgroupIds!=null && checkgroupIds.length>0) {
            insertSetChe(setmeal.getId(), checkgroupIds);
        }
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    @Override
    public List<Setmeal> findAll() {

        return setmealMapper.findAll();
    }

    @Override
    public Setmeal findById(int id) {

        return setmealMapper.findById(id);
    }


    public void insertSetChe(int SetId,String[] checkgroupIds){
        setmealMapper.insertSetChe(SetId,checkgroupIds);
    }
}
