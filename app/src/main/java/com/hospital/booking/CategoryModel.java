package com.hospital.booking;

public class CategoryModel {
    private String title, genre;
    public CategoryModel() {
    }
    public CategoryModel(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
}