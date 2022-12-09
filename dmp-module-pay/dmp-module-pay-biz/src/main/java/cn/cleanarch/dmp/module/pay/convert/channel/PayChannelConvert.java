package cn.cleanarch.dmp.module.pay.convert.channel;

import java.util.*;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;

import cn.cleanarch.dmp.module.pay.controller.admin.merchant.vo.channel.PayChannelCreateReqVO;
import cn.cleanarch.dmp.module.pay.controller.admin.merchant.vo.channel.PayChannelExcelVO;
import cn.cleanarch.dmp.module.pay.controller.admin.merchant.vo.channel.PayChannelRespVO;
import cn.cleanarch.dmp.module.pay.controller.admin.merchant.vo.channel.PayChannelUpdateReqVO;
import cn.cleanarch.dmp.module.pay.dal.dataobject.merchant.PayChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayChannelConvert {

    PayChannelConvert INSTANCE = Mappers.getMapper(PayChannelConvert.class);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelCreateReqVO bean);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelUpdateReqVO bean);

    @Mapping(target = "config",expression = "java(cn.cleanarch.dmp.framework.common.util.json.JsonUtils.toJsonString(bean.getConfig()))")
    PayChannelRespVO convert(PayChannelDO bean);

    List<PayChannelRespVO> convertList(List<PayChannelDO> list);

    PageResult<PayChannelRespVO> convertPage(PageResult<PayChannelDO> page);

    List<PayChannelExcelVO> convertList02(List<PayChannelDO> list);



}
