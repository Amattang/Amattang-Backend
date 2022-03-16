package com.example.amattang.domain.image;

import com.example.amattang.domain.checkList.CheckList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Image(CheckList checkListId, String url) {
        this.checkListId = checkListId;
        this.url = url;
        this.main = false;
    }

    public void deleteMain() {
        this.main = false;
    }

    public void addMain() {
        this.main = true;
    }
}
