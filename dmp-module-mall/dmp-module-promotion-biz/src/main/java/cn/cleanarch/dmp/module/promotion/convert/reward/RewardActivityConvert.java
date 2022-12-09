package cn.cleanarch.dmp.module.promotion.convert.reward;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.module.promotion.controller.admin.reward.vo.RewardActivityCreateReqVO;
import cn.cleanarch.dmp.module.promotion.controller.admin.reward.vo.RewardActivityRespVO;
import cn.cleanarch.dmp.module.promotion.controller.admin.reward.vo.RewardActivityUpdateReqVO;
import cn.cleanarch.dmp.module.promotion.dal.dataobject.reward.RewardActivityDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 满减送活动 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface RewardActivityConvert {

    RewardActivityConvert INSTANCE = Mappers.getMapper(RewardActivityConvert.class);

    RewardActivityDO convert(RewardActivityCreateReqVO bean);

    RewardActivityDO convert(RewardActivityUpdateReqVO bean);

    RewardActivityRespVO convert(RewardActivityDO bean);

    PageResult<RewardActivityRespVO> convertPage(PageResult<RewardActivityDO> page);

}
