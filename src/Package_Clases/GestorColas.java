package Package_Clases;

import java.util.LinkedList;
import java.util.Queue;

public class GestorColas {

    // Patrón Singleton: Una única sala de espera para todo el sistema
    private static GestorColas instancia;
    
    // Las colas de espera
    private Queue<Paciente> colaTriaje;
    private Queue<Paciente> colaConsultorio;

    private GestorColas() {
        colaTriaje = new LinkedList<>();
        colaConsultorio = new LinkedList<>();
    }

    public static GestorColas getInstancia() {
        if (instancia == null) {
            instancia = new GestorColas();
        }
        return instancia;
    }
       public java.util.Queue<Paciente> getColaTriaje() {
        return colaTriaje;
    }

    // --- MÉTODOS PARA TRIAJE ---
    public void encolarTriaje(Paciente p) {
        colaTriaje.add(p);
          System.out.println("DEBUG: Paciente " + p.getNombre() + " encolado en Triaje. Tamano actual: " + colaTriaje.size());
    }

    public Paciente llamarSiguienteTriaje() {
        return colaTriaje.poll(); // Saca al paciente de la cola y lo devuelve
    }

    // --- MÉTODOS PARA CONSULTORIO ---
    public void encolarConsultorio(Paciente p) {
        colaConsultorio.add(p);
        System.out.println("DEBUG: Paciente " + p.getNombre() + " encolado en Consultorio. Tamaño actual: " + colaConsultorio.size());
    }

    public Paciente llamarSiguienteConsultorio() {
        return colaConsultorio.poll();
    }
 
}