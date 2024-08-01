package com.example.mapper;

import com.example.entity.Foods;
import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FoodsMapper {


    @Select("select * from foods where name = #{name}")
    Foods selectByFoodsname(String name);

    void insert(Foods foods);

    void deleteById(Integer id);

    void updateById(Foods foods);


    @Select("select * from foods where id = #{id}")
    Foods selectById(Integer id);


    List<Foods> selectAll(String name);
}
