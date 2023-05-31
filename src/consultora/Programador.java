package consultora;

import java.util.ArrayList;

public class Programador extends Trabajador{
    private int hsTrabajadasTotales;
    private int hsTrabajadasMes;
    public ArrayList<DiasyHoras> mes;

    public Programador(String nombre, String legajo) {
        super(nombre, legajo);
    }

    public Programador(int hsTrabajadasTotales, String nombre, String legajo) {
        super(nombre, legajo);
        this.hsTrabajadasTotales = hsTrabajadasTotales;
    }

    public int getHsTrabajadasTotales() {
        return hsTrabajadasTotales;
    }

    public void setHsTrabajadasTotales(int hsTrabajadasTotales) {
        this.hsTrabajadasTotales = hsTrabajadasTotales;
    }
    
    
    
}
