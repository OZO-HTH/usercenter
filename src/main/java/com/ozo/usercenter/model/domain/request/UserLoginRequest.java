package com.ozo.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -5539228241777909987L;
    private String userAccount;
    private String userPassword;
}
