package Clinica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Doctor {
    private Map<String, DoctorInfo> doctores; // HashMap para almacenar informacion de doctores

    public Doctor() {
        doctores = new HashMap<>();
    }

    // Clase para almacenar la informacion de cada doctor
    public class DoctorInfo {
        private String nombre;
        private String especialidad;

        public DoctorInfo(String nombre, String especialidad) {
            this.nombre = nombre;
            this.especialidad = especialidad;
        }

        public String getNombre() {
            return nombre;
        }

        public String getEspecialidad() {
            return especialidad;
        }
    }

    // Carga la informacion de los doctores desde el archivo CSV
    public void load() {
        try {
        	String carpetaDB = "db"; 
            String archivoCSV = carpetaDB + File.separator + "Doctores.csv";
            File archivo = new File(archivoCSV);

            // Verifica si el archivo CSV existe, si no, crea uno nuevo
            if (!archivo.exists()) {
                try {
                    archivo.createNewFile();
                    System.out.println("No existe el archivo Doctores, se creara uno nuevo.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al crear el archivo CSV.");
                }
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivoCSV));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Verifica si hay partes suficientes para representar un doctor
                if (parts.length == 3) {
                    String idDoctor = parts[0];
                    String nombre = parts[1];
                    String especialidad = parts[2];
                    doctores.put(idDoctor, new DoctorInfo(nombre, especialidad));
                }
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Guarda la informacion de los doctores en el archivo CSV
    public void save() {
        try {
        	String carpetaDB = "db"; 
            String archivoCSV = carpetaDB + File.separator + "Doctores.csv";

            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoCSV));

            for (Map.Entry<String, DoctorInfo> entry : doctores.entrySet()) {
                String idDoctor = entry.getKey();
                DoctorInfo doctorInfo = entry.getValue();
                writer.write(idDoctor + "," + doctorInfo.getNombre() + "," + doctorInfo.getEspecialidad());
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Crea un nuevo doctor
    public void create(String idDoctor, String nombre, String especialidad) {
        doctores.put(idDoctor, new DoctorInfo(nombre, especialidad));
    }

    // Elimina un doctor
    public void delete(String idDoctor) {
        doctores.remove(idDoctor);
    }
    
    public boolean existeDoctor(String idDoctor) {
        return doctores.containsKey(idDoctor);
    }
}