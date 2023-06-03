package consultora;

import java.io.*;
import javax.swing.*;
import java.util.Map;

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
    public static void registrarCliente(Map<String, Cliente> clientes) throws IOException {
        // Obtener los datos del programador desde la interfaz gráfica
        Cliente cliente = clienteInterfaz();

        // Los guardamos en un HashMap (probablemente saquemos esto
        clientes.put(cliente.getNombre(), cliente);

        //Guardamos los atributos del objeto en una base de datos (TXT)
        baseDeDatosCliente(cliente);
    }

    //Interfaz para registrar al cliente
    public static Cliente clienteInterfaz() {
        // Crear un JFrame para mostrar la interfaz
        JFrame frame = new JFrame("Registrar Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un JPanel para contener los componentes
        JPanel panel = new JPanel();

        // Agregar un JLabel para el nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        panel.add(nombreLabel);

        // Solicitar el nombre al usuario mediante JOptionPane
        String nombre = JOptionPane.showInputDialog(frame, "Ingrese nombre del Cliente");
        //Modificamos el nombre para que esté todo en MAYUSCULAS
        nombre = nombre.toUpperCase();
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

        // Agregar un JLabel para el apellido
        JLabel apellidoLabel = new JLabel("Apellido:");
        panel.add(apellidoLabel);

        // Solicitar el apellido al usuario mediante JOptionPane
        String apellido = JOptionPane.showInputDialog(frame, "Ingrese apellido del Cliente");
        //Modificamos el apellido para que esté todo en MAYUSCULAS
        apellido = apellido.toUpperCase();
        JLabel apellidoValueLabel = new JLabel(apellido);
        panel.add(apellidoValueLabel);

        // Agregar un JLabel para la direccion
        JLabel direccionLabel = new JLabel("Direccion:");
        panel.add(direccionLabel);

        // Solicitar la direccion al usuario mediante JOptionPane
        String direccion = JOptionPane.showInputDialog(frame, "Agregue la direccion del cliente");
        //Modificamos la direccion para que esté todo en MAYUSCULAS
        direccion = direccion.toUpperCase();
        JLabel direccionValueLabel = new JLabel(direccion);
        panel.add(direccionValueLabel);

        // Agregar un JLabel para el precio a cobrar por hora trabajada
        JLabel PxcobrarLabel = new JLabel("Precio a cobrar por hora: $");
        panel.add(PxcobrarLabel);

        // Solicitar el precio por hora al usuario mediante JOptionPane
        String PxcobrarString = JOptionPane.showInputDialog(frame, "Ingrese cuanto se le cobrara al cliente por hora");
        JLabel PxcobrarValueLabel = new JLabel(PxcobrarString);
        panel.add(PxcobrarValueLabel);

        // Mostrar un JOptionPane con el panel para obtener la selección del usuario
        int option = JOptionPane.showOptionDialog(
                frame, panel, "Registrar Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            double Pxcobrar = Double.parseDouble(PxcobrarString);

            // Crear un objeto Programador con los datos ingresados
            Cliente cliente = new Cliente(nombre, apellido, direccion, Pxcobrar);

            frame.dispose();
            return cliente;
        } else {
            return null;
        }
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
