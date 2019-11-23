-- dropping everything
DROP TABLE customer CASCADE CONSTRAINTS;
DROP TABLE reservation CASCADE CONSTRAINTS;
DROP TABLE vehicle CASCADE CONSTRAINTS;
DROP TABLE vehicletype CASCADE CONSTRAINTS;
DROP TABLE rent CASCADE CONSTRAINTS;
DROP TABLE return CASCADE CONSTRAINTS;

-- recreating everything
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
    status          VARCHAR(1)  NOT NULL,  -- [A]vailable;[R]ented;[M]aintenance
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
    startdate   DATE          NOT NULL,
    enddate     DATE          NOT NULL,
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

-- Adding examples of vehicle types
INSERT INTO vehicletype VALUES('SUV', NULL, 7.0, 1.0, 0.2, 0.1, 5.0, 1.0, 0.1);
INSERT INTO vehicletype VALUES('Sedan', NULL, 4.0, 0.5, 0.1, 0.1, 2.0, 1.0, 0.1);
INSERT INTO vehicletype VALUES('Crossover', NULL, 9.0, 1.5, 0.2, 0.1, 5.0, 1.0, 0.1);
INSERT INTO vehicletype VALUES('Coupe', NULL, 7.0, 1.0, 0.3, 0.1, 6.0, 1.0, 0.1);
INSERT INTO vehicletype VALUES('Convertible', NULL, 10.0, 2.0, 0.4, 0.1, 6.0, 1.0, 0.1);

-- Adding examples of vehicles
INSERT INTO vehicle VALUES('AAA1234', 'SUV', 1000, 'A', 'Blue', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA1235', 'SUV', 2000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA1236', 'SUV', 3000, 'A', 'Grey', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA1237', 'SUV', 2200, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA2234', 'SUV', 1000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA2235', 'SUV', 2000, 'A', 'Black', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA2236', 'SUV', 1050, 'A', 'Green', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA2237', 'SUV', 2200, 'A', 'Red', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA3234', 'SUV', 1000, 'A', 'Blue', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA3235', 'SUV', 2000, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA3236', 'SUV', 1050, 'A', 'Grey', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA3237', 'SUV', 2200, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA4234', 'SUV', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA5235', 'SUV', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA6236', 'SUV', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA7237', 'SUV', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA8234', 'SUV', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA8235', 'SUV', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA8236', 'SUV', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('AAA8237', 'SUV', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');

INSERT INTO vehicle VALUES('BAA1234', 'Sedan', 1000, 'A', 'Blue', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA1235', 'Sedan', 2000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA1236', 'Sedan', 3000, 'A', 'Grey', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA2237', 'Sedan', 2200, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA2234', 'Sedan', 1000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA2235', 'Sedan', 2000, 'A', 'Black', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA2236', 'Sedan', 1050, 'A', 'Green', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA2237', 'Sedan', 2200, 'A', 'Red', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA3234', 'Sedan', 1000, 'A', 'Blue', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA3235', 'Sedan', 2000, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA3236', 'Sedan', 1050, 'A', 'Grey', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA3237', 'Sedan', 2200, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA4234', 'Sedan', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA5235', 'Sedan', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA6236', 'Sedan', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA7237', 'Sedan', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA8235', 'Sedan', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA8236', 'Sedan', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA8237', 'Sedan', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA9234', 'Sedan', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA9235', 'Sedan', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA9236', 'Sedan', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('BAA9237', 'Sedan', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');

INSERT INTO vehicle VALUES('CAA1234', 'Crossover', 1000, 'A', 'Blue', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA1235', 'Crossover', 2000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA1236', 'Crossover', 3000, 'A', 'Grey', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA1237', 'Crossover', 2200, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA2234', 'Crossover', 1000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA2235', 'Crossover', 2000, 'A', 'Black', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA2236', 'Crossover', 1050, 'A', 'Green', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA2237', 'Crossover', 2200, 'A', 'Red', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA3234', 'Crossover', 1000, 'A', 'Blue', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA3235', 'Crossover', 2000, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA3236', 'Crossover', 1050, 'A', 'Grey', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA3237', 'Crossover', 2200, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA4234', 'Crossover', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA5235', 'Crossover', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA6236', 'Crossover', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA7237', 'Crossover', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA8235', 'Crossover', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA8236', 'Crossover', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA8237', 'Crossover', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA9234', 'Crossover', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA9235', 'Crossover', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA9236', 'Crossover', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('CAA9237', 'Crossover', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');

INSERT INTO vehicle VALUES('DAA1234', 'Coupe', 1000, 'A', 'Blue', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA1235', 'Coupe', 2000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA1236', 'Coupe', 3000, 'A', 'Grey', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA1237', 'Coupe', 2200, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA2234', 'Coupe', 1000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA2235', 'Coupe', 2000, 'A', 'Black', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA2236', 'Coupe', 1050, 'A', 'Green', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA2237', 'Coupe', 2200, 'A', 'Red', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA3234', 'Coupe', 1000, 'A', 'Blue', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA3235', 'Coupe', 2000, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA3236', 'Coupe', 1050, 'A', 'Grey', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA3237', 'Coupe', 2200, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA4234', 'Coupe', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA5235', 'Coupe', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA6236', 'Coupe', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA7237', 'Coupe', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA8235', 'Coupe', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA8236', 'Coupe', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA8237', 'Coupe', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA9234', 'Coupe', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA9235', 'Coupe', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA9236', 'Coupe', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('DAA9237', 'Coupe', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');

INSERT INTO vehicle VALUES('EAA1234', 'Convertible', 1000, 'A', 'Blue', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA1235', 'Convertible', 2000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA1236', 'Convertible', 3000, 'A', 'Grey', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA1237', 'Convertible', 2200, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA2234', 'Convertible', 1000, 'A', 'Black', '300 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA2235', 'Convertible', 2000, 'A', 'Black', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA2236', 'Convertible', 1050, 'A', 'Green', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA2237', 'Convertible', 2200, 'A', 'Red', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA3234', 'Convertible', 1000, 'A', 'Blue', '400 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA3235', 'Convertible', 2000, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA3236', 'Convertible', 1050, 'A', 'Grey', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA3237', 'Convertible', 2200, 'A', 'Black', '500 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA4234', 'Convertible', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA5235', 'Convertible', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA6236', 'Convertible', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA7237', 'Convertible', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA8235', 'Convertible', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA8236', 'Convertible', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA8237', 'Convertible', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA9234', 'Convertible', 1000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA9235', 'Convertible', 2000, 'A', 'Black', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA9236', 'Convertible', 1050, 'A', 'Green', '600 Example St.', 'Vancouver');
INSERT INTO vehicle VALUES('EAA9237', 'Convertible', 2200, 'A', 'Red', '600 Example St.', 'Vancouver');

-- Adding examples of customers
INSERT INTO customer VALUES('12345678', 'Lucca Siaudzionis', 6046001234, '123 Example St.');
INSERT INTO customer VALUES('22345678', 'Beriwan Salamat', 6047781111, '234 Example St.');
INSERT INTO customer VALUES('32345678', 'Jonathan Medhanie', 6047781112, '345 Example St.');
