package com.lxh.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lxh.entity.CheckGroup;
import com.lxh.entity.CheckItem;
import com.lxh.entity.PageResult;
import com.lxh.mapper.CheckgroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckgroupService.class)
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {
    @Autowired
    private CheckgroupMapper checkgroupMapper;


    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> checkGroups = checkgroupMapper.selectCodeOrderByName(queryString);

        return new PageResult(checkGroups.getTotal(),checkGroups.getResult());
    }

    @Override
    public void insertCheckgroup(CheckGroup checkGroup, String[] checkitemids) {
        checkgroupMapper.insertCheckgroup(checkGroup);
        insertCheckgroupAnditem(checkGroup.getId(),checkitemids);
    }

    @Override
    public void deleteCheckgroup(int groupid) {
        deletegroupAnditem(groupid);
        checkgroupMapper.deleteCheckgroup(groupid);
    }

    @Override
    public void updateCheckgroup(CheckGroup checkGroup, String[] checkitemids) {
        checkgroupMapper.updateCheckgroup(checkGroup);
        deletegroupAnditem(checkGroup.getId());
        insertCheckgroupAnditem(checkGroup.getId(),checkitemids);
    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> list = checkgroupMapper.findAll();
        return list;
    }

    public void insertCheckgroupAnditem(Integer checkGroupId , String[] checkitemids){
        checkgroupMapper.insertGroupAndItem(checkGroupId,checkitemids);
    }

    public void deletegroupAnditem(int groupid){
        checkgroupMapper.deletegroupAnditem(groupid);
    }
}
