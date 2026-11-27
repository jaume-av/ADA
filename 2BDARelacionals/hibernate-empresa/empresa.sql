PRAGMA foreign_keys = ON;

BEGIN;

CREATE TABLE Departaments (
    IdDepartament INTEGER PRIMARY KEY AUTOINCREMENT,
    NomDepartament VARCHAR(100) NOT NULL,
    Responsable VARCHAR(100)
);

CREATE TABLE Empleats (
    NIF VARCHAR(9) PRIMARY KEY,
    Nom VARCHAR(100),
    Cognoms VARCHAR(100),
    Salari REAL,
    IdDepartament INTEGER REFERENCES Departaments(IdDepartament)
);

INSERT INTO Departaments (IdDepartament, NomDepartament, Responsable) VALUES
    (1, 'Recursos Humans', 'Marta Pérez'),
    (2, 'Desenvolupament', 'Jaume Martí'),
    (3, 'Comptabilitat', 'Fina Soler');

INSERT INTO Empleats (NIF, Nom, Cognoms, Salari, IdDepartament) VALUES
    ('123456789', 'Pep', 'Martínez González', 2500.50, 1),
    ('987654321', 'Marta', 'Soler Sánchez', 3000.75, 2),
    ('111111111', 'Fina', 'Valls Beltran', 3000.75, 3),
    ('222222222', 'Josefa', 'Beltran Benedito', 3000.75, 1),
    ('333333333', 'Jaime', 'Valls Abad', 3000.75, 2),
    ('444444444', 'Sergi', 'Navarro Pérez', 2000.00, 3);

COMMIT;
