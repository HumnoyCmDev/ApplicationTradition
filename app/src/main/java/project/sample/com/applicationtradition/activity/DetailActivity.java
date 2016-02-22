package project.sample.com.applicationtradition.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import project.sample.com.applicationtradition.R;
import project.sample.com.applicationtradition.gao.ProductGao;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "DetailActivity";

    /*findViewById Map Map ID Java*/
    @Bind(R.id.ivPhoto)
    ImageView photo;

    @Bind(R.id.tvTitle)
    TextView title;

    @Bind(R.id.tvDetail)
    TextView detail;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ProductGao gao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            intiToolbar();
            initInstance();
        }
    }

    private void intiToolbar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initInstance() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*รับข้อมูล ถูกส่งมาจาก MainActivity*/
        gao = Parcels.unwrap(getIntent().getExtras().getParcelable("ProductGao"));

        /*กำหนดข้อมูล ใน TextView */
        title.setText(gao.getTitle());
        detail.setText(gao.getDetail());
        Picasso.with(this)//โหลดรูปจาก เซฟเวอร์ แสดงใน ImageView
                .load("http://www.poll.informalsoft.com/travel/picture/"+gao.getPicture())
                .into(photo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Zoom map ไปยังตำแหน่ง
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(gao.getGpsLa()),Double.parseDouble(gao.getGpsLong())),15));


        // ปักหมุด ใน map
        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(Double.parseDouble(gao.getGpsLa()),Double.parseDouble(gao.getGpsLong())));
        options.title(gao.getTitle());
        googleMap.addMarker(options);

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
