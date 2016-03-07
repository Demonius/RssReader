package by.lykashenko.loadingxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Admin on 07.03.16.
 */

@Root(name = "channel")
public class Channel {


    @Element(name = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    @Element(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    @Element(name = "language")
    private String language;

    @ElementList(entry = "image",inline = true)
    private List<ImageChannel> imageChannel;

    @Element(name = "pubDate")
    private String pubDate;

    public String getPubDate() {
        return pubDate;
    }

    @Element(name = "lastBuildDate")
    private String lastBuildDate;

    @Element(name = "ttl")
    private String ttl;

    @ElementList(name = "link",inline = true)
    private List<Link> link1;

    public List<Link> getLink1() {
        return link1;
    }

    @ElementListUnion(
            @ElementList(name= "item", type = ItemNews.class, ))
    private List<ItemNews> item;

    public List<ItemNews> getItem() {
        return item;
    }


}
