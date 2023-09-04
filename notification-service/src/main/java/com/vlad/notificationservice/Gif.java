package com.vlad.notificationservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class Gif {

    private int id;

    private String name;

    private String description;
    private Calendar created;

    private int views;

    @Override
    public String toString() {
        return "Gif{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", views=" + views +
                ", url='" + url + '\'' +
                '}';
    }

    private String url;

}