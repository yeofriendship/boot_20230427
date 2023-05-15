package com.example.dto;

import lombok.Data;

@Data
// DB에 없기 때문에 dto로 만들어야함. entity로 만들면 안됨
public class Search {
    final String[] typeCode = {"phone", "name", "address", "type"};
    final String[] typeName = {"연락처", "상호명", "주소", "종류"};

    private int page = 1;
    private String type = "phone";
    private String text = "";
}