package by.lykashenko.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
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

import by.lykashenko.interfaces.ImageDownloader;
import by.lykashenko.rssreader.R;

/**
 * Created by Admin on 14.03.16.
 */
public class DialogNewsRead extends DialogFragment implements View.OnClickListener {


    private String URLImageNews;
    private String newsText;

    private Button buttonCloseNews;
    private ImageView imageNews;
    private TextView textViewNews;


    public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View viewDialog = inflater.inflate(R.layout.dialog_news_read, null);
        buttonCloseNews = (Button) viewDialog.findViewById(R.id.button_close);
        textViewNews = (TextView) viewDialog.findViewById(R.id.textViewNews);
        imageNews = (ImageView) viewDialog.findViewById(R.id.imageViewNews);

        setRetainInstance(true);



            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.setMode(ImageDownloader.Mode.CORRECT);
            imageDownloader.download(URLImageNews, imageNews);

            Document doc = Jsoup.parse(newsText);

            Element link = doc.select("p").get(1);
            String textNews = link.html();

            Element link1 = doc.select("p").last();
            String textNext = link1.html();

            String text = new StringBuilder().append(textNews).append(" ").append(textNext).toString();
            textViewNews.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
            textViewNews.setMovementMethod(LinkMovementMethod.getInstance());


        buttonCloseNews.setOnClickListener(this);


        return viewDialog;

     }
        @Override
        public void onClick (View view){
            switch (view.getId()){
                case R.id.button_close:{
                    dismiss();
                    break;
                }
            }

        }

    public void setURLImageNews(String URLImageNews) {
        this.URLImageNews = URLImageNews;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }


}
