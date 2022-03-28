package com.example.amattang.payload.reponse;

import com.example.amattang.domain.checkList.CheckList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class MainViewCheckListResponseDto {

    private Long id;
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

    //latlng : latitude, longitude


    public static MainViewCheckListResponseDto fromEntity(CheckList checkList) {
        return MainViewCheckListResponseDto.builder()
                .id(checkList.getId())
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
                .build();
    }
}
