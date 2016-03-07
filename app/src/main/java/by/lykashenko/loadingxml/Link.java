package by.lykashenko.loadingxml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by Admin on 05.03.16.
 */

public class Link {

    @Attribute(name = "href",required = false)
    private String href;

        @Attribute(name = "rel",required = false)
        private String rel;


        @Attribute(name = "type",required = false)
        private String type;


       @Text(required = false)
       private String link_channel;
}
