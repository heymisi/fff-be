package hu.fitforfun.model.request;

import lombok.Data;

@Data
public class TransactionItemRequestModel {
    private Long shopItemId;
    private Integer quantity;
    private Double price;
}
