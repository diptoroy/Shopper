package com.atcampus.shopper.Model;

import java.util.List;

public class MultipleRecyclerviewModel {

    public static final int SLIDER = 0;
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
}
