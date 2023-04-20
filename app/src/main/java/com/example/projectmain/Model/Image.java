package com.example.projectmain.Model;

public class Image {

    private int resource;

    public Image(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private String source;



    public Image(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }



}
