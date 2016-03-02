package com.tripsters.sample.model;

import com.tripsters.android.model.City;

public class PinyinCity extends City {
    private String pinyin;

    public PinyinCity(City city, String pinyin) {
        setId(city.getId());
        setCountryCode(city.getCountryCode());
        setCityNameCn(city.getCityNameCn());
        setCityNameEn(city.getCityNameEn());
        setCityNameLocal(city.getCityNameLocal());
        setCityLng(city.getCityLng());
        setCityLat(city.getCityLat());
        setCityHot(city.getCityHot());
        setPinyin(pinyin);
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
