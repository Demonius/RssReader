package by.lykashenko.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.lykashenko.interfaces.ImageDownloader;
import by.lykashenko.onlinernews.News;
import by.lykashenko.rssreader.NewsActivity;
import by.lykashenko.rssreader.R;

/**
 * Created by Дмитрий on 16.03.2016.
 */
public class ListNewsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<News> getAllNews;
    private Integer position_click;

    private View vFragment1;


    public interface OnItemSelectedListener {
        public void onItemSelected(int position);
    }
    OnItemSelectedListener pressedItemSelectedListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
           pressedItemSelectedListener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnItemSelectedListener");
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        vFragment1 = inflater.inflate(R.layout.fragment_list_news, null);
        mRecyclerView = (RecyclerView) vFragment1.findViewById(R.id.recycler_list_news);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        getAllNews = NewsActivity.GetAllNews();

        mAdapter = new MyAdapter(getAllNews);
        mRecyclerView.setAdapter(mAdapter);
        return vFragment1;
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

                        Log.d(NewsActivity.LOG_TAG, "state ="+Integer.toString(position_click));
                        pressedItemSelectedListener.onItemSelected(position_click);


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
}
