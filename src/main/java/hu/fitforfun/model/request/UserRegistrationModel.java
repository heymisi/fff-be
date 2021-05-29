package hu.fitforfun.model.request;

import lombok.Data;

@Data
public class UserRegistrationModel {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String telNumber;
    private String shippingCountry;
    private String shippingCounty;
    private String shippingCity;
    private String shippingStreet;
    private String billingCountry;
    private String billingCounty;
    private String billingCity;
    private String billingStreet;
}
