
package Clinica;

import java.util.*;

public class Main {
	
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		Administrador administrador = new Administrador(); // Crea una instancia de la clase 
		Doctor doctor = new Doctor();
		Paciente paciente = new Paciente();
		Cita cita = new Cita();
		//CitasLista citasLista = new CitasLista();
		
		// Carga la informacion de los archivos 
		administrador.load(); 
		doctor.load();
		paciente.load();
		cita.load();
		
		boolean accesoConsedido = administrador.verificarAdmin(); // 
		while (accesoConsedido) {
			System.out.println("Bienvenido al sistema de administracion de citas");
	        System.out.println("1. Dar de alta doctores");
	        System.out.println("2. Dar de alta pacientes");
	        System.out.println("3. Crear cita");
	        System.out.println("4. Ver citas relacionadas con doctor");
	        System.out.println("5. Dar de alta administradores");
	        System.out.println("6. Guardar y Salir");
	        System.out.print("Ingrese una opcion: ");
	        int opcion = scanner.nextInt();
	        scanner.nextLine();

	        switch (opcion) {
	            case 1:
	            	System.out.println("Dar de alta doctores");
	            	System.out.println("Identificador de Doctor: ");
	    			String idDoctor = scanner.nextLine();
	    			System.out.println("Nombre de Doctor: ");
	    			String nombreDoctor = scanner.nextLine();
	    			System.out.println("Especialidad: ");
	    			String especialidad = scanner.nextLine();
	            	doctor.create(idDoctor, nombreDoctor, especialidad);
	            	System.out.println("--- Doctor agregado a la base da datos. ---");
	                break;
	                
	            case 2:
	            	System.out.println("Dar de alta paciente");
	            	System.out.println("Identificador de Paciente: ");
	    			String idPaciente = scanner.nextLine();
	    			System.out.println("Nombre de Paciente: ");
	    			String nombrePaciente = scanner.nextLine();
	            	paciente.create(idPaciente, nombrePaciente);
	            	System.out.println("--- Paciente agregado a la base da datos. ---");
	                break;
	                
	            case 3:
	            	System.out.println("Identificador de Cita: ");
	    			String idCita = scanner.nextLine();
	    			System.out.println("Fecha y Hora: ");
	    			String fechaHora = scanner.nextLine();
	    			System.out.println("Motivo: ");
	    			String motivo = scanner.nextLine();
	    			System.out.println("Identificador de Doctor: ");
	    			String idDoctorVerificacion = scanner.nextLine();
	    			if (doctor.existeDoctor(idDoctorVerificacion)) {
	    		        // El doctor existe, puedes crear la cita
	    		    } else {
	    		        // El doctor no existe, muestra un mensaje de error
	    		        System.out.println("No se encontro un doctor con el ID especificado. La cita no pudo ser creada.");
	    		        break;
	    		    }
	    			System.out.println("Identificador de Paciente: ");
	    			String idPacienteVerificacion = scanner.nextLine();
	    			if (paciente.existePaciente(idPacienteVerificacion)) {
	    		        // El paciente existe, puedes crear la cita
	    		    } else {
	    		        // El doctor no existe, muestra un mensaje de error
	    		        System.out.println("No se encontro un paciente con el ID especificado. La cita no pudo ser creada.");
	    		        break;
	    		    }
	    			
	            	cita.create(idCita, fechaHora, motivo, idDoctorVerificacion, idPacienteVerificacion);
	            	System.out.println("--- Cita agregada a la base da datos. ---");
	                break;
	                
	            case 4:
	            	System.out.println("Ver citas ");
	            	listarCitas(cita);
	                break;
	                
	            case 5:
	            	System.out.println("Identificador de Administrador: ");
	    			String adminName = scanner.nextLine();
	    			System.out.println("Password: ");
	    			String password = scanner.nextLine();
	    			administrador.create (adminName, password);
	    			System.out.println("--- Administrador agregado a la base da datos. ---");
	                break;
	                
	            case 6:
	                System.out.println("Gracias por usar el sistema de administracion de citas. Hasta luego!");
	                administrador.save(); // Guarda los cambios creados en los archivos 
	                cita.save(); 
	        		doctor.save();
	        		paciente.save();
	                System.exit(0);
	                
	            default:
	                System.out.println("Opcion invalida. Por favor, seleccione una opcion valida.");
	                break;
	        }
		}
		
		
		
	}
	
	
	// Metodo para listar todas las citas
    public static void listarCitas(Cita cita) {
        Map<String, Cita.CitaInfo> citas = cita.getCitas(); // Obten el HashMap de citas
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
        } else {
            System.out.println("Lista de citas:");
            for (Map.Entry<String, Cita.CitaInfo> entry : citas.entrySet()) {
                String idCita = entry.getKey();
                Cita.CitaInfo citaInfo = entry.getValue();
                System.out.println("ID de Cita: " + idCita);
                System.out.println("Fecha y Hora: " + citaInfo.getFechaHora());
                System.out.println("Motivo: " + citaInfo.getMotivo());
                System.out.println("Doctor: " + citaInfo.getDoctor());
                System.out.println("Paciente: " + citaInfo.getPaciente());
                System.out.println();
            }
        }
    }
}



