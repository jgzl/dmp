package cn.cleanarch.dmp.module.system.mq.consumer.mail;

import cn.cleanarch.dmp.framework.mq.core.stream.AbstractStreamMessageListener;
import cn.cleanarch.dmp.module.system.mq.message.mail.MailSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// TODO 芋艿：这个暂未实现
@Component
@Slf4j
public class MailSendConsumer extends AbstractStreamMessageListener<MailSendMessage> {

    @Override
    public void onMessage(MailSendMessage message) {
        log.info("[onMessage][消息内容({})]", message);
    }

}
