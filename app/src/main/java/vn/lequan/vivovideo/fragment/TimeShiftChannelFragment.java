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
import android.widget.TextView;

import com.smile.studio.libsmilestudio.utils.Debug;
import com.smile.studio.network.ver2.face.Face_ScheduleByDay;
import com.smile.studio.network.ver2.model.ChanelItem.ItemChanel;
import com.smile.studio.network.ver2.model.ChanelItem.LiveChannelSchedule;
import com.smile.studio.network.ver2.model.ScheduleByDay.ScheduleByDay;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
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
import vn.lequan.vivovideo.adapter.TimeShiftVODAdapter;
import vn.lequan.vivovideo.model.GlobalApp;

/**
 * Created by admin on 20/08/2016.
 */
public class TimeShiftChannelFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager layout;
    private TimeShiftVODAdapter adapter;
    private static String Itemchanel = "itemChanel";
    private TextView tv_error, tv_timenow;
    private String strDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar = Calendar.getInstance();

    public TimeShiftChannelFragment() {
    }

    public static TimeShiftChannelFragment newInstance(ItemChanel itemChanel) {
        Bundle args = new Bundle();
        args.putParcelable(Itemchanel, itemChanel);
        TimeShiftChannelFragment fragment = new TimeShiftChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TimeShiftChannelFragment newInstance() {
        Bundle args = new Bundle();
        TimeShiftChannelFragment fragment = new TimeShiftChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_timeshift, container, false);
            view.findViewById(R.id.spinner).setVisibility(View.GONE);
            tv_error = (TextView) view.findViewById(R.id.tv_error);
            tv_timenow = (TextView) view.findViewById(R.id.tv_timenow);
            tv_timenow.setTextColor(getResources().getColor(R.color.white));
            tv_error = (TextView) view.findViewById(R.id.tv_error);
            strDate = dateFormat.format(new Date(System.currentTimeMillis()));
            tv_timenow.setText(strDate);
            final ItemChanel itemChanel = getArguments().getParcelable(Itemchanel);
            tv_timenow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(year, monthOfYear, dayOfMonth);
                            tv_timenow.setText(strDate = dateFormat.format(calendar.getTime()));
                            assert itemChanel != null;
                            getEpgsByChannel(itemChanel.getData().getLiveChannel().getId(), strDate);
                        }
                    }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                    dpd.show(getActivity().getFragmentManager(), DatePickerDialog.class.getSimpleName());
                }
            });
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            layout = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layout);
            adapter = new TimeShiftVODAdapter(getActivity(), new ArrayList<LiveChannelSchedule>());
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.padding1).color(Color.parseColor("#78999999")).build());

            getEpgsByChannel(itemChanel, dateFormat.format(new Date(System.currentTimeMillis())));
        }
        return view;
    }

    private void getEpgsByChannel(ItemChanel itemChanel, String date) {
        Debug.e(String.valueOf(itemChanel.getData().getLiveChannel().getLiveChannelSchedules().size()));
        if (itemChanel.getData().getLiveChannel().getLiveChannelSchedules() == null || itemChanel.getData().getLiveChannel().getLiveChannelSchedules().size() == 0) {
            tv_error.setVisibility(View.VISIBLE);
        } else {
            forTimeCurrent(itemChanel.getData().getLiveChannel().getLiveChannelSchedules());
            adapter.addAll(itemChanel.getData().getLiveChannel().getLiveChannelSchedules());
            tv_error.setVisibility(View.GONE);
        }
    }

    private void forTimeCurrent(List<LiveChannelSchedule> data) {
        for (int i = 0; i < data.size(); i++) {
            initTimeCurrent(data.get(i), i);
        }
    }
    private void getEpgsByChannel(int channelID, String date) {
        Call<ScheduleByDay> call = GlobalApp.getInstance().retrofit_ver2.create(Face_ScheduleByDay.class).getEpg(date, channelID, GlobalApp.getInstance().token, GlobalApp.getInstance().Platform);
        call.enqueue(new Callback<ScheduleByDay>() {

            @Override
            public void onResponse(Call<ScheduleByDay> call, Response<ScheduleByDay> response) {
                try {
                    adapter.clean();
                    ScheduleByDay value = response.body();
                    Debug.e(value.getMessage());
                    tv_error.setVisibility(View.GONE);
                    adapter.addAll(value.getData());
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

    private void initTimeCurrent(LiveChannelSchedule data, int i) {

        ItemChanel itemChanel = getArguments().getParcelable(Itemchanel);
        assert itemChanel != null;
        if (itemChanel.getData().getLiveProgram() != null)
        if (itemChanel.getData().getLiveProgram().getId().equals(data.getId())) {
            Debug.e("getLiveProgram");
            if (i - 3 > 0) {
                recyclerView.scrollToPosition(i - 3);
            } else {
                recyclerView.scrollToPosition(i);
            }
            adapter.setPoi(i);
        }
    }


}
