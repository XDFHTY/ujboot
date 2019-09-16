package com.cj.core.v2entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by lw 2019-06-29 16:09:33
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "v2_location")
public class V2Location implements Serializable {
    /**
     * 定位表主键
     */
    @ApiModelProperty(name = "locationId",value = "定位表主键",dataType = "java.lang.Long")
    @TableId(type = IdType.AUTO)
    private Long locationId;

    /**
     * 用户
     */
    @ApiModelProperty(name = "userId",value = "用户",dataType = "java.lang.Long")
    private Long userId;

    /**
     * 经度
     */
    @ApiModelProperty(name = "longitude",value = "经度",dataType = "java.lang.Double")
    private Double longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(name = "latitude",value = "纬度",dataType = "java.lang.Double")
    private Double latitude;

    /**
     * 海拔
     */
    @ApiModelProperty(name = "altitude",value = "海拔",dataType = "java.lang.Double")
    private Double altitude;

    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "java.util.Date")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}