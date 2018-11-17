package com.n26.api.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.util.StatisticsSerializer;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsDTO {

    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal sum;

    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal avg;

    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal max;

    @JsonSerialize(using = StatisticsSerializer.class)
    private BigDecimal min;

    private Integer count;
}
