package com.cj.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019/04/17
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "urine_proposal")
public class UrineProposal {
    /**
     * 尿检改善建议
     */
    @ApiModelProperty(name = "urineProposalId",value = "尿检改善建议",dataType = "java.lang.Long")
    private Long urineProposalId;

    /**
     * 标题
     */
    @ApiModelProperty(name = "urineProposalName",value = "标题",dataType = "java.lang.String")
    private String urineProposalName;

    /**
     * 尿检项
     */
    @ApiModelProperty(name = "urineProposalResult",value = "尿检项",dataType = "java.lang.String")
    private String urineProposalResult;

    /**
     * 内容
     */
    @ApiModelProperty(name = "urineProposalContent",value = "内容",dataType = "java.lang.String")
    private String urineProposalContent;
}