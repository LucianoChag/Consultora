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

    public static void empleadoMejorPago(ArrayList<Programador> programadores, LocalDate fechaDesde, LocalDate fechaHasta) throws IOException {
        double sueldoMayor = 0;
        Programador progMejorPagoMes = new Programador();
        for (Programador programador : programadores) {
            double sueldo = Programador.calcularSueldoProgramador(programador.getNombre(), programador.getApellido(), fechaDesde, fechaHasta);
            if (sueldo > sueldoMayor) {
                sueldoMayor = sueldo;
                progMejorPagoMes.setNombre(programador.getNombre());
                progMejorPagoMes.setApellido(programador.getApellido());

            }
        }
        String mensaje = """
        El empleado mejor pago es: %s %s
        Con un sueldo de: $%.2f
        """;

        String nombre = progMejorPagoMes.getNombre();
        String apellido = progMejorPagoMes.getApellido();
        String mensajeCompleto = String.format(mensaje, nombre, apellido, sueldoMayor);
        JOptionPane.showMessageDialog(null, mensajeCompleto);
    }

    public static void main(String[] args) throws IOException {

        InterfazPrincipal frame = new InterfazPrincipal();
        frame.setVisible(true);

    }

}
