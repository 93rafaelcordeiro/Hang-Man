import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Server {

    LinkedList<ClientH> clientsList = new LinkedList<>();

    int port =3030;

    ServerSocket ss = new ServerSocket(port);
    Socket clientS;
    String name=null;

    public LinkedList<ClientH> getClientsList() {
        return clientsList;
    }

    int clientcounter=0;
    public Server() throws IOException {




        while (true)
    {
        try {

            clientS = ss.accept();
            clientcounter++;
            System.out.println("New client  received : " + clientS);

            ClientH client = new ClientH(this,clientS, clientsList, null);

            System.out.println("Creating runnable for this client...");

            Thread t = new Thread(client);

            System.out.println("Adding this client to active client list");

            clientsList.offer(client);

            t.start();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }



} public void game() throws IOException, InterruptedException {


    }
    }







