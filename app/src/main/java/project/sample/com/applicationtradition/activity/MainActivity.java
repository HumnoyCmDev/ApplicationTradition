package project.sample.com.applicationtradition.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.sample.com.applicationtradition.R;
import project.sample.com.applicationtradition.gao.ProductCollectionGao;
import project.sample.com.applicationtradition.gao.ProductGao;
import project.sample.com.applicationtradition.manager.Contextor;
import project.sample.com.applicationtradition.manager.HttpManager;
import project.sample.com.applicationtradition.manager.http.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Bind(R.id.etSearch)
    EditText editTextSearch;
    @Bind(R.id.calendar_view)
    CalendarPickerView calendar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ProductCollectionGao gao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        intiToolbar();
        initDate();
        loadData();

    }

    private void initDate() {
        // ปฏิทิน
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        calendar.init(new Date(), nextYear.getTime(), new Locale("th", "TH"))
                .inMode(CalendarPickerView.SelectionMode.SINGLE);

        /*Listener Event ตอนกด(กดวันที่เป็นสี highlight) */
        calendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                List<ProductGao> productGaos = gao.getProducts();
                if (productGaos != null) {
                    for (ProductGao g : productGaos) {
                        /*ทุกการกด จะหา วันที่เป็นเทศกาล หากใช่ จะไปหา DetailActivity*/
                        if (date.equals(g.getDate())) {
//                            Toast.makeText(MainActivity.this, g.getTitle() + "\n" + g.getDetail(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("ProductGao", Parcels.wrap(g));
                            startActivity(intent);
                            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        }
                    }
                }


                return true;
            }
        });
    }

    private void loadData() {
        /*load ข้อมูลจาก เซฟเวอ*/
        ApiService apiService = HttpManager.getInstance().getService();
        apiService.loadJsonObject().enqueue(new Callback<ProductCollectionGao>() {
            @Override
            public void onResponse(Call<ProductCollectionGao> call, Response<ProductCollectionGao> response) {

                if (response.isSuccess()) {
                    // เก็บข้อมูลไว้ กับ ProductManager
                    // สั่ง Adapter รีข้อมูลใน ListView ใหม่
                    gao = response.body();
                    List<Date> list = new ArrayList<>();
                    Date date = new Date();
                    for (ProductGao pg : response.body().getProducts()) {
                        Date d = pg.getDate();
                        if (!d.before(date)) {
                            list.add(d);
                        }
                    }
                    calendar.highlightDates(list);
                } else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext()
                                , response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ProductCollectionGao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext()
                        , t.toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: load all", t);
            }
        });
    }

    private void intiToolbar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btnSearch)
    public void onSearch() {

        if (editTextSearch.getText().toString().trim().equals("")) {
            intentActivityListView();
        } else {
            searchData();
        }

    }

    private void intentActivityListView() {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("ProductCollectionGao", Parcels.wrap(gao));
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    private void searchData() {
        /*ค้นหาข้อมูลจากเซฟเวอร์*/
        ApiService service = HttpManager.getInstance().getService();
        Call<ProductCollectionGao> call = service.search(editTextSearch.getText().toString().trim());
        call.enqueue(new Callback<ProductCollectionGao>() {
            @Override
            public void onResponse(Call<ProductCollectionGao> call, Response<ProductCollectionGao> response) {
                if (response.isSuccess()) {
                    ProductCollectionGao gao = response.body();
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("ProductCollectionGao", Parcels.wrap(gao));
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);


                } else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext()
                                , "error body::" + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductCollectionGao> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext()
                        , "Fail::" + t.toString(), Toast.LENGTH_LONG).show();

                Log.e(TAG, "onFailure: ", t);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
