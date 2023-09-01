package com.morningglory.model;

import lombok.Data;

/**
 * @author qianniu
 * @date 2023/3/13
 * @desc
 */
@Data
public class Post {

    private int postId;
    private String title ;
    private String text;
    private String  category;
    private User user;

}
