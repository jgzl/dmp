package cn.cleanarch.dmp.module.infra.mq.consumer.file;

import cn.cleanarch.dmp.framework.mq.core.pubsub.AbstractChannelMessageListener;
import cn.cleanarch.dmp.module.infra.mq.message.file.FileConfigRefreshMessage;
import cn.cleanarch.dmp.module.infra.service.file.FileConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link FileConfigRefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class FileConfigRefreshConsumer extends AbstractChannelMessageListener<FileConfigRefreshMessage> {

    @Resource
    private FileConfigService fileConfigService;

    @Override
    public void onMessage(FileConfigRefreshMessage message) {
        log.info("[onMessage][收到 FileConfig 刷新消息]");
        fileConfigService.initFileClients();
    }

}
