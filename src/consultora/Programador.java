package consultora;

import java.util.ArrayList;

public class Programador extends Trabajador{
    private int hsTrabajadasTotales;
    private int hsTrabajadasMes;
    private double pxh; //Agregue un atributo pxh en programador para que cada programador cobre distinto en base a lo que la consultora desee
    public ArrayList<DiasyHoras> mes;

    public Programador(String nombre, String legajo) {
        super(nombre, legajo);
    }

    public Programador(double pxh, String nombre, String legajo) {
        super(nombre, legajo);
        this.pxh = pxh;
    }

    

    public int getHsTrabajadasTotales() {
        return hsTrabajadasTotales;
    }

    public void setHsTrabajadasTotales(int hsTrabajadasTotales) {
        this.hsTrabajadasTotales = hsTrabajadasTotales;
    }

    public int getHsTrabajadasMes() {
        return hsTrabajadasMes;
    }

    public void setHsTrabajadasMes(int hsTrabajadasMes) {
        this.hsTrabajadasMes = hsTrabajadasMes;
    }

    public double getPxh() {
        return pxh;
    }

    public void setPxh(double pxh) {
        this.pxh = pxh;
    }

    public ArrayList<DiasyHoras> getMes() {
        return mes;
    }

    public void setMes(ArrayList<DiasyHoras> mes) {
        this.mes = mes;
    }
    
    
    
    
    
}
