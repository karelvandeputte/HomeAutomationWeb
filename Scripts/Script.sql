CREATE TABLE `ActivityTasks` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CreationDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `LastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `Name` varchar(100) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1