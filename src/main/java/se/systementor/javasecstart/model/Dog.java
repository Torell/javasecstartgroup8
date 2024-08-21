package se.systementor.javasecstart.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="Dog")
public class Dog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;

    @Column(name="Age")
    private String age;

    @Column(name="Gender")
    private String gender;

    @Column(name = "Breed")
    private String breed;

    @Column(name="SoldTo")
    private String soldTo;

    @Column(name="Price")
    private int price;

    @Column(name="Name")
    private String name;

    @Column(name="Size")
    private String size;

    @Column(name="Image")
    private String image;
}