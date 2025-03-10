package com.overmighties.pubber.feature.alcohol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlcoholUiState {
    private Long drink_id;
    private String name;
    private String brewery;
    private String short_des;
    private String long_des;
    private String brewery_des;
    private String photo_path;
    private String hopiness;
    private String maltiness;
    private String alcoholContent;
    
    public void changeAllData(String name, String brewery, String short_des,
                              String long_des, String brewery_des, String photo_path, 
                              String hopiness, String maltiness, String alcoholContent){
        this.name = name;
        this.brewery = brewery;
        this.short_des = short_des;
        this.long_des = long_des;
        this.brewery_des = brewery_des;
        this.photo_path = photo_path;
        this.hopiness = hopiness;
        this.maltiness = maltiness;
        this.alcoholContent = alcoholContent;
    }
}
