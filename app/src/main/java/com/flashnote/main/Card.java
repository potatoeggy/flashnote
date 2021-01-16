package com.example.flashnote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Card {
    ArrayList<Tag> categories;
    String term;
    String definition;
    Date creationDate;
    String user;
    public Card(List<Tag> categories, String term, String definition, Date creationDate, String user){
        this.categories = new ArrayList<>();
        for(Tag t:categories) this.categories.add(t);
        this.term = term;
        this.definition = definition;
        this.creationDate = creationDate;
        this.user = user;
    }
}
