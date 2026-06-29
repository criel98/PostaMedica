package Package_Clases;

public class Usuario {

    private int idUsuario;
    private String usuario;
    private String pass;
    private boolean estado;
    private String Pesona;
    private Persona personavincuPersona;
    private int intentosFallidos = 0;

    public Usuario(int idUsuario, String usuario, String pass, boolean estado, Persona personavincuPersona) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.pass = pass;
        this.estado = estado;
        this.personavincuPersona = personavincuPersona;
    }
  
    public boolean validarLogin(String inputUsuario, String inputPass) {
        // 1. Si la cuenta ya está inactiva/bloqueada, rechazamos de inmediato
        if (!this.estado) {
            return false;
        }

        // 2. Comparamos los datos del objeto (this) con los datos ingresados (input)
        if (this.usuario.equals(inputUsuario) && this.pass.equals(inputPass)) {
            this.intentosFallidos = 0; // Reiniciamos el contador tras un éxito
            return true;
        }

        // 3. Si los datos fallan, aumentamos el contador
        this.intentosFallidos++;
        System.out.println(this.intentosFallidos);
        if (this.intentosFallidos >= 3) {
            this.estado = false; 
        }

        return false;
    }
 
    public int getIntentosRestantes() {
        return Math.max(0, 3 - this.intentosFallidos);
    }

    public boolean isBloqueado() {
        return !this.estado;
    }

}
