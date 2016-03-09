package by.lykashenko.interfaces;

import by.lykashenko.loadingxml.XmlConstructure;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceLoadingXml  {

//    @GET("rss/all.rss")
    @GET("feed")
    Call<XmlConstructure> getXml();
//    void getXml(Callback<ModelXml> cb);
}
