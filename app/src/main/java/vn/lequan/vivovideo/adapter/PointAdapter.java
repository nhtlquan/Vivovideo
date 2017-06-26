package vn.lequan.vivovideo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.text.DecimalFormat;

import vn.lequan.vivovideo.R;


public class PointAdapter extends BaseAdapter {

    private int[] point;
    private MyViewHolder myHolder;
    private int poAnInt;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public PointAdapter(int[] point) {
        super();
        this.point = point;
    }
    public void setPosition(int position) {
        poAnInt = position;
    }

    @Override
    public int getCount() {
        return point.length;
    }

    @Override
    public Object getItem(int position) {
        return point[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_point_betting, parent, false);
            myHolder = new MyViewHolder(view);
            view.setTag(myHolder);
        } else {
            myHolder = (MyViewHolder) view.getTag();
        }
        myHolder.init(point[position],position);
        return view;
    }

    class MyViewHolder {
        TextView title,view_line;

        public MyViewHolder(View view) {
            view_line = (TextView) view.findViewById(R.id.view_line);
            title = (TextView) view.findViewById(R.id.title);
        }

        public void init(int item, int position) {
            if (poAnInt == position) {
                view_line.setVisibility(View.GONE);
            } else {
                view_line.setVisibility(View.VISIBLE);
            }
            title.setText(decimalFormat.format(item));
        }

    }

}
