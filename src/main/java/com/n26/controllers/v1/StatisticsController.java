package com.n26.controllers.v1;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.services.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.n26.util.LoggingUtil.getEnteringMethodMessage;

@Slf4j
@Api("Controller manage transactions statistics")
@RestController
@RequestMapping(StatisticsController.BASE_URL)
public class StatisticsController {
    static final String BASE_URL = "/statistics";

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @ApiOperation(value = "Get transactions statistics")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StatisticsDTO getStatistics() {
        log.debug(getEnteringMethodMessage("StatisticsController.getStatistics"));
        return statisticsService.getStatistics();
    }
}
