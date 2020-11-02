import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dot;
    private BufferedReader br;

    String baca = "";
    String kirim = "";

    public server (int port) throws IOException {
        
        this.serverSocket = new ServerSocket(port);
        this.socket = serverSocket.accept();

        this.din = new DataInputStream(socket.getInputStream());
        this.dot = new DataOutputStream(socket.getOutputStream());
        InputStreamReader r=new InputStreamReader(System.in);
        this.br = new BufferedReader(r);
    
    }

    private void kirimPesan(String pesan) throws IOException{
        this.kirim = pesan;
        if("".equals(pesan)){
            System.out.print("Server : ");
            this.kirim = this.br.readLine();
        } 
        dot.writeUTF(this.kirim);
        dot.flush();

    }

    private String bacaPesan() throws IOException{

        this.baca = din.readUTF();
        return this.baca;

    }

    private void matiinKoneksi() throws IOException{

        din.close();
        dot.close();
        socket.close();
        serverSocket.close();
    
    }

    public static void main(String[]args) throws IOException{

        server s = new server(1456);

        s.bacaPesan();
        System.out.println("Client : " + s.baca );

        if(s.baca.equals("Halo server")){
            
            s.kirimPesan("Hai Client");

        }
        while(!"selesai".equals(s.baca)){
            
            s.bacaPesan();
            System.out.println("Client : " + s.baca);
            s.kirimPesan("");
            
        }

        s.matiinKoneksi();

    }   

}