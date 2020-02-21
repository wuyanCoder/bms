package com.wuyan.bms.controller;

/**
 * GenerateController
 *
 * @author {yuanwei}
 * @date 2020/2/19 14:49
 */

import com.wuyan.bms.common.dto.BeanField;
import com.wuyan.bms.common.dto.GenerateDetail;
import com.wuyan.bms.common.dto.GenerateInput;
import com.wuyan.bms.common.dto.TableList;
import com.wuyan.bms.common.result.Results;
import com.wuyan.bms.service.GenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码生成接口
 *
 */
@Api(tags = "代码生成")
@Controller
@RequestMapping("/generate")
public class GenerateController {

    @Autowired
    private GenerateService generateService;

    @ApiOperation("查询数据库表信息")
    @GetMapping("tableList")
    @ResponseBody
    public Results<TableList> getTableList() {
        return generateService.getTableList();
    }

    @ApiOperation("查询数据库表信息")
    @GetMapping("edit")
    public String edit(Model model,@RequestParam String tableName) {
        model.addAttribute("tableName",tableName);
        return "generate/edit";
    }


    @ApiOperation("根据表名显示表信息")
    @GetMapping(value = "generateByTableName",params = { "tableName" })
    @ResponseBody
    public GenerateDetail generateByTableName(String tableName) {
        GenerateDetail detail = new GenerateDetail();
        detail.setBeanName(generateService.upperFirstChar(tableName));
        List<BeanField> fields = generateService.listBeanField(tableName);
        detail.setFields(fields);

        return detail;
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/save")
    @ResponseBody
    public Results save(@RequestBody GenerateInput input) {
        generateService.saveCode(input);
        return Results.success();
    }
}
