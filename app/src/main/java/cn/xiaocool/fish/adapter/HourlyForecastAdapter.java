package cn.xiaocool.fish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.xiaocool.fish.R;
import cn.xiaocool.fish.model.Weather;

public class HourlyForecastAdapter extends BaseAdapter {
    private List<Weather.HourlyForecastEntity> mData;

    public HourlyForecastAdapter(List<Weather.HourlyForecastEntity> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        HourlyForecastAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_weather_view_holder_hourly_forecast, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTime.setText(timeFormat(mData.get(position).date));
        holder.tvTemp.setText(context.getString(R.string.temp, mData.get(position).tmp));
        holder.tvHum.setText(context.getString(R.string.hourly_forecast_hum, mData.get(position).hum));
        holder.tvWind.setText(windFormat(context, mData.get(position).wind.sc));
        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.tv_time)
        public TextView tvTime;
        @Bind(R.id.tv_temp)
        public TextView tvTemp;
        @Bind(R.id.tv_hum)
        public TextView tvHum;
        @Bind(R.id.tv_wind)
        public TextView tvWind;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    private String timeFormat(String time) {
        SimpleDateFormat fromSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat toSdf = new SimpleDateFormat("HH:mm");
        try {
            return toSdf.format(fromSdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return toSdf.format(new Date());
    }

    private String windFormat(Context context, String wind) {
        if (wind.contains("风")) {
            return wind;
        } else {
            return context.getString(R.string.hourly_forecast_wind, wind);
        }
    }
}
