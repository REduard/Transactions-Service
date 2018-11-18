package com.n26.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction data model
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private BigDecimal amount;
    private LocalDateTime timestamp;
}
