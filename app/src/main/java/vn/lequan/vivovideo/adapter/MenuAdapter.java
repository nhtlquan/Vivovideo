package vn.lequan.vivovideo.adapter;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.smile.studio.libsmilestudio.recyclerviewer.OnItemTouchListener;
import com.smile.studio.libsmilestudio.utils.AndroidDeviceInfo;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.model.NavMenu.MenuItem;

import java.util.ArrayList;
import java.util.List;

import vn.lequan.vivovideo.Interface_NavMenu;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.menu.FragmentDrawer;

/**
 * Created by admin on 18/08/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MenuItem> lstData;
    private Point point;
    private int seleted = Integer.MIN_VALUE;
    private RequestManager manager;
    private int numberColmns = 0;
    private int size;
    private FragmentDrawer.FragmentDrawerListener fragmentDrawerListener;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private Interface_NavMenu interface_navMenu;
    private int p_parent, p_children;
    private MenuAdapter menuAdapter;

    public MenuAdapter(int p_parent, int p_children) {
        this.p_parent = p_parent;
        this.p_children = p_children;
    }

    public MenuAdapter(Context context, List<MenuItem> lstData, FragmentDrawer.FragmentDrawerListener drawerListener, DrawerLayout mDrawerLayout, View containerView) {
        this.context = context;
        this.lstData = lstData;
        this.mDrawerLayout = mDrawerLayout;
        this.containerView = containerView;
        this.fragmentDrawerListener = drawerListener;
        point = AndroidDeviceInfo.getScreenSize(context);
    }

    public void setList(Interface_NavMenu face_comment) {
        interface_navMenu = face_comment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.init(position, lstData.get(position));
    }

    public int getSeleted() {
        return seleted;
    }

    public void setSeleted(int seleted) {
        this.seleted = seleted;
    }

    public void clean() {
        this.lstData.clear();
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public void addAll(List<MenuItem> data) {
        lstData.clear();
        this.lstData.addAll(data);
        notifyDataSetChanged();
    }

    public void add(int vitri, MenuItem menuItem) {
        lstData.add(vitri, menuItem);
        notifyItemChanged(vitri);
    }

    public MenuItem getItemAtPosition(int position) {
        return lstData.get(position);
    }

    public void selectItemPosition(int position) {
        if (seleted != position) {
            notifyItemChanged(seleted);
            seleted = position;
            notifyItemChanged(seleted);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumb;
        private TextView title;
        private RecyclerView recyclerView;
        private MenuChildAdapter menuChildAdapter;

        public MyViewHolder(View view) {
            super(view);
            thumb = (ImageView) view.findViewById(R.id.thumb);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setNestedScrollingEnabled(false);
            title = (TextView) view.findViewById(R.id.title);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            menuChildAdapter = new MenuChildAdapter(context, new ArrayList<MenuItem>());
        }

        public void init(int position, final MenuItem item) {
            title.setText(item.getName());
            if (item.getChildren() != null) {
                if (item.getChildren().size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    menuChildAdapter.clean();
                    menuChildAdapter.addAll(item.getChildren());
                    recyclerView.setAdapter(menuChildAdapter);

                    recyclerView.addOnItemTouchListener(new OnItemTouchListener(context, recyclerView, new OnItemTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
//                            menuAdapter = new MenuAdapter(position, seleted);
                            interface_navMenu.clickNavMenu(item.getChildren().get(position), position, menuChildAdapter);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                    title.setTextSize(21);
                    title.setTextColor(context.getResources().getColor(R.color.text_disabled));
                } else {
                    menuChildAdapter.clean();
                    title.setTextSize(16);
                    title.setTextColor(context.getResources().getColor(R.color.white));
                    recyclerView.setVisibility(View.GONE);
                }
            }
            Typeface font_bold = Typeface.createFromAsset(
                    context.getAssets(),
                    "fonts/Roboto-Bold.ttf");
            Typeface font_light = Typeface.createFromAsset(
                    context.getAssets(),
                    "fonts/Roboto-Light.ttf");
            if (seleted == position) {
                title.setTypeface(font_bold);
                thumb.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                title.setTypeface(font_light);
                thumb.setBackgroundColor(context.getResources().getColor(R.color.background_menu));
            }
        }


    }
}