package hu.fitforfun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contact_data")
public class ContactData extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "tel_number")
    private String telNumber;
}
