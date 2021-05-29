package hu.fitforfun.model.request;

import lombok.Data;

@Data
public class OrderResponse {
    private Integer total;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
