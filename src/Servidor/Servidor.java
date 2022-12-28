package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    public static ArrayList<ManejadorCliente> clientes;

    public static void main(String[] args) throws IOException {
        System.out.println("Servidor iniciado");

        // Crea una instancia de ServerSocket en el puerto especificado
        ServerSocket serverSocket = new ServerSocket(9991);
        int i = 0;

        clientes = new ArrayList<ManejadorCliente>();

        // Bucle infinito para escuchar y aceptar conexiones entrantes
        while(true){
            // Acepta una nueva conexión
            Socket socketCliente = serverSocket.accept();

            System.out.println("Nuevo Cliente");

            // Crea una nueva instancia de ClientHandler para manejar la conexión con el cliente
            ManejadorCliente hand = new ManejadorCliente(socketCliente,"Cliente " + i);

            clientes.add(hand);

            // Inicia el manejador del cliente en un hilo separado
            hand.start();

            i++;
        }
    }

    public static void mandarMensajes(String nombre, String mensaje){
        for(int i = 0; i < clientes.size(); i++){
            clientes.get(i).mandarMensaje(nombre,mensaje);
        }
    }
}
