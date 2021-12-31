package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.Multiple;
import com.example.demo.mapper.MultipleMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/multiple")
public class MultipleController {
    @Resource
    MultipleMapper multipleMapper;

    @PostMapping //从前端把数据导入到数据库
    public Result<?> save(@RequestBody Multiple multiple){
        multipleMapper.insert(multiple);
        return Result.success();
    }

    @GetMapping("/{num}")
    public Result<?> produceSingle(@PathVariable Long num){
        String lastSql = "order by rand() limit "+num;
        List<Multiple> multipleList = multipleMapper.selectList(new QueryWrapper<Multiple>().last(lastSql));
        return Result.success(multipleList);
    }
}
