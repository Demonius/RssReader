package by.lykashenko.loadingxml;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Admin on 03.03.16.
 */
@Root(name = "rss")
@Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
public class XmlConstructure {
    public XmlConstructure(){}
    //
    @Attribute(name = "version")
    private String version;

    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
