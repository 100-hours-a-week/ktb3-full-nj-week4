package com.example.dance_community.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String name;
    private String address;
    private String link;

    public Location() {

    }
}
