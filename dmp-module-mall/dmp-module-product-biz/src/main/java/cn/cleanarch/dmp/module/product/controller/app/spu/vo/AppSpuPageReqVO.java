package cn.cleanarch.dmp.module.product.controller.app.spu.vo;

import cn.cleanarch.dmp.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("App - 商品spu分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppSpuPageReqVO extends PageParam {

    @ApiModelProperty(value = "分类id")
    private Long categoryId;
}
