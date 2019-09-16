package com.cj.suser.domain;

import com.cj.core.entity.Feedback;
import com.cj.core.entity.FeedbackPicture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户投诉实体")
public class VoFeed extends Feedback {

    @ApiModelProperty(name = "feedbackPictures",value = "意见反馈图片集合",dataType = "List")
    private List<FeedbackPicture> feedbackPictures;
}
