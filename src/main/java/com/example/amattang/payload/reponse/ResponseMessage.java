package com.example.amattang.payload.reponse;

public interface ResponseMessage {

    public String GET_USER_ALL_CHECK_LIST = "현재 사용자의 체크리스트 목록 조회 성공";
    public String GET_CHECK_LIST_CATEGORY = "현재 카테고리의 질문, 답변 목록 조회 성공";

    public String CREATE_CHECK_LIST = "체크리스트 생성 및 질문 목록 반환 성공";

    public String UPDATE_CHECK_LIST_ANSWER = "현재 카테고리에 답변 등록 성공";
    public String UPDATE_CHECK_LIST_QUESTION_STATUS = "현재 카테고리의 질문 추가(삭제) 성공";


    public String GET_CUSTOM_ALL_QUESTION = "현재 사용자의 커스텀 카테고리와 질문 목록 조회 성공";
    public String CREATE_CUSTOM_QUESTION = "현재 사용자의 커스텀 카테고리와 질문 추가 성공";
    public String UPDATE_CUSTOM_QUESTION = "현재 사용자의 커스텀 카테고리와 질문 수정 성공";
    public String DELETE_CUSTOM_CATEGORY = "현재 사용자의 커스텀 카테고리 삭제 성공";
    public String DELETE_CUSTOM_QUESTION = "현재 사용자의 커스텀 카테고리의 질문 삭제 성공";


    public String CREATE_CHECK_LIST_IMAGE = "현재 체크리스트에 이미지 저장 성공";
    public String DELETE_CHECK_LIST_IMAGE = "현재 체크리스트의 이미지 삭제 성공";
    public String UPDATE_CHECK_LIST_MAIN_IMAGE = "현재 체크리스트의 메인 이미지 변경 성공";

}
