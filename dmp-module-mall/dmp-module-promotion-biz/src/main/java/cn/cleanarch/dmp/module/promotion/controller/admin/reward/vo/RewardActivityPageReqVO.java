package cn.cleanarch.dmp.module.promotion.controller.admin.reward.vo;

import lombok.*;
import io.swagger.annotations.*;
import cn.cleanarch.dmp.framework.common.pojo.PageParam;

@ApiModel("管理后台 - 满减送活动分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RewardActivityPageReqVO extends PageParam {

    @ApiModelProperty(value = "活动标题", example = "满啦满啦")
    private String name;

    @ApiModelProperty(value = "活动状态", example = "1")
    private Integer status;

}
