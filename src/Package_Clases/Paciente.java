
package Package_Clases;

public class Paciente extends Persona{
private int idPaciente;
private String tipoPaciente;


    public Paciente(int idPaciente, String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento, String genero, String tipoDocumento, String numeroDocumento, int numeroTelefono, String tipoPaciente) {
        super(nombre, apellidoPaterno, apellidoMaterno, genero, fechaNacimiento, tipoDocumento, numeroDocumento, numeroTelefono);
        this.idPaciente = idPaciente;
        this.tipoPaciente = tipoPaciente;
    }

  
      
    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
       public String getTipoPaciente() {
        return tipoPaciente;
    }

    public void setTipoPaciente(String tipoPaciente) {
        this.tipoPaciente = tipoPaciente;
    }

    
}
