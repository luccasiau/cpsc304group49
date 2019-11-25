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

CREATE TABLE return (
    rentid      VARCHAR(20)     NOT NULL,
    returnDate  DATE            NOT NULL,
    odometer    INT             NOT NULL,
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
INSERT INTO vehicle VALUES('BBA2237', 'Sedan', 2200, 'A', 'Black', '300 Example St.', 'Vancouver');
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

-- Reservations not rented yet
INSERT INTO reservation VALUES('111111111', '12345678', 'SUV', '400 Example St.', 'Vancouver', DATE '2019-07-09', DATE '2019-08-15');
INSERT INTO reservation VALUES('111111112', '12345678', 'Sedan', '500 Example St.', 'Vancouver', DATE '2019-07-09', DATE '2019-08-15');
INSERT INTO reservation VALUES('111111113', '12345678', 'Sedan', '600 Example St.', 'Vancouver', DATE '2019-08-09', DATE '2019-08-15');
INSERT INTO reservation VALUES('111111114', '12345678', 'SUV', '600 Example St.', 'Vancouver', DATE '2019-08-09', DATE '2019-08-15');
INSERT INTO reservation VALUES('111111115', '22345678', 'SUV', '600 Example St.', 'Vancouver', DATE '2019-08-09', DATE '2019-08-15');
INSERT INTO reservation VALUES('111111116', '22345678', 'SUV', '300 Example St.', 'Vancouver', DATE '2019-08-15', DATE '2019-08-16');
INSERT INTO reservation VALUES('111111117', '22345678', 'Convertible', '300 Example St.', 'Vancouver', DATE '2019-08-18', DATE '2019-08-22');
INSERT INTO reservation VALUES('111111118', '22345678', 'Coupe', '400 Example St.', 'Vancouver', DATE '2019-08-22', DATE '2019-10-15');
INSERT INTO reservation VALUES('111111119', '32345678', 'SUV', '500 Example St.', 'Vancouver', DATE '2019-10-09', DATE '2019-10-15');
INSERT INTO reservation VALUES('111111120', '32345678', 'SUV', '600 Example St.', 'Vancouver', DATE '2019-11-09', DATE '2020-01-04');

-- Vehicles not returned yet
INSERT INTO reservation VALUES('211111111', '12345678', 'SUV', '500 Example St.', 'Vancouver', DATE '2019-10-09', DATE '2019-10-15');
INSERT INTO rent VALUES('111111112', 'AAA3237', '12345678', '211111111', DATE '2019-10-09', DATE '2019-10-15', 'Beriwan', '424109184', 2012);

INSERT INTO reservation VALUES('311111111', '12345678', 'SUV', '600 Example St.', 'Vancouver', DATE '2019-08-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('111111113', 'AAA6236', '12345678', '311111111', DATE '2019-08-09' , DATE '2019-08-15', 'Lucca', '424109184', 2112);

INSERT INTO reservation VALUES('411111111', '12345678', 'SUV', '600 Example St.', 'Vancouver', DATE '2019-08-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('111111114', 'AAA8234', '12345678', '411111111', DATE '2019-08-09' , DATE '2019-08-15', 'Jonathan', '424109184', 1912);

INSERT INTO reservation VALUES('511111111', '32345678', 'Sedan', '600 Example St.', 'Vancouver', DATE '2019-11-09' , DATE '2020-01-04');
INSERT INTO rent VALUES('111111115', 'BAA7237', '32345678', '511111111', DATE '2019-11-09' , DATE '2020-01-04', 'Beriwan', '424109184', 2212);

INSERT INTO reservation VALUES('611111111', '22345678', 'Crossover', '600 Example St.', 'Vancouver', DATE '2019-08-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('111111116', 'CAA8236', '22345678', '611111111', DATE '2019-08-09' , DATE '2019-08-15', 'Lucca', '424109184', 2103);

INSERT INTO reservation VALUES('711111111', '22345678', 'Coupe', '600 Example St.', 'Vancouver', DATE '2019-08-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('111111117', 'DAA9234', '22345678', '711111111', DATE '2019-08-09' , DATE '2019-08-15', 'Jonathan', '424109184', 1912);

-- Vehicles returned
INSERT INTO reservation VALUES('121111111', '22345678', 'Convertible', '300 Example St.', 'Vancouver', DATE '2019-10-09' , DATE '2019-10-15');
INSERT INTO rent VALUES('441111112', 'EAA2234', '22345678', '121111111', DATE '2019-10-09' , DATE '2019-10-15', 'Beriwan', '424109184', 2012);
INSERT INTO return VALUES('441111112', DATE '2019-10-15', 6000, 1, 650.0);

INSERT INTO reservation VALUES('131111111', '22345678', 'Convertible', '400 Example St.', 'Vancouver', DATE '2019-08-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('551111112', 'EAA3234', '22345678', '131111111', DATE '2019-08-09' , DATE '2019-08-15', 'Lucca', '424109184', 2103);
INSERT INTO return VALUES('551111112', DATE '2019-08-15', 6000, 1, 650.0);

INSERT INTO reservation VALUES('141111111', '22345678', 'Convertible', '500 Example St.', 'Vancouver', DATE '2019-08-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('661111112', 'EAA3237', '22345678', '141111111', DATE '2019-08-09' , DATE '2019-08-15', 'Jonathan', '424109184', 1912);
INSERT INTO return VALUES('661111112', DATE '2019-08-15', 6000, 1, 650.0);

INSERT INTO reservation VALUES('151111111', '22345678', 'SUV', '600 Example St.', 'Vancouver', DATE '2019-07-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('771111112', 'AAA8237', '22345678', '151111111', DATE '2019-07-09' , DATE '2019-08-15', 'Beriwan', '424109184', 1212);
INSERT INTO return VALUES('771111112', DATE '2019-08-15', 6000, 1, 650.0);

INSERT INTO reservation VALUES('161111111', '22345678', 'Sedan', '600 Example St.', 'Vancouver', DATE '2019-08-09' , DATE '2019-08-15');
INSERT INTO rent VALUES('881111112', 'BAA9237', '22345678', '161111111', DATE '2019-08-09' , DATE '2019-08-15', 'Lucca', '424109184', 2103);
INSERT INTO return VALUES('881111112', DATE '2019-08-15', 6000, 1, 650.0);

-- Vehicles rented without reservation
INSERT INTO rent VALUES('882111112', 'EAA9234', '22345678', NULL, DATE '2019-08-09' , DATE '2019-08-15', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('883111112', 'EAA9235', '22345678', NULL, DATE '2019-08-09' , DATE '2019-08-15', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('884111112', 'EAA9236', '22345678', NULL, DATE '2019-09-09' , DATE '2019-09-15', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('885111112', 'EAA9237', '22345678', NULL, DATE '2019-09-09' , DATE '2019-09-18', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('886111112', 'DAA8235', '22345678', NULL, DATE '2019-10-09' , DATE '2019-10-15', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('887111112', 'DAA8236', '22345678', NULL, DATE '2019-10-09' , DATE '2019-10-18', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('888111112', 'DAA8237', '22345678', NULL, DATE '2019-11-09' , DATE '2019-11-15', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('889111112', 'CAA1234', '22345678', NULL, DATE '2019-11-09' , DATE '2019-11-18', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('881211112', 'CAA1235', '22345678', NULL, DATE '2019-12-09' , DATE '2019-12-15', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('881311112', 'CAA1236', '22345678', NULL, DATE '2019-08-09' , DATE '2019-12-15', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('881411112', 'CAA1237', '22345678', NULL, DATE '2019-08-09' , DATE '2019-12-18', 'Lucca', '424109184', 2103);
INSERT INTO rent VALUES('881511112', 'CAA3237', '22345678', NULL, DATE '2019-09-11' , DATE '2019-10-15', 'Lucca', '424109184', 2103);
