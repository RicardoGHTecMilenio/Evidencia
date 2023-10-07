package Clinica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;


public class Administrador {
	
private Map<String, String> administradores; // HashMap para almacenar los contactos
	
	public Administrador() {
		administradores = new HashMap<>();
	}

// Carga los contactos del archivo csv
public void load () {
	try {
		
		Scanner scanner = new Scanner(System.in);
		
		// Verificar si el archivo CSV existe, si no, crear uno nuevo
		String carpetaDB = "db";
        String archivoCSV = carpetaDB + File.separator +"Administradores.csv";
        File archivo = new File(archivoCSV);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("No existe el archivo Administradores, se creara uno nuevo.");
                
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al crear el archivo CSV.");
            }
        }         

		BufferedReader reader = new BufferedReader(new FileReader(archivoCSV)); //se crea BufferReader para leer el archivo
		String line;
		
		// Se lee cada linea del archivo mediante un bucle 
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(",");
			
			// Se verifica si hay dos partes que son numero y nombre
			if (parts.length == 2) {
				administradores.put(parts[0], parts[1]); // Si hay dos partes, se agregan al hashmap contactos
			}
		}
		
		reader.close(); // Se cierra el bufferReader
		//En caso de un error en la lectura del archivo manda un mensaje de error
	} catch (IOException e) {
		System.err.println("Error al cargar el archivo: " + e.getMessage());
	}
	
}

// Guarda los nuevos contactos en el archivo csv
public void save () {

	try { 
		
		String carpetaDB = "db";
		String archivoCSV = carpetaDB + File.separator + "Administradores.csv";
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(archivoCSV)); //Se crea BufferedWriter para escribir en el archivo
		
		// Se itera a traves de cada entrada del hashmap administradores
		for (Map.Entry<String, String> entry : administradores.entrySet()) {
			writer.write(entry.getKey() + "," + entry.getValue()); // Se escribe el administrador y la contrasena separados por una coma
			writer.newLine();
		}
		writer.close();		// Se cierra BufferWriter despues de escribir el administrador nuevo
		
		//Crea un mensaje de error en caso de no poder guardar la informacion
	} catch (IOException e) {
		System.err.println("Error al guardar el archivo: " + e.getMessage());
	}
	
	
}

public void create (String adminName, String password) {
	administradores.put(adminName, password);
}

// Elimina un contacto segun el numero proporcionado por el usuario
public void delete (String adminName) {
	administradores.remove(adminName);
}

public boolean verificarAdmin () {
	
	Scanner scanner = new Scanner(System.in); //Crea un objeto scanner para leer la entrada del usuario
	
	 System.out.println("Ingrese el identificador del administrador:");
     String adminName = scanner.nextLine();
     System.out.println("Ingrese la contrasena del administrador:");
     String password = scanner.nextLine();

     if (verificarAdministrador(adminName, password)) {
         System.out.println("Acceso concedido.");
         // Logica para continuar con las operaciones del administrador
         return true;
     } else {
         System.out.println("Acceso denegado. Verifique su identificacion y contrasena.");
         
         if (administradores.isEmpty()) {
             System.out.println("No se encontraron administradores. Por favor, cree el primer administrador.");
             System.out.println("Ingrese el identificador del administrador:");
             String newAdminName = scanner.nextLine();
             System.out.println("Ingrese la contrasena del administrador:");
             String newPassword = scanner.nextLine();
             create (newAdminName, newPassword); 
             return true;
            } else {
            	return false;
            }
        }
}

private boolean verificarAdministrador(String adminName, String password) {
    // Busca el identificador en el HashMap de administradores y compara las contrasenas
    String savedPassword = administradores.get(adminName);
    return savedPassword != null && savedPassword.equals(password);
}

	
}
