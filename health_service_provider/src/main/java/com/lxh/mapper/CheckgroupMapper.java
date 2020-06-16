package com.lxh.mapper;

import com.github.pagehelper.Page;
import com.lxh.entity.CheckGroup;
import com.lxh.entity.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckgroupMapper {
    Page<CheckGroup> selectCodeOrderByName(@Param("vule") String vule);


    void insertCheckgroup(@Param("checkGroup") CheckGroup checkGroup);


    void insertGroupAndItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemids") String[] checkitemids);

    void deletegroupAnditem(@Param("groupid") int groupid);

    void deleteCheckgroup(@Param("Pgroupid") int groupid);

    void updateCheckgroup(@Param("upgroup") CheckGroup checkGroup);

    List<CheckGroup> findAll();
}