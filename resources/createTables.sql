DROP TABLE customer CASCADE CONSTRAINTS;
DROP TABLE reservation CASCADE CONSTRAINTS;
DROP TABLE vehicle CASCADE CONSTRAINTS;
DROP TABLE vehicletype CASCADE CONSTRAINTS;
DROP TABLE rent CASCADE CONSTRAINTS;
DROP TABLE return CASCADE CONSTRAINTS;

CREATE TABLE customer (
  dlicense    VARCHAR(10) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  cellNum     INT,
  address     VARCHAR(255),
  PRIMARY KEY(dlicense)
);

CREATE TABLE vehicletype (
    vtname          VARCHAR(20)   NOT NULL,
    features        VARCHAR(255),
    weeklyRate      FLOAT,
    dailyRate       FLOAT,
    hourlyRate      FLOAT,
    kiloRate        FLOAT,
    weeklyInsRate   FLOAT,
    dailyInsRate    FLOAT,
    hourlyInsRate   FLOAT,
    PRIMARY KEY (vtname)
);

CREATE TABLE vehicle (
    vlicense        VARCHAR(10) NOT NULL,
    vtname          VARCHAR(20) NOT NULL,
    odometer        INT,
    status          CHAR(1),
    colour          VARCHAR(20),
    location        VARCHAR(255) NOT NULL,
    city            VARCHAR(255) NOT NULL,
    FOREIGN KEY (vtname) REFERENCES vehicletype,
    PRIMARY KEY (vlicense)
);

CREATE TABLE reservation (
    confno      VARCHAR(20)  NOT NULL,
    dlicense    VARCHAR(20)  NOT NULL,
    vtname      VARCHAR(20)  NOT NULL,
    location    VARCHAR(255) NOT NULL,
    city        VARCHAR(255) NOT NULL,
    startdate   DATE         NOT NULL,
    enddate     DATE         NOT NULL,
    FOREIGN KEY (vtname) REFERENCES vehicletype,
    FOREIGN KEY (dlicense) REFERENCES customer,
    PRIMARY KEY(confno)
);

CREATE TABLE rent (
    rentid      VARCHAR(20)   NOT NULL,
    vlicense    VARCHAR(10)   NOT NULL,
    dlicense    VARCHAR(10)   NOT NULL,
    confno      VARCHAR(20),
    fromDate    DATE          NOT NULL,
    toDate      DATE          NOT NULL,
    cardname    VARCHAR(255)  NOT NULL,
    cardno      VARCHAR(16)   NOT NULL,
    expdate     INT           NOT NULL,
    FOREIGN KEY (vlicense) REFERENCES vehicle,
    FOREIGN KEY (dlicense) REFERENCES customer,
    FOREIGN KEY (confno)   REFERENCES reservation,
    PRIMARY KEY (rentid)
);

CREATE TABLE return(
    rentid      VARCHAR(20)     NOT NULL,
    returnDate  DATE            NOT NULL,
    odomoter    INT             NOT NULL,
    fullTalk    SMALLINT        NOT NULL,
    revenue     FLOAT           NOT NULL,
    FOREIGN KEY (rentid)  REFERENCES rent,
    PRIMARY KEY (rentid)
);