package info.phj233;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import info.phj233.util.ContentFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MineBBS 的 RSS 订阅类
 * @author phj233
 * @since  2022/11/23 11:44
 * @version 1.1
 */
public class Rss {
    String title,author,content,category,link;
    Date publishDate;
    SyndFeed feed;

    public Rss(String url) throws IOException, FeedException {
        feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        SyndEntry entry = feed.getEntries().get(0);
        publishDate = entry.getPublishedDate();
        title = entry.getTitle();
        link = entry.getLink();
    }
    /**
     * 获取最新的帖子
     */
    public String getLastEntry() {
        SyndEntry entry = feed.getEntries().get(0);
        this.title = entry.getTitle();
        this.link = entry.getLink();
        this.publishDate = entry.getPublishedDate();
        this.author = entry.getAuthor();
        this.category = entry.getCategories().get(0).getName();
        String contentValue = entry.getContents().get(0).getValue();
        Elements bbWrapper = Jsoup.parse(contentValue).getElementsByClass("bbWrapper");
        this.content = bbWrapper.text();
        return new ContentFormat(this.title,this.author,this.content,this.category,this.link).getContentsFormat();
    }

    /**
     * 检查是否有更新
     * @param time 上次检查时间
     */
    public Boolean checkUpdate(Date time){
        try {
            if (publishDate.after(time)) {
                Document doc = Jsoup.connect(link).get();
                //过滤有回复的帖子，以免多次发送
                Elements reply = doc.getElementsByClass("message   message--post   js-post js-inlineModContainer  ");
                Elements threadStarterPost = doc.getElementsByClass("message    message-threadStarterPost message--post   js-post js-inlineModContainer  ");
                Elements staffPost = doc.getElementsByClass("message   message-staffPost  message--post   js-post js-inlineModContainer  ");
                if (reply.size()==0 && threadStarterPost.size()==0 && staffPost.size()==0) {
                    //判断是否是最新主题
                    String datetime = doc.getElementsByClass("u-dt").get(0).attr("data-date-string");
                    String sdf = new SimpleDateFormat("yyyy/MM/dd").format(publishDate);
                    return datetime.equals(sdf);
                }
            }
            return false;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Date getPublishDate() {
        return publishDate;
    }
}
