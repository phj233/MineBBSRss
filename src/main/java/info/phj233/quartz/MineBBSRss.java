package info.phj233.quartz;

import com.rometools.rome.io.FeedException;
import info.phj233.Config;
import info.phj233.Rss;
import net.mamoe.mirai.Bot;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

import static java.lang.Thread.sleep;


/**
 * @projectName: MineBBSRss
 * @package: info.phj233.quartz
 * @className: MineBBSRss
 * @author: phj233
 * @date: 2023/1/5 19:11
 * @version: 1.0
 */
public class MineBBSRss {
    public static Bot bot;
    public static Date flagDate;
    public static Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(MineBBSRss.class);

    public static void start(Bot bot) throws FeedException, SchedulerException, InterruptedException, IOException {
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
        }catch (IOException e){
            logger.info(String.valueOf(e));
            sleep(5000 );
            start(bot);

        }
    }

    public static void stop() throws SchedulerException {
        scheduler.shutdown();
    }
}
