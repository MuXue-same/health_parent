package com.lxh.mapper;
import java.util.List;

import com.github.pagehelper.Page;
import com.lxh.entity.CheckItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CheckitemMapper {

    int insertCheckItem(@Param("checkItem")CheckItem checkItem);


    @Select("<script>"+"select * from t_checkitem"+"<if test='value != null'>"+
            "where code = #{value} or `name` LIKE concat('%',#{value},'%')"+"</if>"+"</script>")
    Page<CheckItem> selectByCodeAndName(@Param("value")String queryString);


    int deleteById(@Param("id")Integer id);

    int updateCheckItem(@Param("checkItem")CheckItem checkItem);

    List<CheckItem> selectCodeAndName(@Param("code")String code,@Param("name")String name);

    List<CheckItem> findAll();

    List<Integer> findById(@Param("groupid")int groupid);


}