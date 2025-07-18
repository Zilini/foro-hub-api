create table usuarios(
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo varchar(100) not null unique,
    contrasena varchar(255) not null,
    perfil varchar(50) not null,

    primary key (id)
);