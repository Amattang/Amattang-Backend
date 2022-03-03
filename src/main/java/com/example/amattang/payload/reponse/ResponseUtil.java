package com.example.amattang.payload.reponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResponseUtil {

    public static <T> ResponseEntity<DefaultResponse<T>> succes(T data, String message) {
        DefaultResponse defaultResponse = new DefaultResponse(HttpStatus.OK, data, message);
        return new ResponseEntity(defaultResponse, HttpStatus.OK);
    }

    public static <T> ResponseEntity<DefaultResponse<T>> succes(HttpStatus httpStatus, T data, String message) {
        DefaultResponse defaultResponse = new DefaultResponse(httpStatus, data, message);
        return new ResponseEntity(defaultResponse, httpStatus);
    }

    public static ResponseEntity<DefaultResponse> fail(HttpStatus httpStatus, String message) {
        DefaultResponse defaultResponse = new DefaultResponse(httpStatus, message);
        return new ResponseEntity(defaultResponse, httpStatus);
    }

    @Data
    public static class DefaultResponse<T> {
        
        @ApiModelProperty(name = "반환 데이터")
        private T data;
        @ApiModelProperty(name = "반환 메시지")
        private String message;
        @ApiModelProperty(name = "상태 코드")
        private int status;

        public DefaultResponse(HttpStatus httpStatus, T data, String message) {
            this.status = httpStatus.value();
            this.data = data;
            this.message = message;
        }

        public DefaultResponse(HttpStatus httpStatus, String message) {
            this.status = httpStatus.value();
            this.message = message;
        }
    }
}
