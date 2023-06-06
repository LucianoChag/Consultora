package consultora;

import java.io.*;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public static void calcularSueldoProgramador(String nombre, String apellido, LocalDate fechaDesde, LocalDate fechaHasta) throws IOException, NumberFormatException {
        //creamos la variable sueldo
        double sueldo = 0;

        //Extraemos al programador solicitado de la base de datos
        Programador programador = consultarProgramador(nombre, apellido);

        //Calculamos las horas trabajadas en el año y mes solicitado
        boolean soloHoras = false;
        int horas = calcularHorasTrabajadasMes(nombre, apellido, fechaDesde, fechaHasta, soloHoras);

        //Calculamos el sueldo que corresponderia en base a las horas trabajadas y cuanto se le paga por hora
        sueldo = programador.getPxh() * horas;

        JOptionPane.showMessageDialog(null, "Sueldo a liquidar: $" + sueldo);
    }

    //Registra a un Programador en la base de datos
    public static void registrarProgramador(String nombre, String apellido, String legajo, double pxh) throws IOException {
        //Pasamos los Strings a MAYUSCULAS para estandarizar la base de datos
        nombre = nombre.toUpperCase();
        apellido = apellido.toUpperCase();

        //Instanciamos un nuevo objeto cliente con los datos obtenidos de la interfaz
        Programador programador = new Programador(pxh, nombre, apellido, legajo);

        // Guardamos los datos en la base de datos
        baseDeDatosProgramador(programador);
    }

    //Base de datos de los programadores
    public static void baseDeDatosProgramador(Programador prog) throws IOException, FileNotFoundException {
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

        String horas = JOptionPane.showInputDialog("Ingrese horas trabajadas el " + fecha);

        registrarDiaProgramadorTXT(nombre, apellido, fecha, horas);
    }

    //Funcion en la que podemos escribir en el TXT
    public static void registrarDiaProgramadorTXT(String nombre, String apellido, LocalDate fechaLocalDate, String horas) throws IOException {
        try {
            nombre = nombre.toUpperCase();
            apellido = apellido.toUpperCase();
            String nombreRegistro = nombre + apellido;
            String fileName = "Empleados\\Programadores\\RegistroPersonal\\" + nombreRegistro + "Personal.txt";

            FileWriter filewriter = new FileWriter(fileName, true);
            BufferedWriter writer = new BufferedWriter(filewriter);

            String fecha = fechaLocalDate.toString();
            fecha = fecha.replace("-", "/");
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

                    // Crear un nuevo objeto Programador con los atributos leídos
                    Programador programador = new Programador(pxh, nombreProgramador, apellidoProgramador, legajo);

                    reader.close();
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
    public static int calcularHorasTrabajadasMes(String nombre, String apellido, LocalDate fechaDesde, LocalDate fechaHasta, boolean soloHoras) throws IOException {
        try {
            nombre = nombre.toUpperCase();
            apellido = apellido.toUpperCase();

            String nombreRegistro = nombre + apellido;
            String fileName = "Empleados\\Programadores\\RegistroPersonal\\" + nombreRegistro + "Personal.txt";
            int horasTrabajadas = 0;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            ArrayList<FechayHoras> fechasProgramador = new ArrayList();
            // Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("Fecha")) {
                    //Guardamos TODAS las fechas en un ArrayList
                    String[] lineaArreglo = linea.split(": |; ");
                    LocalDate fecha = LocalDate.parse(lineaArreglo[1]);
                    int horas = Integer.parseInt(lineaArreglo[3]);
                    FechayHoras fechas = new FechayHoras(fecha, horas);
                    fechasProgramador.add(fechas);
                }

            }

            //Ordenamos el ArrayList
            Collections.sort(fechasProgramador, Comparator.comparing(FechayHoras::getFecha));

            //Calculamos las horas trabajadas en base a la fecha especifica que el usuario solicita
            if (soloHoras) {
                for (FechayHoras fecha : fechasProgramador) {
                    if (fecha.getFecha().compareTo(fechaDesde) >= 0 & fecha.getFecha().compareTo(fechaHasta) <= 0) {
                        horasTrabajadas += fecha.getHoras();
                    }
                }
            } else {
                for (FechayHoras fecha : fechasProgramador) {

                    horasTrabajadas += fecha.getHoras();
                }
            }
            reader.close();
            return horasTrabajadas;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return 0;
        }

    }

}
