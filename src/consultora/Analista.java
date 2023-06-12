package consultora;

import java.io.*;
import javax.swing.*;

public class Analista extends Trabajador {

    private String categoria;

    public Analista() {
    }

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
    public static double calcularSueldoAnalista(String nombre, String apellido) {
        //creamos la variable sueldo
        double sueldo = 0;

        //Extraemos de la base de datos el analista solicitado
        Analista analista = consultarAnalista(nombre, apellido);

        //Calculamos el sueldo en base a su categoria
        switch (analista.getCategoria()) {
            case "Inicial" ->
                sueldo = 70000;
            case "Intermedio" ->
                sueldo = 120000;
            case "Superior" ->
                sueldo = 200000;
            case "Senior" ->
                sueldo = 500000;
        }
        return sueldo;

    }

    //Registra a un Analista en la base de datos
    public static void registrarAnalista(String nombre, String apellido, String legajo, String categoria) throws IOException {
      
        //Corroboramos que el analista no exista previamente en la base de datos
        boolean valido = validar(nombre, apellido);

        if (valido) {
            Analista analista = new Analista(categoria, nombre, apellido, legajo);

            //Guardar los analistas en una base de datos (txt)
            baseDeDatosAnalista(analista);
        }
    }

    //Base de datos de los analistas
    public static void baseDeDatosAnalista(Analista ana) throws FileNotFoundException, IOException {
        try {
            // Crear un FileWriter para escribir en el archivo de texto
            FileWriter fileWriter = new FileWriter("BASE DE DATOS\\EMPLEADOS\\ANALISTAS\\analistas.txt", true);

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
            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("BASE DE DATOS\\EMPLEADOS\\ANALISTAS\\analistas.txt");

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

    public static boolean validar(String nombre, String apellido) {

        // Verificar si el nombre o apellido están vacíos o son nulos
        if (nombre == null || apellido == null || nombre.equals("") || apellido.equals("")) {
            JOptionPane.showMessageDialog(null, "El nombre o apellido están vacíos, ingrese uno válido");
            return false; // Si la condición se cumple, se muestra un mensaje y se retorna false
        }

        Analista existe = consultarAnalista(nombre, apellido); // Consultar si existe un analista con el nombre y apellido dados
        boolean esValido = false;

        // Verificar si no se encontró un analista con el mismo nombre y apellido
        if (existe == null) {
            esValido = true; // Si no se encontró, se marca como válido
        }

        // Mostrar un mensaje si los datos ya están registrados en la base de datos
        if (!esValido) {
            JOptionPane.showMessageDialog(null, "Los datos ingresados ya están registrados en nuestra base de datos.");
        }

        return esValido; // Retornar el valor de esValido (true si es válido, false si no)

    }
}
