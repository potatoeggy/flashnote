package com.example.flashnote;

public class Tag {
    String color;
    String name;
    public Tag(String color, String name){
        this.color = color;
        this.name = name;
    }
    @Override
    public boolean equals(Object obj) {
        return name.equals(((Tag)obj).name);
    }
}