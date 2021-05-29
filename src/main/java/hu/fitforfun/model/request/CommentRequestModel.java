package hu.fitforfun.model.request;

import lombok.Data;

@Data
public class CommentRequestModel {
    private String message;
    private int rate;
    private Long userId;
}
