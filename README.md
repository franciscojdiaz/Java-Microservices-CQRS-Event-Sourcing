# Java-Microservices-CQRS-Event-Sourcing

This Java backend application is a microservices model having the Apache Kafka middleware as communication bus.

The implementation of the microservices were created using Spring Boot, connected to databases such as MySql and MongoDb, based on CQRS and Event Sourcing development patterns.

It basically demonstrates how an Event Store, in a development model executed under a docker compose platform.

It is a java backend application that emulates banking operations; account opening, fund withdrawals, money deposits, account inquiries, customer inquiries, etc.

The main features of the project:

1. Spring Framework for developing projects in Java.
2. Use MySql as Reading Database.
3. Use MongoDB as Event Store.
4. Create virtual network using Docker-Compose.
5. Design and implementation of CQRS and Event Sourcing.
6. Use Apache Kafka as a Message Bus.
7. Optimistic Concurrency Control.

It is a design pattern widely used in application development to support high recurrence demand.

The project is made up as follows:

banking-account:
1. account_cmd: is in charge of processing the insert, update and delete operations. Also, it generates the event that will be stored in the mongodb database and then passed to apache kafka.
2. account_common: has necessary and common resources for the project account_cmd and account_query.
3. account_query: works as a consumer of the message or streaming that comes from apache kafka and stores it in the Mysql database. It is also in charge of offering the read operation, that is, only queries.

cqrs_core:
it has the interfaces, abstract classes and other resources to generate the events and carry out the whole process.

How does it work:

the account_cmd and account_query projects should be run as spring boot projects, then test the endpoints from postman.
