package consultora;


public class Programador extends Trabajador {

    private int hsTrabajadasTotales;
    private int hsTrabajadasMes;
    private double pxh; //Agregue un atributo pxh en programador para que cada programador cobre distinto en base a lo que la consultora desee
    String registroDiasTrabajados;

    public Programador(String nombreYapellido, String legajo) {
        super(nombreYapellido, legajo);
    }

    public String getRegistroDiasTrabajados() {
        return registroDiasTrabajados;
    }

    public void setRegistroDiasTrabajados(String registroDiasTrabajados) {
        this.registroDiasTrabajados = registroDiasTrabajados;
    }

    public Programador(String nombreYapellido, String legajo, double pxh) {
        super(nombreYapellido, legajo);
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

}
