package com.atcampus.shopper.Model;

import java.util.List;

public class MultipleRecyclerviewModel {

    public static final int SLIDER = 0;
    public static final int SLIDER_ADS = 1;
    public static final int DEALS_DAY = 2;
    public static final int TRENDING = 3;

    private int type;

    //slider
    private List<SliderModel> sliderModelList;

    public MultipleRecyclerviewModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    //slider ads
    private int resource;
    private String backgroundColor;

    public MultipleRecyclerviewModel(int type, int resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    //deals day
    private String title;
    private List<DealsModel> dealsModelList;

    public MultipleRecyclerviewModel(int type, String title, List<DealsModel> dealsModelList) {
        this.type = type;
        this.title = title;
        this.dealsModelList = dealsModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DealsModel> getDealsModelList() {
        return dealsModelList;
    }

    public void setDealsModelList(List<DealsModel> dealsModelList) {
        this.dealsModelList = dealsModelList;
    }
}
