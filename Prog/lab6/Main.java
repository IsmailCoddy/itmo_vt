


public class Main {
    private Main() {
    }


    public static void main(String[] args) {
        System.out.println("Лабораторная 6 разделена на клиент и сервер.");
        System.out.println("Сборка: mvn package");
        System.out.println("Сервер: mvn exec:java -Dexec.mainClass=server.ServerMain -Dexec.args=\"data.json 5555\"");
        System.out.println("Клиент: mvn exec:java -Dexec.mainClass=client.ClientMain -Dexec.args=\"localhost 5555\"");
        System.out.println("После сборки также можно запускать через jar из target:");
        System.out.println("java -cp target/lab6-client-server-1.0.0.jar server.ServerMain data.json 5555");
        System.out.println("java -cp target/lab6-client-server-1.0.0.jar client.ClientMain localhost 5555");
    }
}
