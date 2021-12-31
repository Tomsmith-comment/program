package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Test;
import com.example.demo.mapper.TestMapper;
import com.example.demo.mapper.TestMessageMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    TestMapper testMapper;
    @Resource
    TestMessageMapper testMessageMapper;

    @PostMapping //从前端把数据导入到数据库
    public Result<?> save(@RequestBody Test test){
        if(test.getStart() == null){
            test.setStart("未发布");
        }
        testMapper.insert(test);
        return Result.success();
    }

    @GetMapping("/stu/{username}")
    public Result<?> findStudentPage(@PathVariable String username,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Test> wrapper = Wrappers.<Test>lambdaQuery().eq(Test::getFlag, 1);      //返回老师开启的考试信息
        if(StrUtil.isNotBlank(search)){
            wrapper.eq(Test::getId, Integer.parseInt(search));
        }
            wrapper.last("and id not in ( select testId from testmessage where username = '"+username+"')");

        Page<Test> testPage = testMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(testPage);
    }

    @GetMapping("/teacher/{username}")
    public Result<?> findPage(@PathVariable String username,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Test> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Test::getUsername,username);
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Test::getProject, search);
        }
        Page<Test> testPage = testMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(testPage);
    }

    @PutMapping //更新数据
    public Result<?> update(@RequestBody Test test){
        testMapper.updateById(test);
        return Result.success();
    }

    @DeleteMapping("/{id}")  //删除数据
    public Result<?> update(@PathVariable Long id){
        testMapper.deleteById(id);
        return Result.success();
    }
}
