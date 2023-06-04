package consultora;

import java.io.*;
import javax.swing.*;


public class Cliente {

    private String nombre;
    private String apellido;
    private String direccion;
    private double Pxcobrar;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String direccion, double Pxcobrar) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.Pxcobrar = Pxcobrar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPxcobrar() {
        return Pxcobrar;
    }

    public void setPxcobrar(double Pxcobrar) {
        this.Pxcobrar = Pxcobrar;
    }

    //Registra a un Cliente en la base de datos        
    public static void registrarCliente(String nombre, String apellido, String direccion, double pxh) throws IOException {
        //Pasamos los Strings a MAYUSCULAS para estandarizar la base de datos
        nombre = nombre.toUpperCase();
        apellido = apellido.toUpperCase();
        direccion = direccion.toUpperCase();
        
        //Instanciamos un nuevo objeto cliente con los datos obtenidos de la interfaz
        Cliente cliente = new Cliente(nombre, apellido, direccion, pxh);

        //Guardamos los atributos del objeto en una base de datos (TXT)
        baseDeDatosCliente(cliente);
    }

    //Base de datos de los clientes
    public static void baseDeDatosCliente(Cliente cliente) throws IOException {

        try {
            FileWriter fileWriter = new FileWriter("Clientes\\clientes.txt", true);

            // Crear un BufferedWriter para escribir en el FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);
            // Obtener los atributos del analista
            String nombre = cliente.getNombre();
            String apellido = cliente.getApellido();
            String direccion = cliente.getDireccion();
            double pxcobrar = cliente.getPxcobrar();

            // Escribir los atributos en el archivo de texto
            writer.write("Nombre: " + nombre + "; ");
            writer.write("Apellido: " + apellido + "; ");
            writer.write("Dirección: " + direccion + "; ");
            writer.write("Precio por cobrar: $" + pxcobrar + "; ");
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
        } catch (IOException e) {
            System.out.println("Error al registrar el cliente: " + e.getMessage());
        }

    }

    //Buscar en la Base de Datos un Cliente solicitado por el Usuario
    public static Cliente consultarCliente(String nombre, String apellido) {
        try {
            nombre = nombre.toUpperCase();
            apellido = apellido.toUpperCase();
            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("Clientes\\clientes.txt");

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
                    String[] atributos = linea.split(": |; |$ ");
                    String nombreCliente = atributos[1];
                    String apellidoCliente = atributos[3];
                    String direccion = atributos[5];
                    String PxcobrarString = atributos[7].replaceAll("\\$", "");
                    double Pxcobrar = Double.parseDouble(PxcobrarString);
                    // Crear un nuevo objeto Cliente con los atributos leídos
                    Cliente cliente = new Cliente(nombreCliente, apellidoCliente, direccion, Pxcobrar);
                    reader.close();
                    JOptionPane.showMessageDialog(null, "Cliente extraido de la base de datos correctamente");
                    return cliente;
                }
            }
            reader.close();
            //Si no se encuentra retornar null
            return null;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al extraer cliente de la base de datos. " + e.getMessage());
            return null;
        }
    }
}
