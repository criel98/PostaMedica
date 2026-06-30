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
    private String rutaHistoriasCSV = "historias.csv";

    // Constructor: Se asegura de que el archivo exista apenas iniciemos el sistema
    public DatosPacienteCSV() {
        verificarArchivo();
    }

    private void verificarArchivo() {
        try {
            // Verificar archivo de pacientes
            File archivoPacientes = new File(rutaCSV);
            if (!archivoPacientes.exists()) {
                archivoPacientes.createNewFile();
            }

            // Verificar archivo de historias clínicas
            File archivoHistorias = new File(rutaHistoriasCSV);
            if (!archivoHistorias.exists()) {
                archivoHistorias.createNewFile();
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
                    + p.getNumeroTelefono() + ","
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

    public Paciente buscarPorId(int idBuscado) {
        // Asumiendo que tu método que lee el CSV y devuelve la lista se llama listarPacientes()
        java.util.ArrayList<Paciente> lista = listarPacientes();

        for (Paciente p : lista) {
            if (p.getIdPaciente() == idBuscado) {
                return p; // Retorna el paciente exacto al encontrar coincidencia de ID
            }
        }
        return null; // Si no lo encuentra
    }

    public boolean actualizarPaciente(Paciente pacienteActualizado) {
        // 1. Cargamos todos los pacientes actuales a una lista en memoria
        java.util.ArrayList<Paciente> listaPacientes = listarPacientes();
        boolean encontrado = false;

        // 2. Buscamos al paciente por su ID y lo reemplazamos con los datos nuevos
        for (int i = 0; i < listaPacientes.size(); i++) {
            if (listaPacientes.get(i).getIdPaciente() == pacienteActualizado.getIdPaciente()) {
                listaPacientes.set(i, pacienteActualizado); // Reemplaza el objeto viejo por el nuevo
                encontrado = true;
                break;
            }
        }

        // 3. Si lo encontramos y modificamos, reescribimos el archivo CSV por completo
        if (encontrado) {
            try {
                // Instanciamos FileWriter con 'false' para SOBREESCRIBIR el archivo desde cero
                java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(rutaCSV, false));

                // Recorremos la lista actualizada y guardamos línea por línea
                for (Paciente p : listaPacientes) {
                    // Asegúrate de que el orden de las variables aquí sea exactamente igual al de tu método Guardar() original
                    pw.println(p.getIdPaciente() + "," + p.getNombre() + "," + p.getApellidoPaterno() + ","
                            + p.getApellidoMaterno() + "," + p.getFechaNacimiento() + "," + p.getGenero() + ","
                            + p.getTipoDocumento() + "," + p.getNumeroDocumento() + "," + p.getNumeroTelefono() + ","
                            + p.getTipoPaciente());
                }
                pw.close();
                return true;
            } catch (Exception e) {
                System.out.println("Error al reescribir el archivo CSV: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    // NUEVOS MÉTODOS PARA EL MANEJO DE LA HISTORIA CLÍNICA
    public void guardarHistoriaClinica(HistoriaClinica hc) {
        try (FileWriter fw = new FileWriter(rutaHistoriasCSV, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(hc.toCSV()); // Reutiliza el toCSV() que creamos en tu clase
        } catch (IOException e) {
            System.out.println("Error al guardar la historia clínica: " + e.getMessage());
        }
    }
    
    public ArrayList<HistoriaClinica> listarHistoriasPorPaciente(int idPacienteBuscado) {
        ArrayList<HistoriaClinica> historial = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaHistoriasCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 9) {
                    int idPaciente = Integer.parseInt(datos[1]);
                    // Filtramos: Solo agregamos si corresponde al paciente consultado
                    if (idPaciente == idPacienteBuscado) {
                        HistoriaClinica hc = new HistoriaClinica(
                            Integer.parseInt(datos[0]), // idRegistro
                            idPaciente,                 // idPaciente
                            datos[2],                   // fecha
                            Double.parseDouble(datos[3]), // peso
                            Double.parseDouble(datos[4]), // talla
                            Double.parseDouble(datos[5]), // temperatura
                            Integer.parseInt(datos[6]),   // pulso
                            Integer.parseInt(datos[7]),   // saturacion
                            datos[8]                    // presionArterial
                        );
                        historial.add(hc);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el historial clínico: " + e.getMessage());
        }
        return historial;
    }
}
