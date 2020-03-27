import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Edetabel {

    public void edetabel(long mängija_aeg){
        //Faili kirjutamine.
        try {
            File edetabel = new File("edetabel.txt");
            if (edetabel.createNewFile() || edetabel.length()==0) {
                try {
                    FileWriter kirjutaja = new FileWriter("edetabel.txt");
                    PrintWriter printWriter = new PrintWriter(kirjutaja);
                    printWriter.print("---E-D-E-T-A-B-E-L---\nmn:sk:mil\tMängija\n");
                    printWriter.printf("%tM:%tS:%tL\t",mängija_aeg,mängija_aeg,mängija_aeg);
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Sisestage oma nimi: ");
                    String mängijaNimi = sc.nextLine();
                    printWriter.printf("%s \n", mängijaNimi);
                    kirjutaja.write("--L-Õ-P-P--");
                    kirjutaja.close();
                    printWriter.close();
                } catch (IOException e) {
                    System.out.println("Midagi läks valesti.");
                    e.printStackTrace();
                }
            } else { //Eksisteeriva faili täiendamine.
                System.out.println("Fail on olemas.");
                try (Scanner sc = new Scanner(edetabel, "UTF-8")) {
                    Path path = Paths.get("edetabel.txt");
                    int ridadearv = (int) Files.lines(path).count();
                    int reaLuger = 1;
                    while (sc.hasNextLine()) {
                        String rida = sc.nextLine();
                        if (ridadearv>reaLuger&&reaLuger>2) {
                            String[] tükid = rida.split("\t")[0].split(":");
                            long aeg= Long.parseLong(tükid[0])*60000+Long.parseLong(tükid[1])*1000+Long.parseLong(tükid[2]);
                            if (aeg>mängija_aeg) break;
                            System.out.println(aeg+" Ja tegija" +mängija_aeg);
                        }
                        reaLuger++;
                        if (reaLuger>ridadearv)reaLuger--;
                    }
                    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                    String lisatavTekst = String.format("%tM:%tS:%tL\t",mängija_aeg,mängija_aeg,mängija_aeg);
                    System.out.println("Sisestage oma nimi: ");
                    Scanner scanner = new Scanner(System.in);
                    String mängijaNimi = scanner.nextLine();
                    System.out.println(reaLuger);
                    lines.add((reaLuger-1), lisatavTekst+mängijaNimi);
                    Files.write(path, lines, StandardCharsets.UTF_8);

                }
            }
        } catch (IOException e) {
            System.out.println("Midagi läks valesti.");
            e.printStackTrace();
        }

        //Väljasta konsooli
        try {
            File myObj = new File("edetabel.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
