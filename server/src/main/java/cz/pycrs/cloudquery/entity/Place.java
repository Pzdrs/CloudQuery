package cz.pycrs.cloudquery.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;


@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    private int id;
    private String cityName;
    private String countryCode;
    private ZoneOffset zoneOffset;
    private String comment;

    public Place(int id, String name, String countryCode, ZoneOffset zoneOffset, String comment) {
        this.id = id;
        this.cityName = name;
        this.countryCode = countryCode;
        this.zoneOffset = zoneOffset;
        this.comment = comment;
    }
}
