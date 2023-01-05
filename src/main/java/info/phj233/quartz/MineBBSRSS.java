package info.phj233.quartz;

import com.rometools.rome.io.FeedException;
import info.phj233.Config;
import info.phj233.Rss;
import net.mamoe.mirai.Bot;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * @projectName: MineBBSRss
 * @package: info.phj233.quartz
 * @className: MineBBSRSS
 * @author: phj233
 * @date: 2023/1/5 19:11
 * @version: 1.0
 */
public class MineBBSRSS {
    public static Bot bot;
    public static Date flagDate;
    public static void start(Bot bot) throws FeedException, IOException, SchedulerException {
        MineBBSRSS.flagDate = new Rss(Config.INSTANCE.getUrl()).getPublishDate();
        MineBBSRSS.bot = bot;
        JobDetail jobDetail = JobBuilder.newJob(RssListenerJob.class)
                .withIdentity("RssJob", "JobGroup")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("RssJob", "JobGroup")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

    }
}
