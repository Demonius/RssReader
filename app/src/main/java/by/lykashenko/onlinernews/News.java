package by.lykashenko.onlinernews;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by Admin on 08.03.16.
 */
@Table(name = "News",id = "_id")
public class News extends Model{

    @Column(name = "title")
    public String title;

    @Column(name = "pubDate")
    public String pubDate;

    @Column(name = "category")
    public String category;

    @Column(name = "description")
    public String description;

    @Column(name = "urlImage")
    public String urlImage;

    public News() {
        super();
    }
    public News (String title,
                 String pubDate,
                 String category,
                 String description,
                 String urlImage){
        this.title = title;
        this.pubDate= pubDate;
        this.category = category;
        this.description = description;
        this.urlImage = urlImage;
    }

}
