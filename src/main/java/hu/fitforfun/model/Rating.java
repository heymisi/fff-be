package hu.fitforfun.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {

    @Column(name = "value")
    private double value;

    @Column(name = "counter")
    private int counter;

    public Rating() {
        value = 0.0;
        counter = 0;
    }
}
