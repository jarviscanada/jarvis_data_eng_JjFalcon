Table of contents
* [Introduction](#Introduction)
* include all first level titles

# Introduction
This project is a POC development of a new trading platform MVP to replace a legacy system built as
a monolithic application with a system built following the microservice architecture.

This project is a REST API that allows users to manage client profiles and accounts,
monitor portfolio performance and trade securities.

The microservices are auto-configured using SpringBoot and utilize Apache Tomcat as the embedded servlet.

The app is built within a Spring Framework to facilitate a decoupling via IoC.

The app fetches data from IEX Cloud via its REST API and persisted to a PostgreSQL database.

# Quick Start
## Prequiresites: 
- Docker v17.05 or higher
- 
  

- Docker scripts with description
	- build images
  - create a docker network
  - start containers
- Try trading-app with SwaggerUI (screenshot)

# Implemenation
## Architecture
- Draw a component diagram that contains controllers, services, DAOs, SQL, IEX Cloud, WebServlet/Tomcat, HTTP client, and SpringBoot. (you must create your own diagram)
- briefly explain the following components and services (3-5 sentences for each)
  - Controller layer (e.g. handles user requests....)
  - Service layer
  - DAO layer
  - SpringBoot: webservlet/TomCat and IoC
  - PSQL and IEX

## REST API Usage
### Swagger
What's swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?
### Quote Controller
- High-level description for this controller. Where is market data coming from (IEX) and how did you cache the quote data (PSQL). Briefly talk about data from within your app
- briefly explain each endpoint
  e.g.
  - GET `/quote/dailyList`: list all securities that are available to trading in this trading system blah..blah..
### Trader Controller
- High-level description for trader controller (e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)
- briefly explain each endpoint
### Order Controller
- High-level description for this controller.
- briefly explain each endpoint
### App controller
- briefly explain each endpoint
### Optional(Dashboard controller)
- High-level description for this controller.
- briefly explain each endpoint

# Test
How did you test your application? Did you use any testing libraries? What's the code coverage?

# Deployment
- docker diagram including images, containers, network, and docker hub
e.g. https://www.notion.so/jarviscanada/Dockerize-Trading-App-fc8c8f4167ad46089099fd0d31e3855d#6f8912f9438e4e61b91fe57f8ef896e0
- describe each image in details (e.g. how psql initialize tables)

# Improvements
If you have more time, what would you improve?
- at least 3 improvements
