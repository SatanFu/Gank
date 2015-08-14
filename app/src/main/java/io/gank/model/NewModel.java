package io.gank.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satan on 2015/8/14.
 */
public class NewModel implements Serializable {

    @SerializedName("Android")
    private List<GankModel> android = new ArrayList<GankModel>(); //android

    @SerializedName("iOS")
    private List<GankModel> ios = new ArrayList<GankModel>();//iOS

    @SerializedName("福利")
    private List<GankModel> welfare = new ArrayList<GankModel>();//福利

    @SerializedName("休息视频")
    private List<GankModel> video = new ArrayList<GankModel>();//休息视频

    @SerializedName("前端")
    private List<GankModel> frontEnd = new ArrayList<GankModel>();//前端

    @SerializedName("拓展资源")
    private List<GankModel> expand = new ArrayList<GankModel>();//拓展资源

    @SerializedName("瞎推荐")
    private List<GankModel> recommendation = new ArrayList<GankModel>();//瞎推荐

    public List<GankModel> getAndroid() {
        return android;
    }

    public void setAndroid(List<GankModel> android) {
        this.android = android;
    }

    public List<GankModel> getIos() {
        return ios;
    }

    public void setIos(List<GankModel> ios) {
        this.ios = ios;
    }

    public List<GankModel> getWelfare() {
        return welfare;
    }

    public void setWelfare(List<GankModel> welfare) {
        this.welfare = welfare;
    }

    public List<GankModel> getVideo() {
        return video;
    }

    public void setVideo(List<GankModel> video) {
        this.video = video;
    }

    public List<GankModel> getFrontEnd() {
        return frontEnd;
    }

    public void setFrontEnd(List<GankModel> frontEnd) {
        this.frontEnd = frontEnd;
    }

    public List<GankModel> getExpand() {
        return expand;
    }

    public void setExpand(List<GankModel> expand) {
        this.expand = expand;
    }

    public List<GankModel> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<GankModel> recommendation) {
        this.recommendation = recommendation;
    }

    @Override
    public String toString() {
        return "NewModel{" +
                "android=" + android +
                ", ios=" + ios +
                ", welfare=" + welfare +
                ", video=" + video +
                ", frontEnd=" + frontEnd +
                ", expand=" + expand +
                ", recommendation=" + recommendation +
                '}';
    }
}
