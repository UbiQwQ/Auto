package com.innovation.auto.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private Integer id;

    private Integer userId;

    private Integer articleId;

    private String content;

    private String agreeNum;

    private String disagreeNum;

    private Integer referenceId;

    private Date createdTime;

    private String title;


}