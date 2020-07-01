drop table Employee;
drop table Department;
create table Department
(
deptid int primary key,
departmentName varchar(20)
);


create table Employee(
empid int primary key,
   salary int,
deptid int references Department (deptid),
city varchar(20)
);

insert into Department(deptid, departmentName) values(1,'One');
insert into Department(deptid, departmentName) values(2,'Two');
insert into Department(deptid, departmentName) values(3,'Three');
insert into Department(deptid, departmentName) values(4,'Four');

-- /////////////////////////////////////////////////////////////////////////////////

Insert into Employee(empid,salary, deptid,city) values(101,3000,1,'BANGALORE');
Insert into Employee(empid,salary, deptid,city) values(102,6000,1,'BANGALORE');
Insert into Employee(empid,salary, deptid,city) values(103,3000,1,'PUNE');

Insert into Employee(empid,salary, deptid,city) values(104,2000,2,'PUNE');
Insert into Employee(empid,salary, deptid,city) values(105,4000,2,'PUNE');

Insert into Employee(empid,salary, deptid,city) values(106,1000,3,'CHANDIGARH');
Insert into Employee(empid,salary, deptid,city) values(107,7000,3,'CHANDIGARH');

Insert into Employee(empid,salary, deptid,city) values(108,1000,4,'PUNE');
Insert into Employee(empid,salary, deptid,city) values(109,1000,4,'PUNE');
Insert into Employee(empid,salary, deptid,city) values(110,1000,4,'BANGALORE');
Insert into Employee(empid,salary, deptid,city) values(111,1000,4,'BANGALORE');

select * from employee
select * from Department 