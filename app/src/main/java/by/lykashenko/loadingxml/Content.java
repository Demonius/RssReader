package by.lykashenko.loadingxml;

import org.simpleframework.xml.Attribute;

/**
 * Created by Admin on 06.03.16.
 */

public class Content {
    @Attribute
    private String url;

    @Attribute(required = false)
    private String type;

    @Attribute(required = false)
    private String medium;

    @Attribute
    private String height;

    @Attribute
    private String width;

    @Attribute(required = false)
    private String fileSize;

    public String getUrl() {
        return url;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }
}
