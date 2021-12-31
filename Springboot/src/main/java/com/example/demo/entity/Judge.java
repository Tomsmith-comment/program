package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("judge")
@Data
public class Judge {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String question;
    @TableField("optionA")
    private String optionA;
    @TableField("optionB")
    private String optionB;
    private String answer;
}
