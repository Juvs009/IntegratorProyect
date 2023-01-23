<?php


function JSONCorrectoActualizar($recibido){
	
	$auxCorrecto = false;
	
	if(isset($recibido["idPersona"]) && isset($recibido["nombrePersona"]) && isset($recibido["apellidoPersona"]) && isset($recibido["notaPersona"]) && isset($recibido["cursoPersona"]) && isset($recibido["peticion"]) && 
	($recibido["peticion"]=="add" || $recibido["peticion"]=="update") ){
		
			$auxCorrecto = true;
		
	}
	
	
	return $auxCorrecto;
	
}
function JSONCorrectoEliminar($recibido){
	
	$auxCorrecto = false;
	
	if(isset($recibido["idPersona"]) && isset($recibido["peticion"]) && ($recibido["peticion"]=="delete" || $recibido["peticion"]=="buscarID")){
		
			$auxCorrecto = true;
		
	}
	
	
	return $auxCorrecto;
	
}
function JSONCorrectoEscribir($recibido){
	
	$auxCorrecto = false;
	
	if(isset($recibido["peticion"]) && $recibido["peticion"]=="addAll" && isset($recibido["Personas"]) && is_array($recibido["Personas"])){
		
			$auxCorrecto = true;
		
	}
	
	
	return $auxCorrecto;
	
}
function JSONCorrectoBuscarST($recibido){
	
	$auxCorrecto = false;
	
	if(isset($recibido["Mensaje"]) && isset($recibido["peticion"]) && $recibido["peticion"]=="buscarST") {
		
			$auxCorrecto = true;
		
	}
	
	
	return $auxCorrecto;
	
}
