package consultora;

import consultora.Interfaces.InterfazPrincipal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Consultora {

    public static ArrayList<Programador> obtenerArrayProgramadores() throws IOException {
        try {
            ArrayList<Programador> programadores = new ArrayList();
            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("BASE DE DATOS\\EMPLEADOS\\PROGRAMADORES\\programadores.txt");

            // Crear un BufferedReader para leer el FileReader
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            //Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                //Verificamos si el nombre que ingresa el usuario coincide con el nombre en la base de datos
                // Extraer los valores de los atributos
                if (linea.contains("Nombre")) {
                    String[] atributos = linea.split(": |; ");
                    String nombreProgramador = atributos[1];
                    String apellidoProgramador = atributos[3];
                    String legajo = atributos[5];
                    double pxh = Double.parseDouble(atributos[7]);
                    String registro = atributos[9];

                    // Crear un nuevo objeto Programador con los atributos leídos
                    Programador programador = new Programador(pxh, registro, nombreProgramador, apellidoProgramador, legajo);

                    //Agregamos al programador al ArrayList
                    programadores.add(programador);

                }

            }
            reader.close();
            return programadores;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al extraer programador de la base de datos. " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Analista> obtenerArrayAnalista() {
        try {
            ArrayList<Analista> analistas = new ArrayList();
            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("BASE DE DATOS\\EMPLEADOS\\ANALISTAS\\analistas.txt");

            // Crear un BufferedReader para leer el FileReader
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            //Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                //Verificamos si el nombre que ingresa el usuario coincide con el nombre en la base de datos
                // Extraer los valores de los atributos
                if (linea.contains("Nombre")) {
                    String[] atributos = linea.split(": |; ");
                    String nombre = atributos[1];
                    String apellido = atributos[3];
                    String legajo = atributos[5];
                    String categoria = atributos[7];

                    // Crear un nuevo objeto Analista con los atributos leídos
                    Analista analista = new Analista(categoria, nombre, apellido, legajo);

                    //Agregamos al analista al ArrayList
                    analistas.add(analista);

                }

            }
            reader.close();
            return analistas;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al extraer analista de la base de datos. " + e.getMessage());
            return null;
        }

    }

    public static void empleadoMejorPago(ArrayList<Programador> programadores, ArrayList<Analista> analistas, LocalDate fechaDesde, LocalDate fechaHasta) throws IOException {
        double sueldoMayorProg = 0;
        Programador progMejorPagoMes = new Programador();
        for (Programador programador : programadores) {
            double sueldo = Programador.calcularSueldoProgramador(programador.getNombre(), programador.getApellido(), fechaDesde, fechaHasta);
            if (sueldo > sueldoMayorProg) {
                sueldoMayorProg = sueldo;
                progMejorPagoMes.setNombre(programador.getNombre());
                progMejorPagoMes.setApellido(programador.getApellido());

            }
        }
        double sueldoMayorAna = 0;
        Analista anaMejorPagoMes = new Analista();
        for (Analista analista : analistas) {
            double sueldo = Analista.calcularSueldoAnalista(analista.getNombre(), analista.getApellido());
            if (sueldo > sueldoMayorAna) {
                sueldoMayorAna = sueldo;
                anaMejorPagoMes.setNombre(analista.getNombre());
                anaMejorPagoMes.setApellido(analista.getApellido());
            }
        }
        String mensaje = """
        El programador mejor pago es: %s %s
        Con un sueldo de: $%.2f
                         
        El analista mejor pago es: %s %s
        con un sueldo de: $%.2f                                           
        """;

        String nombreProg = progMejorPagoMes.getNombre();
        String apellidoProg = progMejorPagoMes.getApellido();
        String nombreAna = anaMejorPagoMes.getNombre();
        String apellidoAna = anaMejorPagoMes.getApellido();
        String mensajeCompleto = String.format(mensaje, nombreProg, apellidoProg, sueldoMayorProg, nombreAna, apellidoAna, sueldoMayorAna);
        JOptionPane.showMessageDialog(null, mensajeCompleto);
    }

    public static void main(String[] args) throws IOException {

        InterfazPrincipal frame = new InterfazPrincipal();
        frame.setVisible(true);

    }

}
