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

        Cliente.registrarCliente(clientes);
        Analista.registrarAnalista(analistas);
        Programador.registrarProgramador(programadores);
        Programador.registrarDiaProgramador();
        
        Cliente cliente = Cliente.consultarCliente("rociO", "CalVETe");
        Analista ana = Analista.consultarAnalista("LucianO", "chagnaud");
        
        System.out.println(cliente.getNombre() + cliente.getApellido() + cliente.getDireccion());
        System.out.println(ana.getNombre() + ana.getApellido());
        
        
        
    }

}
