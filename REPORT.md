# FIA-Local Service Finder

## Table of Contents

1. [Introduction](#introduction)

2. [Design Problem](#design-problem)<br>
    2.1 [Problem Definition](#problem-definition)<br>
    2.2 [Design Requirements](#design-requirements)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;2.2.1 [Functions](#functions)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;2.2.2 [Objectives](#objectives)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;2.2.3 [Constraints](#constraints)

3. [Solution](#solution)<br>
    3.1 [First Solution](#first-solution)<br>
    3.2 [Second Solution](#second-solution)<br>
    3.3 [Final Solution](#final-solution)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;3.3.1 [Components](#components)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;3.3.2 [Features](#features)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;3.3.3 [Environmental, Societal, Safety, and Economic Considerations](#environmental-societal-safety-and-economic-considerations)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;3.3.4 [Tests And Results](#tests-and-results)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;3.3.5 [Limitations](#limitations)

4. [Team Work](#team-work)<br>
    4.1 [First Meeting](#first-meeting)<br>
    4.2 [Second Meeting](#second-meeting)<br>
    4.3 [Third Meeting](#third-meeting)<br>
    4.4 [Fourth Meeting](#fourth-meeting)<br>

5. [Resources](#resources)

# Introduction

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Local businesses frequently face challenges in growing their customer base due to intense competition, which result in their websites ranking lower in search results. To solve this issue, we are designing a platform called "FIA (Find It ALL)-Local-Service-Finder" that aims to enhance the online visibility of local businesses. This platform allows users to list their services as well as request services, thereby fostering a mutually beneficial ecosystem for businesses and customers alike.

# Design Problem

## Problem Definition

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Local businesses often struggle to grow due to high competition and lack of effective SEO (Search Engine Optimization), causing their websites to rank lower in search results. To address this issue, we are creating a platform where users can both list their services and request services, helping local businesses improve their online visibility and attract more customers.

## Design Requirements

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The design requirements for our platform are structured around a series of Minimum Viable Products (MVPs). Each MVP phase builds upon the previous one, progressively adding more functionality and enhancing the user experience.

### Functions

#### MVP 1
- <b>Create and manage service listings:</b> Users can add, update, and remove their service listings, providing details such as description, pricing, and availability
- <b>Search for and request services:</b> Users can search for specific services based on various criteria and send requests to service providers

#### MVP 2
- <b>Provide a user-friendly interface:</b> The platform will offer an intuitive and easy-to-navigate interface to enhance user experience
- <b>Ensure secure user authentication:</b> The system will include secure login mechanisms to protect user accounts and data

#### MVP 3
- <b>Implement a review and rating system:</b> Users can leave reviews and ratings for services they have used, helping others make informed decisions
- <b>Offer personalized recommendations:</b> The platform will suggest services to users based on their preferences and past activities


### Objectives

#### MVP 1

- <b>Comprehensive:</b> The platform should provide all necessary features for service listing, discovery, and management
- <b>Data Integrity:</b> Ensure that all data is accurate, consistent, and reliable by implementing thorough validation and testing mechanisms throughout the system

#### MVP 2
- <b>User-friendly:</b> The platform should be easy to use, with a clean and intuitive design
- <b>Responsive:</b> The platform should work well on different devices, including desktops, tablets, and smartphones
- <b>Secure:</b> Ensure the safety and privacy of user data through robust security measures

#### MVP 3
- <b>Efficient:</b> The system should handle multiple requests smoothly, with minimal delays
- <b>Scalable:</b> The platform should be able to grow and handle an increasing number of users and services
- <b>Reliable:</b> The system should have high uptime and be available when users need it

### Constraints

- <b>Economic factors:</b> The project must be developed within a budget of $1000. Cost-effective solutions should be sought to minimize expenses without compromising quality
- <b>Reliability:</b> The system must have high uptime and be reliable, ensuring that users can access the platform whenever needed. It should handle at least 100 simultaneous users without performance issues
- <b>Societal impacts:</b> The platform must positively impact local communities by helping local businesses improve their online visibility and attract more customers
- <b>Ethics:</b> The platform should adhere to ethical standards, ensuring fair treatment of all users, preventing discrimination, and avoiding misuse for fraudulent or harmful activities

# Solution

## First Solution

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Our first solution involved creating a simple listing platform where users could add and search for services. However, this solution lacked user authentication and the ability to update or delete posts. We did not select this solution because it did not meet our requirements for user interaction and security.

## Second Solution

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The second solution improved upon the first by adding user authentication and the ability to update and delete posts. While this solution addressed the basic functionalities, it still lacked update personal information and had a more complex user interface, which could potentially affect user experience. Thus, it was not selected as the final solution.

## Final Solution

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Our final solution includes all the desired features and satisfies the constraints. It allows users to register and log in, search for service posts, add new posts, update the status of their own posts, and update personal information. The final solution is better because it provides a comprehensive, user-friendly platform while ensuring security and meeting all functional requirements.

### Components

- <b>User Authentication:</b> Ensures secure register, login and account management.
- <b>Service Listings:</b> Allows users to add, update, and delete posts.
- <b>Search Functionality:</b> Enables users to search for services based on various criteria.
- <b>Post Status Management:</b> Users can update the status of their posts to active, in progress, or completed.
- <b>Profile Management:</b> Users can update their personal information.
- <b>Testing:</b> Ensures all functionalities work as expected.

### Features

| Feature                    | Description                            |
|----------------------------|----------------------------------------|
| User Registration & Login  | Secure user authentication             |
| Service Listings           | Add, update, delete service posts      |
| Search Functionality       | Search for services based on criteria  |
| Post Status Management     | Update status of posts                 |
| Profile Management         | Update personal information            |

### Environmental, Societal, Safety, and Economic Considerations

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Our design positively impacts local communities by enhancing the visibility of local businesses, fostering economic growth. Cost-effective solutions were employed to develop the platform within a $1000 budget without compromising quality. The system is designed to be reliable and safe, ensuring high uptime and secure user data management.

### Tests And Results

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;We designed test suites to verify user registration, login, service listing management, search functionality, and post status updates. Each test case was executed, ensuring all functionalities performed as expected. The tests confirmed the robustness and reliability of our solution.

<b>The following documents detail the various types of tests conducted:</b>

- [Boundary Value Testing](Testing/FirstIteration/BoundaryValueTesting.pdf)
- [Decision Tables Testing](Testing/FirstIteration/DecisionTablesTesting.pdf)
- [State Transition Testing](Testing/SecondIteration/StateTransitionTesting.pdf)
- [Use Case Testing](Testing/SecondIteration/UseCaseTesting.pdf)
- [DataFlow Testing](Testing/ThirdIteration/DataFlowTesting.pdf)
- [Equivalence Class Testing](Testing/ThirdIteration/EquivalenceClassTesting.pdf)
- [Integration Testing](Testing/ThirdIteration/IntegrationTesting.pdf)

### Limitations

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Some limitations include the initial lack of a review and rating system and personalized recommendations, which are planned for future enhancements. Additionally, the platform is currently hosted on a Raspberry Pi which is not designed to handle more than 100 simultaneous users, which may need to be scaled up as the user base grows.

# Team Work

## First Meeting

<b>Time:</b> May 20, 2024, 9:00 am to 11:30 am<br>
<b>Agenda:</b> Distribution of Project Tasks

| Team Member    | Previous Task | Completion State | Next Task           |
|----------------|---------------|------------------|---------------------|
| Feras Aljoudi         | N/A           | N/A              | User Registration & Login |
| Ihab Mohammad  | N/A           | N/A              |  Search Functionality   |
| Aman Padda  | N/A           | N/A              | Service Listings |

## Second Meeting

<b>Time:</b> June 08, 2024, 9:00 am to 11:00 am<br>
<b>Agenda:</b> Review of Individual Progress

| Team Member    | Previous Task           | Completion State | Next Task          |
|----------------|-------------------------|------------------|--------------------|
| Feras Aljoudi  | User Registration & Login | 100%              | Profile Management Testing |
| Ihab Mohammad  | Search Functionality  | 100%              | Search Functionality Testing   |
| Aman Padda  | Service Listings  | 100%             | Service Listings Testing |

## Third Meeting

<b>Time:</b> June 30, 2024, 9:00 pm to 11:15 pm<br>
<b>Agenda:</b> Finalizing Features and Testing Plan

| Team Member    | Previous Task                       | Completion State | Next Task               |
|----------------|-------------------------------------|------------------|-------------------------|
| Feras Aljoudi          | Profile Management Testing | 100%        | Boundary Value & Integration Testing |
| Ihab Mohammad  | Search Functionality Testing                    | 100%              | Decision Tables & Use Case Testing |
| Aman Padda  | Service Listings Testing              | 100%             | State Transition & Equivalence Class Testing |

## Fourth Meeting

<b>Time:</b> July 20, 2024, 9:15 pm to 11:15 pm<br>
<b>Agenda:</b> Review of Testing Results and Project Completion

| Team Member    | Previous Task                | Completion State | Next Task        |
|----------------|------------------------------|------------------|------------------|
| Feras Aljoudi          | Boundary Value & Integration Testing | 100%        | Final Review |
| Ihab Mohammad  | Decision Tables & Use Case Testing       | 100%        | Final Review |
| Aman Padda  | State Transition & Equivalence Class Testing    | 100%             | Final Review     |

# Resources

- [Presentation Slides](Presentation.pdf)
- <a href="https://www.youtube.com/watch?v=nFdElVBFcwE" title="Presentation Video">Presentation Video</a>