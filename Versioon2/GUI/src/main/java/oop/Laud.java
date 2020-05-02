package oop;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Laud extends Application {
    private static  boolean segatud =false;
    private static boolean käib = false;
    private static boolean noolte_loogika = true;
    private static boolean OnKorraküsitud=false; //Edetabelisse nime lisamise jaoks.

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage peaLava) throws FileNotFoundException {

        //oop.Laua loomine ja nuppude loomine.
        GridPane laud = new GridPane();
        Scene stseen = new Scene(laud);
        peaLava.setScene(stseen);
        peaLava.sizeToScene();
        Button n1 = new Button(" 1 ");
        Button n2 = new Button(" 2 ");
        Button n3 = new Button(" 3 ");
        Button n4 = new Button(" 4 ");
        Button n5 = new Button(" 5 ");
        Button n6 = new Button(" 6 ");
        Button n7 = new Button(" 7 ");
        Button n8 = new Button(" 8 ");
        Button n9 = new Button(" 9 ");
        Button n10 = new Button("10");
        Button n11 = new Button("11");
        Button n12 = new Button("12");
        Button n13 = new Button("13");
        Button n14 = new Button("14");
        Button n15 = new Button("15");
        Button n16 = new Button("");
        Button sega = new Button("Sega");
        sega.fontProperty().bind(n1.fontProperty());
        Button[] nupud = new Button[]{n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16};

        //Menüü nupuvaliku loomine
        MenuItem noolteLoogikaNupp = new MenuItem("Noolte loogika teistsuguseks");
        MenuItem edetabeliNupp = new MenuItem("Edetabel");
        MenuItem tuhjendaEdetabel = new MenuItem("Tühjenda edetabel");

        //Menüü nupu pildi lisamine
        ImageView imageView = new ImageView(new Image(new FileInputStream("hammasratas.png"))); //Pildi au ja kuulsus : https://www.flaticon.com/.
        imageView.setPreserveRatio(true);
        MenuButton menuNupp = new MenuButton("", imageView, noolteLoogikaNupp, edetabeliNupp,tuhjendaEdetabel);

        //Mängu lahendades dialoogikasti moodustamine.
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Kinnitus edetabeli jaoks");
        dialog.setHeaderText("Edetabelisse lisamiseks");
        dialog.setContentText("Sisestage oma nimi/hüüdnimi:");

        //Laua välimus
        laud.setPadding(new Insets(8));
        laud.setVgap(8);
        laud.setHgap(8);
        laud.setStyle("-fx-base: rgb(145,145,145);");
        for (int i = 0; i <4 ; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setFillWidth(true);
            col.setMinWidth(50);
            laud.getColumnConstraints().add(col);
        }
        for (int i = 0; i <5 ; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            row.setFillHeight(true);
            row.setMinHeight(50);
            laud.getRowConstraints().add(row);
        }

        //Panen nupud listi, et neid oleks lihtsam hallata
        for (int i = 0; i < 16; i++) {
            laud.add(nupud[i],i%4,i/4);
        }
        laud.add(sega, 0, 4,2,1);
        sega.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //Stopper
        Label aeg= new Label();
        aeg.setText("00:00");
        aeg.fontProperty().bind(n1.fontProperty());//n1 on dünaamiline suurus, mis reguleerib ennast akna suurusega.
        DateFormat tf = new SimpleDateFormat( "mm:ss" );
        Timeline tl= new Timeline();
        tl.setCycleCount( Animation.INDEFINITE );
        laud.add(aeg,2,4,2,1);
        laud.add(menuNupp,3,4,2,1 );


        //Nuppudele funktsioonide lisamine
        for (Button nupp : nupud) {
            nupp.arm();
            //nupu välimus ja teksti suurus
            nupp.setStyle("-fx-background-color: rgb(221,221,221);");
            nupp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            //Dünaamilise suurusega tekst.
            stseen.widthProperty().addListener((observable, oldvalue, newvalue) -> {
                if ((Double) newvalue < laud.getHeight()) {
                    nupp.setFont(Font.font("Lato", (double) newvalue / 12));
                    imageView.setFitWidth((double) newvalue/8);
                }
            });
            stseen.heightProperty().addListener((observable, oldvalue, newvalue) -> {
                if ((Double) newvalue < laud.getWidth()) {
                    nupp.setFont(Font.font("Lato", (double) newvalue / 12));
                    imageView.setFitHeight((double) newvalue / 8);
                }

            });

            //Mingile nupule(kivile) vajutades...
            nupp.setOnAction(actionEvent -> {
                //Kui mäng veel ei käi ja laud on segatud, siis alustab mängu.
                if (!käib && segatud) {
                    long algus = System.currentTimeMillis();
                    tl.getKeyFrames().add(
                            new KeyFrame(
                                    Duration.millis(500),
                                    event -> {
                                        aeg.setText(tf.format(System.currentTimeMillis()-algus));
                                    }
                            ));
                    tl.play();//alustab taimeri.
                    käib = true;
                    sega.setDisable(true);
                    OnKorraküsitud=false;
                    menuNupp.setDisable(true);
                }

                //Liigutamised. Nupp liigub, kui tema kõrval on tühi koht. Sel juhul liigub selles suunas, kus on tema
                // suhtes tühi koht, kas üles, alla, vasakule või paremal.
                if (GridPane.getRowIndex(nupp).equals(GridPane.getRowIndex(n16)) &&
                        GridPane.getColumnIndex(nupp) - 1 == GridPane.getColumnIndex(n16)) {
                    Integer temp = GridPane.getColumnIndex(nupp);
                    GridPane.setColumnIndex(nupp, GridPane.getColumnIndex(n16));
                    GridPane.setColumnIndex(n16, temp);
                } else if (GridPane.getRowIndex(nupp).equals(GridPane.getRowIndex(n16)) &&
                        GridPane.getColumnIndex(nupp) + 1 == GridPane.getColumnIndex(n16)) {
                    Integer temp = GridPane.getColumnIndex(nupp);
                    GridPane.setColumnIndex(nupp, GridPane.getColumnIndex(n16));
                    GridPane.setColumnIndex(n16, temp);
                } else if (GridPane.getColumnIndex(nupp).equals(GridPane.getColumnIndex(n16)) &&
                        GridPane.getRowIndex(nupp) + 1 == GridPane.getRowIndex(n16)) {
                    Integer temp = GridPane.getRowIndex(nupp);
                    GridPane.setRowIndex(nupp, GridPane.getRowIndex(n16));
                    GridPane.setRowIndex(n16, temp);
                } else if (GridPane.getColumnIndex(nupp).equals(GridPane.getColumnIndex(n16)) &&
                        GridPane.getRowIndex(nupp) - 1 == GridPane.getRowIndex(n16)) {
                    Integer temp = GridPane.getRowIndex(nupp);
                    GridPane.setRowIndex(nupp, GridPane.getRowIndex(n16));
                    GridPane.setRowIndex(n16, temp);
                }
                //Peale liigutamist kontrollib kas mäng on lahendud. Kui on siis peatab taimeri ja lubab alustada uue
                // mängu alustamise.
                if (onLahendatud(nupud)&&segatud) {
                    String[] minsec = aeg.getText().split(":");
                    if(Integer.parseInt(minsec[0])*60 + Integer.parseInt(minsec[1])<4) throw new ViganeAegErind(aeg.getText());
                    tl.stop();
                    sega.setDisable(false);
                    menuNupp.setDisable(false); //Kui mäng on läbi saab ka menüüd kasutada.
                    if (!OnKorraküsitud) {      //Ja küsib mängija nime, et teda edetabelisse lisada. Kui on ühe korra vastuse saanud, ei küsi rohkem.
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent() && !result.get().equals("")) {
                            lisaEdetabelisse(minsec, result.get());
                            OnKorraküsitud = true;
                        }
                    }
                }

            });

            //Klaviatuuri (nooltega liikumise) funktsioon
            nupp.setOnKeyPressed(KeyEvent -> {
                //Kontrollib analoogselt hiirega vajutamisele, kas mäng on segatud ja käib.
                //Vastavalt noolte_loogikale määrab, kas noolele vajutamisega liigub tühikoht vajutatud noole suunas
                // või liigub kõrval olev kivi noolega näidatud suunas.
                if (!käib && segatud) {
                    long algus = System.currentTimeMillis();
                    tl.getKeyFrames().add(
                            new KeyFrame(
                                    Duration.millis(500),
                                    event -> {
                                        aeg.setText(tf.format(System.currentTimeMillis() - algus));
                                    }
                            ));
                    tl.play();
                    käib = true;
                    sega.setDisable(true);
                    OnKorraküsitud=false;
                    menuNupp.setDisable(true);
                }

                if ((KeyEvent.getCode() == KeyCode.UP && noolte_loogika) ||
                        (KeyEvent.getCode() == KeyCode.DOWN && !noolte_loogika) &&
                                GridPane.getRowIndex(n16) != 0) {
                    for (Button button : nupud) {
                        if (GridPane.getColumnIndex(button) == GridPane.getColumnIndex(n16) &&
                                GridPane.getRowIndex(button) == GridPane.getRowIndex(n16) - 1) {
                            GridPane.setRowIndex(button, GridPane.getRowIndex(n16));
                            GridPane.setRowIndex(n16, GridPane.getRowIndex(n16) - 1);
                            break;
                        }
                    }

                } else if ((KeyEvent.getCode() == KeyCode.DOWN && noolte_loogika) ||
                        (KeyEvent.getCode() == KeyCode.UP && !noolte_loogika) &&
                                GridPane.getRowIndex(n16) != 3) {
                    for (Button button : nupud) {
                        if (GridPane.getColumnIndex(button).equals(GridPane.getColumnIndex(n16)) &&
                                GridPane.getRowIndex(button) == GridPane.getRowIndex(n16) + 1) {
                            GridPane.setRowIndex(button, GridPane.getRowIndex(n16));
                            GridPane.setRowIndex(n16, GridPane.getRowIndex(n16) + 1);
                            break;
                        }
                    }
                } else if ((KeyEvent.getCode() == KeyCode.LEFT && noolte_loogika) ||
                        (KeyEvent.getCode() == KeyCode.RIGHT && !noolte_loogika) &&
                                GridPane.getColumnIndex(n16) != 0) {
                    for (Button button : nupud) {
                        if (GridPane.getRowIndex(button).equals(GridPane.getRowIndex(n16)) &&
                                GridPane.getColumnIndex(button) == GridPane.getColumnIndex(n16) - 1) {
                            GridPane.setColumnIndex(button, GridPane.getColumnIndex(n16));
                            GridPane.setColumnIndex(n16, GridPane.getColumnIndex(n16) - 1);
                            break;
                        }
                    }
                } else if ((KeyEvent.getCode() == KeyCode.RIGHT && noolte_loogika) ||
                        (KeyEvent.getCode() == KeyCode.LEFT && !noolte_loogika) &&
                                GridPane.getColumnIndex(n16) != 3) {
                    for (Button button : nupud) {
                        if (GridPane.getRowIndex(button).equals(GridPane.getRowIndex(n16)) &&
                                GridPane.getColumnIndex(button) == GridPane.getColumnIndex(n16) + 1) {
                            GridPane.setColumnIndex(button, GridPane.getColumnIndex(n16));
                            GridPane.setColumnIndex(n16, GridPane.getColumnIndex(n16) + 1);
                            break;
                        }
                    }
                }
                //Lõpp
                if (onLahendatud(nupud)&&segatud) {
                    String[] minsec = aeg.getText().split(":");
                    if(Integer.parseInt(minsec[0])*60 + Integer.parseInt(minsec[1])<4) throw new ViganeAegErind(aeg.getText());
                    tl.stop();
                    sega.setDisable(false);
                    menuNupp.setDisable(false);
                    if (!OnKorraküsitud) { //Küsib edetabelisse lisamiseks kasutaja nime (kui lõpetati klahvivajutusega).
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent() && !result.get().equals("")) {
                            lisaEdetabelisse(minsec, result.get());
                            OnKorraküsitud = true;
                        }
                    }
                }
            });
        }
        n16.setStyle("-fx-background-color: rgb(145,145,145);");
        //segamise nupule vajutamisel...
        sega.setOnMouseClicked(mouseEvent -> {
            aeg.setText("00:00");//Aeg nulli
            segatud=false;//Et segamise käigus ei hakkaks mäng pihta.
            //segamine tähendab, et arvuti klõpustab 100000 juhsliku nuppu.
            for (int i = 0; i < 100000 ; i++) {
                int juhuslik = (int) (Math.random()*16);
                nupud[juhuslik].fire();}
            //Ettevalmistused mängu minekuks.
            segatud=true;
            käib=false;
        });

        //Menüü nupuvalik
        //Noolteloogika muutab hetkeloogika vastupidiseks.
        noolteLoogikaNupp.setOnAction(event -> {
            if (noolte_loogika){
                noolte_loogika=false;
            }
            else {
                noolte_loogika=true;
            }
        });

        //Edetabeli nupule vajutades ilmub edetabel, kui see leidub.
        edetabeliNupp.setOnAction(event -> {
            EdetabeliAken edetabeliAken = new EdetabeliAken("edetabel.txt");
            File edetabeliFail = new File("edetabel.txt");
            if (edetabeliFail.exists()&&edetabeliFail.length()>3)
                edetabeliAken.showAndWait();
        });
        //Tühjenda edetabelit nupule vajutades tühjendatakse edetabeli kui edetabelis on midagi.
        tuhjendaEdetabel.setOnAction(event -> {
            File edetabeliFail = new File("edetabel.txt");
            if (edetabeliFail.exists())
                edetabeliFail.delete();
        });

        peaLava.setTitle("15 Kivi");
        peaLava.setScene(stseen);
        peaLava.show();
        peaLava.setMinHeight(350);
        peaLava.setMinWidth(280);
    }

    /**
     * meetod lisaEdetabelisse võtab sisendiks hetke mängija aja ning sisestatud nime.
     * Väljastab edetabeli.
     *
      * @param minsec Mängija lahendamiseks kulunud aeg formaadis minutid:sekundid.
     * @param nimi Mängija sisestatud nimi.
     */
    private void lisaEdetabelisse(String[] minsec, String nimi) {
        try {
            File edetabel = new File("edetabel.txt");
            if (edetabel.createNewFile() || edetabel.length()==0) {
                //Loob faili ja kirjutab sinna.
                kirjutaFailiNullist(minsec,nimi);
            } else { //Lisab eksisteerivasse faili vajaliku rea juurde.
                kirjutaFailiJuurde(minsec,edetabel,nimi);
            }
        } catch (IOException e) {
            System.out.println("Midagi läks valesti.");
            e.printStackTrace();
        }
        //Näitab edetabelit.
        EdetabeliAken edetabeliAken = new EdetabeliAken("edetabel.txt");
        edetabeliAken.showAndWait();
    }


    /**Meetod onLahendatud võitab siendiks nuppude massivi ja tagastab, kas mäng on lahendatud
     * @param nupud Massiiv nuppudest.
     * @return tõeväärtus
     */
    private boolean onLahendatud(Button[] nupud) {
        boolean lahendatud=true;
        for (int i = 0; i < 16; i++) {
            if ((GridPane.getRowIndex(nupud[i])!=i/4 ) || (GridPane.getColumnIndex(nupud[i])!= i%4)) {
                lahendatud=false;
                break;}

        }
        return lahendatud;
    }

    /**
     * Kirjutab andmed faili siis, kui faili eelnevalt ei eksisteeri.
     *
     * @param mängija_aeg Mängija etteantud aeg.
     * @param mängijaNimi Mängija nimi.
     */
    private void kirjutaFailiNullist(String[] mängija_aeg, String mängijaNimi) {
        //Loob faili. Lisab sinna raamistiku ning esimese mängija tulemuse koos kasutaja poolt antud nimega.
        try {
            FileWriter kirjutaja = new FileWriter("edetabel.txt");
            PrintWriter printWriter = new PrintWriter(kirjutaja);
            printWriter.print(mängija_aeg[0]+":"+mängija_aeg[1]+":");
            printWriter.printf("%s \n", mängijaNimi);
            kirjutaja.close();
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Midagi läks valesti. Kontakteeru programmeerijaga!");
            e.printStackTrace();
        }
    }

    /**
     * Avab ette antud faili, otsib õige rea ning lisab sinna mängija tulemuse koos ette antud ajaga.
     *
     * @param uus_aeg mängija hetkeaeg.
     * @param edetabel edetabeli fail.
     * @param mängijaNimi mängija nimi.
     */
    private void kirjutaFailiJuurde(String[] uus_aeg,File edetabel, String mängijaNimi){
        try (Scanner sc = new Scanner(edetabel, "UTF-8")) {
            Path path = Paths.get("edetabel.txt");
            int ridadearv = (int) Files.lines(path).count();
            int reaLuger = 1;
            int mängija_aeg=Integer.parseInt(uus_aeg[0])*60+Integer.parseInt(uus_aeg[1]);
            //Otsib õige rea võrreldes olemasolevaid aegu lisatava ajaga.
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                if (ridadearv>reaLuger) {
                    String[] tükid = rida.split(":");
                    int aeg = Integer.parseInt(tükid[0])*60+Integer.parseInt(tükid[1]);
                    if (aeg>mängija_aeg) break;
                }
                reaLuger++;
            }
            //Lisab mängija tulemuse koos ajaga õigele reale.
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            System.out.println(reaLuger);
            lines.add((reaLuger-1), uus_aeg[0]+":"+uus_aeg[1]+":"+mängijaNimi);
            Files.write(path, lines, StandardCharsets.UTF_8);
        }

        catch (IOException e) {
            System.out.println("Midagi läks valesti. Kontakteeru programmeerijaga!");
        }
    }

}

