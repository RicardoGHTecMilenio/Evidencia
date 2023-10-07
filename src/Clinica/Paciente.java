package Clinica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Paciente {
    private Map<String, PacienteInfo> pacientes; // HashMap para almacenar informacion de pacientes

    public Paciente() {
        pacientes = new HashMap<>();
    }

    // Clase para almacenar la informacion de cada paciente
    public class PacienteInfo {
        private String nombre;

        public PacienteInfo(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }

    // Carga la informacion de los pacientes desde el archivo CSV
    public void load() {
        try {
        	String carpetaDB = "db"; 
            String archivoCSV = carpetaDB + File.separator + "Pacientes.csv";
            File archivo = new File(archivoCSV);

            // Verifica si el archivo CSV existe, si no, crea uno nuevo
            if (!archivo.exists()) {
                try {
                    archivo.createNewFile();
                    System.out.println("No existe el archivo Pacientes, se creara uno nuevo.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al crear el archivo CSV.");
                }
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivoCSV));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Verifica si hay partes suficientes para representar un paciente
                if (parts.length == 2) {
                    String idPaciente = parts[0];
                    String nombre = parts[1];
                    pacientes.put(idPaciente, new PacienteInfo(nombre));
                }
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Guarda la informacion de los pacientes en el archivo CSV
    public void save() {
        try {
        	String carpetaDB = "db"; 
            String archivoCSV = carpetaDB + File.separator + "Pacientes.csv";

            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoCSV));

            for (Map.Entry<String, PacienteInfo> entry : pacientes.entrySet()) {
                String idPaciente = entry.getKey();
                PacienteInfo pacienteInfo = entry.getValue();
                writer.write(idPaciente + "," + pacienteInfo.getNombre());
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Crea un nuevo paciente
    public void create(String idPaciente, String nombre) {
        pacientes.put(idPaciente, new PacienteInfo(nombre));
    }

    // Elimina un paciente
    public void delete(String idPaciente) {
        pacientes.remove(idPaciente);
    }
    
    public boolean existePaciente(String idPaciente) {
        return pacientes.containsKey(idPaciente);
    }
}