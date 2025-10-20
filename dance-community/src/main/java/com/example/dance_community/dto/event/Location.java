package com.example.dance_community.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Location {
    private String name;
    private String address;
    private String link;
}
