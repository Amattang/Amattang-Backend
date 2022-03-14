package com.example.amattang.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommonRequestDto {

    private List<TypeARequest> typeA = new ArrayList<>();
    private List<TypeBRequest> typeB = new ArrayList<>();
    private List<TypeDRequest> typeD = new ArrayList<>();

    //a-1, a-2
    //c는 이미지 컨트롤러로 받을 것
    @Data
    public static class TypeARequest {
        private Long questionId;
//        private Long answerId;
        List<ButtonRequest> answer;

        @Data
        public static class ButtonRequest {
            private String type;
            private Boolean ans;
            private Boolean redType;
        }
    }
    @Data
    public static class TypeBRequest {
        private Long questionId;
        private List <AnswerBRequest> answer;

        @Data
        public static class AnswerBRequest {
            private String type;
            private String description;
        }
    }

    @Data
    public static class TypeDRequest {
        private Long questionId;
        private List<Request> answer;

        @Data
        public static class Request {
            private String type;
            private Boolean ans;
        }

    }


}
