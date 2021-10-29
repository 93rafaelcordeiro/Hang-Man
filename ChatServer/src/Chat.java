import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Stream;


public class Chat {

    static LinkedList<ClientH> clientsList = new LinkedList<>();

    public static void main(String[] args) throws IOException {

        int port =8080;

        ServerSocket ss = new ServerSocket(port);
        Socket clientS;
        int name=0;

        while (true)
        {
           try {

            clientS = ss.accept();
            name++;
            System.out.println("New client  received : " + clientS);

            DataInputStream in = new DataInputStream(clientS.getInputStream());

            PrintWriter out = new PrintWriter(clientS.getOutputStream(),true);

            ClientH client = new ClientH(clientS,clientsList,String.valueOf(name));

            System.out.println("Creating runnable for this client...");

            Thread t = new Thread(client);

            System.out.println("Adding this client to active client list");

            clientsList.offer(client);

            t.start();



        } catch (IOException e) {
               e.printStackTrace();
           }
        }
}

static class ClientH implements Runnable {
    private Scanner scanner = new Scanner(System.in);
    private String name;
    private DataInputStream in;
    private PrintWriter out;
    private Socket clientS;


    public ClientH(Socket clientS, LinkedList<ClientH> clientsList, String name) {

        this.name = name;
        this.clientS = clientS;

    }

    @Override
    public synchronized void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
            out = new PrintWriter(clientS.getOutputStream(), true);

            while (true) {
                String outS = in.readLine();

                if (outS.equals("users")) {
                    for (ClientH c : clientsList) {
                        out.println(c.name);
                    }

                    if (outS.equals("exit")) {
                        for (ClientH c : clientsList) {
                            c.out.println(this.name + " has exited Chat");
                        }
                        clientsList.remove(this);
                        clientS.close();

                    }
                }

                messageAllClients(outS);
                System.out.println("server received: " + outS);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void messageAllClients(String outS) throws IOException {
        String[] st;
        String MsgToSend = null;
        String recipient = null;
        if (outS.contains("#")) {
            st = outS.split("#");
            MsgToSend = st[1];
            recipient = st[0];
        }


        for (ClientH c : clientsList) {

            if (c.name.equals(recipient)) {
                c.out.println(this.name + ": DM : " + MsgToSend);
                break;
            }

            if (recipient.equals("all")) {
                c.out.println(this.name + ": Broadcast: " + MsgToSend);
            }
        }
        if (recipient.equals(this.name)) {
            out.println("This client its you");
        }
        if (recipient.equals("") || !(recipient.equals(clientsList.element().name))) {
            out.println("Client does not exist");
        }



    }


} 
        }





