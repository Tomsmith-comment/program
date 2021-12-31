package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("test")
@Data
public class Test {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    @TableField("teacher")
    private String name;
    private String project;
    private Integer single;
    private Integer multiple;
    private Integer judge;
    private Integer time;
    private int flag;
    private String start;

}
