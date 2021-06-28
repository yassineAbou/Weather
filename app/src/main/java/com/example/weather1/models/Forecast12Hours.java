package com.example.weather1.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Yassine Abou on 6/11/2021.
 */
public class Forecast12Hours {

    public List<Integer> cloudCover;
    public List<String> dayOfWeek;
    public List<String> dayOrNight;
    public List<Integer> expirationTimeUtc;
    public List<Integer> iconCode;
    public List<Integer> iconCodeExtend;
    public List<Integer> precipChance;
    public List<String> precipType;
    public List<Double> pressureMeanSeaLevel;
    public List<Double> qpf;
    public List<Double> qpfSnow;
    public List<Integer> relativeHumidity;
    public List<Double> temperature;
    public List<Double> temperatureDewPoint;
    public List<Double> temperatureFeelsLike;
    public List<Double> temperatureHeatIndex;
    public List<Double> temperatureWindChill;
    public List<String> uvDescription;
    public List<Integer> uvIndex;
    public List<Date> validTimeLocal;
    public List<Integer> validTimeUtc;
    public List<Double> visibility;
    public List<Integer> windDirection;
    public List<String> windDirectionCardinal;
    public List<Object> windGust;
    public List<Integer> windSpeed;
    public List<String> wxPhraseLong;
    public List<String> wxPhraseShort;
    public List<Integer> wxSeverity;
    public List<Integer> ceiling;
    public List<Integer> scatteredCloudBaseHeight;
    public List<Double> pressureAltimeter;
    public List<Double> qpfIce;
    public List<Object> qualifierSet;

    //------------
    // GETTERS
    //------------

    public List<Integer> getCloudCover() { return cloudCover; }
    public List<String> getDayOfWeek() { return dayOfWeek; }
    public List<String> getDayOrNight() { return dayOrNight; }
    public List<Integer> getExpirationTimeUtc() { return expirationTimeUtc; }
    public List<Integer> getIconCode() { return iconCode; }
    public List<Integer> getIconCodeExtend() { return iconCodeExtend; }
    public List<Integer> getPrecipChance() { return precipChance; }
    public List<String> getPrecipType() { return precipType; }
    public List<Double> getPressureMeanSeaLevel() { return pressureMeanSeaLevel; }
    public List<Double> getQpf() { return qpf; }
    public List<Double> getQpfSnow() { return qpfSnow; }
    public List<Integer> getRelativeHumidity() { return relativeHumidity; }
    public List<Double> getTemperature() { return temperature; }
    public List<Double> getTemperatureDewPoint() { return temperatureDewPoint; }
    public List<Double> getTemperatureFeelsLike() { return temperatureFeelsLike; }
    public List<Double> getTemperatureHeatIndex() { return temperatureHeatIndex; }
    public List<Double> getTemperatureWindChill() { return temperatureWindChill; }
    public List<String> getUvDescription() { return uvDescription; }
    public List<Integer> getUvIndex() { return uvIndex; }
    public List<Date> getValidTimeLocal() { return validTimeLocal; }
    public List<Integer> getValidTimeUtc() { return validTimeUtc; }
    public List<Double> getVisibility() { return visibility; }
    public List<Integer> getWindDirection() { return windDirection; }
    public List<String> getWindDirectionCardinal () { return windDirectionCardinal; }
    public List<Object> getWindGust () { return windGust; }
    public List<Integer> getWindSpeed () { return windSpeed; }
    public List<String> getWxPhraseLong () { return wxPhraseLong; }
    public List<String> getWxPhraseShort () { return wxPhraseShort; }
    public List<Integer> getWxSeverity () { return wxSeverity; }
    public List<Integer> getCeiling () { return ceiling; }
    public List<Integer> getScatteredCloudBaseHeight () { return scatteredCloudBaseHeight; }
    public List<Double> getPressureAltimeter () { return pressureAltimeter; }
    public List<Double> getQpfIce () { return qpfIce; }
    public List<Object> getQualifierSet () { return qualifierSet; }

    //----------------
    // SETTERS
    //----------------

    public void setCloudCover(List<Integer> cloudCover) { this.cloudCover = cloudCover; }
    public void setDayOfWeek(List<String> dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setDayOrNight(List<String> dayOrNight) { this.dayOrNight = dayOrNight; }
    public void setExpirationTimeUtc(List<Integer> expirationTimeUtc) { this.expirationTimeUtc = expirationTimeUtc; }
    public void setIconCode(List<Integer> iconCode) { this.iconCode = iconCode; }
    public void setIconCodeExtend(List<Integer> iconCodeExtend) { this.iconCodeExtend = iconCodeExtend; }
    public void setPrecipChance(List<Integer> precipChance) { this.precipChance = precipChance; }
    public void setPressureMeanSeaLevel(List<Double> pressureMeanSeaLevel) { this.pressureMeanSeaLevel = pressureMeanSeaLevel; }
    public void setQpf(List<Double> qpf) { this.qpf = qpf; }
    public void setQpfSnow(List<Double> qpfSnow) { this.qpfSnow = qpfSnow; }
    public void setRelativeHumidity(List<Integer> relativeHumidity) { this.relativeHumidity = relativeHumidity; }
    public void setCeiling(List<Integer> ceiling) { this.ceiling = ceiling; }
    public void setPressureAltimeter(List<Double> pressureAltimeter) { this.pressureAltimeter = pressureAltimeter; }
    public void setQpfIce(List<Double> qpfIce) { this.qpfIce = qpfIce; }
    public void setQualifierSet(List<Object> qualifierSet) { this.qualifierSet = qualifierSet; }
    public void setTemperature(List<Double> temperature) { this.temperature = temperature; }
    public void setTemperatureDewPoint(List<Double> temperatureDewPoint) { this.temperatureDewPoint = temperatureDewPoint; }
    public void setTemperatureFeelsLike(List<Double> temperatureFeelsLike) { this.temperatureFeelsLike = temperatureFeelsLike; }
    public void setTemperatureHeatIndex(List<Double> temperatureHeatIndex) { this.temperatureHeatIndex = temperatureHeatIndex; }
    public void setTemperatureWindChill(List<Double> temperatureWindChill) { this.temperatureWindChill = temperatureWindChill; }
    public void setUvDescription(List<String> uvDescription) { this.uvDescription = uvDescription; }
    public void setUvIndex(List<Integer> uvIndex) { this.uvIndex = uvIndex; }
    public void setValidTimeLocal(List<Date> validTimeLocal) { this.validTimeLocal = validTimeLocal; }
    public void setValidTimeUtc(List<Integer> validTimeUtc) { this.validTimeUtc = validTimeUtc; }
    public void setVisibility(List<Double> visibility) { this.visibility = visibility; }
    public void setWindDirection(List<Integer> windDirection) { this.windDirection = windDirection; }
    public void setWindDirectionCardinal(List<String> windDirectionCardinal) { this.windDirectionCardinal = windDirectionCardinal; }
    public void setWindGust(List<Object> windGust) { this.windGust = windGust; }
    public void setWindSpeed(List<Integer> windSpeed) { this.windSpeed = windSpeed; }
    public void setWxPhraseLong(List<String> wxPhraseLong) { this.wxPhraseLong = wxPhraseLong; }
    public void setWxPhraseShort(List<String> wxPhraseShort) { this.wxPhraseShort = wxPhraseShort; }
    public void setWxSeverity(List<Integer> wxSeverity) { this.wxSeverity = wxSeverity; }
    public void setScatteredCloudBaseHeight(List<Integer> scatteredCloudBaseHeight) { this.scatteredCloudBaseHeight = scatteredCloudBaseHeight; }
    public void setPrecipType(List<String> precipType) { this.precipType = precipType; }
}
