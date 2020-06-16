package com.lxh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lxh.entity.*;
import com.lxh.service.CheckgroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {
    @Reference
    private CheckgroupService checkgroupService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult page = checkgroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());

        return page;

    }

    @RequestMapping("/insertCheckgroup")
    public Result insertCheckgroup(@RequestBody CheckGroup checkGroup,String[] checkitemids){
        try {
            checkgroupService.insertCheckgroup(checkGroup,checkitemids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }

        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @DeleteMapping("/deleteCheckgroup/{groupid}")
    public Result deleteCheckgroup(@PathVariable("groupid") int groupid){
        try {
            checkgroupService.deleteCheckgroup(groupid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/updateCheckgroup")
    public Result updateCheckgroup(@RequestBody CheckGroup checkGroup,String[] checkitemids){
        try {
            checkgroupService.updateCheckgroup(checkGroup,checkitemids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> list = checkgroupService.findAll();

        if (list!=null && list.size()>0){
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }

}
