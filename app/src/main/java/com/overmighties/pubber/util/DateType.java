package com.overmighties.pubber.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class DateType {
    @Setter
    private String time;
    private final boolean type;
}
