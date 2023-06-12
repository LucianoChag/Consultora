package consultora;

import java.time.LocalDate;
import java.util.ArrayList;

public class FechasyHoras {
    private LocalDate fecha;
    private int horas;
    public ArrayList<FechasyHoras> fechasProgramador;

    public FechasyHoras() {
    }

    public FechasyHoras(LocalDate fecha, int horas) {
        this.fecha = fecha;
        this.horas = horas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public ArrayList<FechasyHoras> getFechasProgramador() {
        return fechasProgramador;
    }

    public void setFechasProgramador(ArrayList<FechasyHoras> fechasProgramador) {
        this.fechasProgramador = fechasProgramador;
    }
    
    
    
}
