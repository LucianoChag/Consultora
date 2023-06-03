package consultora;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Consultora {

    public Map<String, Analista> analistas;
    public Map<String, Programador> programadores;
    public Map<String, Cliente> clientes;

    public Consultora() {
        analistas = new HashMap<>();
        programadores = new HashMap<>();
        clientes = new HashMap<>();
    }
    


    public static void main(String[] args) throws IOException {
        Map<String, Analista> analistas = new HashMap<>();
        Map<String, Programador> programadores = new HashMap<>();
        Map<String, Cliente> clientes = new HashMap<>();

        
        
        Programador.registrarDiaProgramador();
        
        int horas = Programador.calcularHorasTrabajadasMes("eduardo", "crucero", "04_Abril");
        System.out.println(horas);
        
        
        
        
    }

}
