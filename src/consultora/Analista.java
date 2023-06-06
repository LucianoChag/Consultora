package consultora;

import java.io.*;
import javax.swing.*;
import java.util.Map;

public class Analista extends Trabajador {

    private String categoria;

    public Analista(String nombre, String apellido, String legajo) {
        super(nombre, apellido, legajo);
    }

    public Analista(String categoria, String nombre, String apellido, String legajo) {
        super(nombre, apellido, legajo);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    

    //Permite la liquidación de haberes de los analistas
    public static void calcularSueldoAnalista(String nombre, String apellido) {
        //creamos la variable sueldo
        double sueldo = 0; 
        
        //Extraemos de la base de datos el analista solicitado
        Analista analista = consultarAnalista(nombre, apellido);
        
        //Calculamos el sueldo en base a su categoria
        switch (analista.getCategoria()){
            case "Inicial" ->
                sueldo = 70000;
            case "Intermedio" ->
                sueldo = 120000;
            case "Superior" ->
                sueldo = 200000;
        }
        
        JOptionPane.showMessageDialog(null, "Sueldo a liquidar: $" + sueldo);
    }

    //Registra a un Analista en la base de datos
    public static void registrarAnalista(String nombre, String apellido, String legajo, String categoria) throws IOException {
        //Pasamos los Strings a MAYUSCULAS para estandarizar la base de datos
        nombre = nombre.toUpperCase();
        apellido = apellido.toUpperCase();
        
        //Instanciamos un nuevo objeto cliente con los datos obtenidos de la interfaz
        Analista analista = new Analista(categoria, nombre, apellido, legajo);
        
        //Guardar los analistas en una base de datos (txt)
        baseDeDatosAnalista(analista);
    }

    //Base de datos de los analistas
    public static void baseDeDatosAnalista(Analista ana) throws FileNotFoundException, IOException {
        try {
            // Crear un FileWriter para escribir en el archivo de texto
            FileWriter fileWriter = new FileWriter("Empleados\\Analistas\\analistas.txt", true);

            // Crear un BufferedWriter para escribir en el FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Obtener los atributos del analista
            String nombre = ana.getNombre();
            String apellido = ana.getApellido();
            String legajo = ana.getLegajo();
            String categoria = ana.getCategoria();

            // Escribir los atributos en el archivo de texto
            writer.write("Nombre: " + nombre + "; ");
            writer.write("Apellido: " + apellido + "; ");
            writer.write("Legajo: " + legajo + "; ");
            writer.write("Categoria: " + categoria + "; ");
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Analista registrado exitosamente");
        } catch (IOException e) {
            System.out.println("Error al registrar el analista: " + e.getMessage());
        }
    }

    //Buscar en la Base de Datos un Analista solicitado por el Usuario
    public static Analista consultarAnalista(String nombre, String apellido) {
        try {
            nombre = nombre.toUpperCase();
            apellido = apellido.toUpperCase();
            
            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("Empleados\\Analistas\\analistas.txt");

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
                    String nombreAnalista = atributos[1];
                    String apellidoAnalista = atributos[3];
                    String legajo = atributos[5];
                    String categoria = atributos[7];
                    
                    // Crear un nuevo objeto Analista con los atributos leídos
                    Analista analista = new Analista(categoria, nombreAnalista, apellidoAnalista, legajo);

                    reader.close();
                    //JOptionPane.showMessageDialog(null, "Analista extraido de la base de datos correctamente");
                    return analista;
                }
            }
            reader.close();
            //Si no se encuentra retornar null
            return null;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al extraer analista de la base de datos. " + e.getMessage());
            return null;
        }
    }
}
