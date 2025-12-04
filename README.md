# Info-Managment-Project-Team2
This repository contains the CS3230: Information Management project. It focuses on learning SQL and database management with PostgreSQL, while using Java to build a front-end that connects and interacts with the database developed throughout the semester.

## How to run 
1. To run this project from GitHub, download or clone the repository and open Git Bash in the same directory as the pom.xml file. You can do this by navigating into the project folder, right-clicking inside it, and selecting ```Open Git Bash here```.

2. Authenticate to Azure
```
Open Command Prompt
Run command: az login
Select work or school account, login with UWG ID, 2FA, then select “No this app only”
Accept default for subscription
```
In the resources/application.properties make sure to use your email

3. Once the terminal is open in the project root, run the command ```mvn javafx:run``` to download dependencies, compile the code, and start the JavaFX application.
