package consultora;

import java.io.*;
import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Programador extends Trabajador {
    
    private int hsTrabajadasMes;
    private double pxh;
    String registroDiasTrabajados;
    private double sueldo;

    public Programador() {
    }
    
    
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
    
    
    
    

    public Programador(int hsTrabajadasMes, double pxh, String registroDiasTrabajados, double sueldo, String nombre, String apellido, String legajo) {
        super(nombre, apellido, legajo);
        this.hsTrabajadasMes = hsTrabajadasMes;
        this.pxh = pxh;
        this.registroDiasTrabajados = registroDiasTrabajados;
        this.sueldo = sueldo;
    }
    
    

    
    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
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
    public static double calcularSueldoProgramador(String nombre, String apellido, LocalDate fechaDesde, LocalDate fechaHasta) throws IOException, NumberFormatException {
        //creamos la variable sueldo
        double sueldo = 0;
        
        //Extraemos al programador solicitado de la base de datos
        Programador programador = obtenerProgramador(nombre, apellido);
        
        //Obtenemos un ArrayList con las fechas trabajadas por el programador
        ArrayList<FechasyHoras> fechasProgramador = obtenerArrayFechasTrabajadas(nombre, apellido);
        
        //Calculamos las horas trabajadas en el año y mes solicitado
        int horas = calcularHorasTrabajadas(fechasProgramador, fechaDesde, fechaHasta);

        //Calculamos el sueldo que corresponderia en base a las horas trabajadas y cuanto se le paga por hora
        sueldo = programador.getPxh() * horas;
        return sueldo;
        
    }

    //Registra a un Programador en la base de datos
    public static void registrarProgramador(String nombre, String apellido, String legajo, double pxh) throws IOException {

        //Instanciamos un nuevo objeto cliente con los datos obtenidos de la interfaz
        Programador programador = new Programador(pxh, nombre, apellido, legajo);

        // Guardamos los datos en la base de datos
        baseDeDatosProgramador(programador);
    }

    //Base de datos de los programadores
    public static void baseDeDatosProgramador(Programador prog) throws IOException, FileNotFoundException {
        try {
            String file = "BASE DE DATOS\\EMPLEADOS\\PROGRAMADORES\\";
            String nombreRegistro = prog.getNombre() + prog.getApellido();
            // Crear un FileWriter para escribir en el archivo de texto
            FileWriter fileWriter = new FileWriter(file + "programadores.txt", true);

            // Crear un BufferedWriter para escribir en el FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Escribir los atributos en el archivo de texto
            writer.write("Nombre: " + prog.getNombre() + "; ");
            writer.write("Apellido: " + prog.getApellido() + "; ");
            writer.write("Legajo: " + prog.getLegajo() + "; ");
            writer.write("Sueldo por hora: " + prog.getPxh() + "; ");
            writer.write("Registro Personal: " + file + "REGISTRO PERSONAL\\" + nombreRegistro + "Personal.txt");
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Programador registrado exitosamente");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al Programador " + e.getMessage());
        }
    }

    //Funcion en la que podemos escribir en el TXT
    public static void registrarDiaProgramadorTXT(String nombre, String apellido, LocalDate fechaLocalDate, String horas) throws IOException {
        try {            
            String nombreRegistro = nombre + apellido;
            String fileName = "BASE DE DATOS\\EMPLEADOS\\PROGRAMADORES\\REGISTRO PERSONAL\\" + nombreRegistro + "Personal.txt";

            FileWriter filewriter = new FileWriter(fileName, true);
            BufferedWriter writer = new BufferedWriter(filewriter);

            String fecha = fechaLocalDate.toString();
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
    public static Programador obtenerProgramador(String nombre, String apellido) {
        try {

            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("BASE DE DATOS\\EMPLEADOS\\PROGRAMADORES\\programadores.txt");

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
                    String registro = atributos[9];

                    // Crear un nuevo objeto Programador con los atributos leídos
                    Programador programador = new Programador(pxh, registro, nombreProgramador, apellidoProgramador, legajo);

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

    //Busca en la base de datos PROPIA de cada programador y retorna un ArrayList ordenado con las fechas y horas trabajadas por cada programador
    public static ArrayList<FechasyHoras> obtenerArrayFechasTrabajadas(String nombre, String apellido) throws IOException {
        try {

            String nombreRegistro = nombre + apellido;
            String fileName = "BASE DE DATOS\\EMPLEADOS\\PROGRAMADORES\\REGISTRO PERSONAL\\" + nombreRegistro + "Personal.txt";
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            ArrayList<FechasyHoras> fechasProgramador = new ArrayList();
            // Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("Fecha")) {
                    //Guardamos TODAS las fechas en un ArrayList
                    String[] lineaArreglo = linea.split(": |; ");                    
                    LocalDate fecha = LocalDate.parse(lineaArreglo[1]);
                    int horas = Integer.parseInt(lineaArreglo[3]);
                    FechasyHoras fechas = new FechasyHoras(fecha, horas);
                    fechasProgramador.add(fechas);
                }

            }

            //Ordenamos el ArrayList
            Collections.sort(fechasProgramador, Comparator.comparing(FechasyHoras::getFecha));
            reader.close();
            return fechasProgramador;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }

    }

    public static int calcularHorasTrabajadas(ArrayList<FechasyHoras> fechasProgramador, LocalDate fechaDesde, LocalDate fechaHasta) {
        int horas = 0;
        //Calculamos las horas trabajadas en base a la fecha especifica que el usuario solicita
        if (fechaDesde == null & fechaHasta == null) {
            for (FechasyHoras fecha : fechasProgramador) {

                horas += fecha.getHoras();
            }
        } else {
        //Calculamos las horas trabajadas desde que ingresó a la consultora
            for (FechasyHoras fecha : fechasProgramador) {
                if (fecha.getFecha().compareTo(fechaDesde) >= 0 & fecha.getFecha().compareTo(fechaHasta) <= 0) {
                    horas += fecha.getHoras();
                }
            }
        }

        return horas;
    }

}
