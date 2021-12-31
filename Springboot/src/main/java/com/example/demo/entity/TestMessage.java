package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("testmessage")
@Data
public class TestMessage {
    @TableId(type = IdType.AUTO)
    private String username;
    private String testname;
    @TableField("testId")
    private Integer testId;
    private String test;
    private Integer score;
    private String date;

}
