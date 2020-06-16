package com.lxh.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lxh.entity.CheckItem;
import com.lxh.entity.MessageConstant;
import com.lxh.entity.PageResult;
import com.lxh.entity.Result;
import com.lxh.mapper.CheckitemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckitemServiceImpl implements CheckitemService {

    @Autowired
    private CheckitemMapper checkitemMapper;

    @Override
    public void insertCheckItem(CheckItem checkItem) {
        checkitemMapper.insertCheckItem(checkItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> items = checkitemMapper.selectByCodeAndName(queryString);

        return new PageResult(items.getTotal(),items.getResult());
    }

    @Override
    public void deleteCheckItem(int id) {
        checkitemMapper.deleteById(id);
    }

    @Override
    public void updateCheckItem(CheckItem checkItem) {
        checkitemMapper.updateCheckItem(checkItem);
    }

    public boolean decg(CheckItem checkItem){
        List<CheckItem> checkItems = checkitemMapper.selectCodeAndName(checkItem.getCode(), checkItem.getName());

        if (checkItems!=null ){
            for (CheckItem item : checkItems) {
                if (checkItem.getId()!= item.getId()){
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> list = checkitemMapper.findAll();

        return list;
    }

    @Override
    public List<Integer> findById(String groupid) {

        List<Integer> list = checkitemMapper.findById(Integer.parseInt(groupid));

        return list;
    }


}
