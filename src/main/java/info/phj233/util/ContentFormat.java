package info.phj233.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @projectName: MineBBSRSS
 * @package: util
 * @className: ContentFormat
 * @author: phj233
 * @date: 2022/12/7 9:04
 * @version: 1.0
 */
public class ContentFormat {
    String title,author,contents, category,link;
    String publishDate;
    public ContentFormat(String title, String author, String contents, String category, Date publishDate, String link) throws ParseException {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.category = category;
        this.publishDate = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss").format(publishDate);
        this.link = link;
    }
    public String extractContents() {
        int i = Math.min(contents.length(), 60);
        return contents.substring(0, i);
    }
    public String getContentsFormat() {
        return """
                MineBBS有新内容啦!!!
                标题: %s
                作者: %s
                内容摘要: %s ...
                分类: %s
                发布时间: %s
                链接: %s
                """.formatted(title,author,extractContents(),category,publishDate,link);
    }
}
