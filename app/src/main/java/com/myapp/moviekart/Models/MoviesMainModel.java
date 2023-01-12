package com.myapp.moviekart.Models;

public class MoviesMainModel {
    String name; String link;
    String category; String duration;
    String description; String thumbnail;
    String trailer;

    public MoviesMainModel() { }
    public MoviesMainModel(String name, String link, String category,
                           String duration, String description,
                           String thumbnail, String trailer) {
        this.name = name;
        this.link = link;
        this.category = category;
        this.duration = duration;
        this.description = description;
        this.thumbnail = thumbnail;
        this.trailer=trailer;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getTrailer() {
        return trailer;
    }
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
