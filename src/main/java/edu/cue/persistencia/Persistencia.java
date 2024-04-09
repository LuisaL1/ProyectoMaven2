package edu.cue.persistencia;

import edu.cue.models.Cita;
import edu.cue.models.Paciente;
import edu.cue.utils.ArchivoUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Persistencia {

    public static final String RUTA_ARCHIVO_PACIENTES = "src/main/resources/archivoPacientes.txt";
    public static final String RUTA_ARCHIVO_CITAS = "src/main/resources/archivoCitas.txt";

    public static ArrayList<Cita> cargarCitas() throws IOException {

        ArrayList<Cita> citas = new ArrayList<>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_CITAS);
        String linea = "";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            String[] arreglo = linea.split(",");

            String fecha = arreglo[0];
            String hora = arreglo[1];
            String razon = arreglo[2];

            String cedula = arreglo[3];
            String nombre = arreglo[4];
            String apellido = arreglo[5];
            int edad = Integer.parseInt(arreglo[6]);
            String genero = arreglo[7];
            String dirección = arreglo[8];
            String telefono = arreglo[9];


            Paciente paciente = new Paciente(cedula,nombre,apellido,edad,genero,dirección,telefono);

            Cita cita = new Cita(fecha,hora,razon,paciente);
            citas.add(cita);
        }
        return citas;
    }

    public static ArrayList<Paciente> cargarPacientes() throws FileNotFoundException, IOException {
        ArrayList<Paciente> pacientes = new ArrayList<Paciente>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PACIENTES);
        String linea = "";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            String[] arreglo = linea.split(",");

            String cedula = arreglo[0];
            String nombre = arreglo[1];
            String apellido = arreglo[2];
            int edad = Integer.parseInt(arreglo[3]);
            String genero = arreglo[4];
            String dirección = arreglo[5];
            String telefono = arreglo[6];

            Paciente paciente = new Paciente(cedula,nombre,apellido,edad,genero,dirección,telefono);
            pacientes.add(paciente);
        }
        return pacientes;
    }

    public static void guardarPacientes(ArrayList<Paciente> listaPacientes) throws IOException {
        String contenido = "";

        for (Paciente pacienteAux : listaPacientes) {
            contenido += pacienteAux.getCedula() + "," + pacienteAux.getName() + "," + pacienteAux.getLastName() +","+ pacienteAux.getAge()
                    + "," + pacienteAux.getGender() + "," + pacienteAux.getAdress() + "," + pacienteAux.getPhoneNumber() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PACIENTES, contenido, false);
    }

    public static void guardarCitas(ArrayList<Cita> listaCitas) throws IOException {
        String contenido = "";

        for (Cita citaAux : listaCitas) {
            contenido += citaAux.getDate() + "," + citaAux.getHour() + "," + citaAux.getReason() + ","+ citaAux.getPaciente().getCedula() + "," +
                    citaAux.getPaciente().getName() + "," + citaAux.getPaciente().getLastName() +","+ citaAux.getPaciente().getAge()
                    + "," + citaAux.getPaciente().getGender() + "," + citaAux.getPaciente().getAdress() + "," + citaAux.getPaciente().getPhoneNumber()+ "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_CITAS, contenido, false);
    }

}


