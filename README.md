# Hi, I'm Mihaela! 👋
And here you can find documentation of the Cinema City Clone project


## 🚀 About Me
I'm a back-end software developer, passionate about solving problems using technology 
## 🛠 Skills
Java, OOP, Spring Boot, Rest APIs, MySQL


## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://mihaelaantalute.github.io/)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mihaela-antalute-4248b7263/)



##Cinema city clone


This application can be used by any cinema which needs a digital ticketing system, and allows users to purchase tickets for their favorite movies.




## Features
As a client, I can:
- view available movie projections
- buy ticket for a projection

As an admin, I can:
- add cinema rooms
- view statistics for sold tickets


## Build with
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

## Demo

Insert gif or link to demo


## API Reference

#### Add a new cinema room

```http
  POST /cinema/add
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `json` | **Required**.  The properties of cinema to be added  |

Request body example:

```json
{
  "extraPrices": [
    {
      "endingRow": 0,
      "extraPrice": 0,
      "startingRow": 0
    }
  ],
  "numberOfCols": 0,
  "numberOfRows": 0
}
```  


#### Updatea cinema room

```http
  PUT /cinema/update/${cinemaRoomId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**.  Id of cinema room to update|
|  `body` | `json` | **Required**.  The properties of cinema to be updated |

Request body example:

```json
{
  "extraPrices": [
    {
      "endingRow": 0,
      "extraPrice": 0,
      "startingRow": 0
    }
  ],
  "numberOfCols": 0,
  "numberOfRows": 0
}
``` 


#### Get a cinema room
```http
  GET /cinema/${cinemaRoomId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**.   Id of cinema room to fetch  |



## API Authentication and Authorization
There are only two requests which don't require authorization headers.
## Prerequisites

For building and running the application you need:
- JDK 1.8 or higher
- Maven 3

For deploying on Heroku you need:
- GIT 
- Heroku CLI

## Dependencies
You don't need any additional dependencies.
All dependecies related to database management, server management, security management and so on, will be automatically injected by Maven using the pom.xml file located in the root folder of the project.

## Installation
Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```






## Run Locally

Use maven to build the app and, to run it, and to start the local embedded Tomcat server

```bash
  mvn spring-boot:run
```


## Running Tests

To run tests, run the following command

```bash
  mvn test
```



## Environment Variables

You can deploy this project using Heroku or other providers as well, by specifying the following environment variables:

`PROFILE`

`MYSQL_URL`

`MYSQL_USER`

`MYSQL_PASS`




## Deployment


To deploy this project run

```bash
  git push heroku master
```



## Usage/Examples

You cand use the a demo version of the app, using SwaggerUI and following this link:

```javascript
https://obscure-peal.heroku.app/swagger-ui/
```

First, obtain an access token by running the /authenticate endpoint with username "user" and password "pass", which will grant you admin rights in the application.

![App Screenshot](https://i.imgur.com/VTQibfA_d.webp?maxwidth=760&fidelity=grand)

After that, authorize yourself by clicking the authorize button and copy-pasteing the token from the response.

![App Screenshot](https://i.imgur.com/arTX2Ke_d.webp?maxwidth=760&fidelity=grand)

From now on, you can use all other available endpoints, as per swagger documentation.



## Roadmap

In the future, application can be extended with following:

- ability of client to return bought tickets

- ability of client to sign in using SSO



## Badges

![Maintained](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![GIT](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![Heroku](https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![JWT](https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink)



