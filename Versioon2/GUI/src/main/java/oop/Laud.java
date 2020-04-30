package oop;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static javafx.scene.paint.Color.rgb;

public class Laud extends Application {
    private static  boolean segatud =false;
    private static boolean käib = false;
    private static boolean noolte_loogika = false;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage peaLava) {

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



        //Nuppudele funktsioonide lisamine
        for (Button nupp : nupud) {
            nupp.arm();
            //nupu välimus ja teksti suurus
            nupp.setStyle("-fx-background-color: rgb(221,221,221);");
            nupp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            //Dünaamilise suurusega tekst.
            stseen.widthProperty().addListener((observable, oldvalue, newvalue) -> {
                if ((Double) newvalue < laud.getHeight())
                    nupp.setFont(Font.font("Lato", (double) newvalue/12));
            });
            stseen.heightProperty().addListener((observable, oldvalue, newvalue) -> {
                if ((Double) newvalue < laud.getWidth())
                    nupp.setFont(Font.font("Lato",  (double) newvalue/12));
            });

            //Mingile nupule vajutades...
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
                }

            });

            nupp.setOnKeyPressed(KeyEvent -> {
                //Klaviatuuri (nooltega liikumise) funktsioon
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

        peaLava.setTitle("15 Kivi");
        peaLava.setScene(stseen);
        peaLava.show();
        peaLava.setMinHeight(350);
        peaLava.setMinWidth(280);
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
}

