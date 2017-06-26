package vn.lequan.vivovideo.menu;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smile.studio.libsmilestudio.recyclerviewer.OnItemTouchListener;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.model.NavMenu.MenuItem;

import java.util.ArrayList;

import vn.lequan.vivovideo.Interface_NavMenu;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.activity.MainActivity;
import vn.lequan.vivovideo.activity.SplashActivity;
import vn.lequan.vivovideo.adapter.MenuAdapter;
import vn.lequan.vivovideo.adapter.MenuChildAdapter;
import vn.lequan.vivovideo.model.GlobalApp;


public class FragmentDrawer extends Fragment {

    private RecyclerView recyclerView = null;
    private ActionBarDrawerToggle mDrawerToggle = null;
    private DrawerLayout mDrawerLayout;
    private MenuAdapter adapter = null;
    private View containerView = null;
    private MenuChildAdapter menu_ChildAdapter;
    private FragmentDrawerListener drawerListener = null;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.removeAllViews();
        if (GlobalApp.getInstance().ListNavMenu != null)
            adapter.addAll(GlobalApp.getInstance().ListNavMenu);
        adapter.selectItemPosition(0);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new MenuAdapter(getActivity(), new ArrayList<MenuItem>(), drawerListener, mDrawerLayout, containerView);
        adapter.setList(new Interface_NavMenu() {
            @Override
            public void clickNavMenu(MenuItem parentId, int positon, MenuChildAdapter menuChildAdapter) {
                drawerListener.onDrawerItemSelected(null, parentId.getActionClick().getType(), parentId);
                if (menu_ChildAdapter != null) {
                    menu_ChildAdapter.selectItemPosition(-1);
                }
                menu_ChildAdapter = menuChildAdapter;
                adapter.selectItemPosition(-1);
                menuChildAdapter.selectItemPosition(positon);
                mDrawerLayout.closeDrawers();
            }
        });
        recyclerView.setAdapter(adapter);
        if (GlobalApp.getInstance().ListNavMenu != null){
            Debug.e("NavMenu Not Null");
            adapter.addAll(GlobalApp.getInstance().ListNavMenu);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new OnItemTouchListener(getActivity(), recyclerView, new OnItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (GlobalApp.getInstance().ListNavMenu.get(position).getChildren() == null) {
                    String id = GlobalApp.getInstance().ListNavMenu.get(position).getActionClick().getType();
                    adapter.selectItemPosition(position);
                    drawerListener.onDrawerItemSelected(view, id, GlobalApp.getInstance().ListNavMenu.get(position));
                    if (menu_ChildAdapter != null) {
                        menu_ChildAdapter.selectItemPosition(-1);
                    }
                    mDrawerLayout.closeDrawers();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    public boolean checkNavMenu() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }


    public void closeNavMenu() {
        mDrawerLayout.closeDrawer(containerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().supportInvalidateOptionsMenu();
                if (GlobalApp.getInstance().draggablePanel.isMaximized())
                    GlobalApp.getInstance().draggablePanel.minimize();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 1);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, String id, MenuItem menuItem);
    }
}