package com.gifs.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gif {
    public Gif(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    @GeneratedValue
    @Id
    private int id;

    private String name;

    private String type;
    @Lob
    private byte[] data;

}
