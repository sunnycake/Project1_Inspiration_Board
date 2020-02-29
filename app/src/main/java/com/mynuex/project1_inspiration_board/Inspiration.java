package com.mynuex.project1_inspiration_board;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "novel_inspiration_board")
public class Inspiration {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String category;
    private String tags;
    private String imagePath;
    private Date date;

    public Inspiration(String title, String description, String category, String tags, String imagePath){
        this.title = title;
        this.description = description;
        this.category = category;
        this.tags = tags;
        this.imagePath = imagePath;
        this.date = new Date();

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Inspiration{" +
                "id=" + id +
                ", title=" + title +
                ", description=" + description +
                ", category=" + category +
                ", tags=" + tags +
                ", date=" + date +
                ",image=" + imagePath +
                '}';
    }

}

