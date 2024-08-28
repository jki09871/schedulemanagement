package com.sparta.schedulemanagement.service;

import com.sparta.schedulemanagement.dto.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTodayWeather() {
        String url = "https://f-api.github.io/f-api/weather.json"; // 주어진 API URL

        // API 응답을 배열로 받아온다.
        WeatherResponse[] responseArray = restTemplate.getForObject(url, WeatherResponse[].class);

        // 오늘의 날짜를 "MM-dd" 형식으로 가져온다.
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));

        if (responseArray != null) {
            // 배열을 순회하면서 오늘의 날짜와 일치하는 날씨 정보를 찾는다.
            for (WeatherResponse response : responseArray) {
                if (todayDate.equals(response.getDate())) {
                    return response.getWeather(); // 오늘의 날짜와 일치하는 날씨를 반환
                }
            }
        }

        return "Unknown"; // 오늘 날짜에 대한 정보가 없을 경우 기본값 반환
    }
}