package by.lykashenko.loadingxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Admin on 05.03.16.
 */
@Root(name = "image")
public class ImageChannel {
    @Element(name = "url")
    private String url;

    @Element(name = "title")
    private String title;

    @Element(name = "link", required = false)
    private String link_1;


}
