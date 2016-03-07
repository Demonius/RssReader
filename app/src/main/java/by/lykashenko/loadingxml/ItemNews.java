package by.lykashenko.loadingxml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Admin on 06.03.16.
 */
@Root(name = "item")
public class ItemNews {

    @Element(name = "title",required = false)
    private String titlenews;

    public String getTitlenews() {
        return titlenews;
    }
    @Element(name = "link",required = false)
    private String linknews;

    public String getLinknews() {
        return linknews;
    }

    @ElementList(name = "description",inline = true, required = false)
    private List<DescriptionNews> descriptionnews;

    public List<DescriptionNews> getDescriptionnews() {
        return descriptionnews;
    }

    @Element(name = "author",required = false)
    private Author author;

    public Author getAuthor() {
        return author;
    }

    //    @ElementList(name = "author")
//    private List<Author> authornews;
//
//    public List<Author> getAuthornews() {
//        return authornews;
//    }
//
    private static class Author {
        @Element(name = "name")
        private String nameauthor;

        public String getNameauthor() {
            return nameauthor;
        }

        @Element(name = "uri")
        private String uriauthor;

        public String getUriauthor() {
            return uriauthor;
        }
    }

    @Element(name = "name",required = false)
    private String name;

    @Element(name = "uri",required = false)
    private String uri;

    @Attribute(name = "domain",required = false)
    private String domain;

    @Element(name = "category",required = false)
    private String categorynews;

    @Attribute(name = "url",required = false)
    private String enclosureurl;

    @Attribute(name = "type",required = false)
    private String enclosuretype;


    @Attribute(name = "length",required = false)
    private String enclosurelength;

    @Attribute(name = "isPermaLink",required = false)
    private String ispermalink;

    @Element(name = "guid", required = false)
    private String guidnews;

    public String getGuidnews() {
        return guidnews;
    }

    @Element(name = "pubDate", required = false)
    private String pubdatenews;

    public String getPubdatenews() {
        return pubdatenews;
    }

    @Attribute(name = "medium",required = false)
    private String medium;

    @Attribute(name = "height",required = false)
    private String height;

    @Attribute(name = "width",required = false)
    private String width;

    @Attribute(name = "fileSize",required = false)
    private String filesize;

    @ElementList(name = "contetn",required = false)
    private List<Content> contentimagenews;

    public List<Content> getContentimagenews() {
        return contentimagenews;
    }
}
