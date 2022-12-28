package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private String serverAddress;
    private int port;
    private Socket socket;
    BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) {
        Cliente cliente = new Cliente("localhost", 9991);
        // 127.0.0.1 == localhost
    }

    public Cliente(String serverAddress, int port){
        this.serverAddress = serverAddress;
        this.port = port;
        Lector l = new Lector();
        Escritor e = new Escritor();

        try {

            conectar();
            l.start();
            e.start();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void conectar() throws IOException{
        socket = new Socket(serverAddress,port);

        // Inicializa las tuberías
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);
    }

    public static void enviarMensaje(String message){
        out.println(message);
    }

    public String recibirMensaje() throws IOException{
        return in.readLine();
    }

    public class Lector extends Thread {
        public Lector(){
        }
        public void run(){
            while (true){
                try {
                    String frase = recibirMensaje();
                    System.out.println(frase);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Escritor extends Thread {
        public Escritor(){
        }
        public void run(){
            while(true){
                Scanner scanner = new Scanner(System.in);
                String mensaje = scanner.nextLine();
                enviarMensaje(mensaje);
            }
        }
    }
}
