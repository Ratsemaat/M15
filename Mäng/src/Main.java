import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Laud juhuslik =new Laud();
        juhuslik.kuvaLaud();

    while(true) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sisesta liikumissuund: (w/a/s/d)");
        String liikumissuund = sc.nextLine();
        System.out.println(liikumissuund);
        juhuslik.liigutaKivi(liikumissuund);
    }
    }
}
