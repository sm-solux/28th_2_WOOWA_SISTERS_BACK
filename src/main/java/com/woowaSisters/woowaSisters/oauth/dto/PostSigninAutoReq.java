package com.woowaSisters.woowaSisters.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSigninAutoReq {
    private String email;
    private String password;
}
