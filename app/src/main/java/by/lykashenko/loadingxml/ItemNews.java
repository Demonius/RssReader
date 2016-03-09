package by.lykashenko.loadingxml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by Admin on 08.03.16.
 */
@Root(name = "item",strict = false)
public class ItemNews {
    @Element(name = "title",data = true)
    private String title;

    @Element
    private String pubDate;

    @Element(name = "category",data = true)
    private String category;

    @Element(name = "description",data = true)
    private String description;

    @Path("thumbnail")
    @Attribute(name = "url")
    private String urlImage;

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
