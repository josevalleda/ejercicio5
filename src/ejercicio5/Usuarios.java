

package ejercicio5;

import java.time.LocalDate;
import java.util.ArrayList;


public class Usuarios {
    
    private String login;
    private LocalDate fultimoacceso;
    private String password;
    private ArrayList<String> historial;

    public Usuarios(String login, String password) {
        this.login = login;
        this.fultimoacceso = LocalDate.now();
        this.password = password;
        this.historial = new ArrayList<String>();
        historial.add(password);
    }

    public LocalDate getFultimoacceso() {
        return fultimoacceso;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    
    public boolean Modificarpassword(String pass,String newpass){
        boolean auxboolean =  false;
        // vreifica que cumpla la primeroa condicion
        if(pass.equals(password) && !historial.contains(newpass)){
            password = newpass;
            historial.add(password);
            auxboolean = true;
        }
        return auxboolean;
    }
    public boolean validar(String pass){
        //se valida que coicida la contraseña
        boolean auxboolean =  false;
        if(pass.equals(password)){
            auxboolean = true;
        }
        return auxboolean;
    }
    public void EstablecerUltimoAcceso(){
        //se asigna el ultimo acceso
        this.fultimoacceso = LocalDate.now();
    }
}
