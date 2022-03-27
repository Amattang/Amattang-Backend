package com.example.amattang.payload.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommonRequestDto {

    private List<TypeARequest> typeA = new ArrayList<>();
    private List<TypeBRequest> typeB = new ArrayList<>();
    private List<TypeDRequest> typeD = new ArrayList<>();
    private TypeMRequest typeM;

    //a-1, a-2
    //c는 이미지 컨트롤러로 받을 것


    @Data
    public static class TypeARequest {
        private Long questionId;
        List<ButtonRequest> answer;

        @Data
        public static class ButtonRequest {
            private String type;
            private Boolean val;
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
            private Boolean val;
        }
    }

    @Data
    public static class TypeMRequest {
        private Long questionId;
        private String address;
        private Double latitude;
        private Double longitude;
    }

}
