package consultora;

public class Analista extends Trabajador{
    private int categoria;

    public Analista(String nombre, String legajo) {
        super(nombre, legajo);
    }

    public Analista(int categoria, String nombre, String legajo) {
        super(nombre, legajo);
        this.categoria = categoria;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
    
}
