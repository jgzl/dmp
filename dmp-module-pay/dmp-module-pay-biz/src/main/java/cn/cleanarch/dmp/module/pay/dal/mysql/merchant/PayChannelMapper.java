package cn.cleanarch.dmp.module.pay.dal.mysql.merchant;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.framework.mybatis.core.mapper.BaseMapperX;
import cn.cleanarch.dmp.framework.mybatis.core.query.QueryWrapperX;
import cn.cleanarch.dmp.module.pay.controller.admin.merchant.vo.channel.PayChannelExportReqVO;
import cn.cleanarch.dmp.module.pay.controller.admin.merchant.vo.channel.PayChannelPageReqVO;
import cn.cleanarch.dmp.module.pay.dal.dataobject.merchant.PayChannelDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface PayChannelMapper extends BaseMapperX<PayChannelDO> {

    default PayChannelDO selectByAppIdAndCode(Long appId, String code) {
        return selectOne(PayChannelDO::getAppId, appId, PayChannelDO::getCode, code);
    }

    @Select("SELECT COUNT(*) FROM pay_channel WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(LocalDateTime maxUpdateTime);

    default PageResult<PayChannelDO> selectPage(PayChannelPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<PayChannelDO>()
                .eqIfPresent("code", reqVO.getCode())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("remark", reqVO.getRemark())
                .eqIfPresent("fee_rate", reqVO.getFeeRate())
                .eqIfPresent("merchant_id", reqVO.getMerchantId())
                .eqIfPresent("app_id", reqVO.getAppId())
                .betweenIfPresent("create_time", reqVO.getCreateTime())
                .orderByDesc("id"));
    }

    default List<PayChannelDO> selectList(PayChannelExportReqVO reqVO) {
        return selectList(new QueryWrapperX<PayChannelDO>()
                .eqIfPresent("code", reqVO.getCode())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("remark", reqVO.getRemark())
                .eqIfPresent("fee_rate", reqVO.getFeeRate())
                .eqIfPresent("merchant_id", reqVO.getMerchantId())
                .eqIfPresent("app_id", reqVO.getAppId())
                .betweenIfPresent("create_time", reqVO.getCreateTime())
                .orderByDesc("id"));
    }

    /**
     * ??????????????????????????????
     *
     * @param merchantId ????????????
     * @param appid      ????????????
     * @param code       ????????????
     * @return ??????
     */
    default Integer selectCount(Long merchantId, Long appid, String code) {
        return this.selectCount(new QueryWrapper<PayChannelDO>().lambda()
                .eq(PayChannelDO::getMerchantId, merchantId)
                .eq(PayChannelDO::getAppId, appid)
                .eq(PayChannelDO::getCode, code)).intValue();
    }

    /**
     * ????????????????????????
     *
     * @param merchantId ????????????
     * @param appid      ???????????? // TODO @aquan???appid =???appId
     * @param code       ????????????
     * @return ??????
     */
    default PayChannelDO selectOne(Long merchantId, Long appid, String code) {
        return this.selectOne((new QueryWrapper<PayChannelDO>().lambda()
                .eq(PayChannelDO::getMerchantId, merchantId)
                .eq(PayChannelDO::getAppId, appid)
                .eq(PayChannelDO::getCode, code)
        ));
    }

    // TODO @aquan???select ??????
    /**
     * ??????????????????ID??????????????????????????????
     *
     * @param appIds ??????????????????
     * @return ??????????????????
     */
    default List<PayChannelDO> getChannelListByAppIds(Collection<Long> appIds){
        return this.selectList(new QueryWrapper<PayChannelDO>().lambda()
                .in(PayChannelDO::getAppId, appIds));
    }

}
