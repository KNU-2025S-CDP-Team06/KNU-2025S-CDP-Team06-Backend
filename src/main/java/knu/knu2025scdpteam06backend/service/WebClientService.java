package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.dto.sales.RealTimeSalesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebClientService{

    private final WebClient webClient;

    public RealTimeSalesDto getRealTimeSalesData(String mbId, String date){

        RealTimeSalesDto realTimeSalesDto = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("mb_id", mbId)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToMono(RealTimeSalesDto.class)
                .block();
        return realTimeSalesDto;
    }

}