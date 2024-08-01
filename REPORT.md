# FIA-Local Service Finder

## Table of Contents

1. [Introduction](#introduction)

2. [Design Problem](#design-problem)<br>
    2.1 [Problem Definition](#problem-definition)<br>
    2.2 [Design Requirements](#design-requirements)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;2.2.1 [Functions](#functions)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;2.2.2 [Objectives](#objectives)
        <br>&nbsp;&nbsp;&nbsp;&nbsp;2.2.3 [Constraints](#constraints)

3. [Solution](#solution)


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

## Solution 1 

### Basic Web Application

#### Description:
- This solution involves creating a basic web application where users can signup and manually list their services or request services through simple forms. 

#### Reasons for Not Selecting:
- <b>Limited Features:</b> This solution lacks advanced features like serch bar to search for a specific service that the user intrested for, insteed of scrolling untill they find a service. 
- <b>Scalability Issues:</b> The basic setup might not handle a large number of users and simultaneous requests efficiently.
- <b>User Experience:</b> The application might be too simplistic and not user-friendly, affecting overall user satisfaction for the users.
- <b>Testing</b> Low reliability. Most test failed.

## Solution 2

### Enhanced Web Platform with Initial MVPs

#### Description:
- This improved solution involves a more advanced web platform that includes the core functionalities of service listing, search, and user authentication. It also begins to integrate a user-friendly interface and initial security measures.

#### Improvements Over Solution 1:
- <b>User Experience:</b> This solution focuses on creating a user-friendly and visually appealing interface.
- <b>Security:</b> Enhanced security features ensure user data protection.
- <b>Scalability:</b> Better infrastructure planning to handle more users and requests compared to Solution 1.
- <b>Testing</b> Low reliability. Most test failed.

#### Reasons for Not Selecting:
- <b>Partial Feature Set:</b> While better than Solution 1, this solution still lacks a comprehensive review system.
- <b>Testing</b> Improved reliability but still limited functionality. Testing may not be fully developed to ensure data integrity and system reliability.

## Solution 3

### Final Solution: Comprehensive Web Platform with Full MVPs

#### Description:
- The final solution incorporates all planned MVPs, offering a robust platform with more features including that users can update the status of there services where they can have them Active, in progress or completed, also the users can renow there post so it comes up at top again, instead of deleting the post and reposting it for the post to be renewed. 

#### Reasons for Selection:
- <b>Complete Feature Set:</b> This solution meets all design requirements, including comprehensive service management, user-friendly interface, and secure authentication.
- <b>Scalability:</b> and Reliability: Designed to handle a high number of users and simultaneous requests efficiently.
- <b>User Experience:</b>User Experience: Creates an easy-to-use and enjoyable interface for better user satisfaction.
- <b>Testing</b> High reliability. All test cases passed. General features for both customers and employees

### 3.3.1 Components

#### Components Used:

- <b>Frontend:</b> React.js for building a dynamic and responsive user interface.
- <b>Backend:</b> Spring Boot for handling server-side logic and database interactions.
- <b>Database:</b> PostgreSQL for reliable and scalable data storage.
- <b>Authentication:</b> JWT (JSON Web Token) for secure user authentication.
- <b>Hosting:</b> Raspberry Pi for hosting the platform, ensuring a cost-effective solution.
