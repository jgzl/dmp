package cn.cleanarch.dmp.module.system.controller.admin.tenant.vo.tenant;

import lombok.*;
import io.swagger.annotations.*;

import java.time.LocalDateTime;

@ApiModel("管理后台 - 租户 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TenantRespVO extends TenantBaseVO {

    @ApiModelProperty(value = "租户编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
