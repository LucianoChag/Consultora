package consultora;

import java.io.*;
import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;

public class Programador extends Trabajador {

    private int hsTrabajadasTotales;
    private int hsTrabajadasMes;
    private double pxh; //Agregue un atributo pxh en programador para que cada programador cobre distinto en base a lo que la consultora desee
    String registroDiasTrabajados;

    public Programador(String nombre, String apellido, String legajo) {
        super(nombre, apellido, legajo);
    }

    public Programador(double pxh, String nombre, String apellido, String legajo) {
        super(nombre, apellido, legajo);
        this.pxh = pxh;
    }

    public Programador(double pxh, String registroDiasTrabajados, String nombre, String apellido, String legajo) {
        super(nombre, apellido, legajo);
        this.pxh = pxh;
        this.registroDiasTrabajados = registroDiasTrabajados;
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

    public String getRegistroDiasTrabajados() {
        return registroDiasTrabajados;
    }

    public void setRegistroDiasTrabajados(String registroDiasTrabajados) {
        this.registroDiasTrabajados = registroDiasTrabajados;
    }

    //Permite la liquidación de haberes de los programadores
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

    //Registra a un Programador en la base de datos
    public static void registrarProgramador(Map<String, Programador> programadores) {
        // Obtener los datos del programador desde la interfaz gráfica
        Programador prog = programadorInterfaz();

        programadores.put(prog.getNombre(), prog);

        baseDeDatosProgramador(prog);

    }

    //Interfaz para registrar al programador
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
        //Modificamos el nombre para que esté todo en MAYUSCULAS
        nombre = nombre.toUpperCase();
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

        // Agregar un JLabel para el apellido
        JLabel apellidoLabel = new JLabel("Apellido:");
        panel.add(apellidoLabel);

        // Solicitar el apellido al usuario mediante JOptionPane
        String apellido = JOptionPane.showInputDialog(frame, "Ingrese apellido del Programador");
        //Modificamos el apellido para que esté todo en MAYUSCULAS
        apellido = apellido.toUpperCase();
        JLabel apellidoValueLabel = new JLabel(apellido);
        panel.add(apellidoValueLabel);

        // Agregar un JLabel para el legajo
        JLabel legajoLabel = new JLabel("Legajo:");
        panel.add(legajoLabel);

        // Solicitar el legajo al usuario mediante JOptionPane
        String legajo = JOptionPane.showInputDialog(frame, "Agregue un legajo ÚNICO al Programador que desea registrar");
        JLabel legajoValueLabel = new JLabel(legajo);
        panel.add(legajoValueLabel);

        // Agregar un JLabel para el precio a pagar por hora trabajada
        JLabel pxhLabel = new JLabel("Precio por hora: $");
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
            Programador prog = new Programador(pxh, nombre, apellido, legajo);

            frame.dispose();
            return prog;

        } else {
            return null;
        }

    }

    //Base de datos de los programadores
    public static void baseDeDatosProgramador(Programador prog) {
        try {
            // Crear un FileWriter para escribir en el archivo de texto
            FileWriter fileWriter = new FileWriter("Empleados\\Programadores\\programadores.txt", true);

            // Crear un BufferedWriter para escribir en el FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Obtener los atributos del analista
            String nombre = prog.getNombre();
            String apellido = prog.getApellido();
            String legajo = prog.getLegajo();
            double precioPorHora = prog.getPxh();

            // Escribir los atributos en el archivo de texto
            writer.write("Nombre: " + nombre + "; ");
            writer.write("Apellido: " + apellido + "; ");
            writer.write("Legajo: " + legajo + "; ");
            writer.write("Sueldo por hora: " + precioPorHora + "; ");
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Programador registrado exitosamente");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al Programador " + e.getMessage());
        }
    }

    //Base de datos PROPIA de cada programador, donde se registran horas y dias trabajados
    public static void registrarDiaProgramador() throws IOException {
        String nombre = JOptionPane.showInputDialog("Nombre del programador: ");
        String apellido = JOptionPane.showInputDialog("Apellido del programador: ");

        nombre = nombre.toUpperCase();
        apellido = apellido.toUpperCase();

        JOptionPane.showMessageDialog(null, "REGISTRE FECHA TRABAJADA");
        int ano = Integer.parseInt(JOptionPane.showInputDialog("Año: "));
        int mes = Integer.parseInt(JOptionPane.showInputDialog("Mes: "));
        int dia = Integer.parseInt(JOptionPane.showInputDialog("Dia: "));
        LocalDate fecha = LocalDate.of(ano, mes, dia);

        //Creamos una variable que nos permita acceder a la carpeta con el mes correspondiente
        String mesCarpeta = "";

        //Con un Switch le asignamos valor 
        switch (mes) {
            case 1 ->
                mesCarpeta = "01_ENERO";
            case 2 ->
                mesCarpeta = "02_FEBRERO";
            case 3 ->
                mesCarpeta = "03_MARZO";
            case 4 ->
                mesCarpeta = "04_ABRIL";
            case 5 ->
                mesCarpeta = "05_MAYO";
            case 6 ->
                mesCarpeta = "06_JUNIO";
            case 7 ->
                mesCarpeta = "07_JULIO";
            case 8 ->
                mesCarpeta = "08_AGOSTO";
            case 9 ->
                mesCarpeta = "09_SEPTIEMBRE";
            case 10 ->
                mesCarpeta = "10_OCTUBRE";
            case 11 ->
                mesCarpeta = "11_NOVIEMBRE";
            case 12 ->
                mesCarpeta = "12_DICIEMBRE";

        }

        String horas = JOptionPane.showInputDialog("Ingrese horas trabajadas el " + fecha);

        registrarDiaProgramadorTXT(nombre, apellido, fecha, horas, mesCarpeta);
    }

    //Funcion en la que podemos escribir en el TXT
    public static void registrarDiaProgramadorTXT(String nombre, String apellido, LocalDate fecha, String horas, String mesCarpeta) throws IOException {
        try {
            String nombreRegistro = nombre + apellido;
            String fileName = "Empleados\\Programadores\\RegistroDias\\" + mesCarpeta + "\\" + nombreRegistro + "-" + mesCarpeta + ".txt";

            FileWriter filewriter = new FileWriter(fileName, true);
            BufferedWriter writer = new BufferedWriter(filewriter);

            writer.write("Fecha: " + fecha + "; ");
            writer.write("horas: " + horas);
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Registro existoso");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error en el registro" + e.getMessage());
        }

    }

    public static int calcularHorasTrabajadasMes(String nombre, String apellido, String mesCarpeta) throws IOException {
        try {
            nombre = nombre.toUpperCase();
            apellido = apellido.toUpperCase();
            
            String nombreRegistro = nombre + apellido;
            String fileName = "Empleados\\Programadores\\RegistroDias\\" + mesCarpeta + "\\" + nombreRegistro + "-" + mesCarpeta + ".txt";
            int horasMes = 0;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            //Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("horas")) {
                    String[] horasString = linea.split(": ");
                    int horas = Integer.parseInt(horasString[2]);
                    horasMes += horas;
                }
            }
            reader.close();
            return horasMes;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }

    }
}
