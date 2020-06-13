package com.atcampus.shopper.Model;

import java.util.List;

public class MultipleRecyclerviewModel {

    public static final int SLIDER = 0;
    public static final int SLIDER_ADS = 1;

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
}
