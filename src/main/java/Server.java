import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private static ServerSocket server;
    private static Socket pipe;
    private static BufferedReader in;
    private static ArrayList<Person>people;
    public static void main(String[] args) {
        people=new ArrayList<Person>();
        try{
            try{
                server=new ServerSocket(4004);
                System.out.println("Сервер запущен!");
                pipe=server.accept();// accept() будет ждать пока
                //кто-нибудь не захочет подключиться
                try{
                    in=new BufferedReader (new InputStreamReader(pipe.getInputStream()));
                    String json=in.readLine();
                    System.out.println(json);
                    ObjectMapper mapper=new ObjectMapper();
                    StringReader reader=new StringReader(json);
                    Person person=(Person)mapper.readValue(reader,Person.class);
                    System.out.println(person.age);
                    people.add(person);
                }finally{
                    in.close();
                }
            }finally {
                server.close();
                System.out.println("Сервер закрыт!");

            }
        }catch (IOException e)
        {
            System.err.println(e);
        }
    }
}

