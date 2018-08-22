package com.timemasta.nyctourguide;

public class Model {
    private String h3, h3Span, link,imagesrc;

    public Model() {
    }

    public Model(String h3, String h3S, String lk, String imgsrc) {
        this.h3 = h3;
        this.h3Span = h3S;
        this.link = lk;
        this.imagesrc = imgsrc;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h) {
        this.h3 = h;
    }

    public String getH3Span() {
        return h3Span;
    }

    public void setH3Span(String h3s) {
        this.h3Span = h3s;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String ln) {
        this.link = ln;
    }

    public String getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(String ims) {
        this.imagesrc = ims;
    }
}