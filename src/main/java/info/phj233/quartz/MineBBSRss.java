package info.phj233.quartz;

import info.phj233.Config;
import info.phj233.Rss;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.MiraiLogger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Date;

import static java.lang.Thread.sleep;


/**
 * MineBBSRss的定时任务
 * @author phj233
 * @since  2023/1/5 19:11
 * @version 1.0
 */
public class MineBBSRss {
    public static Bot bot;
    public static Date flagDate;
    public static Scheduler scheduler;

    public static void start(Bot bot) throws SchedulerException, InterruptedException {
        MiraiLogger logger = bot.getLogger();
        try {
            MineBBSRss.flagDate = new Rss(Config.INSTANCE.getUrl()).getPublishDate();
            MineBBSRss.bot = bot;
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
        }catch (Exception e){
            logger.error(e.getCause());
            scheduler.shutdown();
            sleep(5000 );
            start(bot);

        }
    }

    public static void stop() throws SchedulerException {
        scheduler.shutdown();
    }
}
