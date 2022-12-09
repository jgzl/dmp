package cn.cleanarch.dmp.module.promotion.controller.admin.coupon;

import cn.hutool.core.collection.CollUtil;
import cn.cleanarch.dmp.framework.common.pojo.CommonResult;
import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.framework.common.util.collection.MapUtils;
import cn.cleanarch.dmp.module.member.api.user.MemberUserApi;
import cn.cleanarch.dmp.module.member.api.user.dto.UserRespDTO;
import cn.cleanarch.dmp.module.promotion.controller.admin.coupon.vo.coupon.CouponPageItemRespVO;
import cn.cleanarch.dmp.module.promotion.controller.admin.coupon.vo.coupon.CouponPageReqVO;
import cn.cleanarch.dmp.module.promotion.convert.coupon.CouponConvert;
import cn.cleanarch.dmp.module.promotion.dal.dataobject.coupon.CouponDO;
import cn.cleanarch.dmp.module.promotion.service.coupon.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

import static cn.cleanarch.dmp.framework.common.pojo.CommonResult.success;
import static cn.cleanarch.dmp.framework.common.util.collection.CollectionUtils.convertSet;

@Api(tags = "管理后台 - 优惠劵")
@RestController
@RequestMapping("/promotion/coupon")
@Validated
public class CouponController {

    @Resource
    private CouponService couponService;
    @Resource
    private MemberUserApi memberUserApi;

//    @GetMapping("/get")
//    @ApiOperation("获得优惠劵")
//    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
//    @PreAuthorize("@ss.hasPermission('promotion:coupon:query')")
//    public CommonResult<CouponRespVO> getCoupon(@RequestParam("id") Long id) {
//        CouponDO coupon = couponService.getCoupon(id);
//        return success(CouponConvert.INSTANCE.convert(coupon));
//    }

    @DeleteMapping("/delete")
    @ApiOperation("回收优惠劵")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('promotion:coupon:delete')")
    public CommonResult<Boolean> deleteCoupon(@RequestParam("id") Long id) {
        couponService.deleteCoupon(id);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获得优惠劵分页")
    @PreAuthorize("@ss.hasPermission('promotion:coupon:query')")
    public CommonResult<PageResult<CouponPageItemRespVO>> getCouponPage(@Valid CouponPageReqVO pageVO) {
        PageResult<CouponDO> pageResult = couponService.getCouponPage(pageVO);
        PageResult<CouponPageItemRespVO> pageResulVO = CouponConvert.INSTANCE.convertPage(pageResult);
        if (CollUtil.isEmpty(pageResulVO.getList())) {
            return success(pageResulVO);
        }
        // 读取用户信息，进行拼接
        Set<Long> userIds = convertSet(pageResult.getList(), CouponDO::getUserId);
        Map<Long, UserRespDTO> userMap = memberUserApi.getUserMap(userIds);
        pageResulVO.getList().forEach(itemRespVO -> MapUtils.findAndThen(userMap, itemRespVO.getUserId(),
                userRespDTO -> itemRespVO.setNickname(userRespDTO.getNickname())));
        return success(pageResulVO);
    }

}
