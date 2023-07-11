<div align="center">
<h2 align="center">CVQS Car Defect App</h2>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#running-locally">Running Locally</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

CVQS App is a backend application created for Toyota to facilitate defect recording
by terminal operators for detecting potential issues in vehicles manufactured in factories,
and to streamline the listing of these defects by team leaders. This project is built using
Spring Boot, Spring Security with JWT Authentication, Spring Data JPA, and Hibernate with
PostgreSQL. Additionally, Docker Compose is used to run the project.

<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

* [Java 17](https://www.java.com/tr/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [PostgreSQL](https://www.postgresql.org/)
* [Hibernate](https://hibernate.org/)
* [Log4j2](https://logging.apache.org/log4j/2.x/)
* [Docker](https://www.docker.com/)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Running Locally

This project is a [Spring Boot](https://spring.io/projects/spring-boot) application
built using [Maven](https://spring.io/guides/gs/maven/). You can run it locally on your machine
using Docker Compose. Follow the instructions below to set up and run the application:

### Prerequisites

Make sure you have [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) installed on your machine.

### Installation

#### 1. Clone the repository:
  ```sh
  $ git clone https://github.com/tastekinselcuk/32bit_CVQS_App
  $ cd 32bit_CVQS_App
  ```
#### 2.Build the Docker image:
  ```sh
  $ docker-compose build
  ```
### Running the Application
To start the application, run the following command:
  ```sh
  $ docker-compose up
  ```
  
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage

You can try it locally or on Postman.

***Here are some of the endpoints:***

### Terminal List Controller

### {POST} Register `/api/auth/register`**
* Send `username` and `password` as `Json`.
* Return type is JSON containing a JWT token.

### {POST} Login `/api/auth/authenticate`**
* Send `email`, `password`, `name`, `surname` as `Json`.
* Return type is JSON containing a JWT token.

### Car Defect Controller

### {GET} Get All Car Defects `/api/carDefect/GetAllCarDefects`**
* Returns a list of all car defects as `CarDefectServiceDTO`.
* Returns a `ResponseEntity` containing a list of all car defects as `CarDefectServiceDTO`.

### {POST} Save Car Defect `/api/carDefect/save`**
* HTTP POST request that saves a car defect report.
* Parameters:
  * `carId`: The ID of the car that the defect is related to (required).
  * `defectPartCategory`: The category of the defective part (required).
  * `defectPartName`: The name of the defective part (required).
  * `reportedBy`: The name of the person who reported the defect (default: "selcuk").
  * `latitude`: The latitude coordinate of the location where the defect was reported (required).
  * `longitude`: The longitude coordinate of the location where the defect was reported (required).
  * `terminalName`: The name of the terminal where the defect was reported (required).
* Returns a `ResponseEntity` object with HTTP status 200 (OK) if the request is successful or with HTTP status 500 (INTERNAL_SERVER_ERROR) if there is an error.

### Car Defect List Controller

### {GET} Get Car Defects `/page/carDefects`**
* Returns a list of all car defects as `CarDefectServiceDTO`.
* Requires the role `TEAMLEAD`.
* Returns a `ResponseEntity` containing a list of all car defects as `CarDefectServiceDTO`.

### {GET} Get Pageable Car Defects `/page/PageableCarDefects`**
* Returns a pageable list of all car defects as `CarDefectServiceDTO`.
* Requires the role `TEAMLEAD`.
* Returns a `ResponseEntity` containing a pageable list of all car defects as `CarDefectServiceDTO`.

### {GET} Get Defects `/page/defects`**
* Returns a list of all defects.
* Requires the role `TEAMLEAD`.
* Returns a `Page` of all defects.


<p align="right">(<a href="#top">back to top</a>)</p>
