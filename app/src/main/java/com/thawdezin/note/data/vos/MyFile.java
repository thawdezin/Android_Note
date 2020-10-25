package com.thawdezin.note.data.vos;

public class MyFile {
    private String imageUri;

    public MyFile(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "imageUri='" + imageUri + '\'' +
                '}';
    }

}
