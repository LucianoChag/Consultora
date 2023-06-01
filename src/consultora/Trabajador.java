
package consultora;




public class Trabajador {    
    private String nombreYapellido;
    private String legajo;
    
    public Trabajador() {
    }

    public Trabajador(String nombreYapellido, String legajo) {
        this.nombreYapellido = nombreYapellido;
        this.legajo = legajo;
    }

    public String getNombreYapellido() {
        return nombreYapellido;
    }

    public void setNombreYapellido(String nombreYapellido) {
        this.nombreYapellido = nombreYapellido;
    }

    

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }
    
    
    

    
}
