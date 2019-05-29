
package ejercicio5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


abstract class Verificador {
    protected ArrayList<Usuarios> ListaUsuario;
    protected Map<String, Usuarios > mapa;
    protected Map<String, String> mapadesafio;
    protected String  peticion;

    public Verificador( String peticion) {
        this.ListaUsuario = new ArrayList<Usuarios>();
        this.peticion = peticion;
        mapa = new  HashMap<String ,Usuarios >();
        mapadesafio = new  HashMap<String ,String >();
    }

    public String getPeticion() {
        return peticion;
    }
    
    public void añadirusuario(Usuarios user){
        ListaUsuario.add(user);
        mapa.put(user.getLogin(), user);
    }
    public boolean borrarusuario(Usuarios user){
        boolean auxboolean =  false;
        if(ListaUsuario.remove(user)){
            mapa.remove(user.getLogin());
            auxboolean = true;
        }
        return auxboolean;
    }
    abstract String Verificador(String Token,String login);
    // realizamos las primeras validadciones
    public String paso1(String login, String pass){
        String auxtoken = "";
        Usuarios user = null;
        if(mapa.containsKey(login)){
           user = mapa.get(login);
           if(user.getPassword().equals(pass)){
               if(!Verificador(auxtoken,login).isEmpty()){
                 auxtoken =  UUID.randomUUID().toString();
                 mapadesafio.put(auxtoken,""+Verificador(auxtoken,login)); 
               }
           }
        }
        return auxtoken;
    }
    abstract boolean paso2(String token,String Resp);
    @Override
    public Verificador clone() throws CloneNotSupportedException 
    { 
        Verificador aux = (Verificador)super.clone();
        aux.mapadesafio = new  HashMap<String ,String >();
        return aux; 
    } 
    
}
class verificadorblacklist extends Verificador{
    
    private ArrayList<Usuarios> Listabloqueado;
    
    public verificadorblacklist(String peticion) {
        super(peticion);
        Listabloqueado = new ArrayList<Usuarios>();
    }

    public void añadirbloqueado (Usuarios user){
        Listabloqueado.add(user);
    }
    public void removerbloqueado (Usuarios user){
        Listabloqueado.remove(user);
    }
    
    boolean paso2(String token,String Resp) {
        boolean auxbool = false;
        if(mapadesafio.containsKey(token)){
            if(mapadesafio.get(token).equals(Resp)){
                auxbool = true;
                mapadesafio.remove(token);
            }
        }
        return auxbool;
    }
    
    String Verificador(String token,String login) {
        for (Usuarios usuarios : Listabloqueado) {
            if(usuarios.getLogin().equals(login)){
                return "";
            }
        }
        return ""+mapa.get(login).getFultimoacceso().getDayOfMonth();
    }

    @Override
    public verificadorblacklist clone() throws CloneNotSupportedException {
        verificadorblacklist aux = (verificadorblacklist)super.clone();
        aux.Listabloqueado = new ArrayList<Usuarios>();
        aux.peticion = "";
        return aux; 
    }
    public String toString(){
        return "peticion:"+this.peticion+"\n Bloqueados:"+ this.Listabloqueado.toString()+"\n usuarios:"+ this.ListaUsuario.toString(); 
    }
}

class verificadorcodigo extends Verificador{
    
    private int numeroint;
    protected Map<String, String> intentos;
    
    public verificadorcodigo(String peticion, int aux) {
        super(peticion);
        numeroint = aux;
        intentos = new  HashMap<String ,String >();
    }

    
    boolean paso2(String token,String Resp) {
        boolean auxbool = false;
        int auxint = 0;
        if(mapadesafio.containsKey(token)){
            if(mapadesafio.get(token).equals(Resp)){
                auxbool = true;
                mapadesafio.remove(token);
            }
        }
        // marcamos y verificamos cuantos intentos hay
        if(intentos.containsKey(token)){
            if(numeroint <= Integer.parseInt(intentos.get(token))){
               mapadesafio.remove(token); 
            }else{
                  auxint = Integer.parseInt(intentos.get(token));
                  intentos.put(token,""+(auxint+1));
            }
        }else{
            intentos.put(token,""+1);  //Integer.parseInt();
        }
        return auxbool;
    }
    // verificador segucodigo
    String Verificador(String token,String login) {
        Random ran = new Random();
        int aux = ran.nextInt(999);
        System.err.println("nuemro generado es : "+aux);
        return ""+aux;
    }
    public verificadorcodigo clone() throws CloneNotSupportedException {
        verificadorcodigo aux = (verificadorcodigo)super.clone();
        aux.peticion = "";
        aux.intentos = new  HashMap<String ,String >();
        aux.numeroint = 0;
        return aux; 
    }
    public String toString(){
        return "peticion:"+this.peticion+"\n numero de intentos:"+ this.numeroint+"\n usuarios:"+ this.ListaUsuario.toString(); 
    }
}