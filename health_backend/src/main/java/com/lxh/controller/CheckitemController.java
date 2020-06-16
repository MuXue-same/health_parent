package com.lxh.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lxh.entity.*;
import com.lxh.service.CheckitemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckitemController {
    @Reference
    private CheckitemService checkitemService;

    @RequestMapping("/insertCheckItem")
    public Result insertCheckItem(@RequestBody CheckItem checkItem){
        try {
            checkitemService.insertCheckItem(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/updateCheckItem")
    public Result updateCheckItem(@RequestBody CheckItem checkItem){
        boolean decg = checkitemService.decg(checkItem);
        if (decg) {
            try {
                checkitemService.updateCheckItem(checkItem);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
            }
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }
        return new Result(false, MessageConstant.CODE_NAME_FAIL);
    }

    @RequestMapping("/deleteCheckItem")
    public Result deleteCheckItem(int id){

        try {
            checkitemService.deleteCheckItem(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }



    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult page = checkitemService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());

        return page;

    }


    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = checkitemService.findAll();

        if (list!=null && list.size()>0){
           return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    @GetMapping("/findById/{groupid}")
    public Result findById(@PathVariable("groupid") String groupid){
        List<Integer> checkitemIds = checkitemService.findById(groupid);

        if (checkitemIds!=null && checkitemIds.size()>0){
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }



}
