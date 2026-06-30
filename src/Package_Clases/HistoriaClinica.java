package Package_Clases;

/**
 * Clase que representa el historial clínico y signos vitales de un paciente.
 * @author Daniel
 */
public class HistoriaClinica {

    private int idRegistro; 
    private int idPaciente; 
    private String fecha;
    private double peso, talla, temperatura;
    private int pulso, saturacion;
    private String presionArterial;

    // Constructor completo
    public HistoriaClinica(int idRegistro, int idPaciente, String fecha, double peso, double talla, double temperatura, int pulso, int saturacion, String presionArterial) {
        this.idRegistro = idRegistro;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.peso = peso;
        this.talla = talla;
        this.temperatura = temperatura;
        this.pulso = pulso;
        this.saturacion = saturacion;
        this.presionArterial = presionArterial;
    }

    // --- GETTERS ---
    public int getIdRegistro() { return idRegistro; }
    public int getIdPaciente() { return idPaciente; }
    public String getFecha() { return fecha; }
    public double getPeso() { return peso; }
    public double getTalla() { return talla; }
    public double getTemperatura() { return temperatura; }
    public int getPulso() { return pulso; }
    public int getSaturacion() { return saturacion; }
    public String getPresionArterial() { return presionArterial; }

    // --- SETTERS ---
    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setTalla(double talla) { this.talla = talla; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }
    public void setPulso(int pulso) { this.pulso = pulso; }
    public void setSaturacion(int saturacion) { this.saturacion = saturacion; }
    public void setPresionArterial(String presionArterial) { this.presionArterial = presionArterial; }

    // --- MÉTODO PARA PERSISTENCIA (CSV) ---
    /**
     * Convierte el objeto a una línea de texto CSV.
     * Asegúrate de que este orden sea el mismo que usarás en tu GestorHistoriaClinica.
     */
    public String toCSV() {
        return idRegistro + "," + 
               idPaciente + "," + 
               fecha + "," + 
               peso + "," + 
               talla + "," + 
               temperatura + "," + 
               pulso + "," + 
               saturacion + "," + 
               presionArterial;
    }
}
