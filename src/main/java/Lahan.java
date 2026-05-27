import java.util.LinkedList;
import java.util.Queue;

public class Lahan {
    int kode;
    double luas;
    double panjang;
    double lebar;
    Queue<Aktivitas> aktivitas = new LinkedList<Aktivitas>();

    public Lahan(double luas) {
        this.luas = luas;
    }
    
    public Lahan(int kode, double panjang, double lebar) {
        this.kode = kode;
        this.panjang = panjang;
        this.lebar = lebar;
        this.luas = panjang * lebar;
    }

    public void hapus() {

    }

    public void ubah() {
        
    }
}
