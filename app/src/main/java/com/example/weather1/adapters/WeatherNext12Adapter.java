package com.example.weather1.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather1.R;
import com.example.weather1.databinding.Item12hoursForecastBinding;
import com.example.weather1.models.Forecast12Hours;
import com.example.weather1.utils.Common;

/**
 * Created by Yassine Abou on 6/11/2021.
 */
public class WeatherNext12Adapter extends RecyclerView.Adapter<WeatherNext12Adapter.ViewHolder> {

    Context mContext;
    Forecast12Hours mHours;

    public WeatherNext12Adapter(Context context, Forecast12Hours hours) {
        mContext = context;
        mHours = hours;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(Item12hoursForecastBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherNext12Adapter.ViewHolder holder, int position) {
        TypedArray images = mContext.getResources().obtainTypedArray(R.array.images);
        holder.mBinding.imgForecast.setImageResource(images.getResourceId(mHours.getIconCode().get(position), -1));
        images.recycle();

        holder.mBinding.txtTime.setText(Common.convertUnixToDate(mHours.getExpirationTimeUtc().get(position)));

        holder.mBinding.txtDescrp.setText(mHours.getUvDescription().get(position));

        holder.mBinding.temp.setText(mContext.getString(R.string.tempValue,
                String.valueOf(mHours.getTemperature().get(position))));
    }

    @Override
    public int getItemCount() {
        return mHours.temperature.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        private final Item12hoursForecastBinding mBinding;

        public ViewHolder(@NonNull Item12hoursForecastBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

}
