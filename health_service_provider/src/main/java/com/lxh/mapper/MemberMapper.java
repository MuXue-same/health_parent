package com.lxh.mapper;

import com.lxh.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {
    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    Member findByTelephone(@Param("telephone") String telephone);
}