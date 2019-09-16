package com.cj.core.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新分页类")
public class Pager<T> extends Page {

    // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
    // page.setOptimizeCountSql(false);
    // 当 total 为非 0 时(默认为 0),分页插件不会进行 count 查询
    // 要点!! 分页返回的对象与传入的对象是同一个
    /**
     * 传入类
     */
    @ApiModelProperty(name = "parameters",value = "传入类",dataType = "T")
    private T parameters;
    /**
     * 传入的单个参数，查询用
     */
    @ApiModelProperty(name = "parameter",value = "单条件字符串",dataType = "String")
    private String parameter;

}
