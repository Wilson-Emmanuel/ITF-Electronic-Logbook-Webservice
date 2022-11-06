
# ITF-Electronic-Logbook-Webservice

A fully functional and secure RESTful Backend API for automating the management of university students’ Industrial training program (aka SIWES).
SIWES is a mandatory industrial training program for undergraduate students in Nigeria, organized by [Industrial Training Fund (ITF)](https://itf.gov.ng/)


## Tools Used
- Java: 1.8
- Spring Boot: 2.4.5
- H2 Database
- Hibernate 
- Spring Fox: 3.0.0


## Setup Instructions

### 🌟 Running the included `jar` file 
A built `jar` file of the project has been included in the `build_v4` folder.

To `run` the included `jar` file, open a terminal on the same folder where the `jar` file is located and execute the following command: 

 `java -jar ITF-eLogbook-Webservice.jar`.

 The running application can be accessed at `localhost:9092`.


 ### 🌟🌟 Building a new `jar` file 
 If necessary, you can edit the `application.properties` file and then build a new `jar` file by running the maven command: `mvn package`.


## Database
This project was developed using an embedded filebase `H2` database. Once the application starts running, the database directory will automatically be created on the same location as the running `jar` file. The database GUI can be accessed at `localhost:9092/h2-console` with the following credentials:

* <mark>Driver class</mark> : `org.h2.Driver`
* <mark>JDBC URL</mark> : `jdbc:h2:file:./data/itflogbook`
* <mark>User Name</mark> : `logbook`
* <mark>Password</mark> : `logbook`

## Documentation
This project uses `swagger` dependencies to generate beautiful html based documentations for the APIs. The documentations can be accessed at 

`http://localhost:9092/swagger-ui/index.html`

## Authentication
This project uses `bearer token` based security to authenticate most of the requests. After login, a token is returned and such token are expected in subsequent requests for authorization. Authenticated requests also expected a header called `x-client-request-key` with value of `kdnonoeno#@2lsn`. These functionalities can be changed in the `application.properties` file.
