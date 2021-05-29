package hu.fitforfun.model.request;

import lombok.Data;

@Data
public class UserLoginRespondModel {
    String token;
    Long userId;
    String role;
}
