# Transactions-Service
[![CircleCI](https://circleci.com/gh/REduard/Transactions-Service/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/REduard/Transactions-Service/tree/master)
[![CodeFactor](https://www.codefactor.io/repository/github/reduard/transactions-service/badge)](https://www.codefactor.io/repository/github/reduard/transactions-service)

# Functionality

## Specs

### Transactions

Every Time a new transaction happened, this endpoint will be called.

Where:
* amount is BigDecimal with scale 2 and roundingMode ROUND_HALF_UP specifying transaction amount,
* timestamp is epoch in millis in UTC time zone specifying transaction time

#### POST
Endpoint to add new transaction
```http
POST /transactions
Content-Type: application/json

{
    "amount": 12.32,
    "timestamp": 1478192204000
}

```
#### Success response sample
```http
201 Created
204 No Content - timestamp is older than 60 seconds
```

#### Error response sample
```http
400 Bad Request - Bad JSON format
400 Bad Request - Bad JSON format
422 Unprocessable Entity - unparsable parameter
422 Unprocessable Entity - transaction has timestamp in the future
```

#### DELETE
Endpoint to delete all transactions
```http
DELETE /transactions
Content-Type: application/json
```
#### Success response sample
```http
204 No Content
```

### Statistics
This is the main endpoint of this task, returns the statistic based on the transactions which happened
in the last 60 seconds.

#### GET
Endpoint to get statistics
```http
GET /statistics
Accept: application/json
```

#### Response sample
```http
200 OK
Content-Type: application/json

{
    "sum": 1000,
    "avg": 100,
    "max": 200,
    "min": 50,
    "count": 10
}
```

Where:
* sum is a double specifying the total sum of transaction value in the last 60
seconds
* avg is a double specifying the average amount of transaction value in the last
60 seconds
* max is a double specifying single highest transaction value in the last 60
seconds
* min is a double specifying single lowest transaction value in the last 60
seconds
* count is a long specifying the total number of transactions happened in the last
60 seconds

Other requirements, which are obvious, but also listed here explicitly:
* The API have to be threadsafe with concurrent requests
* The project should be buildable, and tests should also complete successfully. e.g. If maven is used, then mvn clean install should complete successfully.
* The API should be able to deal with time discrepancy, which means, at any point of time, we could receive a transaction which have a timestamp of the past
* Make sure to send the case in memory solution without database (including in-memory database)
* Endpoints have to execute in constant time and memory (O(1))
