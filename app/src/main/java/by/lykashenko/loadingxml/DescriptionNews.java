package by.lykashenko.loadingxml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by Admin on 06.03.16.
 */
public class DescriptionNews {
    @Attribute
    private String src;

    public String getSrc() {
        return src;
    }

    @Attribute(required = false)
    private String width;

    @Attribute(required = false)
    private String height;

    @Attribute(required = false)
    private String alt;

    @Attribute(required = false)
    private String aligh;

    @Attribute(required = false)
    private String hspace;

    @Text
    private String textdescription;

    public String getTextdescription() {
        return textdescription;
    }

    @Attribute(required = false)
    private String clear;
}
