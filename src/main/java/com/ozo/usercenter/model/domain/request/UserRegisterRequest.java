package com.ozo.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -5437737070364832074L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
