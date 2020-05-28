package com.atcampus.shopper.Model;

public class SliderModel {

    private int slider;
    private String backgroundColor;

    public SliderModel(int slider, String backgroundColor) {
        this.slider = slider;
        this.backgroundColor = backgroundColor;
    }

    public int getSlider() {
        return slider;
    }

    public void setSlider(int slider) {
        this.slider = slider;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
