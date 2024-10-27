package com.overmighties.pubber.app.designsystem.AlcoholAlertDialog;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlcoholAlertDialogUiState {
    private String name;
    private String short_des;
    private String long_des;
    private String photoPath;
    //parametrs for beer [0]-hoppiness, [1]-maltiness, [2]-alcohol percantage for cocktails [0]-booziness, [1]-sweetness, [2]-alcohol percentage
    private List<Float> parameters;
}
