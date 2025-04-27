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
    private double latitude, longitude;

    public Place(int id, String name, String countryCode, ZoneOffset zoneOffset, double latitude, double longitude) {
        this.id = id;
        this.cityName = name;
        this.countryCode = countryCode;
        this.zoneOffset = zoneOffset;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
