package consultora;

public class Cliente {
    public String nombre;
    public String direccion;
    public double Pxcobrar;
    
    public Cliente() {
    }

    public Cliente(String nombre, String direccion, double Pxcobrar) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.Pxcobrar = Pxcobrar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPxcobrar() {
        return Pxcobrar;
    }

    public void setPxH(double Pxcobrar) {
        this.Pxcobrar = Pxcobrar;
    }
    
}
