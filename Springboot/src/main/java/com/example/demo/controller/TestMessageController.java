package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.TestMessage;
import com.example.demo.mapper.TestMessageMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/testmessage")
public class TestMessageController {
    @Resource
    TestMessageMapper testMessageMapper;

    @PostMapping
    public Result<?> save(@RequestBody TestMessage testMessage){
        testMessageMapper.insert(testMessage);
        return Result.success();
    }

    @GetMapping("/{username}")  //从后端获取数据
    public Result<?> findPage(@PathVariable String username,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<TestMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TestMessage::getUsername,username);
        if(StrUtil.isNotBlank(search)){
            wrapper.eq(TestMessage::getTestId, Integer.parseInt(search));
        }
        Page<TestMessage> testMessagePage = testMessageMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(testMessagePage);
    }

    @GetMapping("/teacher/{username}")
    public Result<?> findTeacherPage(@PathVariable String username,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<TestMessage> wrapper = Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.eq(TestMessage::getUsername, search);
            wrapper.last("and testId in (select id from test where username = '"+username+"')");
        }else{
            wrapper.last("where testId in (select id from test where username = '"+username+"')");
        }

        Page<TestMessage> testMessagePage = testMessageMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(testMessagePage);
    }



}
