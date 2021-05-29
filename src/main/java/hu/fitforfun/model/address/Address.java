package hu.fitforfun.model.address;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "address")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Address.class)
public class Address extends BaseEntity {

    @Column(name = "country")
    private String country;

    @Column(name = "county")
    private String county;

    @OneToOne
    private City city;

    @Column(name = "street")
    private String street;

}
