HOW TO INSTALL:

1. Install Eclipse
2. Go to file->import
3. Import the Auto Project into Eclipse
4. Make sure that the project class path includes the included classes12.zip file
5. Load the tables.sql and CARS.sql files using SQL*PLus or some other Oracle Database utility (Oracle SQL Developer is my preferred tool). Ignore any prompts for substitutions. 
6. MAKE SURE TO ERASE ALL FOREIGN KEY CONSTRAINTS ON TABLES!!!
7. Go into the DatabaseConnection.java source file. At the bottom are variables used to set and create the Oracle database connection. Change the user_id and password as needed, as well as the DB_URL containing the database connection string. You will need to point it to the database you executed the included sql files on (it originally points to ¡°localhost:1521:dbtest¡±).
8. Run the AutoProject.java file as a Java Application
It should work from then on.
Note that there are small bugs. When creating a new maker, all fields must have text entered. Other than that it works fine.
Please contact matt.castagnolo@gmail.com if you are unable to get this project working and I will assist

Matt Castagnolo
4/12/2012
