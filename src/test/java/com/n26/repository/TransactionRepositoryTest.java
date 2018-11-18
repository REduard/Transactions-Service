package com.n26.repository;

import com.n26.controllers.v1.AbstractRestControllerTest;
import com.n26.domain.Transaction;
import lombok.Data;
import org.junit.Before;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TransactionRepositoryTest implements AbstractRestControllerTest {

    private TransactionRepository transactionRepository;


    final static Transaction testTransaction = new Transaction(BigDecimal.valueOf(1223.45), LocalDateTime.now(Clock.systemUTC()));
    final static Transaction testTransaction2 = new Transaction(BigDecimal.valueOf(14443.45), LocalDateTime.now(Clock.systemUTC()));

    @Before
    void setUp() throws Exception {
        transactionRepository = new TransactionRepository();

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void save(ValidationBean validationBean) {
        //given
        transactionRepository = new TransactionRepository();

        //when
        for (Transaction t : validationBean.getTransactions()) {
            transactionRepository.save(t);
        }

        //then
        assertThat(transactionRepository.getLatestTransactions().size()).isEqualTo(validationBean.getExpectedTransactionsToPass());

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void getLatestTransactions(ValidationBean validationBean) {
        //given
        transactionRepository = new TransactionRepository();
        for (Transaction t : validationBean.getTransactions()) {
            transactionRepository.save(t);
        }

        //when
        List<Transaction> result = transactionRepository.getLatestTransactions();

        //then
        assertThat(result.size()).isEqualTo(validationBean.getExpectedTransactionsToPass());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void deleteAll(ValidationBean validationBean) {
        //given
        transactionRepository = new TransactionRepository();
        for (Transaction t : validationBean.getTransactions()) {
            transactionRepository.save(t);
        }

        //when
        transactionRepository.deleteAll();

        //then
        assertThat(transactionRepository.getLatestTransactions().size()).isEqualTo(0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void getTransactions(ValidationBean validationBean) {
        //given
        transactionRepository = new TransactionRepository();
        for (Transaction t : validationBean.getTransactions()) {
            transactionRepository.save(t);
        }

        //when
        int transactionsCount=transactionRepository.getTransactions().size();

        //then
        assertThat(transactionsCount).isEqualTo(validationBean.getExpectedTransactionsToPass());

    }

    static Stream<ValidationBean> scenarios() {
        return Stream.of(new ValidationBean("Test null transactions", Arrays.asList(null, null), 0),
                new ValidationBean("Test null params transactions", Arrays.asList(new Transaction(), new Transaction()), 0),
                new ValidationBean("Test null and valid transaction", Arrays.asList(null, testTransaction), 1),
                new ValidationBean("Test valid transactions", Arrays.asList(testTransaction, testTransaction2), 2));
    }

    @Data
    static class ValidationBean {
        String testName;
        List<Transaction> transactions;
        int expectedTransactionsToPass;

        public ValidationBean(String testName, List<Transaction> transactions, int expectedTransactionsToPass) {
            this.testName = testName;
            this.transactions = transactions;
            this.expectedTransactionsToPass = expectedTransactionsToPass;
        }

        @Override
        public String toString() {
            return testName;
        }
    }
}