import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class client{

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dot;
    private BufferedReader br;

    String baca = "";
    String kirim = "";

    public client(String ip, int port) throws IOException{

        this.socket = new Socket(ip, port);

        this.din = new DataInputStream(socket.getInputStream());
        this.dot = new DataOutputStream(socket.getOutputStream());
        InputStreamReader r = new InputStreamReader(System.in);
        this.br = new BufferedReader(r);
    
    }

    private void kirimPesan(String pesan) throws IOException{
        this.kirim = pesan;
        if("".equals(pesan)){
            System.out.print("Client : ");
            this.kirim = this.br.readLine();
        } 
        this.dot.writeUTF(this.kirim);
        this.dot.flush();

    }

    private String bacaPesan() throws IOException{

        this.baca = din.readUTF();
        return this.baca;

    }

    private void matiinKoneksi() throws IOException{

        din.close();
        dot.close();
        socket.close();
    
    }

    public static void main(String[]args) throws IOException {

        client s = new client("localhost",1456);

        s.kirimPesan("Halo server");
        s.bacaPesan();
        System.out.println("Server : " + s.baca);

        while(!"selesai".equals(s.baca)){
            s.kirimPesan("");
            s.bacaPesan();
            System.out.println("Server : " + s.baca);
            
        }

        s.matiinKoneksi();

    }


}