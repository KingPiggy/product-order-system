package com.kingpiggy.study.productordersystem.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import javafx.scene.control.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    // api 통신시간
    private LocalDateTime transactionTime;

    // api 응답 코드
    private String resultCode;

    // api 부가 설명
    private String description;

    private T data;

    // pagination
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pagination pagination;

    // OK
    public static <T> Header<T> OK(){
        return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("OK")
            .description("OK")
            .build();
    }


    // DATA OK
    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("OK")
            .description("OK")
            .data(data)
            .build();
    }

    public static <T> Header<T> OK(T data, Pagination pagination){
        return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("OK")
            .description("OK")
            .data(data)
            .pagination(pagination)
            .build();
    }

    // ERROR
    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("ERROR")
            .description(description)
            .build();
    }

    // ERROR with Code
    public static <T> Header<T> ERROR(String code, String description){
        return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode(code)
            .description(description)
            .build();
    }
    // ERROR with Code
    public static <T> Header<T> ERROR(String code, String description, T data){
        return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode(code)
            .description(description)
            .data(data)
            .build();
    }
}
