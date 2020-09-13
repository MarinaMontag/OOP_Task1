import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket pipe;
    private static BufferedReader reader;// нам нужен ридер читающий с консоли
    private static PrintWriter out;// поток записи в сокет

    public static void main(String[] args) {
        try{
            try{
                pipe=new Socket("localhost",4004);
                reader=new BufferedReader(new InputStreamReader(System.in));
                out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(pipe.getOutputStream())));
                String name,surname;
                int age;
                System.out.print("Введите имя: ");
                name=reader.readLine();
                System.out.print("Введите фамилию: ");
                surname= reader.readLine();
                System.out.print("Введите возраст: ");
                age=Integer.parseInt(reader.readLine());
                Person person=new Person(name,surname,age);
                ObjectMapper mapper=new ObjectMapper();
                StringWriter writer=new StringWriter();
                mapper.writeValue(writer,person);
                String json=writer.toString();
                out.write(json);
            }finally{
                System.out.println("Клиент был закрыт...");
                pipe.close();
                out.close();
                reader.close();
            }
        }catch(IOException e) {
            System.err.println(e);
        }
    }
}
