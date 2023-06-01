package consultora;

public class Analista extends Trabajador{
    private int categoria;

    public Analista(String nombreYapellido, String legajo) {
        super(nombreYapellido, legajo);
    }

    public Analista(int categoria, String nombreYapellido, String legajo) {
        super(nombreYapellido, legajo);
        this.categoria = categoria;
    }

    

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
    
}
