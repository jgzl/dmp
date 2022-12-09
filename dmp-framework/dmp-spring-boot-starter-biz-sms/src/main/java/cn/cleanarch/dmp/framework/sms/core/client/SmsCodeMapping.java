package cn.cleanarch.dmp.framework.sms.core.client;

import cn.cleanarch.dmp.framework.common.exception.ErrorCode;
import cn.cleanarch.dmp.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 *
 * @author 芋道源码
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {
}
