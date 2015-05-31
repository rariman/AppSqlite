package com.rariman.appsqlite.domain;

/**
 * Created by Rariman on 31/05/2015.
 */
public class Book {
    private String title;
    private String description;

    public Book(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
