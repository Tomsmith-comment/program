package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.Judge;
import com.example.demo.mapper.JudgeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/judge")
public class JudgeController {
    @Resource
    JudgeMapper judgeMapper;

    @PostMapping
    public Result<?> save(@RequestBody Judge judge){
        judge.setOptionA("对");
        judge.setOptionB("错");
        judgeMapper.insert(judge);
        return Result.success();
    }

    @GetMapping("/{num}")
    public Result<?> produceJudge(@PathVariable Long num){
        String lastSql = "order by rand() limit "+num;
        List<Judge> judgeList = judgeMapper.selectList(new QueryWrapper<Judge>().last(lastSql));
        return Result.success(judgeList);
    }
}
