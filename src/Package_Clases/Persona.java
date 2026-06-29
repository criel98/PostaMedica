package Package_Clases;

public class Persona {

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String genero;
    private String fechaNacimiento;
    private String tipoDocumento;
    private String numeroDocumento;
    private int numeroTelefono;

    public int calcularEdad() {
        if (this.fechaNacimiento == null || this.fechaNacimiento.trim().length() < 10) {
            return 0;
        }

        try {
            // Separamos el string "DD/MM/AAAA" usando las barras diagonales
            String[] partes = this.fechaNacimiento.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);

            // Convertimos las partes a un objeto LocalDate
            java.time.LocalDate fechaNac = java.time.LocalDate.of(anio, mes, dia);
            java.time.LocalDate fechaActual = java.time.LocalDate.now();

            // Calculamos el periodo transcurrido entre ambas fechas
            return java.time.Period.between(fechaNac, fechaActual).getYears();

        } catch (Exception e) {
            // Si la fecha tiene un formato inválido o está incompleta, retorna 0 por seguridad
            return 0;
        }
    }

    public Persona(String nombre, String apellidoPaterno, String apellidoMaterno, String genero, String fechaNacimiento, String tipoDocumento, String numeroDocumento, int numeroTelefono) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroTelefono = numeroTelefono;
    }
    public Persona() {
    // Este constructor no hace nada, pero permite hacer: new Persona()
}

  

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDcocumento) {
        this.tipoDocumento = tipoDcocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

}
