package hu.fitforfun.model.request;

import lombok.Data;

@Data
public class UserUpdateDuringTransactionRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String city;
    private String street;
}
