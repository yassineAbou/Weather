package com.example.weather1.models;

import java.util.Date;

/**
 * Created by Yassine Abou on 6/17/2021.
 */
public class CurrentWeather {

    public Object cloudCeiling;
    public String cloudCoverPhrase;
    public String dayOfWeek;
    public String dayOrNight;
    public int expirationTimeUtc;
    public int iconCode;
    public int iconCodeExtend;
    public Object obsQualifierCode;
    public Object obsQualifierSeverity;
    public double precip1Hour;
    public double precip6Hour;
    public double precip24Hour;
    public double pressureAltimeter;
    public double pressureChange;
    public double pressureMeanSeaLevel;
    public int pressureTendencyCode;
    public String pressureTendencyTrend;
    public int relativeHumidity;
    public double snow1Hour;
    public double snow6Hour;
    public double snow24Hour;
    public Date sunriseTimeLocal;
    public int sunriseTimeUtc;
    public Date sunsetTimeLocal;
    public int sunsetTimeUtc;
    public int temperature;
    public int temperatureChange24Hour;
    public int temperatureDewPoint;
    public int temperatureFeelsLike;
    public int temperatureHeatIndex;
    public int temperatureMax24Hour;
    public int temperatureMaxSince7Am;
    public int temperatureMin24Hour;
    public int temperatureWindChill;
    public String uvDescription;
    public int uvIndex;
    public Date validTimeLocal;
    public int validTimeUtc;
    public double visibility;
    public int windDirection;
    public String windDirectionCardinal;
    public Object windGust;
    public int windSpeed;
    public String wxPhraseLong;
    public String wxPhraseMedium;
    public String wxPhraseShort;

    //-------------
    // Getters
    //--------------

    public Object getCloudCeiling() { return cloudCeiling; }
    public String getCloudCoverPhrase() { return cloudCoverPhrase; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getDayOrNight() { return dayOrNight; }
    public int getExpirationTimeUtc() { return expirationTimeUtc; }
    public int getIconCode() { return iconCode; }
    public int getIconCodeExtend() { return iconCodeExtend; }
    public Object getObsQualifierCode() { return obsQualifierCode; }
    public Object getObsQualifierSeverity() { return obsQualifierSeverity; }
    public double getPrecip1Hour() { return precip1Hour; }
    public double getPrecip6Hour() { return precip6Hour; }
    public double getPrecip24Hour() { return precip24Hour; }
    public double getPressureAltimeter() { return pressureAltimeter; }
    public double getPressureChange() { return pressureChange; }
    public double getPressureMeanSeaLevel() { return pressureMeanSeaLevel; }
    public int getPressureTendencyCode() { return pressureTendencyCode; }
    public String getPressureTendencyTrend() { return pressureTendencyTrend; }
    public int getRelativeHumidity() { return relativeHumidity; }
    public double getSnow1Hour() { return snow1Hour; }
    public double getSnow6Hour() { return snow6Hour;}
    public double getSnow24Hour() { return snow24Hour; }
    public Date getSunriseTimeLocal() { return sunriseTimeLocal; }
    public int getSunriseTimeUtc() { return sunriseTimeUtc; }
    public Date getSunsetTimeLocal() { return sunsetTimeLocal; }
    public int getSunsetTimeUtc() { return sunsetTimeUtc; }
    public int getTemperature() { return temperature; }
    public int getTemperatureChange24Hour() { return temperatureChange24Hour; }
    public int getTemperatureDewPoint() { return temperatureDewPoint; }
    public int getTemperatureFeelsLike() { return temperatureFeelsLike; }
    public int getTemperatureHeatIndex() { return temperatureHeatIndex; }
    public int getTemperatureMax24Hour() { return temperatureMax24Hour; }
    public int getTemperatureMaxSince7Am() { return temperatureMaxSince7Am; }
    public int getTemperatureMin24Hour() { return temperatureMin24Hour; }
    public int getTemperatureWindChill() { return temperatureWindChill; }
    public String getUvDescription() { return uvDescription; }
    public int getUvIndex() { return uvIndex; }
    public Date getValidTimeLocal() { return validTimeLocal; }
    public int getValidTimeUtc() { return validTimeUtc; }
    public double getVisibility() { return visibility; }
    public int getWindDirection() { return windDirection; }
    public String getWindDirectionCardinal() { return windDirectionCardinal; }
    public Object getWindGust() { return windGust; }
    public int getWindSpeed() { return windSpeed; }
    public String getWxPhraseLong() { return wxPhraseLong; }
    public String getWxPhraseMedium() { return wxPhraseMedium; }
    public String getWxPhraseShort() { return wxPhraseShort; }

    //----------------
    // Setters
    //---------------

    public void setCloudCeiling(Object cloudCeiling) { this.cloudCeiling = cloudCeiling; }
    public void setCloudCoverPhrase(String cloudCoverPhrase) { this.cloudCoverPhrase = cloudCoverPhrase; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setDayOrNight(String dayOrNight) { this.dayOrNight = dayOrNight; }
    public void setExpirationTimeUtc(int expirationTimeUtc) { this.expirationTimeUtc = expirationTimeUtc; }
    public void setIconCode(int iconCode) { this.iconCode = iconCode; }
    public void setIconCodeExtend(int iconCodeExtend) { this.iconCodeExtend = iconCodeExtend; }
    public void setObsQualifierCode(Object obsQualifierCode) { this.obsQualifierCode = obsQualifierCode; }
    public void setObsQualifierSeverity(Object obsQualifierSeverity) { this.obsQualifierSeverity = obsQualifierSeverity; }
    public void setPrecip1Hour(double precip1Hour) { this.precip1Hour = precip1Hour; }
    public void setPrecip6Hour(double precip6Hour) { this.precip6Hour = precip6Hour; }
    public void setPrecip24Hour(double precip24Hour) { this.precip24Hour = precip24Hour; }
    public void setPressureAltimeter(double pressureAltimeter) { this.pressureAltimeter = pressureAltimeter; }
    public void setPressureChange(double pressureChange) { this.pressureChange = pressureChange; }
    public void setPressureMeanSeaLevel(double pressureMeanSeaLevel) { this.pressureMeanSeaLevel = pressureMeanSeaLevel; }
    public void setPressureTendencyCode(int pressureTendencyCode) { this.pressureTendencyCode = pressureTendencyCode; }
    public void setPressureTendencyTrend(String pressureTendencyTrend) { this.pressureTendencyTrend = pressureTendencyTrend; }
    public void setRelativeHumidity(int relativeHumidity) { this.relativeHumidity = relativeHumidity; }
    public void setSnow1Hour(double snow1Hour) { this.snow1Hour = snow1Hour; }
    public void setSnow6Hour(double snow6Hour) { this.snow6Hour = snow6Hour; }
    public void setSnow24Hour(double snow24Hour) { this.snow24Hour = snow24Hour; }
    public void setSunriseTimeLocal(Date sunriseTimeLocal) { this.sunriseTimeLocal = sunriseTimeLocal; }
    public void setSunriseTimeUtc(int sunriseTimeUtc) { this.sunriseTimeUtc = sunriseTimeUtc; }
    public void setSunsetTimeLocal(Date sunsetTimeLocal) { this.sunsetTimeLocal = sunsetTimeLocal; }
    public void setSunsetTimeUtc(int sunsetTimeUtc) { this.sunsetTimeUtc = sunsetTimeUtc; }
    public void setTemperature(int temperature) { this.temperature = temperature; }
    public void setTemperatureChange24Hour(int temperatureChange24Hour) { this.temperatureChange24Hour = temperatureChange24Hour; }
    public void setTemperatureDewPoint(int temperatureDewPoint) { this.temperatureDewPoint = temperatureDewPoint; }
    public void setTemperatureFeelsLike(int temperatureFeelsLike) { this.temperatureFeelsLike = temperatureFeelsLike; }
    public void setTemperatureHeatIndex(int temperatureHeatIndex) { this.temperatureHeatIndex = temperatureHeatIndex; }
    public void setTemperatureMax24Hour(int temperatureMax24Hour) { this.temperatureMax24Hour = temperatureMax24Hour; }
    public void setTemperatureMaxSince7Am(int temperatureMaxSince7Am) { this.temperatureMaxSince7Am = temperatureMaxSince7Am; }
    public void setTemperatureMin24Hour(int temperatureMin24Hour) { this.temperatureMin24Hour = temperatureMin24Hour; }
    public void setTemperatureWindChill(int temperatureWindChill) { this.temperatureWindChill = temperatureWindChill; }
    public void setUvDescription(String uvDescription) { this.uvDescription = uvDescription; }
    public void setUvIndex(int uvIndex) { this.uvIndex = uvIndex; }
    public void setValidTimeLocal(Date validTimeLocal) { this.validTimeLocal = validTimeLocal; }
    public void setValidTimeUtc(int validTimeUtc) { this.validTimeUtc = validTimeUtc; }
    public void setVisibility(double visibility) { this.visibility = visibility; }
    public void setWindDirection(int windDirection) { this.windDirection = windDirection; }
    public void setWindDirectionCardinal(String windDirectionCardinal) { this.windDirectionCardinal = windDirectionCardinal; }
    public void setWindGust(Object windGust) { this.windGust = windGust; }
    public void setWindSpeed(int windSpeed) { this.windSpeed = windSpeed; }
    public void setWxPhraseLong(String wxPhraseLong) { this.wxPhraseLong = wxPhraseLong; }
    public void setWxPhraseMedium(String wxPhraseMedium) { this.wxPhraseMedium = wxPhraseMedium; }
    public void setWxPhraseShort(String wxPhraseShort) { this.wxPhraseShort = wxPhraseShort; }
}
