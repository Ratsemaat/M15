import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Numbrisekvents juhuslikJärjestus = new Numbrisekvents();
        Laud mängulaud = new Laud(juhuslikJärjestus);

        Scanner sc = new Scanner(System.in);
        mängulaud.kuvaLaud();
        System.out.println("Sisesta liikumissuund: (w/a/s/d)");
        while(true){
            String liikumissuund = sc.nextLine();
            mängulaud.liigutaKivi(liikumissuund);
            }
    }
}
