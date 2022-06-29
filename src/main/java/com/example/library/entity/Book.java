package com.example.library.entity;

import com.example.library.enums.Genre;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data // For getters and setters
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_TBL")
public class Book {
    @Id // Shows it is a primary key
    @GeneratedValue // Autogenerated by hibernate
    private int id;

    @Column(name = "title", nullable = false)
    private  String title;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private Author author;

    @Column(name = "pub_year", nullable = false)
    @ColumnDefault("-1")
    private int pubYear = -1;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "num_of_copies")
    @ColumnDefault("0")
    private int numOfCopies = 0;

    @Column(name = "num_of_loaned")
    @ColumnDefault("0")
    private int numOfLoaned = 0;
}
