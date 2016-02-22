package project.sample.com.applicationtradition.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import project.sample.com.applicationtradition.R;
import project.sample.com.applicationtradition.adapter.ItemListViewAdapter;
import project.sample.com.applicationtradition.gao.ProductCollectionGao;
import project.sample.com.applicationtradition.gao.ProductGao;

public class ListActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    ListView listView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ProductCollectionGao gao;
    private ItemListViewAdapter adapter;

    private static final String TAG = "ListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);
        ButterKnife.bind(this);

        if(savedInstanceState == null){
            intiToolbar();
            /*รับค่าที่ถูกส่งเข้ามา*/
            gao = Parcels.unwrap(getIntent().getExtras().getParcelable("ProductCollectionGao"));
            initInstance();
        }
    }

    private void initInstance() {

        // add Data to Listview
        adapter = new ItemListViewAdapter();
        listView.setAdapter(adapter);

        adapter.setGao(gao);
        adapter.notifyDataSetChanged();

        /*Event Click item list view
        * เมื่อกดไอเทม จะส่งค่าไปยัง DetailActivity
        * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductGao g = gao.getProducts().get(position);
                Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                intent.putExtra("ProductGao", Parcels.wrap(g));
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
            }
        });

    }

    private void intiToolbar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
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
