package consultora;

import java.io.*;
import javax.swing.*;
import java.time.LocalDate;

public class Programador extends Trabajador {

    private int hsTrabajadasTotales;
    private int hsTrabajadasMes;
    private double pxh;
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
    public static double calcularSueldoProgramador(String nombre, String apellido, String ano, String mes) throws IOException, NumberFormatException {
        //creamos la variable sueldo
        double sueldo = 0;

        //Extraemos al programador solicitado de la base de datos
        Programador programador = consultarProgramador(nombre, apellido);

        //Utilizamos un bloque try-catch por si el usuario ingresa un mes con numeros "03" o con palabras "marzo"
        try {
            //En caso de que sea un numero lo verificamos creando esta variable, si logra castearse a int entonces 
            //se llama a la funcion para estandarizar el mes 
            int mesInteger = Integer.parseInt(mes);
            mes = castearMes(mes);
        } catch (NumberFormatException e) {
            //En caso de que sea una palabra, larga la excepcion y se estandariza el mes en este bloque
            mes = mes.toUpperCase();
            switch (mes) {
                case "ENERO" ->
                    mes = "01_" + mes;
                case "FEBRERO" ->
                    mes = "02_" + mes;
                case "MARZO" ->
                    mes = "03_" + mes;
                case "ABRIL" ->
                    mes = "04_" + mes;
                case "MAYO" ->
                    mes = "05_" + mes;
                case "JUNIO" ->
                    mes = "06_" + mes;
                case "JULIO" ->
                    mes = "07_" + mes;
                case "AGOSTO" ->
                    mes = "08_" + mes;
                case "SEPTIEMBRE" ->
                    mes = "09_" + mes;
                case "OCTUBRE" ->
                    mes = "10_" + mes;
                case "NOVIEMBRE" ->
                    mes = "11_" + mes;
                case "DICIEMBRE" ->
                    mes = "12_" + mes;

            }
        }

        //Calculamos las horas trabajadas en el año y mes solicitado
        int horas = calcularHorasTrabajadasMes(nombre, apellido, ano, mes);

        //Calculamos el sueldo que corresponderia en base a las horas trabajadas y cuanto se le paga por hora
        sueldo = programador.getPxh() * horas;

        return sueldo;
    }

    //Registra a un Programador en la base de datos
    public static void registrarProgramador() {
        // Obtener los datos del programador desde la interfaz gráfica
        Programador prog = programadorInterfaz();

        // Guardamos los datos en la base de datos
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

    //Buscar en la Base de Datos un Programador solicitado por el Usuario
    public static Programador consultarProgramador(String nombre, String apellido) {
        try {
            nombre = nombre.toUpperCase();
            apellido = apellido.toUpperCase();

            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("Empleados\\Programadores\\programadores.txt");

            // Crear un BufferedReader para leer el FileReader
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            //Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                //Verificamos si el nombre que ingresa el usuario coincide con el nombre en la base de datos
                if (linea.contains(nombre) & linea.contains(apellido)) {
                    // Extraer los valores de los atributos
                    //Lo que hace este metodo es separar el string en distintos substring, utilizando el delimitador ": "
                    //Luego con el [] accedemos al valor y se lo asignamos a una variable.
                    String[] atributos = linea.split(": |; ");
                    String nombreProgramador = atributos[1];
                    String apellidoProgramador = atributos[3];
                    String legajo = atributos[5];
                    double pxh = Double.parseDouble(atributos[7]);

                    // Crear un nuevo objeto Analista con los atributos leídos
                    Programador programador = new Programador(pxh, nombreProgramador, apellidoProgramador, legajo);

                    reader.close();
                    JOptionPane.showMessageDialog(null, "Programador extraido de la base de datos correctamente");
                    return programador;
                }
            }
            reader.close();
            //Si no se encuentra retornar null
            return null;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al extraer programador de la base de datos. " + e.getMessage());
            return null;
        }
    }

    //Busca en la base de datos PROPIA de cada programador y retorna las horas trabajadas en un año y mes especifico
    public static int calcularHorasTrabajadasMes(String nombre, String apellido, String ano, String mesCarpeta) throws IOException {
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
                if (linea.contains(ano) & linea.contains("horas")) {
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
    
    //Estandariza el mes por si el usuario ingresa un mes en formato "01" o "ENERO"
    public static String castearMes(String mes) {
        if (mes.equals("10")) {
            mes = "10_OCTUBRE";
            return mes;
        } else {
            mes = mes.replaceAll("0", "");
            switch (mes) {
                case "1" ->
                    mes = "01_ENERO";
                case "2" ->
                    mes = "02_FEBRERO";
                case "3" ->
                    mes = "03_MARZO";
                case "4" ->
                    mes = "04_ABRIL";
                case "5" ->
                    mes = "05_MAYO";
                case "6" ->
                    mes = "06_JUNIO";
                case "7" ->
                    mes = "07_JULIO";
                case "8" ->
                    mes = "08_AGOSTO";
                case "9" ->
                    mes = "09_SEPTIEMBRE";
                case "11" ->
                    mes = "11_NOVIEMBRE";
                case "12" ->
                    mes = "12_DICIEMBRE";
            }
            return mes;
        }
    }

}
