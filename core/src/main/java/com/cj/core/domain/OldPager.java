package com.cj.core.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <b> 分页通用类 </b>
 *
 * @author kangxu
 * @param <T>
 *
 */
@Data
@ApiModel(value = "旧分页类")
public class OldPager<T> implements Serializable {

//    ========================= ujv2 加入MP    ===========================

    /**
     * 传入的多个参数
     */
    @ApiModelProperty(name = "params",value = "多条件类/集合",dataType = "HashMap")
    private T params;
    /**
     * 传入的单个参数，模糊查询用
     */
    @ApiModelProperty(name = "param",value = "单条件",dataType = "String")
    private String param;

//    ============================  ujv1    ==================================
    /**
     * currentPage 页码
     */
    @ApiModelProperty(name = "currentPage",value = "页码",dataType = "Integer")
    private int currentPage = 1;
    /**
     * pageSize 每页大小
     */
    @ApiModelProperty(name = "pageSize",value = "每页大小",dataType = "Integer")
    private int pageSize = 10; //limit 第二个参数


    private int minRow = 0;  //limit 中的第一个参数，开始下标

    /**
     * pageTotal 总页数
     */
    @ApiModelProperty(name = "pageTotal",value = "总页数",dataType = "Integer")
    private int pageTotal;
    /**
     * recordTotal 总条数
     */
    @ApiModelProperty(name = "recordTotal",value = "总条数",dataType = "Integer")
    private int recordTotal = 0;
    /**
     * 传入的多个参数
     */
    @ApiModelProperty(name = "parameters",value = "多条件map集合",dataType = "HashMap")
    private Map<String, Object> parameters = new HashMap<>();
    /**
     * 传入的单个参数，模糊查询用
     */
    @ApiModelProperty(name = "parameter",value = "单条件",dataType = "String")
    private T parameter;

    /**
     * content 返回的每页的内容
     */
    @ApiModelProperty(name = "content",value = "返回的每页的内容")
    private Object content;

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
        this.pageTotal = recordTotal%this.pageSize>0? recordTotal/this.pageSize+ 1:recordTotal/this.pageSize;
    }

    public int getMinRow() {
        return ((this.currentPage - 1) * this.pageSize)<0?0:(this.currentPage - 1) * this.pageSize;
    }

    public void setLists(List<List<?>> lists){
        List<Total> totals = (List<Total>)lists.get(1);
        this.recordTotal = totals.get(0).getTotal();
        this.content = lists.get(0);
        this.pageTotal = this.recordTotal%this.pageSize>0? this.recordTotal/this.pageSize+1:this.recordTotal/this.pageSize;
    }
}