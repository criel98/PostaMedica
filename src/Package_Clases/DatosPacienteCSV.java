package Package_Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DatosPacienteCSV {

    // Ruta relativa: El archivo se creará en la carpeta principal de tu proyecto en NetBeans
    private String rutaCSV = "pacientes.csv";

    // Constructor: Se asegura de que el archivo exista apenas iniciemos el sistema
    public DatosPacienteCSV() {
        verificarArchivo();
    }

    private void verificarArchivo() {
        try {
            File archivo = new File(rutaCSV);
            if (!archivo.exists()) {
                archivo.createNewFile(); // Crea el archivo si es la primera vez que abres el sistema
            }
        } catch (IOException e) {
            System.out.println("Error al crear la base de datos CSV: " + e.getMessage());
        }
    }

    // Método para BUSCAR un paciente por su DNI (numeroDocumento)
    public Paciente Buscar(String numeroDNI) {
        // BufferedReader es la herramienta de Java para leer archivos de texto muy rápido
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;

            // Leemos línea por línea hasta que se acabe el archivo
            while ((linea = br.readLine()) != null) {
                // Separamos la línea por comas (formato CSV)
                String[] datos = linea.split(",");

                // Si el arreglo tiene datos y el primer dato (posición 0) coincide con el DNI buscado
                if (datos.length >= 10 && datos[7].equals(numeroDNI)) {

                    // Reconstruimos al Paciente usando el constructor que ya tienes
                    // Formato guardado: idPaciente, nombre, paterno, materno, fecha, genero, tipoDoc, numeroDoc, numeroTelefono, tipoPaciente
                     int id = Integer.parseInt(datos[0]);
                    int numero = Integer.parseInt(datos[8]);
                    Paciente p = new Paciente(id, datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], numero, datos[9]);
                    return p;
                
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar en el archivo CSV: " + e.getMessage());
        }

        // Si termina de leer todo el archivo y no lo encuentra, retorna null
        return null;
    }

    // Método para GUARDAR un paciente nuevo
    public void Guardar(Paciente p) {
        try (FileWriter fw = new FileWriter(rutaCSV, true); PrintWriter pw = new PrintWriter(fw)) {

            // Armamos la línea asegurándonos de tener los 8 datos EXACTOS
            String linea = p.getIdPaciente() + ","
                    + p.getNombre() + ","
                    + p.getApellidoPaterno() + ","
                    + p.getApellidoMaterno() + ","
                    + p.getFechaNacimiento() + ","
                    + p.getGenero() + ","
                    + p.getTipoDocumento() + ","
                    + p.getNumeroDocumento() + ","
                    + p.getNumeroTelefono()+ ","
                    + p.getTipoPaciente();

            pw.println(linea);

        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public ArrayList<Paciente> listarPacientes() {
        ArrayList<Paciente> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 10) {
                    // Reconstruimos el objeto Paciente y lo agregamos a la lista
                    int id = Integer.parseInt(datos[0]);
                    int numero = Integer.parseInt(datos[8]);
                    Paciente p = new Paciente(id, datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], numero, datos[9]);
                    lista.add(p);
                }
            }
           
        } catch (IOException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}
