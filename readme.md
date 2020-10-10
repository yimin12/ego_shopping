# Ego Shopping Website

[![Author](https://img.shields.io/badge/Author-YiminH-orange)](https://www.linkedin.com/in/yimin-huang-amc/)

## Preface

I spent a month creating this e-commerce site project. Theoretically, this website can handle hundreds of millions of traffic at the same time. In China, this level of e-commerce platform has many, such as [Tmall](https://www.tmall.com/), [JD](https://www.jd.com/index.html) or [PDD](https://m.pinduoduo.com/). Of course, this kind of project is still far from the leading enterprises. I can't implement more details on my own in such a short time. This project mainly focuses on providing Service-Oriented Architecture and an example of handling high traffic volume.I hoped that some small and medium-sized projects can be solved after knowing this e-commerce project. Unlike today's popular Microserver Architecture, this project is essentially a distributed project which is friendly to some architect beginners. Welcome to have a discussion about it.

This repository contains:

1. [The specification](spec.md) for how a standard e-commerce architecture should look.
2. How different modules interact with each other.
3. General ideas about why we use these techniques for implementations.
4. Simple introduction of e-commerce architecture.

Ego website is temporarily shutdown for the reason that I do not want to maintain server's fee.


## Table of Contents

- [Background Introduction](#background introduction)
- [Configuration](#configuration)
- [Usage](#usage)
    - [Generator](#generator)
- [Badge](#badge)
- [Example Readmes](#example-readmes)
- [Related Efforts](#related-efforts)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Background Introduction

### E-commerce architecture

B2B Architecture
: Business-to-business (B2B or, in some countries, BtoB) e.g. Alibaba

B2C Architecture
: Business-to-client e.g. JD, Tmall, Amazon, WayFair

C2C Architecture
: Client-to-Client e.g. Ebay, XianYu

O2O Architecture
: Online-to-Offline e.g. MeiTuan, Elema

### Technology selection

| Selection |            |
|   :---:   |    :---:   |
|   JSP     | Spring AMQP|  
|   JQuery  | Spring Data|
|   EasyUI  | Spring Security|
| SpringBoot| HttpClient |
| SpringMVC | RestTemplate|
|  MyBatis  | MyBatis PageHelper|
|   Dubbo   | FastDFS-Java-Client|
|   Druid   | LogBack    |
| MyBatis Generator| 

### Developed Environment

| Environment |   Version   | 
|    :---:    |    :----:   |   
|  Maven      |    3.6.3    |  
|Linux(Centos)|    8.0.1    |        
|   Itellij   |    11.0.6   |        
|  Zookeeper  |    3.5.5    |
|  FastDFS    |    5.0.8    |
|    Nginx    |    1.16.1   |
|    Solr     |    8.2.0    |
|  RabbitMQ   |    3.7.17   |
|    Redis    |    5.0.5    |
|    MyCat    |    1.6      |
|    MySQL    |    5.7.27   |
|   Tomcat    |    9.0.38   |

### Functional module division

Split according to the function module and divide the functions that need to be completed into the following items.

- [ego_parent](#ego_parent) : Parent module
    - [ego_pojo](#ego_pojo) : Real entity module
    - [ego_api](#ego_api) : Application Programming Interface
    - [ego_mapper](#ego_mapper) : Provide implementation to access database
    - [ego_provider](#ego_provider) : Implementation of ego_api
    - [ego_commons](#ego_commons) : All shared resources. e.g. Configurations, Utils class, Common Pojo 
    - [ego_manage](#ego_manage) : Backend System. 
    - [ego_portal](#ego_portal) : Portal
    - [ego_search](#ego_search) : Provide Solr Search Function
    - [ego_item](#ego_item) : Details of Product
    - [ego_cart](#ego_cart) : Cart
    - [ego_passport](#ego_passport) : Single point login system
    - [ego_trade](#ego_trade) : Trade System
    - [ego_redis](#ego_redis) : Redis Operations.
    - [ego_rabbitmq_sender](#ego_rabbitmq_sender) : Sending message to rabbitMQ
    - [ego_rabbitmq_receive](#ego_rabbitmq_receive) : Monitoring the message queue

> Remember: Only an architecture demo, not real world website



## Configuration

There are totally 10 servers

> Server_1 : Zookeeper (192.168.119.7)
>
> Server_2 : FastDFS (192.168.119.8)
>
> Server_3 : Redis (192.168.119.9)
>
> Server_4 : RabbitMQ (192.168.119.10)
>
> Server_5 : Solr (192.168.119.11)
>
> Server_7 : MySQL Master (192.168.119.12)
>
> Server_8 : MySQL Salve1 (192.168.119.13)
>
> Server_9 : MySQL Salve2 (192.168.119.14)
>
> Server_10 : MyCat (192.168.119.15)

Zookeeper and Eureka can both be used as service registry center. 





## License

[MIT](LICENSE) © Richard Littauer




