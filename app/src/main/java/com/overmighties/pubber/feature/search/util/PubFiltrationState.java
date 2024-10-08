package com.overmighties.pubber.feature.search.util;


import lombok.Data;

@Data
public class PubFiltrationState {
    private Integer compatibility;
    private Integer allCompatibility;
    private Boolean doesTimeFit;
    private Boolean doesDistancefit;

    public PubFiltrationState(Integer compatibility, Boolean time, Boolean disctance){
        this.compatibility = compatibility;
        this.allCompatibility = -1;
        this.doesTimeFit = time;
        this.doesDistancefit = doesTimeFit;
    }
}
