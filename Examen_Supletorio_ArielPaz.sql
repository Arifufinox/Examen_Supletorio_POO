create database ventas;

use ventas;

create table usuarios(
	id_usuario int primary key auto_increment,
    correo varchar(25),
    password varchar(50)
);

CREATE TABLE cliente (
  id_cliente int primary key AUTO_INCREMENT,
  cedula varchar(10) unique NOT NULL,
  nombre varchar(50) NOT NULL,
  apellidos varchar(50) NOT NULL,
  email varchar(100) unique,
  telefono varchar(15) ,
  rol varchar(20) NOT NULL,
  estado boolean default true
  );


insert into usuarios (correo, password)
values
("pepa@epn.edu.ec", "123"),
("admin@epn.edu.ec", "123");

insert into cliente (cedula, nombre, apellidos, email, telefono, rol, estado)
values ("1234567890", "Pepa", "Perez", "perez@epn.edu.ec", "1234567890", "vendedor", true);