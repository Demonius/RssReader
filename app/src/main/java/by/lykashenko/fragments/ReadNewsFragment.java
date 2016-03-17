package by.lykashenko.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

import by.lykashenko.interfaces.ImageDownloader;
import by.lykashenko.onlinernews.News;
import by.lykashenko.rssreader.NewsActivity;
import by.lykashenko.rssreader.R;

/**
 * Created by Дмитрий on 16.03.2016.
 */
public class ReadNewsFragment extends Fragment {


    private View vFragment1;
    private Button buttonCloseNews;
    private ImageView imageNews;
    private TextView textViewNews;
    private Integer position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       position = getArguments().getInt("position_item");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vFragment1 = inflater.inflate(R.layout.fragment_news_read, null);

        textViewNews = (TextView)vFragment1.findViewById(R.id.textViewNews);
        imageNews = (ImageView)vFragment1.findViewById(R.id.imageViewNews);

        List<News> list_news = NewsActivity.GetAllNews();

        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.setMode(ImageDownloader.Mode.CORRECT);
        imageDownloader.download(list_news.get(position).urlImage.toString(), imageNews);

        Document doc = Jsoup.parse(list_news.get(position).description.toString());

        Element link = doc.select("p").get(1);
        String textNews = link.html();

        Element link1 = doc.select("p").last();
        String textNext = link1.html();

        String text = new StringBuilder().append(textNews).append(" ").append(textNext).toString();
        textViewNews.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        textViewNews.setMovementMethod(LinkMovementMethod.getInstance());

        return vFragment1;
    }
}
