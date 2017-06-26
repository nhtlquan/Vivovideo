package vn.lequan.vivovideo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smile.studio.network.ver2.model.QuestionByID.Answer;

import java.util.List;

import vn.lequan.vivovideo.R;


public class AnswserAdapter extends BaseAdapter {

    private List<Answer> answer;
    private MyViewHolder myHolder;
    private int poAnInt;

    public AnswserAdapter(List<Answer> answer) {
        super();
        this.answer = answer;
    }

    @Override
    public int getCount() {
        return answer.size();
    }

    public void setPosition(int position) {
        poAnInt = position;
    }

    @Override
    public Object getItem(int position) {
        return answer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_anwser, parent, false);
            myHolder = new MyViewHolder(view);
            view.setTag(myHolder);
        } else {
            myHolder = (MyViewHolder) view.getTag();
        }
        myHolder.init(answer.get(position), position);
        return view;
    }

    class MyViewHolder {

        TextView title, view_line;


        public MyViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.tv_answer);
            view_line = (TextView) view.findViewById(R.id.view_line);
        }

        public void init(Answer item, int position) {
            title.setText(item.getAnswer());
            if (poAnInt == position) {
                view_line.setVisibility(View.GONE);
            } else {
                view_line.setVisibility(View.VISIBLE);
            }
        }

    }

}
