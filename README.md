# restaurant-automation

This is a back end part of Restaurant Automation System.

# About the project:

The restaurant order automation system is aimed at simplifying the ordering process in a restaurant and improving customer interaction. It is aimed at reducing the level of social pressure that a visitor may experience when communicating with a waiter, as well as improving the productivity of staff who do not spend extra time communicating with customers and taking orders.

The restaurant automation system is a software system that is installed on devices located at each table of the restaurant, as well as on a separate administrator's device.

The visitor is allowed to start a session of use when he or she comes to the restaurant, select the desired dishes from the menu, add them to the cart and place an order without calling the waiter. When the order is ready, the waiter will deliver it to the right table. The customer also does not need to call the waiter once again if he wants to use the bank card payment method. You just need to choose a convenient online payment system and use your bank's application.

On the part of the administrator, the order automation system allows him to monitor the current occupancy of tables, check their statuses and additional information, as well as generate and view reports on completed orders, which will help in analyzing the most popular dishes to build a successful marketing strategy and increase profits. In addition, the administrator can edit the system's content including adding and authorizing new tables to the system, editing menu content and prices and implementing promotional offers.

# Prerequisites:

- Java JDK 17 or later installed
- Maven 3.8.6 or later installed
- MySQL 8.0.26 or later installed

# Setup:

- Clone the repository to your local machine.
- Create a new database in MySQL and import the SQL file located in the db folder.
- Update the application.properties file located in the src/main/resources folder with your MySQL database information.
- Open a terminal or command prompt and navigate to the project's root directory.
- Run the command mvn clean package to build the project and create an executable jar file.
- Run the command java -jar target/ras.jar to start the application.

# Usage:

Open a web browser and navigate to http://localhost:8080/ to access the application. 
