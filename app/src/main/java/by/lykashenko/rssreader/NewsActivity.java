package by.lykashenko.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.lykashenko.fragments.DialogNewsRead;
import by.lykashenko.interfaces.ImageDownloader;
import by.lykashenko.onlinernews.News;


public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fabUpdateNews;
    private CoordinatorLayout coordinatorLayout;
    private List<News> getAllNews;
    public static final String DIALOG_NEWS = "dialog_news";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String LOG_TAG = "list_news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Intent intent = getIntent();
        Integer state = Integer.parseInt(intent.getStringExtra("state"));
        Log.d(LOG_TAG, "state ="+Integer.toString(state));

        if (state == 1){
            Snackbar.make(coordinatorLayout, getResources().getText(R.string.loadfromdb_ok), Snackbar.LENGTH_LONG).show();
        }

        if (state == 2){
            Snackbar.make(coordinatorLayout, getResources().getText(R.string.internet_off), Snackbar.LENGTH_INDEFINITE).setAction(getResources().getText(R.string.close), new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                }
            }).show();
        }



        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitleEnabled(false);
//        collapsingToolbar.setTitle(getResources().getString(R.string.app_name));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageToolbarUpload();



        fabUpdateNews = (FloatingActionButton)findViewById(R.id.buttonFloatUpdate);
        fabUpdateNews.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list_news);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getAllNews = GetAllNews();
        Log.d(LOG_TAG, getAllNews.get(0).title.toString());
        mAdapter = new MyAdapter(getAllNews);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void ImageToolbarUpload() {
        ImageView imageBacground = (ImageView) findViewById(R.id.backdrop);
        ImageDownloader imageDownloader1 = new ImageDownloader();
        imageDownloader1.setMode(ImageDownloader.Mode.CORRECT);
        imageDownloader1.download("http://content.onliner.by/img/rss/logo.png",imageBacground);
    }

    private List<News> GetAllNews() {
        return new Select().from(News.class).execute();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PersonViewHolder> {
        private List<News> m_list_news;

        public MyAdapter(List<News> getAllNews) {
            m_list_news=getAllNews;
        }

        public class PersonViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public TextView pubDate;
            public CardView cv;
            public ImageView imagePreviev;
            public PersonViewHolder (final View item_view){
                super(item_view);
                title = (TextView)item_view.findViewById(R.id.textTtile);
                pubDate = (TextView)item_view.findViewById((R.id.textPubDate));
                cv = (CardView)item_view.findViewById(R.id.card_view_news);
                imagePreviev = (ImageView) item_view.findViewById(R.id.imageViewPreview);
                item_view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                       Integer position_click = getAdapterPosition();
                        DialogNewsRead dialogNewsRead = new DialogNewsRead();
                        dialogNewsRead.setURLImageNews(getAllNews.get(position_click).urlImage.toString());
                        dialogNewsRead.setNewsText(getAllNews.get(position_click).description.toString());
                        dialogNewsRead.show(getFragmentManager(), DIALOG_NEWS);
                        Snackbar.make(coordinatorLayout, "Нажата поз = "+Integer.toString(position_click), Snackbar.LENGTH_SHORT).show();

                    }
                });
            }
        }

        @Override
        public MyAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_news_holder,parent, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PersonViewHolder personHolder, int position) {
            personHolder.title.setText(m_list_news.get(position).title.toString());

            String pubDate = m_list_news.get(position).pubDate.toString();
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
            Date date = null;
            try {
                date = format.parse(pubDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatNew = new SimpleDateFormat("dd MMM yyyy hh:mm:ss", Locale.getDefault());
            personHolder.pubDate.setText(formatNew.format(date));
            String URL = m_list_news.get(position).urlImage.toString();
            ImageDownloader imageDownloader = new ImageDownloader();

            imageDownloader.setMode(ImageDownloader.Mode.CORRECT);

            imageDownloader.download(URL, personHolder.imagePreviev);

        }

        @Override
        public int getItemCount() {
            return m_list_news.size();
        }


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonFloatUpdate: {

                SplashActivity.UpdateNews();
                List<News> getAllNewsUpdate = GetAllNews();
                mRecyclerView.setAdapter(new MyAdapter(getAllNewsUpdate));
                mRecyclerView.invalidate();
                ImageToolbarUpload();

                break;
            }
        }

    }
}