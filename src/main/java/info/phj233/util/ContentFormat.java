package info.phj233.util;

/**
 * @projectName: MineBBSRss
 * @package: util
 * @className: ContentFormat
 * @author: phj233
 * @date: 2022/12/7 9:04
 * @version: 1.0
 */
public class ContentFormat {
    String title,author,contents, category,link;
    public ContentFormat(String title, String author, String contents, String category,  String link) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.category = category;
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
                版块: %s
                链接: %s""".formatted(title,author,extractContents(),category,link);
    }
}
