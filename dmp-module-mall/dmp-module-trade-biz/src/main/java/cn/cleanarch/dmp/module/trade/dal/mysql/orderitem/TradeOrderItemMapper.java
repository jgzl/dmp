package cn.cleanarch.dmp.module.trade.dal.mysql.orderitem;

import cn.cleanarch.dmp.framework.mybatis.core.mapper.BaseMapperX;
import cn.cleanarch.dmp.module.trade.dal.dataobject.order.TradeOrderItemDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeOrderItemMapper extends BaseMapperX<TradeOrderItemDO> {
}
