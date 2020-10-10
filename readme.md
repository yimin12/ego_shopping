# Ego Shopping Project

[![Author](https://img.shields.io/badge/Author-YiminH-orange)](https://www.linkedin.com/in/yimin-huang-amc/)

A standard style for README files

Your README file is normally the first entry point to your code. It should tell people why they should use your module, how they can install it, and how they can use it. Standardizing how you write your README makes creating and maintaining your READMEs easier. Great documentation takes work!

This repository contains:

1. [The specification](spec.md) for how a standard README should look.
2. A link to a linter you can use to keep your README maintained ([work in progress](https://github.com/RichardLitt/standard-readme/issues/5)).
3. A link to [a generator](https://github.com/RichardLitt/generator-standard-readme) you can use to create standard READMEs.
4. [A badge](#badge) to point to this spec.
5. [Examples of standard READMEs](example-readmes/) - such as this file you are reading.

Standard Readme is designed for open source libraries. Although it’s [historically](#background) made for Node and npm projects, it also applies to libraries in other languages and package managers.


## Table of Contents

- [Background](#background)
- [Install](#install)
- [Usage](#usage)
    - [Generator](#generator)
- [Badge](#badge)
- [Example Readmes](#example-readmes)
- [Related Efforts](#related-efforts)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Background

Standard Readme started with the issue originally posed by [@maxogden](https://github.com/maxogden) over at [feross/standard](https://github.com/feross/standard) in [this issue](https://github.com/feross/standard/issues/141), about whether or not a tool to standardize readmes would be useful. A lot of that discussion ended up in [zcei's standard-readme](https://github.com/zcei/standard-readme/issues/1) repository. While working on maintaining the [IPFS](https://github.com/ipfs) repositories, I needed a way to standardize Readmes across that organization. This specification started as a result of that.

> Your documentation is complete when someone can use your module without ever
having to look at its code. This is very important. This makes it possible for
you to separate your module's documented interface from its internal
implementation (guts). This is good because it means that you are free to
change the module's internals as long as the interface remains the same.

> Remember: the documentation, not the code, defines what a module does.

~ [Ken Williams, Perl Hackers](http://mathforum.org/ken/perl_modules.html#document)

Writing READMEs is way too hard, and keeping them maintained is difficult. By offloading this process - making writing easier, making editing easier, making it clear whether or not an edit is up to spec or not - you can spend less time worrying about whether or not your initial documentation is good, and spend more time writing and using code.

By having a standard, users can spend less time searching for the information they want. They can also build tools to gather search terms from descriptions, to automatically run example code, to check licensing, and so on.

The goals for this repository are:

1. A well defined **specification**. This can be found in the [Spec document](spec.md). It is a constant work in progress; please open issues to discuss changes.
2. **An example README**. This Readme is fully standard-readme compliant, and there are more examples in the `example-readmes` folder.
3. A **linter** that can be used to look at errors in a given Readme. Please refer to the [tracking issue](https://github.com/RichardLitt/standard-readme/issues/5).
4. A **generator** that can be used to quickly scaffold out new READMEs. See [generator-standard-readme](https://github.com/RichardLitt/generator-standard-readme).
5. A **compliant badge** for users. See [the badge](#badge).

## Install

This project uses [node](http://nodejs.org) and [npm](https://npmjs.com). Go check them out if you don't have them locally installed.

```sh
$ npm install --global standard-readme-spec
```

## Usage

This is only a documentation package. You can print out [spec.md](spec.md) to your console:

```sh
$ standard-readme-spec
# Prints out the standard-readme spec
```

### Generator

To use the generator, look at [generator-standard-readme](https://github.com/RichardLitt/generator-standard-readme). There is a global executable to run the generator in that package, aliased as `standard-readme`.

## Badge

If your README is compliant with Standard-Readme and you're on GitHub, it would be great if you could add the badge. This allows people to link back to this Spec, and helps adoption of the README. The badge is **not required**.

[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

To add in Markdown format, use this code:

```
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
```

## Example Readmes

To see how the specification has been applied, see the [example-readmes](example-readmes/).

## Related Efforts

- [Art of Readme](https://github.com/noffle/art-of-readme) - 💌 Learn the art of writing quality READMEs.
- [open-source-template](https://github.com/davidbgk/open-source-template/) - A README template to encourage open-source contributions.

## Maintainers

[@YiminH](https://github.com/yimin12/).

## Contributing

Feel free to dive in! [Open an issue](https://github.com/RichardLitt/standard-readme/issues/new) or submit PRs.

Standard Readme follows the [Contributor Covenant](http://contributor-covenant.org/version/1/3/0/) Code of Conduct.

### Contributors

This project exists thanks to all the people who contribute. 


## License

[MIT](LICENSE) © Richard Littauer





Techniques used in this demo:
1. JSP                 10. MyBatis PageHelper
2. JQuery              11. Dubbo
3. EasyUI              12. FastDFS-java-client
4. Spring Boot         13. Spring Data from Apache Solr
5. Spring MVC          14: Spring AMQP
6. MyBatis             15: Spring Data Redis
7. MyBatis Generator   16: Spring Security
8. Druid               17: HttpClient/RestTemplate
9. Logback


Developed Environment
1. Maven               8. RabbitMQ
2. Linux               9. Redis
3. Itellij             10. MyCat
4. Zookeeper           11. MySQL
5. FastDFS             12. Tomcat
6. Nginx
7. Solr


Staffing tables
1. Product Manager : 4  (Figure out the blueprint and the requirement)
2. Senior Product Manager : 1  (Supervice the product)
3. Front End Group : 5  (Make the prototype of static page followed by PM)
4. Back End Group : 20 (implement the service)
5. Testing Group : 5   (Test all functions required)
6. Maintainance Group : 3

Product Period:
6 months

ego_parent: parent module（maven 项目使用下划线，不要使用中划线）
    ego_pojo: pojo module
    ego_api : interface module
    ego_mapper: mapper used by myBatis, mapping to database
    ego_provider: implementation of interface module
    ego_commons: commons utils for all project
    ego_manage:the backend system of project
    ego_portal: main page
    ego_search: solr search -> can change to elaste search
    ego_item: info of items
    ego_cart: cart
    ego_passport: single point login system
    ego_trade: trade system
    ego_redis: redis relavant
    ego_rabbitmq_sender: rabbitmq sending message
    ego_rabbitmq_receive: monitoring rabbitmq queue


基于Dubbo 进行实现SOA 架构
    把项目分为表现层和服务层。
    表现层：Dubbo 中的Consumer。
    服务层：Dubbo 中的Provider。
    互联网发展，除了叫做SOA 架构，又可以称为RPC 软件模型。RPC 软件模型和SOA 架
    构是两个概念。发展到目前SOA 架构和RPC 软件模型都属于服务综合治理，在其中都包含：
    负载均衡、服务容灾、缓存等。





电商行业的几种模式.
1.1B2B
企业到企业，商家到商家。代表：阿里巴巴、慧聪网。
1.2B2C
商家到客户。代表：京东、淘宝商城（B2B2C）。
1.3C2C
客户到客户。淘宝集市。
1.4O2O
线上到线下。

