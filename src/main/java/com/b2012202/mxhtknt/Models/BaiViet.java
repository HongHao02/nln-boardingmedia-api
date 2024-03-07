package com.b2012202.mxhtknt.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BAI_VIET")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaiViet {
    @Id
    @SequenceGenerator(
            name = "bai_viet_sequence",
            sequenceName = "bai_viet_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bai_viet_sequence"
    )
    @Column(name = "IDBAIVIET")
    private Long idBaiViet;

    @ManyToOne
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "DESCIPTION")
    private String description;
    @Column(name = "PUBLISHED_AT")
    private LocalDateTime published_at = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    @Column(name = "LAST_UPDATE")
    private LocalDateTime last_update = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    @Column(name = "LOCK")
    private boolean lock;
    @Column(name = "DELETED")
    private boolean deleted = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "BAIVIET_FILE",
            joinColumns = {@JoinColumn(name = "IDBAIVIET", referencedColumnName = "IDBAIVIET")},
            inverseJoinColumns = {@JoinColumn(name = "IDFILE", referencedColumnName = "IDFILE")}
    )
    @JsonManagedReference
    private Set<File> fileSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "BAIVIET_PHONG",
            joinColumns = {@JoinColumn(name = "IDBAIVIET", referencedColumnName = "IDBAIVIET")},
            inverseJoinColumns = {
                    @JoinColumn(name = "IDNHATRO", referencedColumnName = "IDNHATRO"),
                    @JoinColumn(name = "IDLAU", referencedColumnName = "IDLAU"),
                    @JoinColumn(name = "IDPHONG", referencedColumnName = "IDPHONG")
            })
    @JsonManagedReference
    private Set<Phong> phongSet = new HashSet<>();

    // Mối quan hệ với thực thể Người dùng (Many-to-Many)
    @ManyToMany(mappedBy = "likedBaiVietSet")
    @JsonBackReference
    private Set<User> nguoiDungLikedSet = new HashSet<>();


}
