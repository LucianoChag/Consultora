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
    public static double calcularSueldoAnalista(Map<String, Analista> analistas, String nombre) {
        double sueldo = 0; //creamos la variable sueldo
        //Asignamos a una variable auxiliar el nombre del analista y el objeto analista
        for (Map.Entry<String, Analista> entry : analistas.entrySet()) {
            String nombreAux = entry.getKey();
            Analista ana = entry.getValue();
            //Si el nombre del analista == al nombre que ingresa el usuario
            if (nombre.equals(nombreAux)) {
                //calculamos su sueldo en base a su categoria
                switch (ana.getCategoria()) {
                    case "Inicial" ->
                        sueldo = 20000;
                    case "Intermedio" ->
                        sueldo = 40000;
                    case "Superior" ->
                        sueldo = 70000;
                }
                break; //Finalizamos el bucle ya que encontramos al analista requerido        
            }
            //Deberiamos agregar condicion en caso de que no encuentre al analista

        }
        return sueldo;
    }

    //Registra a un Analista en la base de datos
    public static void registrarAnalista(Map<String, Analista> analistas) throws IOException {
        // Obtener los datos del analista desde la interfaz gráfica
        Analista ana = analistaInterfaz();

        //Guardar los analistas en el HashMap 
        analistas.put(ana.getNombre(), ana);//capaz esta linea habrá que sacarla porque al guardar los datos en una base de datos no hace falta el hashmap

        //Guardar los analistas en una base de datos (txt)
        baseDeDatosAnalista(ana);
    }

    //Interfaz para registrar al analista
    public static Analista analistaInterfaz() {
        // Crear un JFrame para mostrar la interfaz
        JFrame frame = new JFrame("Registrar Analista");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un JPanel para contener los componentes
        JPanel panel = new JPanel();

        // Agregar un JLabel para el nombre
        JLabel nombreLabel = new JLabel("Nombre:");
        panel.add(nombreLabel);

        // Solicitar el nombre al usuario mediante JOptionPane
        String nombre = JOptionPane.showInputDialog(frame, "Ingrese nombre del Analista");
        //Modificamos el nombre para que esté todo en MAYUSCULAS
        nombre = nombre.toUpperCase();
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

        // Agregar un JLabel para el apellido
        JLabel apellidoLabel = new JLabel("Apellido:");
        panel.add(apellidoLabel); 
        
        // Solicitar el apellido al usuario mediante JOptionPane
        String apellido = JOptionPane.showInputDialog(frame, "Ingrese apellido del Analista");
        //Modificamos el apellido para que esté todo en MAYUSCULAS
        apellido = apellido.toUpperCase();
        JLabel apellidoValueLabel = new JLabel(apellido);
        panel.add(apellidoValueLabel);

        // Agregar un JLabel para el legajo
        JLabel legajoLabel = new JLabel("Legajo:");
        panel.add(legajoLabel);

        // Solicitar el legajo al usuario mediante JOptionPane
        String legajo = JOptionPane.showInputDialog(frame, "Agregue un legajo ÚNICO al Analista que desea registrar");
        JLabel legajoValueLabel = new JLabel(legajo);
        panel.add(legajoValueLabel);

        // Agregar un JLabel para la categoría
        JLabel categoriaLabel = new JLabel("Categoría:");
        panel.add(categoriaLabel);

        // Crear un JComboBox con las opciones de categoría
        String[] categorias = {"Inicial", "Intermedio", "Superior"};
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias);
        panel.add(categoriaComboBox);

        // Mostrar un JOptionPane con el panel para obtener la selección del usuario
        int option = JOptionPane.showOptionDialog(
                frame, panel, "Registrar Analista",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            // Obtener la categoría seleccionada del JComboBox
            Object item = categoriaComboBox.getSelectedItem();
            String categoria = item.toString();

            // Crear un objeto Analista con los datos ingresados
            Analista analista = new Analista(categoria, nombre, apellido, legajo);

            frame.dispose();
            return analista;

        } else {
            return null;
        }

    }

    //Base de datos de los analistas
    public static void baseDeDatosAnalista(Analista ana) throws FileNotFoundException, IOException {
        try {
            // Crear un FileWriter para escribir en el archivo de texto
            FileWriter fileWriter = new FileWriter("Empleados\\analistas.txt", true);

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
            FileReader fileReader = new FileReader("Empleados\\analistas.txt");

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
                    JOptionPane.showMessageDialog(null, "Analista extraido de la base de datos correctamente");
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
