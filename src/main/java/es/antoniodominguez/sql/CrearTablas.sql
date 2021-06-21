CREATE TABLE GRUPODISENNO(
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    Estudio VARCHAR(25)/** sala de estudio donde trabajan **/,
    Empresa VARCHAR(25), /** EMPRESA que contrata al grupor de diseñadores**/
    CONSTRAINT ID_GRUPODISENNO PRIMARY KEY (ID)
);

CREATE TABLE DISENNADOR (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    DNI CHAR(9),
    NOMBRE VARCHAR(15),
    APELLIDOS VARCHAR(20),
    TELEFONO CHAR(9),
    EMAIL VARCHAR(30),
    PROYECTOS SMALLINT,
    FECHA_NAC DATE,
    FOTO VARCHAR(30),
    LIDER BOOLEAN,
    GRUPO INTEGER,
    CONSTRAINT GRUP_DISENNADOR_FK FOREIGN KEY (GRUPO) REFERENCES GRUPODISENNO (ID),
    CONSTRAINT ID_DISENNADOR PRIMARY KEY (ID)
);