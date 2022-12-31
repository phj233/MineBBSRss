package info.phj233.listener;

import com.rometools.rome.io.FeedException;
import info.phj233.Config;
import info.phj233.Rss;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: mirai-console-plugin-template
 * @package: info.phj233.mirai.plugin.listener
 * @className: RssListener
 * @author: phj233
 * @date: 2022/12/14 16:55
 * @version: 1.0
 */
public class RssListener {
    public static final RssListener INSTANCE = new RssListener();
    public void start(Bot bot) throws FeedException, IOException {
        Rss rss = new Rss(Config.INSTANCE.getUrl());
        Date[] flagDate = {rss.getPublishDate()};
        Runnable runnable = () -> {
            try {
                Rss rss1 = new Rss(Config.INSTANCE.getUrl());
                if (rss1.checkUpdate(flagDate[0])) {
                    flagDate[0] = rss1.getPublishDate();
                    //发送信息
                    for (Long group : Config.INSTANCE.getGroups()) {
                        Group sendGroup = Bot.getInstance(bot.getId()).getGroup(group);
                        if (sendGroup != null) {
                            sendGroup.sendMessage(rss1.getLastEntry());
                        }
                    }
                }
            } catch (IOException | FeedException e) {
                throw new RuntimeException(e);
            }
        };
        ScheduledExecutorService  service = Executors.newScheduledThreadPool(5);
        service.scheduleAtFixedRate(runnable, 0, 15, TimeUnit.SECONDS);
    }
}
