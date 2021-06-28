
package com.example.weather1.utils;

import android.location.Location;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yassine Abou on 5/25/2021.
 */
public class Common {

    public static final String OPENWEATHERMAP_ID = "5c15883bc2aa8181a1d35d6e2099ae8c";
    public static final String IBM_ID = "1bbf9714b9ac4babbf9714b9acebabca";
    public static Location currentLocation = null;

    public static String convertUnixToDate(double dt) {
        Date date = new Date((long) (dt*1000L));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        return sdf.format(date);
    }

    public static String convertUnixToHour(double dt) {
        Date date = new Date((long) (dt*1000L));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public static String convertDateToHour(Long date) {
        Format formatter = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String s = formatter.format(date);
        return s;
    }

    


}
