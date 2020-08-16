package com.atcampus.shopper.Model;

public class SliderModel {

    private String slider;
    private String backgroundColor;

    public SliderModel(String slider, String backgroundColor) {
        this.slider = slider;
        this.backgroundColor = backgroundColor;
    }

    public String getSlider() {
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
