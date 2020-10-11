# BANK ACCOUNT PROJECT

## PRESENTATION

Application which manage bank account transactions and transfers history

## The components

| Composant | Description |
|---|---|	
| bank-account | Maven parent project |
| bank-account-repository | maven module : manage the application models with their services |
| bank-account-ws | maven module : offers the different web services |

## Prerequisites
* Maven 2 or higher
* JAVA 8 or higher

## Run
* Go to bank-account project (parent project) and launch maven command "mvn clean install"
* Go to bank-account-ws and launch maven command "spring-boot:run "
* With swagger you can see and test all services on http://localhost:8080/swagger-ui.html#/

