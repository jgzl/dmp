package cn.cleanarch.dmp.module.promotion.controller.admin.coupon.vo.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.cleanarch.dmp.framework.common.validation.InEnum;
import cn.cleanarch.dmp.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.cleanarch.dmp.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.cleanarch.dmp.module.promotion.enums.coupon.CouponTemplateValidityTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.cleanarch.dmp.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.cleanarch.dmp.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
* 优惠劵模板 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class CouponTemplateBaseVO {

    @ApiModelProperty(value = "优惠劵名", required = true, example = "春节送送送")
    @NotNull(message = "优惠劵名不能为空")
    private String name;

    @ApiModelProperty(value = "发行总量", required = true, example = "1024", notes = "-1 - 则表示不限制发放数量")
    @NotNull(message = "发行总量不能为空")
    private Integer totalCount;

    @ApiModelProperty(value = "每人限领个数", required = true, example = "66", notes = "-1 - 则表示不限制")
    @NotNull(message = "每人限领个数不能为空")
    private Integer takeLimitCount;

    @ApiModelProperty(value = "领取方式", required = true, example = "1", notes = "参见 CouponTakeTypeEnum 枚举类")
    @NotNull(message = "领取方式不能为空")
    private Integer takeType;

    @ApiModelProperty(value = "是否设置满多少金额可用", required = true, example = "100", notes = "单位：分；0 - 不限制")
    @NotNull(message = "是否设置满多少金额可用不能为空")
    private Integer usePrice;

    @ApiModelProperty(value = "商品范围", required = true, example = "1", notes = "参见 PromotionProductScopeEnum 枚举类")
    @NotNull(message = "商品范围不能为空")
    @InEnum(PromotionProductScopeEnum.class)
    private Integer productScope;

    @ApiModelProperty(value = "商品 SPU 编号的数组", example = "1,3")
    private List<Long> productSpuIds;

    @ApiModelProperty(value = "生效日期类型", required = true, example = "1")
    @NotNull(message = "生效日期类型不能为空")
    @InEnum(CouponTemplateValidityTypeEnum.class)
    private Integer validityType;

    @ApiModelProperty(value = "固定日期 - 生效开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime validStartTime;

    @ApiModelProperty(value = "固定日期 - 生效结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime validEndTime;

    @ApiModelProperty(value = "领取日期 - 开始天数")
    @Min(value = 0L, message = "开始天数必须大于 0")
    private Integer fixedStartTerm;

    @ApiModelProperty(value = "领取日期 - 结束天数")
    @Min(value = 1L, message = "开始天数必须大于 1")
    private Integer fixedEndTerm;

    @ApiModelProperty(value = "优惠类型", required = true, example = "1", notes = "参见 PromotionDiscountTypeEnum 枚举")
    @NotNull(message = "优惠类型不能为空")
    @InEnum(PromotionDiscountTypeEnum.class)
    private Integer discountType;

    @ApiModelProperty(value = "折扣百分比", example = "80", notes = "例如说，80% 为 80")
    private Integer discountPercent;

    @ApiModelProperty(value = "优惠金额", example = "10", notes = "单位：分")
    @Min(value = 0, message = "优惠金额需要大于等于 0")
    private Integer discountPrice;

    @ApiModelProperty(value = "折扣上限", example = "100", notes = "单位：分，仅在 discountType 为 PERCENT 使用")
    private Integer discountLimitPrice;

    @AssertTrue(message = "商品 SPU 编号的数组不能为空")
    @JsonIgnore
    public boolean isProductSpuIdsValid() {
        return Objects.equals(productScope, PromotionProductScopeEnum.ALL.getScope()) // 全部范围时，可以为空
                || CollUtil.isNotEmpty(productSpuIds);
    }

    @AssertTrue(message = "生效开始时间不能为空")
    @JsonIgnore
    public boolean isValidStartTimeValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.DATE.getType())
                || validStartTime != null;
    }

    @AssertTrue(message = "生效结束时间不能为空")
    @JsonIgnore
    public boolean isValidEndTimeValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.DATE.getType())
                || validEndTime != null;
    }

    @AssertTrue(message = "开始天数不能为空")
    @JsonIgnore
    public boolean isFixedStartTermValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.TERM.getType())
                || fixedStartTerm != null;
    }

    @AssertTrue(message = "结束天数不能为空")
    @JsonIgnore
    public boolean isFixedEndTermValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.TERM.getType())
                || fixedEndTerm != null;
    }

    @AssertTrue(message = "折扣百分比需要大于等于 1，小于等于 99")
    @JsonIgnore
    public boolean isDiscountPercentValid() {
        return ObjectUtil.notEqual(discountType, PromotionDiscountTypeEnum.PERCENT.getType())
                || (discountPercent != null && discountPercent >= 1 && discountPercent<= 99);
    }

    @AssertTrue(message = "优惠金额不能为空")
    @JsonIgnore
    public boolean isDiscountPriceValid() {
        return ObjectUtil.notEqual(discountType, PromotionDiscountTypeEnum.PRICE.getType())
                || discountPrice != null;
    }

    @AssertTrue(message = "折扣上限不能为空")
    @JsonIgnore
    public boolean isDiscountLimitPriceValid() {
        return ObjectUtil.notEqual(discountType, PromotionDiscountTypeEnum.PERCENT.getType())
                || discountLimitPrice != null;
    }

}