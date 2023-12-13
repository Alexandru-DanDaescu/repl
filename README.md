# Repl
Real Estate Property Listings

Is a real estate tool for agents to list properties, with advanced features for buyers to organize viewings and save favorite listings.

The project uses:

> Java 17
> SpringBoot 3.1.5
> PostMan
> PostgreSQL
> Maven
> Mockito
> Junit
> Lombok
> Spring Data JPA
> Hibernate



    ! Important !

    To use this application make sure that you have the following file: main/resources/application.properties
    This file contains the database credentials(you can find an example below).
    
    # Database connection properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_DATABASE_HERE
    spring.datasource.username=YOUR_USERNAME
    spring.datasource.password=YOUR_PASSWORD
    spring.datasource.driver-class-name=org.postgresql.Driver


    JPA properties
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

    Logging properties
    logging.level.org.hibernate.SQL=DEBUG
    logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# API References

Create a client and add property/properties to the client:
> POST /api/clients/{propertyIds}/properties

Sort the properties of the client based on status of utilities, type of property and year built:
> GET /api/clients-properties/{clientId}


Get all the clients that currently exist in the database:
> GET /api/clients

Update a client: 
> PUT /api/clients/{id}

Delete a client:
> DELETE /api/clients/{id}

Create a property:
> POST /api/properties

Get all properties that currently exist in the database:
> GET /api/properties

Update a property:
> PUT /api/properties/{id}

Delete a property:
> DELETE /api/properties/{id}