package com.atcampus.shopper.Model;

public class RewardModel {

    private String rewardTitle;
    private String rewardDate;
    private String rewardDesc;

    public RewardModel(String rewardTitle, String rewardDate, String rewardDesc) {
        this.rewardTitle = rewardTitle;
        this.rewardDate = rewardDate;
        this.rewardDesc = rewardDesc;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }

    public String getRewardDate() {
        return rewardDate;
    }

    public void setRewardDate(String rewardDate) {
        this.rewardDate = rewardDate;
    }

    public String getRewardDesc() {
        return rewardDesc;
    }

    public void setRewardDesc(String rewardDesc) {
        this.rewardDesc = rewardDesc;
    }
}
