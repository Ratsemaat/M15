import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Numbrisekvents juhuslikJärjestus = new Numbrisekvents();
        Laud mängulaud = new Laud(juhuslikJärjestus);

        Scanner sc = new Scanner(System.in);
        mängulaud.kuvaLaud();

        System.out.println("Sisesta liikumissuund: (w/a/s/d)");
        String liikumissuund = sc.nextLine();
        long algus = System.currentTimeMillis();
        mängulaud.liigutaKivi(liikumissuund);

        while(true){
            liikumissuund = sc.nextLine();
            mängulaud.liigutaKivi(liikumissuund);
            if (mängulaud.onLahendatud())break;
        }
        System.out.println("-------------");
        System.out.printf("|%2s %2s %2s %2s| \n","1","2","3","4");
        System.out.printf("|%2s %2s %2s %2s| \n", "5","6","7","8");;
        System.out.printf("|%2s %2s %2s %2s| \n", "9","10","11","12");;
        System.out.printf("|%2s %2s %2s %2s| \n", "13","14","15","16");
        System.out.println("-------------");
        System.out.println("Lahedatud");
        System.out.println("Aega kulus: "+ (System.currentTimeMillis()-algus)/1000.0+" sekundit");
    }
}
