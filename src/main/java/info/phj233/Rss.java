package info.phj233;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import info.phj233.util.ContentFormat;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * @projectName: MineBBSRSS
 * @package: com.phj233
 * @className: info.phj233.MineBBSRSS. Rss
 * @author: phj233
 * @date: 2022/11/23 11:44
 * @version: 1.0
 */
public class Rss {
    String title,author,content, category,link;
    Date publishDate;
    SyndFeed feed;

    public Rss(String url) throws IOException, FeedException {
        feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        publishDate = feed.getEntries().get(0).getPublishedDate();
    }
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
        return new ContentFormat(this.title,this.author,this.content,this.category,this.publishDate,this.link).getContentsFormat();
    }

    public Boolean checkUpdate(Date time) {
        return publishDate.after(time);
    }
    public Date getPublishDate() {
        return publishDate;
    }
}
