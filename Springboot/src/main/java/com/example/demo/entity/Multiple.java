package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("multiple")
@Data
public class Multiple {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String question;
    @TableField("optionA")
    private String optionA;
    @TableField("optionB")
    private String optionB;
    @TableField("optionC")
    private String optionC;
    @TableField("optionD")
    private String optionD;
    private String answer;
}
