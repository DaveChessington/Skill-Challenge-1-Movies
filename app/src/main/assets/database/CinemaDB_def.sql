CREATE DATABASE IF NOT EXISTS Cinema;

USE Cinema;
DROP TABLE IF EXISTS Detalle_ventas;
DROP TABLE IF EXISTS Ventas;
DROP TABLE IF EXISTS Funciones;
DROP TABLE IF EXISTS Salas;
DROP TABLE IF EXISTS Cinemas;
DROP TABLE IF EXISTS Peliculas;
CREATE TABLE IF NOT EXISTS Peliculas (
	id_pelicula INT AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    director VARCHAR(255) NOT NULL,
    disponible BOOL NOT NULL,
    sinopsis TEXT,
    duracion INT NOT NULL,
    PRIMARY KEY (id_pelicula)
);

CREATE TABLE IF NOT EXISTS Cinemas(
	id_cinema INT AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_cinema)
);

CREATE TABLE IF NOT EXISTS Salas(
	id_sala INT AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    no_asientos INT NOT NULL,
    id_cinema INT NOT NULL,
    PRIMARY KEY (id_sala),
    CONSTRAINT fk_cinema_sala
    FOREIGN KEY (id_cinema)
    REFERENCES Cinemas(id_cinema)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Funciones(
	id_funcion INT AUTO_INCREMENT,
    dia DATE NOT NULL,
    hora TIME NOT NULL,
    disponible BOOL NOT NULL,
    asientos_disponibles INT,
    precio DECIMAL(10,2) NOT NULL,
    id_pelicula INT NOT NULL,
    id_sala INT NOT NULL,
    PRIMARY KEY (id_funcion),
	CONSTRAINT fk_pelicula_funcion
    FOREIGN KEY (id_pelicula)
    REFERENCES Peliculas(id_pelicula)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_sala_funcion
    FOREIGN KEY (id_sala)
    REFERENCES Salas(id_sala)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Ventas(
	id_venta INT AUTO_INCREMENT,
    comprador VARCHAR(255) NOT NULL,
    no_asientos_reservados INT DEFAULT 0,
    importe DECIMAL(10, 2) DEFAULT 0.00,
    PRIMARY KEY (id_venta)
);


CREATE TABLE IF NOT EXISTS Detalle_ventas(
	id_detalle_venta INT AUTO_INCREMENT,
    id_funcion INT NOT NULL,
    id_venta INT NOT NULL,
    asiento varchar(3) NOT NULL,
    PRIMARY KEY (id_detalle_venta),
	CONSTRAINT fk_funcion
    FOREIGN KEY (id_funcion)
    REFERENCES Funciones(id_funcion)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT fk_venta
    FOREIGN KEY (id_venta)
    REFERENCES Ventas(id_venta)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


DELIMITER //
-- Set initial seat capacity when a showtime is created
DROP TRIGGER IF EXISTS obtener_asientos//
CREATE TRIGGER obtener_asientos 
BEFORE INSERT ON Funciones
FOR EACH ROW
BEGIN
    SET NEW.asientos_disponibles = (SELECT no_asientos FROM Salas WHERE id_sala = NEW.id_sala);
END//

-- Cancel showtimes if a movie is taken out of theaters
DROP TRIGGER IF EXISTS validar_disponibilidad_pelicula//
CREATE TRIGGER validar_disponibilidad_pelicula
AFTER UPDATE ON Peliculas
FOR EACH ROW
BEGIN
    IF NEW.disponible = 0 THEN
        UPDATE Funciones SET disponible = 0 WHERE id_pelicula = NEW.id_pelicula;
    END IF;
END//

-- Validate seat availability BEFORE saving a ticket item
DROP TRIGGER IF EXISTS validar_asiento_disponible//
CREATE TRIGGER validar_asiento_disponible
BEFORE INSERT ON Detalle_ventas
FOR EACH ROW
BEGIN
    DECLARE no_asientos INT;
    DECLARE libres INT;
    DECLARE funcion_activa BOOL;

    -- Check if the showtime is still active/available
    SELECT disponible, asientos_disponibles INTO funcion_activa, libres 
    FROM Funciones WHERE id_funcion = NEW.id_funcion;

    IF funcion_activa = 0 OR libres <= 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Esta función no está disponible o está llena.';
    END IF;

    -- Check if the specific seat is already occupied for this showtime
    SELECT COUNT(*) INTO no_asientos 
    FROM Detalle_ventas 
    WHERE id_funcion = NEW.id_funcion AND asiento = NEW.asiento; 
    
    IF no_asientos > 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: El asiento solicitado ya está ocupado.';
    END IF;
END//

-- Process transaction modifications AFTER a ticket item is saved
DROP TRIGGER IF EXISTS procesar_compra_asiento//
CREATE TRIGGER procesar_compra_asiento
AFTER INSERT ON Detalle_ventas
FOR EACH ROW
BEGIN
    DECLARE precio_boleto DECIMAL(10,2);
    
    -- substract 1 available seat from the showtime
    UPDATE Funciones 
    SET asientos_disponibles = asientos_disponibles - 1
    WHERE id_funcion = NEW.id_funcion;
    
    -- Get ticket price
    SELECT precio INTO precio_boleto FROM Funciones WHERE id_funcion = NEW.id_funcion;
    
    -- Automatically add the price to the total bill of the sale
    UPDATE Ventas 
    SET no_asientos_reservados = no_asientos_reservados + 1,
        importe = importe + precio_boleto
    WHERE id_venta = NEW.id_venta;
END//

DELIMITER ;

/*
-- Cancel showtimes if a movie is taken out of theaters
DELIMITER //
DROP TRIGGER IF EXISTS validar_disponibilidad_pelicula;
CREATE TRIGGER validar_disponibilidad_pelicula
AFTER UPDATE ON Peliculas
FOR EACH ROW
BEGIN
    IF NEW.disponible = 0 THEN
        UPDATE Funciones SET disponible = 0 WHERE id_pelicula = NEW.id_pelicula;
    END IF;
END// 
DELIMITER;

-- validate availibility of seat
DELIMITER //
DROP TRIGGER IF EXISTS validar_asiento_disponible//
CREATE TRIGGER validar_asiento_disponible
BEFORE INSERT 
ON Detalle_ventas
FOR EACH ROW
BEGIN
	DECLARE no_asientos INT;
	DECLARE funcion_activa BOOL;
    DECLARE libres INT;
    
    -- check  showtime still available
    SELECT disponible, asientos_disponibles INTO funcion_activa, libres 
    FROM Funciones WHERE id_funcion = NEW.id_funcion;
    IF funcion_activa = 0 OR libres <= 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Esta función no está disponible o está llena.';
    END IF;
    
    -- check if seat is available at that time
	SELECT COUNT(*) INTO no_asientos FROM Detalle_ventas where id_funcion=NEW.id_funcion and asiento=New.asiento; 
    IF no_asientos>0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: asiento ocupado';
    END IF;
END//
DELIMITER ;

-- contar los asientos de acuerdo a detalle pedido para registralo en el resumen de venta
DELIMITER //
DROP TRIGGER IF EXISTS contar_asientos//
CREATE TRIGGER contar_asientos
BEFORE INSERT 
ON Ventas
FOR EACH ROW
BEGIN
	DECLARE no_asientos INT;
	SELECT COUNT(*) INTO no_asientos FROM Detalle_ventas where id_venta=NEW.id_venta; 
    IF no_asientos<0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: debe haber por lo menos un asiento asociado a esta venta';
    END IF;
END//
DELIMITER ;


DELIMITER //

DROP TRIGGER IF EXISTS obtener_asientos//
CREATE TRIGGER obtener_asientos 
BEFORE INSERT ON Funciones
FOR EACH ROW
BEGIN
    SET NEW.asientos_disponibles = (SELECT no_asientos FROM Salas WHERE id_sala = NEW.id_sala);
END//

-- Actualizar los asientos restantes tras registrar la venta
DELIMITER //

DROP TRIGGER IF EXISTS update_asientos_funcion//
CREATE TRIGGER update_asientos_funcion
AFTER INSERT 
ON Ventas
FOR EACH ROW
BEGIN
   UPDATE Funciones 
   SET asientos_disponibles = asientos_disponibles - (
       SELECT no_asientos_reservados 
       FROM Ventas 
       WHERE id_venta = NEW.id_venta
   ) 
   WHERE id_funcion = NEW.id_funcion;
END//

DELIMITER ;

-- Calcular importe automáticamente al insertar una venta
DELIMITER //

DROP TRIGGER IF EXISTS calcular_importe//
CREATE TRIGGER calcular_importe
BEFORE INSERT
ON Ventas
FOR EACH ROW
BEGIN
    SET NEW.importe = NEW.asientos_reservados * (SELECT precio FROM Funciones WHERE id_funcion = NEW.id_funcion);
END//

DELIMITER ;

-- Copiar la capacidad total de la sala al crear una función nueva
DELIMITER //

DROP TRIGGER IF EXISTS obtener_asientos//
CREATE TRIGGER obtener_asientos 
BEFORE INSERT
ON Funciones
FOR EACH ROW
BEGIN
    SET NEW.asientos_disponibles = (SELECT no_asientos FROM Salas WHERE id_sala = NEW.id_sala);
END//

DELIMITER ;

-- Bloquear compras si la función ya se quedó sin asientos libres
DELIMITER //

DROP TRIGGER IF EXISTS validar_disponibilidad_funcion//
CREATE TRIGGER validar_disponibilidad_funcion
BEFORE INSERT 
ON Ventas
FOR EACH ROW
BEGIN
    DECLARE asientos_libres INT;
    DECLARE asientos_solicitados INT;

    SELECT asientos_disponibles INTO asientos_libres 
    FROM Funciones 
    WHERE id_funcion = NEW.id_funcion;

    SELECT asientos_reservados INTO asientos_solicitados 
    FROM Ventas 
    WHERE id_venta = NEW.id_venta;

    IF asientos_solicitados > asientos_libres THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: No hay suficientes asientos disponibles para esta función.';
    END IF;
END//

DELIMITER ;


-- Cancelar funciones si la película sale de cartelera
DELIMITER //

DROP TRIGGER IF EXISTS validar_disponibilidad_pelicula//
CREATE TRIGGER validar_disponibilidad_pelicula
AFTER UPDATE 
ON Peliculas
FOR EACH ROW
BEGIN
    IF NEW.disponible = 0 THEN
        UPDATE Funciones 
        SET disponible = 0 
        WHERE id_pelicula = NEW.id_pelicula; -- ¡Corregido: faltaba ';'!
    END IF;
END//

DELIMITER ;
*/