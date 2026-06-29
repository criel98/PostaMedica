/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package_Clases;

public class Enfermera extends Persona {

    private int idEnfermera;
    private String turno;

    public Enfermera(int idEnfermera, String nombre, String apellidoPaterno, String apellidoMaterno, String genero, String fechaNacimiento, String tipoDocumento, String numeroDocumento, int numeroTelefono) {
        super(nombre, apellidoPaterno, apellidoMaterno, genero, fechaNacimiento, tipoDocumento, numeroDocumento, numeroTelefono);
        this.idEnfermera = idEnfermera;
    }

}
