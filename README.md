# Dice Rolls Simulation Task

This application is a result of a task completion for Avaloq 

## Build & Run
### Package

```
./mvnw clean package
```
### Run jar
To start the application run:
```
java -jar target/AvaloqDiceTask-1.0.jar
```
### Tests
To launch the application's tests, run:
```
./mvnw clean test
```

## Solution details
This application has three tiers that includes controllers, services and repositories. 
There were no authentication methods used, however in real life we should provide a security layer to filter unauthorised requests. This could be implemented using spring security.

For demonstration purposes I've used the H2 in memory database.
During the development I came up with a need to have a custom repository.
It allows making custom queries, e.g., fetching all simulations reduced the processing x10 times compared to related entities.

There were no details given about the amount of clients using that API. In case it would be big, parallelization could be implemented to not block the process.   

## Endpoints

### Create new Simulation
```
http://localhost:8080/api/dice/simulation?diceNumber=3&diceSides=6&rollsNumber=100
```
#### Parameters
diceNumber, Not Null, min 1

diceSides, Not Null, min 4

rollsNumber, Not Null, min 1
#### Example response
```json
{
  "timesPerTotal": {
    "3": 6,
    "4": 14,
    "5": 33,
    "6": 51,
    "7": 73,
    "8": 89,
    "9": 106,
    "10": 137,
    "11": 131,
    "12": 109,
    "13": 84,
    "14": 75,
    "15": 50,
    "16": 30,
    "17": 10,
    "18": 2
  }
}
```

### Get all Simulations
```
http://localhost:8080/api/dice/simulations
```
#### Example response
```json
{
    "totalSimulations": 15,
    "combinations": {
        "2x4": 600,
        "3x6": 9200,
        "6x8": 200
    }
}
```
### Dice distribution
```
http://localhost:8080/api/dice/distribution/{diceNumber}x{diceSides}
```
####Parameters
diceNumber, min 1

diceSides, min 4

#### Example response
```json
{
  "distributionMap": {
    "3": "0.53%",
    "4": "1.29%",
    "5": "2.72%",
    "6": "5.00%",
    "7": "6.56%",
    "8": "9.75%",
    "9": "11.06%",
    "10": "12.70%",
    "11": "12.52%",
    "12": "11.65%",
    "13": "9.72%",
    "14": "7.29%",
    "15": "5.00%",
    "16": "2.63%",
    "17": "1.17%",
    "18": "0.41%"
  }
}
```