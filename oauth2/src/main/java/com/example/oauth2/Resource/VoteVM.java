package com.example.oauth2.Resource;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hcj on 18-7-26
 */
@Setter
@Getter
public class VoteVM {
    private String userId;
    private String authorId;
    private String articleId;
}
