# BANK ACCOUNT PROJECT

## PRESENTATION

Application that manages bank account transactions and transfers history

## The components

| Composant | Description |
|---|---|	
| bank-account | Maven parent project |
| bank-account-repository | maven module : manage the application models with their service |
| bank-account-ws | maven module : offers the different web services |

## Prerequisites
* Maven 2 or higher
* JAVA 8 or higher

# Run

To launch the application :
	-On bank-account project (parent project)
		-mvn clean install (bank-account-ws)
	-On bank-account-ws
		-spring-boot:run 
		
With swagger you can see and test all services on http://localhost:8080/swagger-ui.html#/