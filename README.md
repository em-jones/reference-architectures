# A Kotlin-Based, Event-Sourced, DD-Designed Cash Register

Greetings!
Thank you for taking your time to review this reference architecture.

This is my first time using Kotlin, so please feel invited to offer feedback relevant to code conventions and API
use/abuse.

## Dependencies
- maven
- JDK 11
- Kotlin
### Optional
- [SDKman](https://sdkman.io)
- [make](https://www.gnu.org/software/make/)

## Running 
### Tests
```bash
make test
```
OR
```bash
mvn test
```
### Application
```bash
make run
```
OR
```bash
mvn exec:java
```

## Modules
High-level code organization makes use of DDD and Onion-architecture concepts
As a result, logic has been separated by `Application` and `Domain` layers.

### Domain
The top-level package has all of the logic used 

#### Denominations
- Domain logic as it relates to money can be found here
#### Events
- Definitions for successful system interactions can be found here

#### Operations
- Definitions for attempted system interactions can be found here

## Resources
[Domain Modeling Made Functional](https://pragprog.com/titles/swdddf/domain-modeling-made-functional/)
