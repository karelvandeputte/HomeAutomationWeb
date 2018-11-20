--Creating the mqttrelais table and view

CREATE TABLE HomeAutomation.mqttrelais (
	ID INT NOT NULL AUTO_INCREMENT,
	Name varchar(100) NOT NULL,
	`Number` INT NULL,
	Port INT NULL,
	CONSTRAINT mqttrelais_PK PRIMARY KEY (ID)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci ;
CREATE UNIQUE INDEX mqttrelais_Name_IDX ON HomeAutomation.mqttrelais (Name) ;


CREATE OR REPLACE VIEW HomeAutomation.v_mqttrelais
AS --  Empty source
select * from mqttrelais ;




--Create Modules table

CREATE TABLE HomeAutomation.Modules (
	ID INT NOT NULL AUTO_INCREMENT,
	TypeID INT NOT NULL,
	Name varchar(100) NULL,
	Description varchar(100) NULL,
	CONSTRAINT Modules_PK PRIMARY KEY (ID)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci ;
CREATE INDEX Modules_Name_IDX ON HomeAutomation.Modules (Name) ;


--Create Relais table and view

CREATE TABLE HomeAutomation.Relais (
	ID INT NOT NULL AUTO_INCREMENT,
	ModuleID INT NOT NULL,
	Name varchar(100) NULL,
	Description varchar(100) NULL,
	`Number` INT NULL,
	Port INT NULL,
	Status varchar(100) NULL,
	CONSTRAINT Relais_PK PRIMARY KEY (ID)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci ;
CREATE INDEX Relais_Name_IDX ON HomeAutomation.Relais (Name) ;



CREATE OR REPLACE VIEW HomeAutomation.v_relais
AS --  Empty source
select
	Relais.ID,
	Relais.ModuleID,
	Relais.Name,
	Relais.Description,
	Relais.Number,
	Relais.Port,
	Relais.Status,
	Modules.Name as ModuleName,
	Modules.Description as ModuleDescription
from Relais
	inner join Modules where Relais.ModuleID = Modules.ID ;






