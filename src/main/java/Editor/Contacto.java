package Editor;

public class Contacto {
    public static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$";

    private String nombre;
    private String email;
    private String numeroDeTelefono;

    public Contacto(String nombre, String email, String numeroDeTelefono) {
        this.nombre = nombre;
        this.email = email;
        this.numeroDeTelefono = numeroDeTelefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    public void setNumeroDeTelefono(String numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}