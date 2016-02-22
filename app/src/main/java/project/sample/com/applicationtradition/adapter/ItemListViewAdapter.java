package project.sample.com.applicationtradition.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import project.sample.com.applicationtradition.R;
import project.sample.com.applicationtradition.gao.ProductCollectionGao;
import project.sample.com.applicationtradition.gao.ProductGao;

/**
 * Created by humnoy on 3/2/59.
 */
public class ItemListViewAdapter extends BaseAdapter{

    private static final String TAG = "ItemListViewAdapter";

    private ProductCollectionGao gao;

    public void setGao(ProductCollectionGao gao) {
        this.gao = gao;
    }

    @Override
    public int getCount() {
        if (gao == null) return 0;
        if (gao.getProducts() == null) return 0;
        return gao.getProducts().size();
    }

    @Override
    public ProductGao getItem(int position) {
        return gao.getProducts().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_view,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        /// coding
        ProductGao entity = getItem(position);
        holder.tvTitle.setText(entity.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvDate.setText(dateFormat.format(entity.getDate()));
        Picasso.with(parent.getContext())
                .load("http://www.poll.informalsoft.com/travel/picture/"+entity.getPicture())
                .into(holder.ivPhoto);

        return convertView;
    }

    public class ViewHolder {
        @Bind(R.id.item_list_view_icon)
        ImageView ivPhoto;
        @Bind(R.id.item_list_view_text)
        TextView tvTitle;

        @Bind(R.id.item_list_view_text_date)
        TextView tvDate;
        public ViewHolder(View v) {
            ButterKnife.bind(this,v);
        }
    }
}
