For this task we were tasked with creating a task-management system for a construction company called 'Poised'. 
The projects uses MySQL. Projects are either written, updated or removed from the database.

A user is displayed a menu where they can either:
-	Display all projects
-	Add a new project
-	Update or finalize existing projects
-	Display a list of incomplete projects
-	Display a list of incomplete and overdue projects
-	Display all contractors
- Display all architects
-	Display all customers
-	Display all invoices	

INSTALLATION: 
In order to be able to run the project create the following database and tables using MySQL.
Using the Command Prompt enter the following commands:

(Change the directory to where your MySQL server is stored)
(Enter in your relative username and password)

C:\Users\Ashira>cd C:\Program Files\MySQL\MySQL Server 8.0\bin

Program Files\MySQL\MySQL Server 8.0\bin>mysql -u ENTER_USERNAME -p
password:ENTER_PASSWORD

mysql> create database if not exists poisedPMS;

mysql> use poisedPMS;

mysql> create table customer (id int primary key,  first_name varchar(50), last_name varchar(50), phone_num varchar(25), email varchar(50), address varchar(50));

mysql> create table architect (id int primary key,  first_name varchar(50), last_name varchar(50), phone_num varchar(25), email varchar(50), address varchar(50));

mysql> create table contractor (id int primary key,  first_name varchar(50), last_name varchar(50), phone_num varchar(25), email varchar(50), address varchar(50));

mysql> create table project_details (project_num int primary key, project_name varchar(50), building_type varchar(50), address varchar(50), erf_number varchar(50), project_fee double, amount_paid double, deadline date, finalised boolean, architect_id int, contractor_id int, customer_id int);

mysql> create table invoice (id int AUTO_INCREMENT primary key, customer_name varchar(50), completion_date date, outstanding_amount double);



(You can also enter in a a few rows - enter them in the following manner)

mysql> insert into customer values (1, 'Ashleigh', 'Green', '0781233456', 'ash@email.com', '12 Ash road rondebosch');

mysql> insert into architect values (1, 'Cameron', 'Blue', '0791433423', 'cameron@email.com', '49 Cam road Kenilworth');

mysql> insert into contractor values (1, 'Ben', 'Purple', '0841433433', 'ben@email.com', '22 Ben road Observatory');

mysql> INSERT INTO project_details VALUES (01, 'Studio Apartment', 'Apartment', '25 studio raod Apartment 29', '56', 3500000, 1750000, '2021-12-12', false, 1, 1, 1);

mysql> INSERT INTO invoice (customer_name, completion_date, outstanding_amount) VALUES ('Rachel Green', '2021-10-05', 130000); 

