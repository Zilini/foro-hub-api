create table usuarios(
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo varchar(100) not null unique,
    contrasena varchar(255) not null,
    activo boolean not null default true,

    primary key (id)
);