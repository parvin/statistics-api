# statistics-api

This is a sample Spring Boot REST API example.

It's aim is to calculate realtime statistics for the last 60 seconds of API usage.

## API Description
It has two REST APIs namely /transactions and /statistics

**POST /transactions**

This endpoint is called whenever new transaction happens.

BODY

    {
        "amount" : double_value
        "timestamp" : epoch_time
    }

**GET /statistics**

It returns the statistics of transactions happened in the last 60 seconds

RESPONSE

    {
        "sum" : double_value
        "avg" : double_value
        "max" : double_value
        "min" : double_value
        "count" : long_value
    }

## Usage

1. Maven build by using following command

    mvn clean install

2. To run application use following command

    java -jar statistics-api.jar


