package consultora;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Consultora {

    public Map<String, Analista> analistas;
    public Map<String, Programador> programadores;
    public Map<String, Cliente> clientes;

    public Consultora() {
        analistas = new HashMap<>();
        programadores = new HashMap<>();
        clientes = new HashMap<>();
    }

    //Recibe como parametros el HashMap de Analistas y el nombre del analista al que se le quiere calcular el sueldo
    public static double calcularSueldoAnalista(Map<String, Analista> analistas, String nombre) {
        double sueldo = 0; //creamos la variable sueldo
        //Asignamos a una variable auxiliar el nombre del analista y el objeto analista
        for (Map.Entry<String, Analista> entry : analistas.entrySet()) {
            String nombreAux = entry.getKey();
            Analista ana = entry.getValue();
            //Si el nombre del analista == al nombre que ingresa el usuario
            if (nombre.equals(nombreAux)) {
                int categoria = ana.getCategoria();
                //calculamos su sueldo en base a su categoria
                switch (categoria) {
                    case 1 ->
                        sueldo = 20000;
                    case 2 ->
                        sueldo = 40000;
                    case 3 ->
                        sueldo = 70000;
                }
                break; //Finalizamos el bucle ya que encontramos al analista requerido        
            }
            //Deberiamos agregar condicion en caso de que no encuentre al analista

        }
        return sueldo;
    }

    //Recibe como parametros el HashMap de Programadores y el nombre del programador al que se le quiere calcular el sueldo
    public static double calcularSueldoProgramador(Map<String, Programador> programadores, String nombre) {
        double sueldo = 0; //creamos la variable sueldo
        //Asignamos a una variable auxiliar el nombre del programador y el objeto programador
        for (Map.Entry<String, Programador> entry : programadores.entrySet()) {
            String nombreAux = entry.getKey();
            Programador prog = entry.getValue();
            //Si el nombre del analista == al nombre que ingresa el usuario
            if (nombre.equals(nombreAux)) {
                sueldo = prog.getHsTrabajadasTotales() * prog.getPxh();
                break;//Finalizamos el bucle ya que encontramos al programador requerido
            }

        }
        return sueldo;
    }

    //Registra a un objeto analista en un HashMap de analistas
    public static void registrarAnalista(Map<String, Analista> analistas) throws IOException {
        // Obtener los datos del analista desde la interfaz gráfica
        Analista ana = analistaInterfaz();

        //Guardar los analistas en el HashMap 
        analistas.put(ana.getNombreYapellido(), ana);//capaz esta linea habrá que sacarla porque al guardar los datos en una base de datos no hace falta el hashmap

        //Guardar los analistas en una base de datos (txt)
        baseDeDatosAnalista(ana);
    }

    //Interfaz para registrar al objeto analista
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
        String nombre = JOptionPane.showInputDialog(frame, "Ingrese nombre del Analista que desea registrar");
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

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
            String categoriaSeleccionada = (String) categoriaComboBox.getSelectedItem();
            int categoria = 0;
            if ("Inicial".equals(categoriaSeleccionada)) {
                categoria = 1;
            } else if ("Intermedio".equals(categoriaSeleccionada)) {
                categoria = 2;
            } else {
                categoria = 3;
            }

            // Crear un objeto Analista con los datos ingresados
            Analista analista = new Analista(categoria, nombre, legajo);

            frame.dispose();
            return analista;

        } else {
            return null;
        }

    }

    //Registra a un objeto Programador en un HashMap de programadores
    public static void registrarProgramador(Map<String, Programador> programadores) {
        // Obtener los datos del programador desde la interfaz gráfica
        Programador prog = programadorInterfaz();

        programadores.put(prog.getNombreYapellido(), prog);

        baseDeDatosProgramador(prog);

    }

    //Interfaz para registrar al objeto programador
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
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

        // Agregar un JLabel para el legajo
        JLabel legajoLabel = new JLabel("Legajo:");
        panel.add(legajoLabel);

        // Solicitar el legajo al usuario mediante JOptionPane
        String legajo = JOptionPane.showInputDialog(frame, "Agregue un legajo ÚNICO al Programador que desea registrar");
        JLabel legajoValueLabel = new JLabel(legajo);
        panel.add(legajoValueLabel);

        // Agregar un JLabel para el precio a pagar por hora trabajada
        JLabel pxhLabel = new JLabel("Precio por hora:");
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
            Programador prog = new Programador(nombre, legajo, pxh);

            frame.dispose();
            return prog;

        } else {
            return null;
        }

    }

    //JUAN X        
    public static void registrarCliente(Map<String, Cliente> clientes) throws IOException {
        // Obtener los datos del programador desde la interfaz gráfica
        Cliente cliente = clienteInterfaz();

        clientes.put(cliente.getNombre(), cliente);
        baseDeDatosCliente(cliente);
    }

    //JUAN X
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
        JLabel nombreValueLabel = new JLabel(nombre);
        panel.add(nombreValueLabel);

        // Agregar un JLabel para la direccion
        JLabel direccionLabel = new JLabel("Direccion:");
        panel.add(direccionLabel);

        // Solicitar la direccion al usuario mediante JOptionPane
        String direccion = JOptionPane.showInputDialog(frame, "Agregue la direccion del cliente");
        JLabel direccionValueLabel = new JLabel(direccion);
        panel.add(direccionValueLabel);

        // Agregar un JLabel para el precio a cobrar por hora trabajada
        JLabel PxcobrarLabel = new JLabel("Precio a cobrar por hora:");
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
            Cliente cliente = new Cliente(nombre, direccion, Pxcobrar);

            frame.dispose();
            return cliente;
        } else {
            return null;
        }
    }

    //LUCAS guardar en un txt TODOS los programadores
    public static void baseDeDatosProgramador(Programador prog) {
        try {
            // Crear un FileWriter para escribir en el archivo de texto
            FileWriter fileWriter = new FileWriter("Empleados\\programadores.txt", true);

            // Crear un BufferedWriter para escribir en el FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Obtener los atributos del analista
            String nombreYapellido = prog.getNombreYapellido();
            String nombreRegistro = nombreYapellido.replaceAll("\\s+", ""); //Quita los espacios en blanco de un String
            String legajo = prog.getLegajo();
            double precioPorHora = prog.getPxh();
            File registroDiasTrabajados = new File("Empleados\\RegistroDiasProgramadores\\registro" + nombreRegistro + ".txt");

            // Crear el archivo para los registros de días trabajados
            registroDiasTrabajados.createNewFile();

            // Escribir los atributos en el archivo de texto
            writer.write("Nombre: " + nombreYapellido + "; ");
            writer.write("Legajo: " + legajo + "; ");
            writer.write("Sueldo por hora: " + precioPorHora + "; ");
            writer.write("Registro de la cantidad de días y horas trabajados: " + registroDiasTrabajados);
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Programador registrado exitosamente");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al Programador " + e.getMessage());
        }
    }

    //Guarda los objetos analistas en una base de datos (TXT)
    public static void baseDeDatosAnalista(Analista ana) throws FileNotFoundException, IOException {
        try {
            // Crear un FileWriter para escribir en el archivo de texto
            FileWriter fileWriter = new FileWriter("Empleados\\analistas.txt", true);

            // Crear un BufferedWriter para escribir en el FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);

            // Obtener los atributos del analista
            String nombre = ana.getNombreYapellido();
            String legajo = ana.getLegajo();
            int categoria = ana.getCategoria();
            String categoriaS = "";
            switch (categoria) {
                case 1 ->
                    categoriaS = "Inicial";
                case 2 ->
                    categoriaS = "Intermedio";
                case 3 ->
                    categoriaS = "Superior";
            }

            // Escribir los atributos en el archivo de texto
            writer.write("Nombre: " + nombre + "; ");
            writer.write("Legajo: " + legajo + "; ");
            writer.write("Categoria: " + categoriaS + "; ");
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Analista registrado exitosamente");
        } catch (IOException e) {
            System.out.println("Error al registrar el analista: " + e.getMessage());
        }
    }

    //FRANCO guardar en un txt TODOS los clientes
    public static void baseDeDatosCliente(Cliente cliente) throws IOException {

        try {
            FileWriter fileWriter = new FileWriter("Clientes\\clientes.txt", true);

            // Crear un BufferedWriter para escribir en el FileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);
            // Obtener los atributos del analista
            String nombre = cliente.getNombre();
            String direccion = cliente.getDireccion();
            double pxcobrar = cliente.getPxcobrar();

            // Escribir los atributos en el archivo de texto
            writer.write("Nombre: " + nombre + "; ");
            writer.write("Dirección: " + direccion + "; ");
            writer.write("Precio por cobrar: " + pxcobrar + "; ");
            writer.newLine();
            writer.newLine();

            // Cerrar el BufferedWriter
            writer.close();

            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
        } catch (IOException e) {
            System.out.println("Error al registrar el cliente: " + e.getMessage());
        }

    }

    //Buscar en la Base de Datos un Analista solicitado por el Usuario
    public static Analista consultarAnalista(String nombreAnalista) {
        try {
            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("Empleados\\analistas.txt");

            // Crear un BufferedReader para leer el FileReader
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            //Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                //Verificamos si el nombre que ingresa el usuario coincide con el nombre en la base de datos
                if (linea.contains(nombreAnalista)) {
                    // Extraer los valores de los atributos
                    //Lo que hace este metodo es separar el string en distintos substring, utilizando el delimitador ": "
                    //Luego con el [] accedemos al valor y se lo asignamos a una variable.
                    String[] atributos = linea.split(": |; ");
                    String nombre = atributos[1];
                    String legajo = atributos[3];
                    String categoriaS = atributos[5];
                    int categoria = 0;
                    switch (categoriaS) {
                        case "Inicial" ->
                            categoria = 1;
                        case "Intermedio" ->
                            categoria = 2;
                        case "Superior" ->
                            categoria = 3;
                    }
                    // Crear un nuevo objeto Analista con los atributos leídos
                    Analista analista = new Analista(categoria, nombre, legajo);

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

    public static Cliente consultarCliente(String nombreCliente) {
        try {
            // Crear un FileReader para leer el archivo de texto
            FileReader fileReader = new FileReader("Clientes\\clientes.txt");

            // Crear un BufferedReader para leer el FileReader
            BufferedReader reader = new BufferedReader(fileReader);

            String linea;
            //Bucle para recorrer el archivo
            while ((linea = reader.readLine()) != null) {
                //Verificamos si el nombre que ingresa el usuario coincide con el nombre en la base de datos
                if (linea.contains(nombreCliente)) {
                    // Extraer los valores de los atributos
                    //Lo que hace este metodo es separar el string en distintos substring, utilizando el delimitador ": "
                    //Luego con el [] accedemos al valor y se lo asignamos a una variable.
                    String[] atributos = linea.split(": |; ");
                    String nombre = atributos[1];
                    String direccion = atributos[3];
                    String PxcobrarString = atributos[5];
                    double Pxcobrar = Double.parseDouble(PxcobrarString);
                    // Crear un nuevo objeto Cliente con los atributos leídos
                    Cliente cliente = new Cliente(nombre, direccion, Pxcobrar);
                    reader.close();
                    JOptionPane.showMessageDialog(null, "Analista extraido de la base de datos correctamente");
                    return cliente;
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

    public static void registrarDiaProgramador() throws IOException {
        String nombreYapellido = JOptionPane.showInputDialog("Nombre y Apellido del programador: ");
        JOptionPane.showMessageDialog(null, "Ingrese fecha: ");
        int ano = Integer.parseInt(JOptionPane.showInputDialog("Año: "));
        int mes = Integer.parseInt(JOptionPane.showInputDialog("Mes: "));
        int dia = Integer.parseInt(JOptionPane.showInputDialog("Dia: "));
        LocalDate fecha = LocalDate.of(ano, mes, dia);
        String horas = JOptionPane.showInputDialog("Ingrese horas trabajadas en dicha fecha: ");

        registrarDiaProgramadorTXT(nombreYapellido, fecha, horas);
    }

    public static void registrarDiaProgramadorTXT(String nombreYapellido, LocalDate fecha, String horas) throws IOException {
        try {
            String nombreRegistro = nombreYapellido.replaceAll("\\s+", "");

            FileWriter filewriter = new FileWriter("Empleados\\RegistroDiasProgramadores\\registro" + nombreRegistro + ".txt", true);
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

    public static void main(String[] args) throws IOException {
        Map<String, Analista> analistas = new HashMap<>();
        Map<String, Programador> programadores = new HashMap<>();
        Map<String, Cliente> clientes = new HashMap<>();
//
//        for (int i = 0; i < 3; i++) {
//            registrarProgramador(programadores);
//        }
        
        registrarDiaProgramador();
        //Analista ana = consultarAnalista("Juan Cruz Filippini");
        //System.out.println(ana.getNombreYapellido() + " " + ana.getLegajo() + " " + ana.getCategoria());

        
//        for (int i = 0; i < 10; i++) {
//            registrarAnalista(analistas);
//        }

//        Analista ana = consultarAnalista("Juan Cruz Filippini");
//        System.out.println(ana.getNombreYapellido() + " " + ana.getLegajo() + " " + ana.getCategoria());
        //registrarProgramador(programadores);
        //System.out.println(clientes.get("Ernesto").Pxcobrar);
        
        //System.out.println(programadores.get("Pilar").getPxh());//prueba de que funciona el registrarProgramador
//        double sueldoa1 = calcularSueldoAnalista(analistas, "Luciano");
//        System.out.println(sueldoa1);
//        double sueldoP = calcularSueldoProgramador(programadores, "Pilar");
//        System.out.println(sueldoP);
    }

}
