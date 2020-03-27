import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

//Klass edetabel on selleks, et iga mängu lõpus saaks väljastada mängijate edetabeli
//Tulemused järjestatakse pusle lahendamiseks kulunud aja põhjal.
public class Edetabel {

    public void edetabel(long mängija_aeg){
        try {
            File edetabel = new File("edetabel.txt");
            if (edetabel.createNewFile() || edetabel.length()==0) {
                //Loob faili ja kirjutab sinna.
                kirjutaFailiNullist(mängija_aeg);
            } else { //Lisab eksisteerivasse faili vajaliku rea juurde.
                kirjutaFailiJuurde(mängija_aeg,edetabel);
            }
        } catch (IOException e) {
            System.out.println("Midagi läks valesti.");
            e.printStackTrace();
        }
        //Väljasta konsooli
        väljastaKonsooli();
    }

    private void väljastaKonsooli(){
        //Prindib rida realt konsoolist tulemuse.
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

    private void kirjutaFailiNullist(long mängija_aeg) {
        //Loob faili. Lisab sinna raamistiku ning esimese mängija tulemuse koos kasutaja poolt antud nimega.
        try {
            FileWriter kirjutaja = new FileWriter("edetabel.txt");
            PrintWriter printWriter = new PrintWriter(kirjutaja);
            printWriter.print("---E-D-E-T-A-B-E-L---\nmn:sk:mil\tMängija\n");
            printWriter.printf("%tM:%tS:%tL\t", mängija_aeg, mängija_aeg, mängija_aeg);
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
    }

    private void kirjutaFailiJuurde(long mängija_aeg,File edetabel){
        //Avab ette antud faili, otsib õige rea ning lisab sinna mängija tulemuse koos ette antud ajaga.
        try (Scanner sc = new Scanner(edetabel, "UTF-8")) {
            Path path = Paths.get("edetabel.txt");
            int ridadearv = (int) Files.lines(path).count();
            int reaLuger = 1;
            //Otsib õige rea võrreldes olemasolevaid aegu lisatava ajaga.
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                if (ridadearv>reaLuger&&reaLuger>2) {
                    String[] tükid = rida.split("\t")[0].split(":");
                    long aeg= Long.parseLong(tükid[0])*60000+Long.parseLong(tükid[1])*1000+Long.parseLong(tükid[2]);
                    if (aeg>mängija_aeg) break;
                }
                reaLuger++;
                if (reaLuger>ridadearv)reaLuger--;
            }
            //Lisab mängija tulemuse koos ajaga õigele reale.
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String lisatavTekst = String.format("%tM:%tS:%tL\t",mängija_aeg,mängija_aeg,mängija_aeg);
            System.out.println("Sisestage oma nimi: ");
            Scanner scanner = new Scanner(System.in);
            String mängijaNimi = scanner.nextLine();
            System.out.println(reaLuger);
            lines.add((reaLuger-1), lisatavTekst+mängijaNimi);
            Files.write(path, lines, StandardCharsets.UTF_8);
        }

        catch (IOException e) {
            System.out.println("Midagi läks valesti.");
            e.printStackTrace();
        }
    }
}
