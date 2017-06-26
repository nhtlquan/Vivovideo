package vn.lequan.vivovideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.smile.studio.network.ver2.model.QuestionByID.Question;

import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.R;

public class DuDoanAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<Question> lstData;
    private IAction actionClickTitle;
    private int seleted = -1;
    private int size;

    public DuDoanAdapter(Context context, ArrayList<Question> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    public interface IAction {
        public void perform(int position, Question data);
    }

    public void clear() {
        lstData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(position, lstData.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_dudoan, parent, false);
        return new MyViewHolder(view);
    }

    private class MyViewHolder extends ViewHolder {

        private TextView title;
        private TextView btn_Question;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            btn_Question = (TextView) view.findViewById(R.id.btn_dudoan);
        }

        public void init(final int position, final Question item) {
            title.setText(item.getQuestion());
            if (item.getBet() != null) {
                // Đã chơi
                btn_Question.setBackgroundResource(R.drawable.background_button_dudoan_white);
                btn_Question.setText(R.string.cancel);
            } else {
                // Chưa chơi
                btn_Question.setBackgroundResource(R.drawable.background_button_dudoan_blue);
                btn_Question.setText(R.string.title_game);
            }
            if (seleted == position) {
                title.setSelected(true);
            } else {
                title.setSelected(false);
            }
            title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    seleted = position;
                    title.setSelected(true);
                }
            });
            btn_Question.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (actionClickTitle != null) {
                        seleted = -1;
                        title.setSelected(false);
                        actionClickTitle.perform(position, item);
                    }
                }
            });
        }

    }

    public void addAll(List<Question> lstData) {
        this.lstData.addAll(lstData);
        size = getItemCount();
        notifyItemRangeInserted(size, getItemCount());
    }

    public void updateStatus(int position) {
//        int status = lstData.get(position).getIsPlay() == 0 ? 1 : 0;
//        this.lstData.get(position).setIsPlay(status);
//        notifyDataSetChanged();
    }

    public Question getItemAtPosition(int position) {
        return this.lstData.get(position);
    }

}
