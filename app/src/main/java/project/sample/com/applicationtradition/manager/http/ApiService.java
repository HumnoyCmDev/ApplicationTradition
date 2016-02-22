package project.sample.com.applicationtradition.manager.http;

import project.sample.com.applicationtradition.gao.ProductCollectionGao;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @POST("feedjson_new.php")
    Call<ProductCollectionGao> loadJsonObject();

    @FormUrlEncoded
    @POST("resultSearch.php")
    Call<ProductCollectionGao> search(@Field("txtSearch") String search);

}
