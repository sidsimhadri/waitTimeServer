package com.example.waitTimeServer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeeklyHourlyBin {
    private String dayOfWeek;
    private int hour;
    private double averageValue;
}