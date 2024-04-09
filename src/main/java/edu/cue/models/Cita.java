package edu.cue.models;

public class Cita {
    String date;
    String hour;
    String reason;
    Paciente paciente;

    public Cita(String date, String hour, String reason, Paciente paciente) {
        this.date = date;
        this.hour = hour;
        this.reason = reason;
        this.paciente = paciente;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
