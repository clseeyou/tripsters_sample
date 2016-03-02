package com.tripsters.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tripsters.android.model.City;
import com.tripsters.sample.R;
import com.tripsters.sample.model.PinyinCity;
import com.tripsters.sample.view.CityItemView;
import com.tripsters.sample.view.CityItemView.OnCityClickListener;
import com.tripsters.sample.view.IndexItemView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IndexCityListAdapter extends BaseAdapter {

    /**
     * IncitntryAdapter适配的数据对象 可使用于需要做字母序分组的ListView
     */
    public static class IndexCity {
        int indexInListArray;
        int indexInList;

        public IndexCity(int indexInListArray, int indexInList) {
            this.indexInListArray = indexInListArray;
            this.indexInList = indexInList;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IndexCity)) {
                return false;
            }
            if (this.indexInList != -1) {
                return false;
            }
            IndexCity another = (IndexCity) o;
            return this.indexInListArray == another.indexInListArray
                    && this.indexInList == another.indexInList;
        }
    }

    private Context mContext;
    private boolean isChinese;
    private boolean mOpenedHidden;
    private boolean mCheckVisiable = true;

    private List<PinyinCity>[] arrSubCities;
    private List<IndexCity> indexCities = new ArrayList<IndexCity>();
    private List<PinyinCity> selectCities = new ArrayList<PinyinCity>();

    private boolean mSelectAll;
    private OnCityClickListener mListener;

    public IndexCityListAdapter(Context context, List<PinyinCity> selectCities) {
        this(context, selectCities, false);
    }

    public IndexCityListAdapter(Context context, List<PinyinCity> selectCities, boolean openedHidden) {
        this.mContext = context;
        this.isChinese = /* "zh".equals(Locale.getDefault().getLanguage()) */true;
        this.selectCities = selectCities;
        this.mOpenedHidden = openedHidden;
    }

    public void setSelectAll(boolean selectAll) {
        this.mSelectAll = selectAll;
    }

    public void setCheckVisiable(boolean checkVisiable) {
        this.mCheckVisiable = checkVisiable;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return getIndexFollows().size();
    }

    @Override
    public City getItem(int position) {
        IndexCity indexFollow = indexCities.get(position);
        if (indexFollow.indexInList == -1) { // A B ... view
            return null;
        } else { // City view
            return arrSubCities[indexFollow.indexInListArray].get(indexFollow.indexInList);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IndexCity indexCity = indexCities.get(position);

        if (indexCity.indexInList == -1) { // A B ... view
            String text;
            boolean hot;

            if (mOpenedHidden) {
                text = Character.toString((char) (indexCity.indexInListArray + 'A'));
                hot = false;
            } else {
                if (indexCity.indexInListArray == 0) {// 热门
                    text = mContext.getString(R.string.index_city_opened);
                    hot = true;
                } else {
                    text = Character.toString((char) (indexCity.indexInListArray + 'A' - 1));
                    hot = false;
                }
            }

            return getIndexItemView(mContext, text, hot);
        }

        // boolean showDivider = true;
        PinyinCity city = arrSubCities[indexCity.indexInListArray].get(indexCity.indexInList);
        // if (position == indexCities.size() - 1) { // A B ...
        // // view的上一个city不显示分隔
        // showDivider = false;
        // // if (indexCities.get(position + 1).indexInList == -1) {
        // // showDivider = false;
        // // }
        // }

        CityItemView itemView = getCityItemView(mContext, convertView, mListener);
        itemView.setCheckVisiable(mCheckVisiable);

        boolean checked;
        if (mSelectAll) {
            checked = true;
        } else {
            if (selectCities.contains(city)) {
                checked = true;
            } else {
                checked = false;
            }
        }

        itemView.update(city, position, checked);
        if (!mOpenedHidden/* && indexCity.indexInListArray == 0 */) {
            itemView.setNameSelected(city.getCityHot() == City.OPENED);
        } else {
            itemView.setNameSelected(false);
        }

        return itemView;
    }

    private static CityItemView getCityItemView(Context context, View convertView,
                                                OnCityClickListener listener) {
        CityItemView iv;

        if (convertView == null || !(convertView instanceof CityItemView)) { // City
            // view
            iv = new CityItemView(context, listener);
        } else {
            iv = (CityItemView) convertView;
        }

        iv.showDivider(/* showDivider */true);

        return iv;
    }

    private List<IndexCity> getIndexFollows() {
        if (indexCities == null) {
            return new ArrayList<IndexCity>();
        }

        return indexCities;
    }

    public int getIndex(IndexCity indexFollow, int index) {
        if (arrSubCities != null && index < arrSubCities.length && arrSubCities[index] != null) {
            return indexCities.indexOf(new IndexCity(index, -1));
        }

        return -1;
    }

    public void filter(List<PinyinCity> cities) {
        if (cities == null || cities.isEmpty()) {
            clear();
            return;
        }

        Collections.sort(cities, new Comparator<PinyinCity>() {

            @Override
            public int compare(PinyinCity lhs, PinyinCity rhs) {
                if (isChinese) {
                    return lhs.getPinyin().charAt(0) - rhs.getPinyin().charAt(0);
                } else {
                    return lhs.getCityNameEn().charAt(0) - rhs.getCityNameEn().charAt(0);
                }
            }
        });

        arrSubCities = subCities(cities);
        indexCities = compose(arrSubCities);
    }

    public void clear() {
        arrSubCities = null;
        indexCities.clear();
    }

    /**
     * 把List按拼音首字母分割到A-Z,# 共26个小List中
     *
     * @param cities
     * @return 分割后的List数组
     */
    @SuppressWarnings("unchecked")
    private List<PinyinCity>[] subCities(List<PinyinCity> cities) {
        if (mOpenedHidden) {
            List<PinyinCity>[] arr = new ArrayList[27];

            for (int i = 0; i < cities.size(); i++) {
                PinyinCity city = cities.get(i);

                if (isChinese) {
                    String pre = city.getPinyin();

                    int index = pre.charAt(0) - 'A';
                    if (index < 0 || index >= 26) {
                        index = 26;
                    }
                    if (arr[index] == null) {
                        arr[index] = new ArrayList<PinyinCity>();
                    }

                    arr[index].add(city);
                } else {
                    String name = city.getCityNameEn();

                    int index = name.charAt(0) - 'A';
                    if (index < 0 || index >= 26) {
                        index = 26;
                    }
                    if (arr[index] == null) {
                        arr[index] = new ArrayList<PinyinCity>();
                    }

                    arr[index].add(city);
                }
            }

            return arr;
        } else {
            List<PinyinCity>[] arr = new ArrayList[28];

            for (int i = 0; i < cities.size(); i++) {
                PinyinCity city = cities.get(i);

                if (city.getCityHot() == City.OPENED) {
                    if (arr[0] == null) {
                        arr[0] = new ArrayList<PinyinCity>();
                    }

                    arr[0].add(city);
                }

                if (isChinese) {
                    String pre = city.getPinyin();

                    int index = pre.charAt(0) - 'A' + 1;
                    if (index < 1 || index >= 27) {
                        index = 27;
                    }
                    if (arr[index] == null) {
                        arr[index] = new ArrayList<PinyinCity>();
                    }

                    arr[index].add(city);
                } else {
                    String name = city.getCityNameEn();

                    int index = name.charAt(0) - 'A' + 1;
                    if (index < 1 || index >= 27) {
                        index = 27;
                    }
                    if (arr[index] == null) {
                        arr[index] = new ArrayList<PinyinCity>();
                    }

                    arr[index].add(city);
                }
            }

            return arr;
        }
    }

    /**
     * 把List数组组合到用于CityAdapter适配的List数据
     *
     * @param arrSubCities
     * @return CityAdapter适配的List数据
     */
    private List<IndexCity> compose(List<PinyinCity>[] arrSubCities) {
        List<IndexCity> indexFollows = new ArrayList<IndexCity>();
        if (arrSubCities != null) {
            for (int i = 0; i < arrSubCities.length; i++) {
                List<PinyinCity> list = arrSubCities[i];
                if (list != null && list.size() > 0) {
                    for (int j = 0; j < list.size(); j++) {
                        if (j == 0) {
                            indexFollows.add(new IndexCity(i, -1));
                        }
                        indexFollows.add(new IndexCity(i, j));
                    }
                }
            }
        }
        return indexFollows;
    }

    private static View getIndexItemView(Context context, String text, boolean hot) {
        IndexItemView view = new IndexItemView(context);

        view.setClickable(false);
        view.update(text, hot);

        return view;
    }
}
