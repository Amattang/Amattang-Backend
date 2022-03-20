package com.example.amattang.payload.reponse;

import com.example.amattang.domain.answer.Answer;
import com.example.amattang.domain.answer.AnswerString;
import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.listToQuestion.ListToQuestion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MainViewCheckListResponseDto {

    private List<MainViewCheckListDto> checkList;

    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MainViewCheckListDto {
        private Long id;
        private String imgUri;
        private String mainTitle; //타이틀 없을 경우 (제목없음)
        private String address; //주소, 강남구 ~~
        private List<Integer> latlng; //위도 경도
        private String distance; //도보 3분
        private String roomType; //원룸
        private String area; //7평
        private boolean pinned;
        private boolean center; // 제일 앞에꺼만 true, 나머지 false

        //latlng : latitude, longitude


        public static MainViewCheckListDto fromEntity(List<ListToQuestion> relation, Long checkListId, String mainImage) {
            MainViewCheckListDto dto = MainViewCheckListDto.builder()
                    .id(checkListId)
                    .imgUri(mainImage)
                    .center(false)
                    .build();
            for (ListToQuestion l : relation) {
                if (l.getCommonQuestionId().getBasicType().equals("title")) {
                    dto.setMainTitle((l.getQuestionToAnswer().getAnswerList() == null || l.getQuestionToAnswer().getAnswerList().size() == 0) ? null : ((AnswerString) l.getQuestionToAnswer().getAnswerList().get(0)).getAns());
                } else if (l.getCommonQuestionId().getBasicType().equals("address")) {
                    dto.setAddress((l.getQuestionToAnswer().getAnswerList() == null || l.getQuestionToAnswer().getAnswerList().size() == 0) ? null : ((AnswerString) l.getQuestionToAnswer().getAnswerList().get(0)).getAns());
                } else if (l.getCommonQuestionId().getBasicType().equals("distance")) {
                    dto.setDistance((l.getQuestionToAnswer().getAnswerList() == null || l.getQuestionToAnswer().getAnswerList().size() == 0) ? null : ((AnswerString) l.getQuestionToAnswer().getAnswerList().get(0)).getAns());
                } else if (l.getCommonQuestionId().getBasicType().equals("type")) {
                    dto.setRoomType((l.getQuestionToAnswer().getAnswerList() == null || l.getQuestionToAnswer().getAnswerList().size() == 0) ? null : ((AnswerString) l.getQuestionToAnswer().getAnswerList().get(0)).getAns());
                } else if (l.getCommonQuestionId().getBasicType().equals("area")) {
                    dto.setArea((l.getQuestionToAnswer().getAnswerList() == null || l.getQuestionToAnswer().getAnswerList().size() == 0) ? null : ((AnswerString) l.getQuestionToAnswer().getAnswerList().get(0)).getAns());
                }
            }
            return dto;
        }
    }

    public static MainViewCheckListResponseDto fromEntity(List<MainViewCheckListDto> dtoList) {
        return new MainViewCheckListResponseDto(dtoList);
    }
}
