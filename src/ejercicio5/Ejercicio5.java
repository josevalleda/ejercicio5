package ejercicio5;

import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String aux, aux2,token = "";

        Usuarios u1 = new Usuarios("fperez", "lechemerengada");
        u1.Modificarpassword("lechemerengada", "cr7comeback");
        Usuarios u2 = new Usuarios("mlama", "tururu");
        verificadorblacklist v1 = new verificadorblacklist("Introduzca el número del día de su último acceso:");
        v1.añadirusuario(u1);
        v1.añadirusuario(u2);
        v1.añadirbloqueado(u2);
        verificadorcodigo v2 = new verificadorcodigo("Introduzca el número que ha recibido por SMS", 2);
        v2.añadirusuario(u1);
        v2.añadirusuario(u2);
        ArrayList<Verificador> l1 = new ArrayList<Verificador>();
        ArrayList<Verificador> l2 = new ArrayList<Verificador>();
        l1.add(v1);
        l1.add(v2);

        for (Verificador verificador : l1) {
            System.out.println("Escriba su Login : ");
            aux = scanner.nextLine();
            System.out.println("Escriba su contraseña : ");
            aux2 = scanner.nextLine();
            token = verificador.paso1(aux, aux2);
            if(!token.isEmpty()){
                System.out.println("\n pregunta: "+ verificador.getPeticion());
                aux = scanner.nextLine();
                if(verificador.paso2(token, aux)){
                    System.out.println("\n Verificacion exitosa!!!");
                }else{
                    System.out.println("\n Verificacion Erronea!!!");
                }
            }else{
                System.out.println("Credenciales erroneas");
            }
        }
        for (Verificador verificador : l1) {
            l2.add(verificador);
        }
        for (Verificador verificador : l2) {
            System.out.println("\n \n copia \n "+verificador.toString());
        }
        scanner.close();
    }

}
