package com.example.weather1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather1.R;
import com.example.weather1.databinding.Item5daysForecastBinding;
import com.example.weather1.models.WeatherForecastResult;
import com.example.weather1.utils.Common;
import com.squareup.picasso.Picasso;

/**
 * Created by Yassine Abou on 6/6/2021.
 */
public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context mContext;
    WeatherForecastResult mResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult result) {
        mContext = context;
        mResult = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(Item5daysForecastBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load("https://openweathermap.org/img/w/" +
                mResult.list.get(position).weather.get(0).getIcon() +
                ".png").into(holder.mBinding.imgWeather);

        holder.mBinding.txtDate.setText(Common.convertUnixToDate(mResult.
                list.get(position).dt));

        holder.mBinding.txtDescription.setText(mResult.list.get(position).weather.get(0).getDescription());

        holder.mBinding.temperature.setText(mContext.getString(R.string.tempValue,
                String.valueOf(mResult.list.get(position).main.getTemp())));

    }

    @Override
    public int getItemCount() {
        return mResult.list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final Item5daysForecastBinding mBinding;

        public MyViewHolder(@NonNull Item5daysForecastBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
