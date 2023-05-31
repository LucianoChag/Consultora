package consultora;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;

public class Consultora {

    public static double horaProgramador = 2000;
    public Map<String, Analista> analistas;
    public Map<String, Programador> programadores;
    public Map<String, Cliente> clientes;

    public Consultora() {
        analistas = new HashMap<>();
        programadores = new HashMap<>();
        clientes = new HashMap<>();
    }

    //Recibe como parametros el HashMap de Analistas y el nombre del analista al que se le quiere calcular el sueldo
    public static double calcularSueldoAnalista(Map<String, Analista> analistas, String nombre) {
        double sueldo = 0; //creamos la variable sueldo
        //Asignamos a una variable auxiliar el nombre del analista y el objeto analista
        for (Map.Entry<String, Analista> entry : analistas.entrySet()) {
            String nombreAux = entry.getKey();
            Analista ana = entry.getValue();
            //Si el nombre del analista == al nombre que ingresa el usuario
            if (nombre.equals(nombreAux)) {
                int categoria = ana.getCategoria();
                //calculamos su sueldo en base a su categoria
                switch (categoria) {
                    case 1 ->
                        sueldo = 20000;
                    case 2 ->
                        sueldo = 40000;
                    case 3 ->
                        sueldo = 70000;
                }
                break; //Finalizamos el bucle ya que encontramos al analista requerido        
            }
            //Deberiamos agregar condicion en caso de que no encuentre al analista

        }
        return sueldo;
    }

    //Recibe como parametros el HashMap de Programadores y el nombre del programador al que se le quiere calcular el sueldo
    public static double calcularSueldoProgramador(Map<String, Programador> programadores, String nombre) {
        double sueldo = 0; //creamos la variable sueldo
        //Asignamos a una variable auxiliar el nombre del programador y el objeto programador
        for (Map.Entry<String, Programador> entry : programadores.entrySet()) {
            String nombreAux = entry.getKey();
            Programador prog = entry.getValue();
            //Si el nombre del analista == al nombre que ingresa el usuario
            if (nombre.equals(nombreAux)) {
                sueldo = prog.getHsTrabajadasTotales() * prog.getPxh();
                break;//Finalizamos el bucle ya que encontramos al programador requerido
            }

        }
        return sueldo;
    }
    //Registra a un objeto analista en un HashMap de analistas
    public static void registrarAnalista(Map<String, Analista> analistas) {
        // Obtener los datos del analista desde la interfaz gráfica
        Analista ana = analistaInterfaz();

        analistas.put(ana.getNombre(), ana);

    }
    
    //Interfaz para registrar al objeto analista
    public static Analista analistaInterfaz() {
        // Crear un JFrame para mostrar la interfaz
        JFrame frame = new JFrame("Registrar Analista");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un JPanel para contener los componentes
        JPanel panel = new JPanel();

        // Agregar un JLabel para el nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        panel.add(nombreLabel);

        // Solicitar el nombre al usuario mediante JOptionPane
        String nombre = JOptionPane.showInputDialog(frame, "Ingrese nombre del Analista que desea registrar");
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

        // Agregar un JLabel para el legajo
        JLabel legajoLabel = new JLabel("Legajo:");
        panel.add(legajoLabel);

        // Solicitar el legajo al usuario mediante JOptionPane
        String legajo = JOptionPane.showInputDialog(frame, "Agregue un legajo ÚNICO al Analista que desea registrar");
        JLabel legajoValueLabel = new JLabel(legajo);
        panel.add(legajoValueLabel);

        // Agregar un JLabel para la categoría
        JLabel categoriaLabel = new JLabel("Categoría:");
        panel.add(categoriaLabel);

        // Crear un JComboBox con las opciones de categoría
        String[] categorias = {"Inicial", "Intermedio", "Superior"};
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias);
        panel.add(categoriaComboBox);

        // Mostrar un JOptionPane con el panel para obtener la selección del usuario
        int option = JOptionPane.showOptionDialog(
                frame, panel, "Registrar Analista",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            // Obtener la categoría seleccionada del JComboBox
            String categoriaSeleccionada = (String) categoriaComboBox.getSelectedItem();
            int categoria = 0;
            if ("Inicial".equals(categoriaSeleccionada)) {
                categoria = 1;
            } else if ("Intermedio".equals(categoriaSeleccionada)) {
                categoria = 2;
            } else {
                categoria = 3;
            }

            // Crear un objeto Analista con los datos ingresados
            Analista analista = new Analista(categoria, nombre, legajo);

            frame.dispose();
            return analista;

        } else {
            return null;
        }

    }

    //Registra a un objeto Programador en un HashMap de programadores
    public static void registrarProgramador(Map<String, Programador> programadores) {
        // Obtener los datos del programador desde la interfaz gráfica
        Programador prog = programadorInterfaz();

        programadores.put(prog.getNombre(), prog);

    }

    //Interfaz para registrar al objeto programador
    public static Programador programadorInterfaz() {
        // Crear un JFrame para mostrar la interfaz
        JFrame frame = new JFrame("Registrar Programador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un JPanel para contener los componentes
        JPanel panel = new JPanel();

        // Agregar un JLabel para el nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        panel.add(nombreLabel);

        // Solicitar el nombre al usuario mediante JOptionPane
        String nombre = JOptionPane.showInputDialog(frame, "Ingrese nombre del Programador");
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

        // Agregar un JLabel para el legajo
        JLabel legajoLabel = new JLabel("Legajo:");
        panel.add(legajoLabel);

        // Solicitar el legajo al usuario mediante JOptionPane
        String legajo = JOptionPane.showInputDialog(frame, "Agregue un legajo ÚNICO al Programador que desea registrar");
        JLabel legajoValueLabel = new JLabel(legajo);
        panel.add(legajoValueLabel);

        // Agregar un JLabel para el precio a pagar por hora trabajada
        JLabel pxhLabel = new JLabel("Precio por hora:");
        panel.add(pxhLabel);

        // Solicitar el precio por hora al usuario mediante JOptionPane
        String pxhString = JOptionPane.showInputDialog(frame, "Ingrese cuanto le pagará al programador por hora");
        JLabel pxhValueLabel = new JLabel(pxhString);
        panel.add(pxhValueLabel);

        // Mostrar un JOptionPane con el panel para obtener la selección del usuario
        int option = JOptionPane.showOptionDialog(
                frame, panel, "Registrar Programador",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            double pxh = Double.parseDouble(pxhString);

            // Crear un objeto Programador con los datos ingresados
            Programador prog = new Programador(pxh, nombre, legajo);

            frame.dispose();
            return prog;

        } else {
            return null;
        }

    }

    //JUAN X        
    public static void registrarCliente(Map<String, Cliente> clientes) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cliente: ");
        String nombre = sc.next();
        System.out.println("Direccion: ");
        String direccion = sc.next();
        System.out.println("Precio a cobrar por hora: ");
        double pxh = sc.nextDouble();
        Cliente cliente = new Cliente(nombre, direccion, pxh);

    }

    //JUAN X
    public static Cliente interfazCliente() {
        return null;
    }

    //LUCAS guardar en un txt TODOS los programadores
    public static void baseDeDatosProgramador(String nombre, LocalDate fecha, int horasTrabajadas) {
        try {
            FileWriter fileWriter = new FileWriter("programadores.txt", true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write("Programador: " + nombre);
            writer.newLine();
            writer.write("Fecha: " + fecha);
            writer.newLine();
            writer.write("Horas trabajadas: " + horasTrabajadas);
            writer.newLine();
            writer.newLine();

            writer.close();
            System.out.println("Los datos se han guardado correctamente en el archivo");
        } catch (IOException e) {
            System.out.println("Ocurrio un error al guardar los datos en el archivo " + e.getMessage());
        }
    }

    //FRANCO guardar en un txt TODOS los analistas
    public static void baseDeDatosAnalista() {
    }

    //FRANCO guardar en un txt TODOS los clientes
    public static void baseDeDatosCliente() {
    }

    public static void consultarAnalista() {
    }

    public static void consultarCliente() {
    }

    public static void registrarDiaProgramador(String nombre) {
    }

    public static void main(String[] args) {
        Map<String, Analista> analistas = new HashMap<>();
        Map<String, Programador> programadores = new HashMap<>();

        registrarAnalista(analistas);
        registrarProgramador(programadores);
        
        
        
        System.out.println(programadores.get("Pilar").getPxh());//prueba de que funciona el registrarProgramador
//        double sueldoa1 = calcularSueldoAnalista(analistas, "Luciano");
//        System.out.println(sueldoa1);
//        double sueldoP = calcularSueldoProgramador(programadores, "Pilar");
//        System.out.println(sueldoP);
    }

}
