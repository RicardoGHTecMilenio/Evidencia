package Clinica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Cita {
    private Map<String, CitaInfo> citas; // HashMap para almacenar informacion de citas

    public Cita() {
        citas = new HashMap<>();
    }

    // Clase para almacenar la informacion de cada cita
    public class CitaInfo {
        private String fechaHora;
        private String motivo;
        private String doctor;
        private String paciente;

        public CitaInfo(String fechaHora, String motivo, String doctor, String paciente) {
            this.fechaHora = fechaHora;
            this.motivo = motivo;
            this.doctor = doctor;
            this.paciente = paciente;
        }

        public String getFechaHora() {
            return fechaHora;
        }

        public String getMotivo() {
            return motivo;
        }

        public String getDoctor() {
            return doctor;
        }

        public String getPaciente() {
            return paciente;
        }
    }

    // Carga la informacion de las citas desde el archivo CSV
    public void load() {
        try {
        	String carpetaDB = "db"; 
            String archivoCSV = carpetaDB + File.separator + "Citas.csv";
            File archivo = new File(archivoCSV);

            // Verifica si el archivo CSV existe, si no, crea uno nuevo
            if (!archivo.exists()) {
                try {
                    archivo.createNewFile();
                    System.out.println("No existe el archivo Citas, se creara uno nuevo.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al crear el archivo CSV.");
                }
            }

            BufferedReader reader = new BufferedReader(new FileReader(archivoCSV));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Verifica si hay partes suficientes para representar una cita
                if (parts.length == 5) {
                    String idCita = parts[0];
                    String fechaHora = parts[1];
                    String motivo = parts[2];
                    String doctor = parts[3];
                    String paciente = parts[4];
                    citas.put(idCita, new CitaInfo(fechaHora, motivo, doctor, paciente));
                }
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Guarda la informacion de las citas en el archivo CSV
    public void save() {
        try {
        	String carpetaDB = "db"; 
            String archivoCSV = carpetaDB + File.separator + "Citas.csv";

            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoCSV));

            for (Map.Entry<String, CitaInfo> entry : citas.entrySet()) {
                String idCita = entry.getKey();
                CitaInfo citaInfo = entry.getValue();
                writer.write(idCita + "," + citaInfo.getFechaHora() + "," + citaInfo.getMotivo() + "," +
                             citaInfo.getDoctor() + "," + citaInfo.getPaciente());
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Crea una nueva cita
    public void create(String idCita, String fechaHora, String motivo, String doctor, String paciente) {
        citas.put(idCita, new CitaInfo(fechaHora, motivo, doctor, paciente));
    }

    // Elimina una cita
    public void delete(String idCita) {
        citas.remove(idCita);
    }

    public Map<String, CitaInfo> getCitas() {
        return citas;
     }
}
