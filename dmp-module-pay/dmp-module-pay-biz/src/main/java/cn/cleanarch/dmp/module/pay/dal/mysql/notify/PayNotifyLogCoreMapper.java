package cn.cleanarch.dmp.module.pay.dal.mysql.notify;

import cn.cleanarch.dmp.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import cn.cleanarch.dmp.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayNotifyLogCoreMapper extends BaseMapperX<PayNotifyLogDO> {
}
