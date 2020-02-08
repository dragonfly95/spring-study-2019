package com.system.restaurant.domain;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Bbs {
    private String tit;
    private String cont;
    private MultipartFile file1;
}
