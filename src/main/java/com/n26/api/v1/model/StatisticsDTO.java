package com.n26.api.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.util.StatisticsSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Data transfer object for statistics
 */
@Data
@Accessors(chain = true)
public class StatisticsDTO {

    @ApiModelProperty(notes = "total sum of transaction value in the last 60 seconds")
    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal sum;

    @ApiModelProperty(notes = "average amount of transaction value in the last 60 seconds")
    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal avg;

    @ApiModelProperty(notes = "highest transaction value in the last 60 seconds")
    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal max;

    @ApiModelProperty(notes = "lowest transaction value in the last 60 seconds")
    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal min;

    @ApiModelProperty(notes = "total number of transactions that happened in the last 60 seconds")
    private Integer count;

    public static StatisticsDTO getEmptyStatistics() {
        return new StatisticsDTO().setSum(BigDecimal.ZERO)
                .setAvg(BigDecimal.ZERO)
                .setMax(BigDecimal.ZERO)
                .setMin(BigDecimal.ZERO)
                .setCount(0);
    }
}
