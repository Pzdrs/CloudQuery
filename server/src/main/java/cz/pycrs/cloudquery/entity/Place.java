package cz.pycrs.cloudquery.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    private int id;
    private String cityName;
    private String countryCode;
    private ZoneOffset zoneOffset;

    public Place(int id, String name, String countryCode, ZoneOffset zoneOffset) {
        this.id = id;
        this.cityName = name;
        this.countryCode = countryCode;
        this.zoneOffset = zoneOffset;
    }
}
