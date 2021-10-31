import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientH implements Runnable {
    Server server;
    Strings strings=new Strings();
    private String name;
    private DataInputStream in;
    private PrintWriter out;
    private Socket clientS;
    String outS;
    String randomWord;

    public ClientH(Server server, Socket clientS, LinkedList<ClientH> clientsList, String name) throws IOException {

        this.name = name;
        this.clientS = clientS;
        this.server=server;

    }

    public PrintWriter getOut() {
        return out;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setOutS(String outS) {
        this.outS = outS;
    }

    public String getOutS() {
        return outS;
    }

    public String getName() {
        return name;
    }

    @Override
    public synchronized void run() {
        try {


            BufferedReader in = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
            out = new PrintWriter(clientS.getOutputStream(), true);


            while (true) {


                    randomWord = strings.words[(int) (Math.random() * strings.words.length)];
                    String hint= Hint();
                    char[] letters = new char[randomWord.length()];

                    Arrays.fill(letters, '_');


                        if(name == null) {
                            out.println("                                HANGMAN");Thread.sleep(1000);
                            out.println(("                         The game Rules are,"));Thread.sleep(100);
                            out.println(("                      Write a letter or the word"));Thread.sleep(120);
                            out.println(("                  If the letter is wrong or the word"));Thread.sleep(130);
                            out.println((" The hangman starts taking form, and you only have 6 wrong attempts"));Thread.sleep(220);
                            out.println(("                 If the Hangman fully forms, its Game OVER"));Thread.sleep(130);
                            out.println(("                   If you guess the word right you WIN"));Thread.sleep(150);

                            out.println("If you write exit you will exit the game");Thread.sleep(100);
                            out.println("Lets Play!" );Thread.sleep(100);


                            out.println("Please write your name now :");

                            name = in.readLine();
                        }

                        if(!(name ==null)){
                            Thread.sleep(200);
                          out.println("Type 'play' to start the game");
                        }
                        outS=in.readLine();
                if (outS.equals("exit")) {

                    out.println(this.name + " has Exited the Game ");
                    server.clientsList.remove(this);
                    clientS.close();
                }
                if (outS.equals("play")) {
                    Thread.sleep(200); out.println("                   Let the Game BEGIN");
                    out.println();
                    Thread.sleep(200);out.println(hint);
                    out.println("Enter your first letter or guess");
                    int lives = 6;

                    while (lives >= 0) {

                        outS = in.readLine();

                        if (outS.equals("exit")) {

                            out.println(this.name + " has Exited the Game ");
                            server.clientsList.remove(this);
                            clientS.close();}



                        if (outS.equals(randomWord)) {
                            Thread.sleep(300);
                            out.println(name.toUpperCase()+" you won the Game!!!  Write 'play' to go to next guess or 'exit' to quit. ");
                            if(outS.equals("play"))  {
                              this.run();
                            }
                            Thread.sleep(300);
                            out.println("The word is indeed " + randomWord);
                            break;
                        }

                        if(lives<=6) {
                            out.println(" _______");
                            out.println(" |     |");

                            if (lives <= 5) {
                                out.println(" O");

                                if (lives <= 4) {
                                    out.print("\\ ");

                                    if (lives <= 3) {
                                        out.println("/");

                                        if (lives <= 2) {
                                            out.println(" |");

                                            if (lives <= 1) {
                                                out.print("/ ");

                                                if (lives == 0) {
                                                    out.println("\\");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }




                        Thread.sleep(100); out.println();
                        Thread.sleep(100);out.println("Input: ");
                        if(outS.equals(" ")) {
                             out.println(" space key ");
                         }
                        else{out.println(outS);}
                        out.println();

                        if(!outS.equals(randomWord)&& outS.length()>=2){
                            out.println("Your guess was wrong!");

                        }

                        char letter;
                            letter = outS.charAt(0);

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
                        Thread.sleep(200);

                        out.println("Word : ");

                        for (char b : letters) {
                            if (b == '_') iscorrect = false;
                            out.print(" "+b+" ");

                        }

                        out.println();
                        out.println("---------------------------");

                        if (iscorrect) {
                            Thread.sleep(200);
                            out.println(name.toUpperCase()+" You won");
                            this.run();

                        }

                        if (lives < 0) {
                            out.println();
                            out.println();
                            out.println();
                            out.println(" _______");
                            out.println(" |     |");
                            out.println(" O");
                            out.print("\\ ");
                            out.println("/");
                            out.println(" |");
                            out.print("/ ");
                            out.println("\\");

                            Thread.sleep(200);
                            out.println(name.toUpperCase()+ " you lost!! The word was " + randomWord);

                            break;
                        }
                    }
                } Thread.sleep(200);
                out.println("Game Over");
                out.println();

           }
            } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public String Hint(){
        String hint;
        if(randomWord.equals(strings.words[0])){
            return hint=strings.hints[2];

        }if(randomWord.equals(strings.words[1])){
            return hint=strings.hints[10];

        }if(randomWord.equals(strings.words[2])){
            return hint=strings.hints[10];

        }if(randomWord.equals(strings.words[3])){
            return hint=strings.hints[10];

        }if(randomWord.equals(strings.words[4])){
            return hint=strings.hints[15];

        }if(randomWord.equals(strings.words[5])){
            return hint=strings.hints[15];

        }if(randomWord.equals(strings.words[6])){
            return hint=strings.hints[10];

        }if(randomWord.equals(strings.words[7])){
            return hint=strings.hints[12];

        }if(randomWord.equals(strings.words[8])){
            return hint=strings.hints[12];

        }if(randomWord.equals(strings.words[9])){
            return hint=strings.hints[16];

        }if(randomWord.equals(strings.words[10])){
            return hint=strings.hints[17];

        }if(randomWord.equals(strings.words[11])){
            return hint=strings.hints[13];

        }
        if(randomWord.equals(strings.words[12])){
            return hint=strings.hints[7];

        }if(randomWord.equals(strings.words[13])){
            return hint=strings.hints[11];

        }if(randomWord.equals(strings.words[14])){
            return hint=strings.hints[18];

        }if(randomWord.equals(strings.words[15])){
            return hint=strings.hints[11];

        }if(randomWord.equals(strings.words[16])){
            return hint=strings.hints[11];

        }if(randomWord.equals(strings.words[17])){
            return hint=strings.hints[22];

        }if(randomWord.equals(strings.words[18])){
            return hint=strings.hints[19];

        }if(randomWord.equals(strings.words[19])){
            return hint=strings.hints[21];

        }if(randomWord.equals(strings.words[20])){
            return hint=strings.hints[24];

        }if(randomWord.equals(strings.words[21])){
            return hint=strings.hints[32];

        }if(randomWord.equals(strings.words[22])){
            return hint=strings.hints[25];

        }if(randomWord.equals(strings.words[23])){
            return hint=strings.hints[33];

        }if(randomWord.equals(strings.words[24])){
            return hint=strings.hints[35];

        }if(randomWord.equals(strings.words[25])){
            return hint=strings.hints[20];

        }if(randomWord.equals(strings.words[26])){
            return hint=strings.hints[26];

        }if(randomWord.equals(strings.words[27])){
            return hint=strings.hints[23];
        }
        if(randomWord.equals(strings.words[28])) {
            return hint = strings.hints[28];
        }
        if(randomWord.equals(strings.words[29])) {
            return hint = strings.hints[36];
        }
        if(randomWord.equals(strings.words[30])) {
            return hint = strings.hints[31];
        }
        if(randomWord.equals(strings.words[31])) {
            return hint = strings.hints[37];
        }
        if(randomWord.equals(strings.words[32])) {
            return hint = strings.hints[38];
        }
        if(randomWord.equals(strings.words[33])) {
            return hint = strings.hints[39];
        }
        if(randomWord.equals(strings.words[34])) {
            return hint = strings.hints[29];
        }
        if(randomWord.equals(strings.words[35])) {
            return hint = strings.hints[30];
        }


        return hint=strings.hints[28];
    }}






