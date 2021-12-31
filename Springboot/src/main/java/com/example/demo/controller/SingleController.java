package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.Single;
import com.example.demo.mapper.SingleMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/single")
public class SingleController {
    @Resource
    SingleMapper singleMapper;

    @PostMapping //从前端把数据导入到数据库
    public Result<?> save(@RequestBody Single single){
        singleMapper.insert(single);
        return Result.success();
    }

    @GetMapping
    public Result<?> selectAll(){
        return Result.success(singleMapper.selectList(null));
    }

    @GetMapping("/{num}")
    public Result<?> produceSingle(@PathVariable Long num){
        String lastSql = "order by rand() limit "+num;
        List<Single> singleList = singleMapper.selectList(new QueryWrapper<Single>().last(lastSql));
        return Result.success(singleList);
    }
}
