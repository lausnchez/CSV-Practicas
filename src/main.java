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
			System.out.println("Práctica de ficheros CSV");
			System.out.println("----------------------------------");
			System.out.println("1. Añadir personas al fichero");
			System.out.println("2. Mostrar datos del fichero");
			System.out.println("----------------------------------");
			System.out.println("0. Salir");
			opcion = scan.nextInt();
			switch(opcion) {
				case 1:
					agregarPersonas();
					break;
				case 2:
					mostrarPersonas();
					break;
			}
		}
	}
	
	private static void agregarPersonas(){
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			System.out.println(" - AGREGAR PERSONAS -");
			System.out.println("¿Cuántas personas quieres agregar?");
			int numPersonas = scan.nextInt();
			for(int i=1; i <= numPersonas; i++) {
				System.out.println(" - Persona " + i + ":");
				// NOMBRE DE LA PERSONA
				int numCaracteres = 0;
				String nombre = "";
				while(numCaracteres > LARGO_CADENAS || numCaracteres == 0) {
					scan.nextLine();
					System.out.println("   * Nombre (menos de 10 caracteres): ");
					nombre = scan.nextLine();
					numCaracteres = nombre.length();
				}
				StringBuffer bufferNombre = new StringBuffer(nombre);
				bufferNombre.setLength(LARGO_CADENAS);
				// CIUDAD DE LA PERSONA
				numCaracteres = 0;
				String ciudad = "";
				while(numCaracteres > LARGO_CADENAS || numCaracteres == 0) {
					System.out.println("   * Ciudad (menos de 10 caracteres): ");
					ciudad = scan.nextLine();
					numCaracteres = ciudad.length();
				}
				StringBuffer bufferCiudad = new StringBuffer(ciudad);
				bufferCiudad.setLength(LARGO_CADENAS);
				// EDAD DE LA PERSONA
				int edad = 0;
				while(edad <= 0 || edad > 100) {
					System.out.println("   * Edad (entre 0 y 100): ");
					edad = scan.nextInt();
				}
				System.out.println("...........................");
				System.out.println("Nombre: " + nombre);
				System.out.println("Ciudad: " + ciudad);
				System.out.println("Edad: " + edad);
				System.out.println("...........................\n");
				
				String persona = "";
				persona = persona.concat(new String(bufferNombre))
								.concat(";")
								.concat(new String(bufferCiudad))
								.concat(";")
								.concat(String.valueOf(edad));
				file.writeChars(persona);
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
	
	private static void mostrarPersonas() {
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			while(file.getFilePointer() != file.length()) {
				// NOMBRE
				char[] aux = new char[10];
				for(int i=0; i<10; i++) {
					aux[i] = file.readChar();
				}
				System.out.println("- Nombre: " + new String(aux));
				file.readChar();	// ;
				// CIUDAD
				aux = new char[10];
				for(int i=0; i<10; i++) {
					aux[i] = file.readChar();
				}
				System.out.println("- Ciudad: " + new String(aux));
				file.readChar(); // ;
				// EDAD
				System.out.println(" - Edad: " + file.readInt());
				System.out.println("______________________________\n");
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

}
