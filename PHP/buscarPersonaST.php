<?php

require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD
require 'jsonEsperado.php';

/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */

$arrMensaje = array();  // Este array es el codificaremos como JSON tanto si hay resultado como si hay error

/*
 * Lo primero es comprobar que nos han enviado la información via JSON
 */

$parameters = file_get_contents("php://input");

if(isset($parameters)){

	// Parseamos el string json y lo convertimos a objeto JSON
	$mensajeRecibido = json_decode($parameters, true);
	// Comprobamos que están todos los datos en el json que hemos recibido
	// Funcion declarada en jsonEsperado.php
	if(JSONCorrectoBuscarST($mensajeRecibido)){
		
		$string = $mensajeRecibido; 
		
		$st = $string["Mensaje"];
		
		$query = "SELECT 
			id AS idPersona, 
			Nombre AS nombrePersona , 
			apellido as apellidoPersona,
			nota as notaPersona,
			curso as cursoPersona 
			FROM alumnos WHERE id LIKE '%$st%' or nombre LIKE '%$st%' 
			or apellido LIKE '%$st%' or Nota LIKE '%$st%' or Curso LIKE '%$st%'"; 
		
		$result = $conn->query ( $query );
		
		if (isset ( $result ) && $result) { // Si pasa por este if, la query está está bien y se obtiene resultado
	
	if ($result->num_rows > 0) { // Aunque la query esté bien puede no obtenerse resultado (tabla vacía). Comprobamos antes de recorrer
		
		$arrPersonas = array();	// Array numérico
		
		while ( $row = $result->fetch_assoc () ) {
			
			// // Por cada vuelta del bucle creamos un jugador. Como es un objeto hacemos un array asociativo
			// $arrJugador = array(); // Asociativo
			// // Por cada columna de la tabla creamos una propiedad para el objeto
			// $arrJugador["nombre"] = $row["playerName"];
			// $arrJugador["id"] = $row["playerId"];
			// $arrJugador["equipo"] = $row["idTeamFK"];
			// $arrJugador["numero"] = $row["playerNumber"];
			// // Por último, añadimos el nuevo jugador al array de Depositos
			// $arrPersonas[] = $arrJugador;
			
			$arrPersonas[] = $row;


		}
		
		// Añadimos al $arrMensaje el array de Depositos y añadimos un campo para indicar que todo ha ido OK
		$arrMensaje["estado"] = "ok";
		$arrMensaje["Personas"] = $arrPersonas;
		
		
	} else {
		
		$arrMensaje["estado"] = "ok";
		$arrMensaje["Personas"] = []; // Array vacío si no hay resultados
	}
		

		
	}else{ // Nos ha llegado un json no tiene los campos necesarios
		
		$arrMensaje["estado"] = "error";
		$arrMensaje["Personas"] = []; // Array vacío si no hay resultados
	}

}else{	// No nos han enviado el json correctamente
	
		$arrMensaje["estado"] = "error";
		$arrMensaje["mensaje"] = "EL JSON NO CONTIENE LOS CAMPOS ESPERADOS";
		$arrMensaje["recibido"] = $mensajeRecibido;
		$arrMensaje["esperado"] = $arrEsperado;
	
}
}
$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
echo $mensajeJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();

die();

?>