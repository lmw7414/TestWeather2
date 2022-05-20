package com.example.weather.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScoreFilter {

    private String location;

    private String weather;

    private Integer wind;
}
