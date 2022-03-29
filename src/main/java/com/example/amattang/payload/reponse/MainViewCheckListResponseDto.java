package com.example.amattang.payload.reponse;

import com.example.amattang.domain.checkList.CheckList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class MainViewCheckListResponseDto {

    private Long id;
    private int order;
    private String imgUri;
    private String mainTitle;
    private String address;
    private Map<String, Double> location;
    private String distance;
    private String roomType;
    private String area;
    private String form;
    private boolean pinned;
    private boolean center;
    private Property information;

    @Data
    @AllArgsConstructor
    static class Property {
        private String distance;
        private String roomType;
        private String area;
        private String form;
    }

    public static MainViewCheckListResponseDto fromEntity(CheckList checkList, int index) {
        return MainViewCheckListResponseDto.builder()
                .id(checkList.getId())
                .order(index)
                .imgUri(checkList.getImage())
                .mainTitle(checkList.getTitle())
                .address(checkList.getAddress())
                .location(
                        new HashMap<>() {{
                            put("latitude", checkList.getLatitude());
                            put("longitude", checkList.getLongitude());
                        }}
                )
                .distance(checkList.getDistance())
                .roomType(checkList.getRoomType())
                .area(checkList.getArea())
                .form(checkList.getFloor())
                .pinned(checkList.isPinned())
                .center(false)
                .information(
                        new Property(
                                checkList.getDistance(),
                                checkList.getRoomType(),
                                checkList.getArea(),
                                checkList.getFloor()
                        )
                )
                .build();
    }
}
