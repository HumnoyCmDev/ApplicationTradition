package project.sample.com.applicationtradition.manager;

import android.os.AsyncTask;
import android.util.Log;



public class LoadJson extends AsyncTask<Void,Void,String>{
    private static final String TAG = "LoadJson";
    private AsyneResuilt resuilt;

    public LoadJson(AsyneResuilt resuilt) {
        this.resuilt = resuilt;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        Gson gson = new Gson();
//        ModelJson modelJson = gson.fromJson(s,ModelJson.class);
//        resuilt.onSucceed(modelJson);


    }

    @Override
    protected String doInBackground(Void... params) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url("http://www.poll.informalsoft.com/travel/feedjson.php/").build();
//
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            if (response.isSuccessful()) {
//               return response.body().string();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
