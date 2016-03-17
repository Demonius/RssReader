package by.lykashenko.rssreader;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
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

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.lykashenko.fragments.DialogNewsRead;
import by.lykashenko.fragments.ListNewsFragment;
import by.lykashenko.fragments.ReadNewsFragment;
import by.lykashenko.interfaces.ImageDownloader;
import by.lykashenko.onlinernews.News;



public class NewsActivity extends AppCompatActivity implements View.OnClickListener, ListNewsFragment.OnItemSelectedListener{


    private FloatingActionButton fabUpdateNews;
    private CoordinatorLayout coordinatorLayout;
    private List<News> getAllNews;
    public static final String DIALOG_NEWS = "dialog_news";
    public static Integer state;
    public static String path;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String LOG_TAG = "list_news";
    private Integer position_click;
    private FragmentTransaction fTrans;
    private static final String FRAGMENT_LIST = "fragment_list_news";
    private static final String FRAGMENT_READ = "fragment_read_news";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        path = getApplicationContext().getFilesDir().getAbsolutePath();

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setContentView(R.layout.activity_news);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

            Intent intent = getIntent();
            state = Integer.parseInt(intent.getStringExtra("state"));

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

            if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {

                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                collapsingToolbar.setTitleEnabled(false);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                ImageToolbarUpload();

                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list_news);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                getAllNews = GetAllNews();

                mAdapter = new MyAdapter(getAllNews);
                mRecyclerView.setAdapter(mAdapter);


                if (savedInstanceState != null) {
                    if (savedInstanceState.getInt("item") > 0) {
                        LoadDialogNews(savedInstanceState.getInt("item") - 1);
                    }
                }
            }
            if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                ImageToolbarUpload();

                if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST) != null){
                    FragmentTransaction fTrans1 = getSupportFragmentManager().beginTransaction();
                    fTrans1.remove(getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST));
                    fTrans1.commit();
                }

                ListNewsFragment listNewsFragment = new ListNewsFragment();
                fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.add(R.id.frameListNews, listNewsFragment, FRAGMENT_LIST);
//            fTrans.addToBackStack(null);
                fTrans.commit();
                Log.d(LOG_TAG, "Запущен главный фрагмент‚ TAG" + listNewsFragment.getTag());
                if (savedInstanceState != null) {
                    if (savedInstanceState.getInt("item") > 0) {
                        onItemSelected(savedInstanceState.getInt("item") - 1);
                    }
                }
            }
        }else{
            setContentView(R.layout.activity_news_table);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

            Intent intent = getIntent();
            Integer state = Integer.parseInt(intent.getStringExtra("state"));

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

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ImageToolbarUpload();

            if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST) != null){
                FragmentTransaction fTrans1 = getSupportFragmentManager().beginTransaction();
                fTrans1.remove(getSupportFragmentManager().findFragmentByTag(FRAGMENT_LIST));
                fTrans1.commit();
            }


            ListNewsFragment listNewsFragment = new ListNewsFragment();
            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.add(R.id.frameListNews, listNewsFragment, FRAGMENT_LIST);
            fTrans.commit();
            Log.d(LOG_TAG, "Запущен главный фрагмент‚ TAG" + listNewsFragment.getTag());

            if (savedInstanceState != null) {
                if (savedInstanceState.getInt("item") > 0) {
                    onItemSelected(savedInstanceState.getInt("item") - 1);
                }
            }
        }


        fabUpdateNews = (FloatingActionButton) findViewById(R.id.buttonFloatUpdate);
        fabUpdateNews.setOnClickListener(this);

    }

    @Override
    public void onItemSelected(int position) {
        position_click = position+1;
        Log.d(LOG_TAG, "position = " +Integer.toString(position));
        ReadNewsFragment readNewsFragment = new ReadNewsFragment();
        Bundle arg = new Bundle();
        arg.putInt("position_item", position);

        readNewsFragment.setArguments(arg);
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frameReadNews, readNewsFragment, FRAGMENT_READ);
//            fTrans.addToBackStack(null);
        fTrans.commit();
        Log.d(LOG_TAG, "Запущен главный фрагмент‚ TAG" + readNewsFragment.getTag());

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
                        position_click = getAdapterPosition();
                        LoadDialogNews(position_click);
                        position_click = position_click + 1;
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
            imageDownloader.setContextImage(getApplication().getFilesDir(), getAllNews.get(position).getId().toString());


        }

        @Override
        public int getItemCount() {
            return m_list_news.size();
        }


    }

    public static List<News> GetAllNews() {
        return new Select().from(News.class).execute();
    }

    private void ImageToolbarUpload() {
        ImageView imageBacground = (ImageView) findViewById(R.id.backdrop);
        imageBacground.setImageDrawable(getResources().getDrawable(R.drawable.onliner));
//        ImageDownloader imageDownloader1 = new ImageDownloader();
//        imageDownloader1.setMode(ImageDownloader.Mode.CORRECT);
//        imageDownloader1.download("http://content.onliner.by/img/rss/logo.png",imageBacground);
    }



    private void LoadDialogNews(Integer position) {
        DialogNewsRead dialogNewsRead = new DialogNewsRead();
        dialogNewsRead.setURLImageNews(getAllNews.get(position).urlImage.toString());
        dialogNewsRead.setNewsText(getAllNews.get(position).description.toString());
        dialogNewsRead.show(getFragmentManager(), DIALOG_NEWS);

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (position_click == null) {
            position_click = 0;
            Log.d(LOG_TAG, "state 0");
        }

        Log.d(LOG_TAG, "position save ="+Integer.toString(position_click));
         outState.putInt("item", position_click);



    }

    public ArrayList<File> listFilesWithSubFolders(File dir) {
        ArrayList<File> files = new ArrayList<File>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory())
                files.addAll(listFilesWithSubFolders(file));
            else
                files.add(file);
        }
        return files;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonFloatUpdate: {

                if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < 600) {
                    if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                        SplashActivity.UpdateNews(getApplicationContext().getFilesDir(),listFilesWithSubFolders(getApplicationContext().getFilesDir()));
                        List<News> getAllNewsUpdate = GetAllNews();
                        mRecyclerView.setAdapter(new MyAdapter(getAllNewsUpdate));
                        mRecyclerView.invalidate();
                    }
                    if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {

                    }else{

                    }
                }




                break;
            }
        }

    }

}