package info.phj233.quartz;

import info.phj233.Config;
import info.phj233.Rss;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static info.phj233.quartz.MineBBSRSS.bot;
import static info.phj233.quartz.MineBBSRSS.flagDate;

/**
 * @projectName: MineBBSRss
 * @package: info.phj233.quartz
 * @className: RssListenerJob
 * @author: phj233
 * @date: 2023/1/5 18:49
 * @version: 1.0
 */
public class RssListenerJob implements Job {
    /**
     * 通过静态变量导入 bot 和 flagDate
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Rss rss1 = new Rss(Config.INSTANCE.getUrl());
            if (rss1.checkUpdate(flagDate)) {
                flagDate = rss1.getPublishDate();
                //发送信息
                for (Long group : Config.INSTANCE.getGroups()) {
                    Group sendGroup = Bot.getInstance(bot.getId()).getGroup(group);
                    if (sendGroup != null) {
                        sendGroup.sendMessage(rss1.getLastEntry());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
