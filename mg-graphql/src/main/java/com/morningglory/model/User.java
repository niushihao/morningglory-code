package com.morningglory.model;

import lombok.Data;

import java.util.List;

/**
 * @author qianniu
 * @date 2023/3/13
 * @desc
 */
@Data
public class User {

    private int userId;
    private String userName;
    private String realName;
    private String email;
    private List<Post> posts;

}
