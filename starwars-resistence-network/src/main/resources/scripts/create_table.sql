CREATE TABLE Localizacao
(
   id int PRIMARY KEY NOT NULL,
   latitude varchar(45) NOT NULL,
   longitude varchar(45) NOT NULL,
   nome varchar(45) NOT NULL
)
;


CREATE TABLE Item
(
   id int PRIMARY KEY NOT NULL,
   nome varchar(45) NOT NULL,
   quantidade int NOT NULL,
   pontos int NOT NULL,
   inventario_id int NOT NULL
)
;
ALTER TABLE Item
ADD CONSTRAINT FK_inventario_id_1
FOREIGN KEY (inventario_id)
REFERENCES Inventario(id)
;
CREATE INDEX FK_inventario_id_1_idx ON Item(inventario_id)
;
CREATE TABLE Inventario
(
   id int PRIMARY KEY NOT NULL
)
;
CREATE TABLE Rebelde
(
   id int NOT NULL,
   nome varchar(45) NOT NULL,
   idade int,
   genero varchar(45),
   traidor int NOT NULL,
   localizacao_id int NOT NULL,
   inventario_id int NOT NULL
)
;
ALTER TABLE Rebelde
ADD CONSTRAINT FK_inventario_id
FOREIGN KEY (inventario_id)
REFERENCES Inventario(id)
;
ALTER TABLE Rebelde
ADD CONSTRAINT FK_localizacao_id
FOREIGN KEY (localizacao_id)
REFERENCES Localizacao(id)
;
CREATE INDEX FK_inventario_id_idx ON Rebelde(inventario_id)
;
CREATE INDEX FK_localizacao_id_idx ON Rebelde(localizacao_id)
;
