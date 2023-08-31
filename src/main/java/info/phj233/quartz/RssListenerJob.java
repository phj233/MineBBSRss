package info.phj233.quartz;

import com.rometools.rome.io.FeedException;
import info.phj233.Config;
import info.phj233.Rss;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static info.phj233.quartz.MineBBSRss.bot;
import static info.phj233.quartz.MineBBSRss.flagDate;

/**
 * RssListenerJob的定时任务
 * @author phj233
 * @since  2023/1/5 18:49
 * @version 1.0
 */
public class RssListenerJob implements Job {
    /**
     * 通过静态变量导入 bot 和 flagDate
     */
    private static final Logger logger = LoggerFactory.getLogger(RssListenerJob.class);
    @Override
    public void execute(JobExecutionContext context) {
        try {
            Rss newRss = new Rss(Config.INSTANCE.getUrl());
            if (newRss.checkUpdate(flagDate)) {
                flagDate = newRss.getPublishDate();
                //发送信息
                for (Long group : Config.INSTANCE.getGroups()) {
                    Group sendGroup = Bot.getInstance(bot.getId()).getGroup(group);
                    if (sendGroup != null) {
                        sendGroup.sendMessage(newRss.getLastEntry());
                    }
                }
            }
        } catch (IOException | FeedException e) {
            logger.info(e.getMessage());
        }
    }
}
