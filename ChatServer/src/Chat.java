import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Stream;


public class Chat {

    static LinkedList<ClientH> clientsList = new LinkedList<>();


    public static void main(String[] args) throws IOException {

        int port =1010;

        ServerSocket ss = new ServerSocket(port);
        Socket clientS;
        String name=null;
        int clientcounter=0;

        while (true)
        {
            try {

                clientS = ss.accept();
                clientcounter++;
                System.out.println("New client  received : " + clientS);

                DataInputStream in = new DataInputStream(clientS.getInputStream());

                PrintWriter out = new PrintWriter(clientS.getOutputStream(),true);

                ClientH client = new ClientH(clientS,clientsList, null);

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
        String outS;

        public ClientH(Socket clientS, LinkedList<ClientH> clientsList, String name) {

            this.name = name;
            this.clientS = clientS;

        }

        @Override
        public synchronized void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
                out = new PrintWriter(clientS.getOutputStream(), true);

                String[] words = new String[]{"computer", "language", "display",};



                String randomWord = words[(int) (Math.random() * words.length)];
                char[] letters = new char[randomWord.length()];

                Arrays.fill(letters, '.');

                if (name == null) {
                    out.println("Please add your name now :");
                    this.name = in.readLine();

                }
                int lives = 8;

                while (lives > 0) {
                    outS = in.readLine();
                    out.println("lives : " + lives);

                    out.println();
                    out.println("Input: ");

                    char letter = outS.charAt(0);

                    out.println();

                    boolean iscorrect = false;
                    for (int i = 0; i < randomWord.length(); i++) {
                        char l = randomWord.charAt(i);
                        if (l == letter) {
                            letters[i] = l;
                            iscorrect = true;

                        }
                    }
                    if (!iscorrect) {
                        lives--;
                    }

                    out.println("Word : ");

                    for (char b : letters) {
                        if (b == '.') iscorrect = false;
                        out.println(b);
                    }


                    out.println();
                    out.println("---------------------------");

                    if (iscorrect) {
                        out.println("You won");

                    }
                    if (lives == 0) {
                        out.println("you lost the word was " + randomWord);
                        break;
                    }


                }
                out.println("Exiting game");

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }}}


