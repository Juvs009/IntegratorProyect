package t1lago;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controlador {
	
	Funcionalidad func = null;
	Funcionalidad func2 = null;
	Scanner s = new Scanner(System.in);
	
	public Funcionalidad MenuSeleccion(Funcionalidad func, Visor v) {
		int opcManager = v.MenuFunc();
		switch(opcManager) {
		case 1:
			func = new FileManager();
			break;
		case 2:
			func = new XMLManager();
			break;
		case 3:
			func = new DBManager();
			break;
		case 4: 
			func = new AccesoHibernate();
			break;
		case 5:
			func = new JSONManager();
			break;
		default:;		
	}
		return func;
	}
	
	public int MenuOpciones(int opcOperacion, Visor v, Funcionalidad func, Funcionalidad func2) {
		try {
		while(opcOperacion != 8 && opcOperacion !=9) {
			opcOperacion = v.MenuOperacion();
			switch (opcOperacion) {
			case 1:
				HashMap<Integer, Persona> hm = func.Leer();
				v.MostrarPersonas(hm);
				break;
			case 2:
				int l = v.MenuPedirID();
				if(func.ComprobarID(l)) {
					func.EliminarPersona(l);
					v.MensajePersonaBorrada();
					}else {
						v.MensajeErrorPersona();
					}

				break;
			case 3:
				Persona p = new Persona();
				p = v.MenuPedirPersona();
				if(!func.ComprobarID(p.getId())) {
				func.NuevaPersona(p);
				}else {
					v.MensajeIDDuplicado();
				}
				break;
			case 4:
					p = v.MenuPedirPersona();
					if(func.ComprobarID(p.getId())) {
						func.ModificarPersona(p);
						v.MensajePersonaModificada();
					}else {
						v.MensajeErrorModificar();
					}

				break;
			case 5:
				HashMap<Integer, Persona> hmm2 = new HashMap<Integer, Persona>();
				int id = v.MenuPedirID();
				p = func.BuscarPersonaID(id);
				if(p != null) {
					hmm2.put(p.getId(), p);
					v.MostrarPersonas(hmm2);
				}else {
					System.err.println("Esa persona no existe");
				}
				break;
			case 6:
				HashMap<Integer, Persona> hmm = func.Leer();
				String st = v.MenuPedirString();
				v.MostrarPersonas(func.BuscarPersonaSt(hmm, st));
				break;
			case 7:
					func2 = v.MenuExportar();
					HashMap<Integer, Persona> hm2 = new HashMap<Integer, Persona>();
					hm2 = func.Leer();
					func2.Escribir(hm2);
					v.MensajeExportacionCorrecta();
				break;
			case 8:
				v.MensajeTipo();
				break;
			case 9:
				v.MensajeAdios();
				break;
			default:
				System.out.println("Escribe un número válido");
			}
		}
		}catch(InputMismatchException ime) {
			v.MensajeErrorInput();
		}
		return opcOperacion;
	}
	
	public void IniciarPrograma(Visor v, Controlador c) {
		try {
			int opcOperacion = 0;
			while(opcOperacion == 0 || opcOperacion == 8) {
			func = c.MenuSeleccion(func, v);
			opcOperacion = 0;
			opcOperacion = c.MenuOpciones(opcOperacion, v, func, func2);
			}
	
			s.close();
		}catch(NullPointerException e) {
			System.err.println("Ruta de fichero incorrecta, compruebe el archivo properties");
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
