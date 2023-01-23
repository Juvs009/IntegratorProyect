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
	
	//var_dump($mensajeRecibido);
	//die();
	
	// Comprobamos que están todos los datos en el json que hemos recibido
	// Funcion declarada en jsonEsperado.php
	if(JSONCorrectoEscribir($mensajeRecibido)){
		
		$arr = $mensajeRecibido; 
		
		$Personas = $arr["Personas"];
		
		$i = 0;
		
		$todoOK = true;
		$error = "";
		
		while($i < count($Personas)){
			$Persona = $Personas[$i];
			$id = $Persona["idPersona"];
			$nombre = $Persona["nombrePersona"];
			$apellido = $Persona["apellidoPersona"];
			$nota = $Persona["notaPersona"];
			$curso = $Persona["cursoPersona"];
			
			$query = "INSERT INTO alumnos VALUES ($id, '$nombre', '$apellido', $nota, '$curso')";
			
			//echo $query;
			
			$result = $conn->query ( $query );
		
			if (!isset ( $result ) || !$result) { // Si pasa por este if, la query está está bien y se obtiene resultado
				$todoOK = false;
				$error .= $conn->error; 
			}
			$i++;
		
		}
		
	if($todoOK){
		
			$arrMensaje["estado"] = "ok";
			$arrMensaje["Mensaje"] = "Personas escritas correctamente";
		
	}else{ // Nos ha llegado un json no tiene los campos necesarios
		
		$arrMensaje["estado"] = "error";
		$arrMensaje["error"] = $error; // Array vacío si no hay resultados
	}

}else{	// No nos han enviado el json correctamente
	
		$arrMensaje["estado"] = "error";
		$arrMensaje["mensaje"] = "EL JSON NO CONTIENE LOS CAMPOS ESPERADOS";
		$arrMensaje["recibido"] = $mensajeRecibido;
	
}
}
$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
echo $mensajeJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();

die();

?>

