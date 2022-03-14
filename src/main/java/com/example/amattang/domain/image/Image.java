package com.example.amattang.domain.image;

import com.example.amattang.domain.checkList.CheckList;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "check_list_image")
public class Image {

    @Id
    @Column(name = "check_list_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "check_list_check_list_id")
    private CheckList checkListId;

    private String url;

    private boolean main;
}
