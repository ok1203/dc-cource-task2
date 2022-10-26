package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor implements Runnable {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    static void prime_N(int N) throws IOException
    {
        // Declaring the variables
        int x, y, flg;

        // Printing display message
        System.out.println(
                "All the Prime numbers within 1 and " + N
                        + " are:");

        // Using for loop for traversing all
        // the numbers from 1 to N
        for (x = 1; x <= N; x++) {

            // Omit 0 and 1 as they are
            // neither prime nor composite
            if (x == 1 || x == 0)
                continue;

            // Using flag variable to check
            // if x is prime or not
            flg = 1;

            for (y = 2; y <= x / 2; ++y) {
                if (x % y == 0) {
                    flg = 0;
                    break;
                }
            }

            // If flag is 1 then x is prime but
            // if flag is 0 then x is not prime
            if (flg == 1)
                System.out.print(x + " ");
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void process() throws IOException {
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.println(request.getRequestLine());
        System.out.flush();

        PrintWriter output = new PrintWriter(socket.getOutputStream());

        String[] requestSplit = request.getRequestLine().split(" ");

        if (requestSplit[1].equals("/create/itemid")){
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>hello</title></head>");
            output.println("<body><p>create item requested</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        } else if(requestSplit[1].equals("/delete/itemid")){
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>hello</title></head>");
            output.println("<body><p>delete item requested</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        } else if(requestSplit[1].equals("/exec/params")){
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>hello</title></head>");
            output.println("<body><p>exec param requested</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        } else if(isNumeric(requestSplit[1].substring(1))){
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>hello</title></head>");
            output.print("<body><p>");
            int x, y, flg;

            // Printing display message
            output.print(
                    "All the Prime numbers within 1 and " + Integer.parseInt(requestSplit[1].substring(1))
                            + " are:");

            // Using for loop for traversing all
            // the numbers from 1 to N
            for (x = 1; x <= Integer.parseInt(requestSplit[1].substring(1)); x++) {

                // Omit 0 and 1 as they are
                // neither prime nor composite
                if (x == 1 || x == 0)
                    continue;

                // Using flag variable to check
                // if x is prime or not
                flg = 1;

                for (y = 2; y <= x / 2; ++y) {
                    if (x % y == 0) {
                        flg = 0;
                        break;
                    }
                }

                // If flag is 1 then x is prime but
                // if flag is 0 then x is not prime
                if (flg == 1)
                    output.print(x + " ");
            }
            //output.println(Integer.parseInt(requestSplit[1].substring(1)) + "</p></body>");
            output.println("</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        } else {
            // We are returning a simple web page now.
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Hello</title></head>");
            output.println("<body><p>Hello, world!</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
    }

    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
