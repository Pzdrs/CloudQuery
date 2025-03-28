package cz.pycrs.cloudquery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Country {
    protected Country() {}

    @Id
    private String code;

    @Column(nullable = false)
    private String name;

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
