package by.lykashenko.loadingxml;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Admin on 03.03.16.
 */
@Root(name = "rss",strict = false)
public class XmlConstructure {
    public XmlConstructure(){}

    @Path("channel")
    @ElementList(name = "item", inline = true)
    private List<ItemNews> item;

    public List<ItemNews> getItem() {
        return item;
    }
}
