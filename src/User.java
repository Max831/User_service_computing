
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.ResourceBundle;

public class User {
    @FXML
    private TextArea text1;

    @FXML
    private TextArea text2;

    @FXML
    private TextArea operation;

    @FXML
    private TextArea answer;

    @FXML
    private Button send;


    public void initialize() {
        Socket socket1=socket();

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                answer.clear();
                try {
                    Integer.parseInt(text1.getText());
                    Integer.parseInt(text2.getText());
                        if (operation.getText().length()>1 |(
                                operation.getText().charAt(0) != '+' &
                                    operation.getText().charAt(0) != '-' &
                                        operation.getText().charAt(0) != '*' &
                                            operation.getText().charAt(0) != '/')) {
                            throw new ArithmeticException();
                    }
                    enter(socket1);
                }
                catch (Exception e)
                {
                    System.out.println(e);
                    System.out.println("Операнды не являются числами либо неверный знак препинания");
                }
            }
        });
    }
    public Socket socket() {
        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.
        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just got hold of the program.");
            return socket;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;


    }
           public void enter(Socket socket){
            try{
                // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();
                // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                DataInputStream in = new DataInputStream(sin);
                DataOutputStream out = new DataOutputStream(sout);
                String line = text1.getText(),line1=operation.getText(),line2=text2.getText(),line3;
                System.out.println("Sending this line to the server...");

                out.writeUTF(line);// отсылаем введенную строку текста серверу
                out.flush(); // заставляем поток закончить передачу данных.
                line3 = in.readUTF(); // ждем пока сервер отошлет строку текста
                System.out.println("The server was very polite. It sent me this : " + line3);

                out.writeUTF(line1);
                out.flush(); // заставляем поток закончить передачу данных.
                line3 = in.readUTF(); // ждем пока сервер отошлет строку текста
                System.out.println("The server was very polite. It sent me this : " + line3);

                out.writeUTF(line2);
                out.flush(); // заставляем поток закончить передачу данных.
                line3 = in.readUTF(); // ждем пока сервер отошлет строку текста
                System.out.println("The server was very polite. It sent me this : " + line3);

                int c=in.readInt();
                String s=String.valueOf(c);
                System.out.println("The server was very polite. It sent me this : " + c);
                answer.appendText(s);

        } catch (Exception x) {
                System.out.println(x);
        }
    }
}

