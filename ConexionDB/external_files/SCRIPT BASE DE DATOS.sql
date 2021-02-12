create database biblioteca;
use biblioteca;

create table libros(

    ID int primary key AUTO_INCREMENT,
    NombreLibro varchar(50) not null,
    editorial varchar(50),
    autor varchar(50) not null,
    precio decimal(6,2) not null

);
 
/*######################################################################*/

CREATE PROCEDURE SP_INSERTAR_LIBROS_LOWER(

IN _NombreLibro varchar(50),
IN _editorial varchar(50),
IN _autor varchar(50),
IN _precio decimal(6,2)


)
BEGIN
/*
	CON EL SIGUIENTE CODIGO SE IMPIDE QUE EL USUARIO INGRESE 
UN LIBRO REPETIDO PARA ESTE EJERCICIO EL FILTRO SE SIMPLIFICO LO MAS POSIBLE
LO QUE PASA ACA ES QUE YO FILTRO EL LIBRO Y PREGUNTO QUE SI HAY ALGUN REGISTRO
EN LA TABLA DE LIBRO DONDE EL NOMBRE SEA IGUAL AL INGRESADO EN EL PROCEDIMIENTO
Y LA EDITORIAL ES TAL EL LIBRO YA EXISTE EN LA TABLA 
Y POR ENDE SE SOBRENTIENDE DE QUE NO HAY QUE VOLVERLO A INGRESAR DE NUEVO
ESTA TECNICA ES MUY UTIL YA QUE DISMINUYE LA REDUNDANCIA DE DATOS 

*/
IF (not exists(select ID 
               from libros 
               where NombreLibro = _NombreLibro and 
               	     editorial = _editorial and
					 autor = _autor)) then

INSERT INTO libros(NombreLibro,
                   editorial,
                   autor,
                   precio)
                   values(
					  LOWER(_NombreLibro),
					  LOWER(_editorial),
					  LOWER(_autor),
					  _precio
					);
          
 END IF;
              
END $$


/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_INSERTAR_LIBROS(

IN _NombreLibro varchar(50),
IN _editorial varchar(50),
IN _autor varchar(50),
IN _precio decimal(6,2)


)
BEGIN

IF (not exists(select ID 
               from libros 
               where NombreLibro = _NombreLibro and 
               	     editorial = _editorial and
		     autor = _autor)) then

INSERT INTO libros(NombreLibro,
                   editorial,
                   autor,
                   precio)
                   values(
						  _NombreLibro,
                          _editorial,
                          _autor,
                          _precio
					);
          
 END IF;
              
END $$

/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_LISTAR_LIBROS(

)

BEGIN

SELECT ID,
       NombreLibro,
       editorial,
       autor,
       precio

FROM libros

ORDER BY NombreLibro ASC;

END $$

/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_BORRAR_LIBROS(

IN _NombreLibro varchar(50),
IN _editorial varchar(50),
IN _autor varchar(50)

)

BEGIN

DELETE FROM libros 
WHERE ID = (SELECT ID 
            FROM libros 
            where NombreLibro = LOWER(_NombreLibro) and 
				  editorial = LOWER(_editorial) and
				  autor = LOWER(_autor)
			);


END $$

/*######################################################################*/

DELIMITER $$
CREATE PROCEDURE SP_BORRAR_LIBROS_ID(

IN _ID int

)
BEGIN

DELETE FROM libros 
WHERE ID = _ID;

END $$

/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_ACTUALIZAR_LIBROS(
  IN _ID int,
  IN _NombreLibro varchar(50),
  IN _editorial varchar(50),
  IN _autor varchar(50),
  IN _precio decimal(6,2) 
)

BEGIN

IF (not exists(select ID 
               from libros 
               where NombreLibro = _NombreLibro and 
               	     editorial = _editorial and
					 autor = _autor and
					 precio = _precio)) then

	UPDATE libros
	SET 
		NombreLibro = _NombreLibro,
		editorial = _editorial,
		autor =  _autor,
		precio = _precio
	
	WHERE ID = _ID;

END IF;
	 
END $$

/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_FILTRAR_LIBROS(

IN _dato varchar(50)
  
)
BEGIN

SELECT ID,
       NombreLibro,
       editorial,
       autor,
       precio

FROM libros

WHERE NombreLibro like CONCAT('%', _dato, '%') or
      editorial like CONCAT('%' , _dato ,'%') or
      autor like CONCAT('%', _dato, '%') or 	
      CONCAT(NombreLibro, ' ', autor) 
	  like CONCAT('%', _dato, '%')

ORDER BY NombreLibro ASC;


END $$

/*######################################################################*/

/*USAR PROCEDIMIENTOS ALMACENADOS - (INSERTAR 10 REGISTROS)*/


CALL SP_INSERTAR_LIBROS_LOWER('The Story of the Ladies of Wrestling',
							  'Bashirian-Weimann',
							  'Zaneta Shakesby',
							   437.36);

CALL SP_INSERTAR_LIBROS_LOWER('Mysterious X, The (Sealed Orders)',
   							  'Roberts Group',
							  'Gabi Nathon',
							  247.15);

CALL SP_INSERTAR_LIBROS_LOWER('Private Affairs of Bel Ami, The',
   							  'Bartoletti and Sons',
							  'Mabelle Vasic',
							  80.77);

CALL SP_INSERTAR_LIBROS_LOWER('Last Laugh, The (Letzte Mann, Der)',
							  'Bradtke LLC',
							  'Baryram Longmate',
							  77.91);

CALL SP_INSERTAR_LIBROS_LOWER('Dark Woods (Villmark)',
							  'Grimes LLC',
							  'Care Schust', 
							  166.24);

CALL SP_INSERTAR_LIBROS_LOWER('The Mascot',
  							  'Gutmann-Kunze',
							  'Zackariah Pollie',
							  737.79);

CALL SP_INSERTAR_LIBROS_LOWER('Kes',
							  'Von-Schamberger', 
							  'Matelda Dickerson',
							  435.52);

CALL SP_INSERTAR_LIBROS_LOWER('Molly Maguires, The',
							  'Waelchi-Schmidt',
							  'Leslie McKirton',
							  429.81);

CALL SP_INSERTAR_LIBROS_LOWER('Night Editor', 
							  'Conn and Sons',
							  'Margo Whyffen',
							  502.35);

CALL SP_INSERTAR_LIBROS_LOWER('The Tomi Ungerer Story',
							  'Beatty-Schamberger', 
							  'Catherine Cadney', 
							  845.15);

-- datos proporcionados gracias a mockaroo.com

/*######################################################################*/

--YA QUE NO HAY PARAMETROS DE ENTRADA 

CALL SP_LISTAR_LIBROS();

/*######################################################################*/

CALL SP_ACTUALIZAR_LIBROS(1, 
						 'Green Pastures, The',
						 'Jakubowski, Mills and Schneider', 
						 'Nettie Izzett',
						  930.24);

CALL SP_ACTUALIZAR_LIBROS(2,
						 'Private Function, A',
						 'Boyer, Kessler and Labadie',
						 'Tiertza Hathway',
						  134.05);

CALL SP_ACTUALIZAR_LIBROS(3,
						 'Solaris (Solyaris)',
						 'Wyman-Koelpin', 
						 'Wolfy Leggitt',
						  264.38);

CALL SP_ACTUALIZAR_LIBROS(4, 
						 'Guerrilla: The Taking of Patty Hearst',
						 'Lynch-Purdy',
						 'Doyle Daintith',
						  873.35);

CALL SP_ACTUALIZAR_LIBROS(5, 
						 'Winning Season, The', 
						 'Tromp, Botsford and Cole',
						 'Veda Faiers',
						  748.99);

CALL SP_ACTUALIZAR_LIBROS(6,
						 'Woman of the Year', 
						 'Hegmann-Feil',
						 'Bessie Booler', 
						  336.11);

CALL SP_ACTUALIZAR_LIBROS(7,
						 'Supercondriaque',
						 'Okuneva, Stroman and Ratke',
						 'Belia Nelissen', 
						  11.4);

CALL SP_ACTUALIZAR_LIBROS(8, 
						 'Falling in Love Again',
						 'Murphy-Trantow',
						 'Rivkah Beddingham',
						  963.71);

CALL SP_ACTUALIZAR_LIBROS(9,
						 'Falls, The, DAmore, Rosenbaum and Jerde',
						 'Hanan Bolding',
						 'Caleb Clarckson'
						  843.24);

CALL SP_ACTUALIZAR_LIBROS(10, 
						  'King of Devils Island (Kongen av Basty)',
						  'Nolan and Sons', 
						  'Seka Gimbrett', 
						   497.43);

/*######################################################################*/

CALL SP_BORRAR_LIBROS_ID(1);

CALL SP_BORRAR_LIBROS_ID(2);

CALL SP_BORRAR_LIBROS_ID(3);

CALL SP_BORRAR_LIBROS_ID(4);

CALL SP_BORRAR_LIBROS_ID(5);

CALL SP_BORRAR_LIBROS_ID(6);

CALL SP_BORRAR_LIBROS_ID(7);

CALL SP_BORRAR_LIBROS_ID(8);

CALL SP_BORRAR_LIBROS_ID(9);

CALL SP_BORRAR_LIBROS_ID(10);

/*######################################################################*/

CALL SP_FILTRAR_LIBROS('DATO');

/*######################################################################*/

create database sena;
use sena;

create table usuario(

    ID int primary key AUTO_INCREMENT,
    Nombres varchar(50) not null,
    telefono varchar(20) not null

);
 
/*######################################################################*/
  
DELIMITER $$

CREATE PROCEDURE SP_INSERTAR_USUARIO(


IN _Nombres varchar(50),
IN _telefono varchar(20)

)
BEGIN

IF (not exists(select ID 
               from usuario
               where Nombres = _Nombres and 
                     telefono = _telefono)) then

INSERT INTO usuario(Nombres,
                   telefono)
                   values(
					   _Nombres),
                       _telefono)
					   );
     
      
 
 
 END IF;
              
END $$

/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_LISTAR_USUARIOS(

)

BEGIN

SELECT ID,
       Nombres,
       telefono

FROM usuario
ORDER BY Nombres ASC;


END $$

/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_BORRAR_USUARIOS(

IN _Nombres varchar(50),
IN _telefono varchar(50)
)
BEGIN

DELETE FROM usuario

WHERE ID = (SELECT ID 
            FROM usuario 
            where Nombres = _Nombres and 
				  telefono = _telefono);


END $$


/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_ACTUALIZAR_USUARIOS(
  IN _ID int,
  IN _Nombres varchar(50),
  IN _telefono varchar(50),
)

BEGIN

	UPDATE usuario
	
	SET 
		Nombres = _Nombres,
		telefono = _telefono,
		
	WHERE ID = _ID;

	
END $$

/*######################################################################*/

DELIMITER $$

CREATE PROCEDURE SP_FILTRAR_USUARIO(

IN _dato varchar(100)
  
)

BEGIN

SELECT ID,
       Nombres,
       telefono

FROM usuario

WHERE Nombres like CONCAT('%', _dato ,'%') or
      telefono like CONCAT('%' , _dato ,'%') or
      CONCAT(Nombres, ' ', telefono ) like 
	  CONCAT ('%', _dato, '%')

ORDER BY Nombres ASC;


END $$

/*######################################################################*/




