package com.hospital.booking;

public class CardModel {
    private int imageId;
    private int titleId;
    private int subtitleId;

    public CardModel(int imageId, int titleId, int subtitleId) {
        this.imageId = imageId;
        this.titleId = titleId;
        this.subtitleId = subtitleId;
    }

    public CardModel(int imageId){
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public int getTitle() {
        return titleId;
    }

    public int getSubtitle() {
        return subtitleId;
    }
}
