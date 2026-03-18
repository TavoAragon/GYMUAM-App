-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS GYMUAM;

-- Usar la base de datos
USE GYMUAM;

-- Crear usuario (si no existe) con acceso desde cualquier host
CREATE USER IF NOT EXISTS 'aragondb'@'%' IDENTIFIED BY 'LTSI';

-- Otorgar privilegios
GRANT ALL PRIVILEGES ON GYMUAM.* TO 'aragondb'@'%';

-- Aplicar cambios
FLUSH PRIVILEGES;

-- Tabla alumnos
CREATE TABLE alumnos (
    matricula INT PRIMARY KEY,
    nombre_completo VARCHAR(100),
    carrera VARCHAR(100),
    password VARCHAR(255)
);

-- Tabla actividades
CREATE TABLE actividades (
    id_actividad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_actividad VARCHAR(100) UNIQUE NOT NULL
);

-- Tabla registro (asistencia)
CREATE TABLE registro (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,
    matricula INT NOT NULL,
    id_actividad INT NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY (matricula) REFERENCES alumnos(matricula),
    FOREIGN KEY (id_actividad) REFERENCES actividades(id_actividad)
);

-- Tabla inventario (juegos)
CREATE TABLE inventario (
    id_juego INT AUTO_INCREMENT PRIMARY KEY,
    nombre_juego VARCHAR(100) UNIQUE NOT NULL,
    cantidad INT NOT NULL DEFAULT 0
);

-- Tabla préstamos
CREATE TABLE prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    matricula INT NOT NULL,
    id_juego INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NULL,
    FOREIGN KEY (matricula) REFERENCES alumnos(matricula),
    FOREIGN KEY (id_juego) REFERENCES inventario(id_juego)
);
