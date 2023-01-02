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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SyndEntry entry = feed.getEntries().get(0);
        publishDate = entry.getPublishedDate();
        title = entry.getTitle();
        link = entry.getLink();
    }
    public String getLastEntry() throws ParseException {
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

    public Boolean checkUpdate(Date time) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements reply = doc.getElementsByClass("message   message--post   js-post js-inlineModContainer  ");
        if (publishDate.after(time) && reply.size()==0) {
            String datetime = doc.getElementsByClass("u-dt").get(0).attr("data-date-string");
            String sdf = new SimpleDateFormat("yyyy/MM/dd").format(publishDate);
            return datetime.equals(sdf);
        }return false;
        }

    public Date getPublishDate() {
        return publishDate;
    }
}
