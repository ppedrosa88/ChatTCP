package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManejadorCliente extends Thread{

    Socket socket;
    String nombre;
    private BufferedReader in;
    private PrintWriter out;

    public ManejadorCliente(Socket socket, String nombre){
        this.socket = socket;
        this.nombre = nombre;
    }

    public void run(){
        try {

            // Inicializa las tuberías
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            while(true){

                String frase = in.readLine();
                //System.out.println(nombre+ " Mensaje "+frase);
                Servidor.mandarMensajes(nombre,frase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mandarMensaje(String nombre, String mensaje){
        out.println(nombre + " ha dicho: " +mensaje);
    }
}
