package hu.fitforfun.model.request;

import lombok.Data;

@Data
public class PasswordResetModel {
    private String token;
    private String password;

}
