package info.phj233.util;

/**
 * 内容格式化类，用于格式化内容，以便发送到QQ群中
 * @author phj233
 * @since 2022/12/7 9:04
 * @version 1.0
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
                -------------------------
                ৹标题: %s
                ৹作者: %s
                ৹内容摘要: %s ...
                ৹版块: %s
                ৹链接-> %s
                """.formatted(title,author,extractContents(),category,link);
    }
}
