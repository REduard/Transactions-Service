package com.n26.controllers.v1;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.services.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(StatisticsController.BASE_URL)
public class StatisticsController {
    public static final String BASE_URL = "/api/v1/statistics";

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StatisticsDTO getStatistics(){
        return statisticsService.getStatistics();
    }
}
