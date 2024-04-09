package edu.cue;

import edu.cue.models.Cita;
import edu.cue.models.Paciente;
import edu.cue.persistencia.Persistencia;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Paciente> pacientes = new ArrayList<>();
    private static ArrayList<Cita> citas = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        pacientes = Persistencia.cargarPacientes();
        citas = Persistencia.cargarCitas();

        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Registrar un nuevo paciente");
            System.out.println("2. Registrar una nueva cita para un paciente existente");
            System.out.println("3. Mostrar la lista de pacientes registrados");
            System.out.println("4. Mostrar la lista de citas registradas para un paciente específico");
            System.out.println("5. Actualizar datos de un paciente existente");
            System.out.println("6. Eliminar una cita agendada");
            System.out.println("7. Eliminar un paciente del sistema");
            System.out.println("8. Salir del programa");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    registrarCita();
                    break;
                case 3:
                    mostrarPacientes();
                    break;
                case 4:
                    mostrarCitasPaciente();
                    break;
                case 5:
                    actualizarDatosPaciente();
                    break;
                case 6:
                    eliminarCitaAgendada();
                    break;
                case 7:
                    eliminarPaciente();
                    break;
                case 8:
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private static void registrarPaciente() throws Exception {
        System.out.println("\nRegistrando nuevo paciente:");

        System.out.print("Cedula: ");
        String cedula = scanner.nextLine();
        if (verificarExistenciaPaciente(cedula)){
            throw new Exception("El paciente ya existe");
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty() || nombre.isBlank()){
            throw new Exception("Ingrese un nombre adecuado");
        }

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        if (apellido.isEmpty() || apellido.isBlank()){
            throw new Exception("Ingrese un apellido adecuado");
        }

        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        if (edad < 1 || edad > 100){
            throw  new Exception("Ingrese una edad de 1 a 100");
        }
        scanner.nextLine();

        System.out.print("Género: ");
        String genero = scanner.nextLine();
        if (genero.isEmpty() || genero.isBlank()){
            throw new Exception("Ingrese un genero adecuado");
        }

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        if (direccion.isEmpty() || direccion.isBlank()){
            throw new Exception("Ingrese una dirección adecuada");
        }

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        if (telefono.isEmpty() || telefono.isBlank() || telefono.length()!=10){
            throw new Exception("Ingrese un telefono adecuado");
        }

        Paciente paciente = new Paciente(cedula, nombre, apellido, edad, genero, direccion,telefono);
        pacientes.add(paciente);
        System.out.println("Paciente registrado exitosamente.");
        Persistencia.guardarPacientes(pacientes);
    }

    private static void registrarCita() throws Exception {
        System.out.println("\nRegistrando nueva cita:");
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados. Por favor registre un paciente primero.");
            return;
        }
        System.out.println("Lista de pacientes:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getName() + " " + pacientes.get(i).getLastName() + " " + pacientes.get(i).getCedula());
        }
        System.out.print("Seleccione el número correspondiente al paciente: ");
        int indicePaciente = scanner.nextInt();
        scanner.nextLine();
        if (indicePaciente < 1 || indicePaciente > pacientes.size()) {
            System.out.println("Número de paciente no válido.");
            return;
        }
        Paciente paciente = pacientes.get(indicePaciente - 1);

        System.out.print("Ingrese el dia: ");
        int dia = Integer.parseInt(scanner.nextLine());
        if (dia<1 || dia>31) {
            throw new Exception("Ingrese un día válido");
        }

        System.out.print("Ingrese el mes: ");
        int mes = Integer.parseInt(scanner.nextLine());
        if (mes<1 || mes>12 || mes<LocalDateTime.now().getMonthValue()){
            throw new Exception("Ingrese un mes válido");
        }

        System.out.print("Ingrese el año: ");
        int anio = Integer.parseInt(scanner.nextLine());
        if (anio<LocalDateTime.now().getYear() || anio>2030){
            throw new Exception("Ingrese un año válido");
        }

        String fecha = dia+"/"+mes+"/"+anio;

        System.out.print("Hora: ");
        String hora = scanner.nextLine();
        if (hora.isEmpty() || hora.isBlank()){
            throw new Exception("Es necesario una hora");
        }

        System.out.print("Motivo: ");
        String motivo = scanner.nextLine();
        if (motivo.isBlank() || motivo.isEmpty()){
            throw new Exception("Es necesario un motivo");
        }

        Cita cita = new Cita(fecha, hora, motivo, paciente);
        citas.add(cita);
        System.out.println("Cita registrada exitosamente.");
        Persistencia.guardarCitas(citas);
    }

    private static void mostrarPacientes() {
        System.out.println("\nLista de pacientes registrados:");
        if (pacientes.isEmpty()){
            System.out.println("No hay pacientes agregados");
        }else{
            for (int i = 0; i < pacientes.size(); i++) {
                Paciente paciente = pacientes.get(i);
                System.out.println((i + 1) + ". Nombre: " + paciente.getName() + " " + paciente.getLastName() + " Cédula: " + paciente.getCedula());
            }
        }
    }

    private static void mostrarCitasPaciente() {
        if (pacientes.isEmpty()) {
            System.out.println("\nNo hay pacientes registrados.");
            return;
        }

        System.out.println("\nSeleccione el paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getName() + " " + pacientes.get(i).getLastName());
        }
        System.out.print("Seleccione el número correspondiente al paciente: ");
        int indicePaciente = scanner.nextInt();
        scanner.nextLine();
        if (indicePaciente < 1 || indicePaciente > pacientes.size()) {
            System.out.println("Número de paciente no válido.");
            return;
        }

        Paciente paciente = pacientes.get(indicePaciente - 1);
        System.out.println("\nLista de citas para " + paciente.getName() + " " + paciente.getLastName() + ":");
        boolean citasEncontradas = false;
        for (Cita cita : citas) {
            if (cita.getPaciente().getCedula().equals(paciente.getCedula())) {
                System.out.println("Fecha: " + cita.getDate() + ", Hora: " + cita.getHour() + ", Motivo: " + cita.getReason());
                citasEncontradas = true;
            }
        }
        if (!citasEncontradas) {
            System.out.println("No hay citas registradas para este paciente.");
        }
    }

    public static void actualizarDatosPaciente() throws Exception {
        System.out.println("\nSeleccione el paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getName() + " " + pacientes.get(i).getLastName());
        }
        System.out.print("Seleccione el número correspondiente al paciente: ");
        int indicePaciente = scanner.nextInt();
        scanner.nextLine();
        if (indicePaciente < 1 || indicePaciente > pacientes.size()) {
            System.out.println("Número de paciente no válido.");
            return;
        }

        Paciente paciente = pacientes.get(indicePaciente - 1);

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty() || nombre.isBlank()){
            throw new Exception("Ingrese un nombre adecuado");
        }
        paciente.setName(nombre);

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        if (apellido.isEmpty() || apellido.isBlank()){
            throw new Exception("Ingrese un apellido adecuado");
        }
        paciente.setLastName(apellido);

        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        if (edad < 1 || edad > 100){
            throw  new Exception("Ingrese una edad de 1 a 100");
        }
        scanner.nextLine();
        paciente.setAge(edad);

        System.out.print("Género: ");
        String genero = scanner.nextLine();
        if (genero.isEmpty() || genero.isBlank()){
            throw new Exception("Ingrese un genero adecuado");
        }
        paciente.setGender(genero);

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        if (direccion.isEmpty() || direccion.isBlank()){
            throw new Exception("Ingrese una dirección adecuada");
        }
        paciente.setAdress(direccion);

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        if (telefono.isEmpty() || telefono.isBlank() || telefono.length()!=10){
            throw new Exception("Ingrese un telefono adecuado");
        }
        paciente.setPhoneNumber(telefono);

        Persistencia.guardarPacientes(pacientes);
    }

    private static void eliminarCitaAgendada() throws Exception {
        if (pacientes.isEmpty()) {
            System.out.println("\nNo hay pacientes registrados.");
            return;
        }

        System.out.println("\nSeleccione el paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getName() + " " + pacientes.get(i).getLastName());
        }
        System.out.print("Seleccione el número correspondiente al paciente: ");
        int indicePaciente = scanner.nextInt();
        scanner.nextLine();
        if (indicePaciente < 1 || indicePaciente > pacientes.size()) {
            System.out.println("Número de paciente no válido.");
            return;
        }

        Paciente paciente = pacientes.get(indicePaciente - 1);
        System.out.println("\nLista de citas para " + paciente.getName() + " " + paciente.getLastName() + ":");
        boolean citasEncontradas = false;
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getPaciente().getCedula().equals(paciente.getCedula())) {
                System.out.println("Codigo: "+i+ " Fecha: " + citas.get(i).getDate() + ", Hora: " + citas.get(i).getHour() + ", Motivo: " + citas.get(i).getReason());
                citasEncontradas = true;
            }
        }
        if (!citasEncontradas) {
            System.out.println("No hay citas registradas para este paciente.");
        }else{
            System.out.println("Ingrese el codigo de la cita que quiere cancelar: ");
            int codigo = scanner.nextInt();
            if (citas.get(codigo).getPaciente().getCedula().equals(paciente.getCedula())){
                citas.remove(codigo);
                System.out.println("Cita eliminada correctamente!");
                Persistencia.guardarCitas(citas);
            }else{
                throw new Exception("Codigo invalido, pacientes no coinciden");
            }
        }
    }

    public static boolean verificarExistenciaPaciente(String cedula) throws Exception{
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getCedula().equals(cedula)){
                return true;
            }
        }
        return false;
    }
    private static void eliminarPaciente() throws Exception {
        if (pacientes.isEmpty()) {
            System.out.println("\nNo hay pacientes registrados.");
            return;
        }

        System.out.println("\nSeleccione el paciente que desea eliminar:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + ". " + pacientes.get(i).getName() + " " + pacientes.get(i).getLastName() + " " + pacientes.get(i).getCedula());
        }
        System.out.print("Seleccione el número correspondiente al paciente: ");
        int indicePaciente = scanner.nextInt();
        scanner.nextLine();
        if (indicePaciente < 1 || indicePaciente > pacientes.size()) {
            System.out.println("Número de paciente no válido.");
            return;
        }

        Paciente pacienteAEliminar = pacientes.get(indicePaciente - 1);

        Iterator<Cita> iterator = citas.iterator();
        while (iterator.hasNext()) {
            Cita cita = iterator.next();
            if (cita.getPaciente().getCedula().equals(pacienteAEliminar.getCedula())) {
                iterator.remove();
            }
        }

        pacientes.remove(pacienteAEliminar);

        System.out.println("Paciente eliminado correctamente.");
        Persistencia.guardarPacientes(pacientes);
        Persistencia.guardarCitas(citas);
    }
}