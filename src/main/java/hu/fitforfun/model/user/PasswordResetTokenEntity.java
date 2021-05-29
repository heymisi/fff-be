package hu.fitforfun.model.user;

import hu.fitforfun.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "password_reset_token")
public class PasswordResetTokenEntity extends BaseEntity {

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userDetails;
}
