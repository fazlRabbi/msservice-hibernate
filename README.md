# msservice-hibernate

## How to build and run project 

You need an IDE, and Maven installed.


## There are 3 steps:

### 1. Database setup:
You can use any relational database. It is recommended to use postgresql version 9.3. 

After you have downloaded and installed postgresql, create a datqabase user and database for the msapplication. 

You will have to provide this information in step 2.

### 2. Configure msservice webapplication: 

Open the project in your favorite IDE (Eclipse is recommended).

Open the file named application.properties from src/main/resources and edit the server port, database name, password. 

Use spring.jpa.hibernate.ddl-auto=create to create the database schema for the first time, 
Or else use spring.jpa.hibernate.ddl-auto=update. 

Run Maven command "install" to update and generate the .jar file.



### 3. Deploy the msservice webapplication: 

Make sure that the database server is running. 

If the project is successfully build at step 2, you will find an executable jar file 'msservice-1.0.jar' under the target directory.

Use the following command in a command line to run the webapplication:  
java -jar msservice-1.0.jar
