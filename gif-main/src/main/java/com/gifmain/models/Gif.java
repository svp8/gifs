package com.gifmain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "gif")
public class Gif {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    private String name;

    private String description;
    private Calendar created;
    @Column(nullable = true)
    private int views;
    @OneToMany(mappedBy = "gif")
    private List<Tag> tags = new ArrayList<>();
    @Transient
    private String url;

}