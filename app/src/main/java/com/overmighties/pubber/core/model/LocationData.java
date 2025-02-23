package com.overmighties.pubber.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
public final class LocationData {
    public static final LocationData BASE_VAL= new LocationData(-1L,0D,0D);
    private final Long time;
    private final Double longitude;
    private final Double latitude;
}
