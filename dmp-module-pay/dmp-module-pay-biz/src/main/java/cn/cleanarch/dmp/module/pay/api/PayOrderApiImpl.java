package cn.cleanarch.dmp.module.pay.api;

import cn.cleanarch.dmp.module.pay.api.order.PayOrderApi;
import cn.cleanarch.dmp.module.pay.api.order.PayOrderInfoCreateReqDTO;
import org.springframework.stereotype.Service;

/**
 * TODO 注释
 */
@Service
public class PayOrderApiImpl implements PayOrderApi {

    @Override
    public Long createPayOrder(PayOrderInfoCreateReqDTO reqDTO) {
        return null;
    }

}
