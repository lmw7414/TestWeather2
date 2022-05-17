package com.example.weather.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyDust {

    private Double pm; // 미세먼지 수치

    private String dt; // 시간
}
