package com.b2012202.mxhtknt.Models;

import com.b2012202.mxhtknt.Models.EmbeddedId.LauID;
import com.b2012202.mxhtknt.Services.LauService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LAU")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lau {

    @EmbeddedId
    private LauID lauID;

    @Column(name = "STTLAU")
    private Integer sttLau;

    @MapsId("idNhaTro")
    @ManyToOne
    @JoinColumn(name = "IDNHATRO")
    //Ngan khong cho con goi nguoc lai cha (truy van thanh vong lap)
    @JsonBackReference
    private NhaTro nhaTro;


    @OneToMany(mappedBy = "lau")
    @JsonManagedReference
    private Set<Phong> phongSet= new HashSet<>();

    //Generate lau_sequence for database
    // ADD this statement in sql script: CREATE SEQUENCE lau_sequence START WITH 1 INCREMENT BY 1;
    @SequenceGenerator(
            name = "lau_sequence",
            sequenceName = "lau_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lau_sequence"
    )
    private Long sequenceValue;


}
