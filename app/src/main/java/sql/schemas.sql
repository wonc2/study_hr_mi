CREATE TABLE `Employees` (
	`Emp_PK`	varchar(30)	NOT NULL,
	`Dep_FK`	varchar(30)	NOT NULL,
	`Name`	varchar(30)	NULL
);

CREATE TABLE `Department` (
	`Dep_PK`	varchar(30)	NOT NULL,
	`Name`	varchar(30)	NULL
);

CREATE TABLE `TimeAttendance` (
	`TA_PK`	varchar(30)	NOT NULL,
	`Emp_FK`	varchar(30)	NOT NULL,
	`Dep_FK`	varchar(30)	NOT NULL,
	`Workday`	varchar(30)	NULL,
	`Status`	varchar(30)	NULL
);

ALTER TABLE `Employees` ADD CONSTRAINT `PK_EMPLOYEES` PRIMARY KEY (
	`Emp_PK`,
	`Dep_FK`
);

ALTER TABLE `Department` ADD CONSTRAINT `PK_DEPARTMENT` PRIMARY KEY (
	`Dep_PK`
);

ALTER TABLE `TimeAttendance` ADD CONSTRAINT `PK_TIMEATTENDANCE` PRIMARY KEY (
	`TA_PK`,
	`Emp_FK`,
	`Dep_FK`
);

ALTER TABLE `Employees` ADD CONSTRAINT `FK_Department_TO_Employees_1` FOREIGN KEY (
	`Dep_FK`
)
REFERENCES `Department` (
	`Dep_PK`
);

ALTER TABLE `TimeAttendance` ADD CONSTRAINT `FK_Employees_TO_TimeAttendance_1` FOREIGN KEY (
	`Emp_FK`
)
REFERENCES `Employees` (
	`Emp_PK`
);

ALTER TABLE `TimeAttendance` ADD CONSTRAINT `FK_Employees_TO_TimeAttendance_2` FOREIGN KEY (
	`Dep_FK`
)
REFERENCES `Employees` (
	`Dep_FK`
);