package by.lykashenko.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import by.lykashenko.interfaces.InterfaceLoadingXml;
import by.lykashenko.loadingxml.ItemNews;
import by.lykashenko.loadingxml.XmlConstructure;
import by.lykashenko.onlinernews.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class SplashActivity extends Activity {

    public static TextView textLoading;
   public static ProgressBar progressBarLoading;
    private static Integer status;
    private  String url = "http://www.onliner.by/";
    public static String LOADING_XML;
    public static String LOADING_DATA;
    public static String LOG_TAG = "log_rss";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("Onliner.db").setModelClasses(News.class).create();
        ActiveAndroid.initialize(dbConfiguration);

        LOADING_XML = getResources().getString(R.string.loading_xml);
        LOADING_DATA = getResources().getString(R.string.loading_bd);
        textLoading = (TextView) findViewById(R.id.textLoading);
        progressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // сеть доступна
            Log.d(LOG_TAG, "сеть доступна");}

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(SimpleXmlConverterFactory.create()).build();
        Log.d(LOG_TAG, "retrofit start");
        InterfaceLoadingXml interfaceLoadingXml = retrofit.create(InterfaceLoadingXml.class);

        Log.d(LOG_TAG, "retrofit interface start");
        Call<XmlConstructure> call = interfaceLoadingXml.getXml();



        call.enqueue(new Callback<XmlConstructure>() {
            @Override
            public void onResponse(Call<XmlConstructure> call, Response<XmlConstructure> response) {
                Log.d(LOG_TAG, "Call onResponse");
                if (response!=null){
                    Log.d(LOG_TAG,"есть данные");
                try{
//                    String title = response.body().getItem().get(0).getTitle().toString();
//                    Log.d("log_rss","title = "+title);
//
//                    String description = response.body().getItem().get(0).getDescription().toString();
//                    Log.d("log_rss","description = "+description);

                    List<ItemNews> news = response.body().getItem();
                    Integer size = news.size();
                    Log.d(LOG_TAG, "size = "+Integer.toString(size));
                    Integer i = 0;
                    ActiveAndroid.beginTransaction();
                    try {
                        while (i < size) {

                            String title = news.get(i).getTitle().toString();
                            String pubDate = news.get(i).getPubDate().toString();
                            String category = news.get(i).getCategory().toString();
                            String description = news.get(i).getDescription().toString();
                            String urlImage = news.get(i).getUrlImage().toString();
                            Log.d(LOG_TAG, "Id = "+Integer.toString(i)+",Title = "+title);
                            News newssave = new News(title,pubDate,category,description,urlImage);
                            newssave.save();

                            i = i + 1;

                        }ActiveAndroid.setTransactionSuccessful();
                    }
                    finally {
                        ActiveAndroid.endTransaction();
                        Log.d("log_rss","сохранено в базу");

                    }

//                    String name = news.get(0).getPubDate().toString();
//                    Log.d("log_rss","pubDate = "+name);

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d(LOG_TAG, e.toString());
                }
                }else{
                    Log.d(LOG_TAG,"нет данных");
                }
            }

            @Override
            public void onFailure(Call<XmlConstructure> call, Throwable t) {
                Log.d(LOG_TAG, "Call onFailure " +t.toString());
            }
        });





        AsyncTaskLoadingXML asyncTaskLoadingXML = new AsyncTaskLoadingXML();
        asyncTaskLoadingXML.execute();




    }


    public class AsyncTaskLoadingXML extends AsyncTask<Void, Integer, Integer> {
        private Integer i = 0;

        protected void onPreExecute() {
            super.onPreExecute();
            textLoading.setText(LOADING_XML);
            progressBarLoading.setProgress(i);
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBarLoading.setProgress(values[0]);
            if (values[0]==60){
                textLoading.setText(LOADING_DATA);
            }
        }

        @Override
        protected Integer doInBackground(Void... params) {
            while (i<=100)  {
                i = i + 5;
                publishProgress(i);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return 1;
        }
        protected void onPostExecute(Integer result){
            super.onPostExecute(result);
            Intent runNewsActivity = new Intent(SplashActivity.this, NewsActivity.class);
            startActivity(runNewsActivity);
            finish();
        }
    }
}
