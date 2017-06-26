package vn.lequan.vivovideo.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.smile.studio.libsmilestudio.recyclerviewer.OnItemTouchListener;
import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_Chanel;
import com.smile.studio.network.ver2.face.Face_ScheduleByDay;
import com.smile.studio.network.ver2.model.ChanelItem.LiveChannelSchedule;
import com.smile.studio.network.ver2.model.ListChanel.ChanelItem;
import com.smile.studio.network.ver2.model.ListChanel.ListChanel;
import com.smile.studio.network.ver2.model.ScheduleByDay.ScheduleByDay;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.lequan.vivovideo.R;
import vn.lequan.vivovideo.adapter.TimeShiftChannelAdaper;
import vn.lequan.vivovideo.adapter.TimeShiftVODAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 18/08/2016.
 */
public class TimeShiftFragment extends Fragment {

    private View view;
    private TextView tv_timenow;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TextView tv_error;
    private String strDate;
    private Spinner spinner;
    private ChanelItem liveChannel;
    private RecyclerView recyclerView;
    private TimeShiftChannelAdaper channelAdaper;
    private TimeShiftVODAdapter vodAdaper;
    private Calendar calendar = Calendar.getInstance();
    private ProgressBar marker_progress;

    public TimeShiftFragment() {
    }

    public static TimeShiftFragment newInstance() {
        Bundle args = new Bundle();
        TimeShiftFragment fragment = new TimeShiftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        //        GlobalApp.getInstance().fragment= TimeShiftFragment.newInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_timeshift, container, false);
            marker_progress = (ProgressBar) view.findViewById(R.id.marker_progress);
            marker_progress.setVisibility(View.VISIBLE);
            tv_timenow = (TextView) view.findViewById(R.id.tv_timenow);
            tv_error = (TextView) view.findViewById(R.id.tv_error);
            strDate = dateFormat.format(new Date(System.currentTimeMillis()));
            tv_timenow.setText(strDate);
            tv_timenow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(new OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(year, monthOfYear, dayOfMonth);
                            tv_timenow.setText(strDate = dateFormat.format(calendar.getTime()));
                            ChanelItem liveChannel = (ChanelItem) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
                            if (liveChannel != null)
                                getEpgsByChannel(liveChannel.getId(), strDate);
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                    dpd.show(getActivity().getFragmentManager(), DatePickerDialog.class.getSimpleName());
                }
            });
            spinner = (Spinner) view.findViewById(R.id.spinner);
            channelAdaper = new TimeShiftChannelAdaper(getActivity(), new ArrayList<ChanelItem>());
            spinner.setAdapter(channelAdaper);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    channelAdaper.setPosition(position);
                    liveChannel = (ChanelItem) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
                    getEpgsByChannel(liveChannel.getId(), strDate);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            vodAdaper = new TimeShiftVODAdapter(getActivity(), new ArrayList<LiveChannelSchedule>());
            recyclerView.setAdapter(vodAdaper);
            recyclerView.setHasFixedSize(true);
            recyclerView.addOnItemTouchListener(new OnItemTouchListener(getActivity(), recyclerView, new OnItemTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                }

                @Override
                public void onLongClick(View view, int position) {
                    TextView title = (TextView) view.findViewById(R.id.tv_title);
                    title.setSelected(true);
                    TextView tv_description = (TextView) view.findViewById(R.id.tv_description);
                    tv_description.setSelected(true);
                }
            }));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.parseColor("#d2d2d2")).build());
        }
        getListChanel();
        return view;
    }

    private void getListChanel() {
        Call<ListChanel> call = GlobalApp.getInstance().retrofit_ver2.create(Face_Chanel.class).getListChanel(1, 100, GlobalApp.getInstance().Platform, GlobalApp.getInstance().acessToken);
        call.enqueue(new Callback<ListChanel>() {
            @Override
            public void onResponse(Call<ListChanel> call, Response<ListChanel> response) {
                try {
                    ListChanel value = response.body();
                    channelAdaper.addAll(value.getData());
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListChanel> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");
//        getEpgs(strDate);
    }

    private void getEpgsByChannel(int channelID, String date) {
        Call<ScheduleByDay> call = GlobalApp.getInstance().retrofit_ver2.create(Face_ScheduleByDay.class).getEpg(date, channelID, GlobalApp.getInstance().token, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<ScheduleByDay>() {

            @Override
            public void onResponse(Call<ScheduleByDay> call, Response<ScheduleByDay> response) {
                try {
                    vodAdaper.clean();
                    ScheduleByDay value = response.body();
                    Debug.e(value.getMessage());
                    tv_error.setVisibility(View.GONE);
                    vodAdaper.addAll(value.getData());
                    marker_progress.setVisibility(View.GONE);
                    forTimeCurrent(value.getData());
                    if (value.getData().size() == 0) {
                        tv_error.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Debug.e("Lỗi: " + e.getMessage());
                    tv_error.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ScheduleByDay> call, Throwable t) {
                Debug.e("Lỗi: " + t.getMessage());
            }
        });
    }

    private void forTimeCurrent(List<LiveChannelSchedule> data) {
        for (int i = 0; i < data.size(); i++) {
            initTimeCurrent(data.get(i), i);
        }
    }

    private void initTimeCurrent(LiveChannelSchedule data, int i) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        String limit = data.getEndAt();
        long limitBus = 0;
        try {
            limitBus = sdfDate.parse(limit).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String start = data.getStartAt();
        long startBus = 0;
        try {
            startBus = sdfDate.parse(start).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        long now1 = 0;
        try {
            now1 = sdfDate.parse(sdfDate.format(now)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startBus < now1 & now1 < limitBus) {
            if (i - 3 > 0) {
                recyclerView.scrollToPosition(i - 3);
            } else {
                recyclerView.scrollToPosition(i);
            }
            vodAdaper.setPoi(i);
        }
    }

}
