package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="FILE_STORE")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @SequenceGenerator(
            name = "file_sequence",
            sequenceName = "file_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_sequence"
    )
    @Column(name = "IDFILE")
    private Long idFile;

    @Column(name = "URL")
    private String url;

    @ManyToMany(mappedBy = "fileSet", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<BaiViet> baiVietSet = new HashSet<>();
}
