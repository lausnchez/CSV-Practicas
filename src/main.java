import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class main {

	static Scanner scan = new Scanner(System.in);
	final static File fichero = new File("src/ficheros/fichero.csv");
	final static int LARGO_CADENAS = 10;
	
	public static void main(String[] args) {
		menu();
	}
	
	private static void menu() {
		int opcion = -1;
		
		while(opcion != 0) {
			System.out.println("\nPráctica de ficheros CSV");
			System.out.println("----------------------------------");
			System.out.println("1. Añadir personas al fichero");
			System.out.println("2. Mostrar datos del fichero");
			System.out.println("3. Añadir una persona al fichero");
			System.out.println("4. Mostrar las personas de una ciudad dada");
			System.out.println("5. Mostrar las personas de una ciudad y menores de una edad dada");
			System.out.println("----------------------------------");
			System.out.println("0. Salir");
			opcion = scan.nextInt();
			switch(opcion) {
				case 1:
					agregarVariasPersonas();
					break;
				case 2:
					mostrarTodo();
					break;
				case 3:
					agregarPersona();
					break;
				case 4:
					mostrarPorCiudad();
					break;
				case 5:
					mostrarPorCiudadYEdad();
					break;
			}
		}
	}
	
	// FUNCIONES DE AGREGAR -------------------------------------------------------------------
	
	private static void agregarPersona() {
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			file.seek(file.length());
			// NOMBRE DE LA PERSONA
			int numCaracteres = 0;
			scan.nextLine();
			System.out.println("   * Nombre: ");
			String nombre = scan.nextLine();
			// CIUDAD DE LA PERSONA
			System.out.println("   * Ciudad: ");
			String ciudad = scan.nextLine();
			// EDAD DE LA PERSONA
			int edad = 0;
			System.out.println("   * Edad: ");
			edad = scan.nextInt();
			
			mostrarUnaPersona(nombre, ciudad, String.valueOf(edad));
			
			String persona = "";
			persona = persona.concat(nombre)
							.concat(";")
							.concat(ciudad)
							.concat(";");
			file.writeChars(persona);
			file.writeInt(edad);
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void agregarVariasPersonas(){
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			System.out.println(" - AGREGAR PERSONAS -");
			System.out.println("¿Cuántas personas quieres agregar?");
			int numPersonas = scan.nextInt();
			file.seek(file.length());
			for(int i=1; i <= numPersonas; i++) {
				System.out.println(" - Persona " + i + ":");
				agregarPersona();
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("No se encuentra el fichero");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
	// FUNCIONES DE MOSTRAR -------------------------------------------------------------------
	private static void mostrarUnaPersona(String nombre, String ciudad, String edad) {
		System.err.println("\n - Nombre: " + nombre);
		System.out.println(" - Ciudad: " + ciudad);
		System.out.println(" - Edad: " + edad);
	}
	
	private static void mostrarTodo() {
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			System.out.println("---- Listado de personas ----------------\n");
			while(file.getFilePointer() != file.length()) {
				// NOMBRE
				char[] auxNombre = new char[50];
				int i = 0;
				for(;;) {
					char temporal = file.readChar();
					if(temporal == ';') break;
					auxNombre[i] = temporal;
					i++;
				}
				// CIUDAD
				char[] auxCiudad = new char[50];
				i = 0;
				for(;;) {
					char temporal = file.readChar();
					if(temporal == ';') break;
					auxCiudad[i] = temporal;
					i++;
				}			
				// EDAD
				int edad = file.readInt();	

				mostrarUnaPersona(new String(auxNombre), new String(auxCiudad), String.valueOf(edad));
			}	
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void mostrarPorCiudad() {
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			file.seek(0);
			//Pedir ciudad
			scan.nextLine();
			System.out.println("¿De qué ciudad desea buscar?");
			String busqueda = scan.nextLine();
			int contador = 0;
			while(file.getFilePointer() != file.length()) {
				// NOMBRE
				char[] auxNombre = new char[50];
				int i = 0;
				for(;;) {
					char temporal = file.readChar();
					if(temporal == ';') break;
					auxNombre[i] = temporal;
					i++;
				}
				// CIUDAD
				char[] auxCiudad = new char[50];
				i = 0;
				for(;;) {
					char temporal = file.readChar();
					if(temporal == ';') break;
					auxCiudad[i] = temporal;
					i++;
				}			
				// EDAD
				int edad = file.readInt();
				
				String nombre = new String(auxNombre);
				String ciudad = new String(auxCiudad);
				if(busqueda.trim().equalsIgnoreCase(ciudad.trim())) {
					contador++;
					mostrarUnaPersona(nombre, ciudad, String.valueOf(edad));
				}
			}
			if(contador == 0) System.err.println("No se encontraron resultados");

			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void mostrarPorCiudadYEdad() {
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			file.seek(0);
			//Pedir ciudad
			scan.nextLine();
			System.out.println("¿De qué ciudad desea buscar?");
			String busqueda = scan.nextLine();
			System.out.println("¿Cuál es la edad máxima de la persona?");
			int busquedaEdad = scan.nextInt();
			int contador = 0;
			while(file.getFilePointer() != file.length()) {
				// NOMBRE
				char[] auxNombre = new char[50];
				int i = 0;
				for(;;) {
					char temporal = file.readChar();
					if(temporal == ';') break;
					auxNombre[i] = temporal;
					i++;
				}
				// CIUDAD
				char[] auxCiudad = new char[50];
				i = 0;
				for(;;) {
					char temporal = file.readChar();
					if(temporal == ';') break;
					auxCiudad[i] = temporal;
					i++;
				}			
				// EDAD
				int edad = file.readInt();
				
				String nombre = new String(auxNombre);
				String ciudad = new String(auxCiudad);
				if(busqueda.trim().equalsIgnoreCase(ciudad.trim()) && edad < busquedaEdad) {
					contador++;
					mostrarUnaPersona(nombre, ciudad, String.valueOf(edad));
				}
			}
			if(contador == 0) System.err.println("No se encontraron resultados");

			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
