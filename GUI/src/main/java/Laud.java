import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.scene.paint.Color.rgb;

public class Laud extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage peaLava) {
        GridPane laud = new GridPane();
        Scene stseen = new Scene(laud,240,300);
        Button n1 = new Button("1");
        Button n2 = new Button("2");
        Button n3 = new Button("3");
        Button n4 = new Button("4");
        Button n5 = new Button("5");
        Button n6 = new Button("6");
        Button n7 = new Button("7");
        Button n8 = new Button("8");
        Button n9 = new Button("9");
        Button n10 = new Button("10");
        Button n11 = new Button("11");
        Button n12 = new Button("12");
        Button n13 = new Button("13");
        Button n14 = new Button("14");
        Button n15 = new Button("15");
        Button n16 = new Button("");
        Button sega = new Button("Sega");
        Button[] nupud = new Button[]{n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16};

        laud.add(nupud[0], 0, 0);
        laud.add(nupud[1], 1, 0);
        laud.add(nupud[2], 2, 0);
        laud.add(nupud[3], 3, 0);
        laud.add(nupud[4], 0, 1);
        laud.add(nupud[5], 1, 1);
        laud.add(nupud[6], 2, 1);
        laud.add(nupud[7], 3, 1);
        laud.add(nupud[8], 0, 2);
        laud.add(nupud[9], 1, 2);
        laud.add(nupud[10], 2, 2);
        laud.add(nupud[11], 3, 2);
        laud.add(nupud[12], 0, 3);
        laud.add(nupud[13], 1, 3);
        laud.add(nupud[14], 2, 3);
        laud.add(nupud[15], 3, 3);
        laud.setPadding(new Insets(8));
        laud.setVgap(8);
        laud.setHgap(8);
        laud.setStyle("-fx-base: rgb(145,145,145);");
        laud.add(sega, 0, 4);


        for (Button nupp : nupud) {
            nupp.arm();
            nupp.setStyle("-fx-background-color: rgb(221,221,221);");
            nupp.setMinSize(50, 50);
            nupp.setFont(Font.font("Lato", 20));
            nupp.setOnAction(actionEvent -> {
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
            });
            n16.setStyle("-fx-background-color: rgb(145,145,145);");
            sega.setOnMouseClicked(mouseEvent -> sega(nupud));


            peaLava.setTitle("15 Kivi");
            peaLava.setScene(stseen);
            peaLava.show();
        }
    }
    private void sega(Button[] nupud) {
        for (int i = 0; i <1000 ; i++) {
            int juhuslik = (int) (Math.random()*16);
            nupud[juhuslik].fire();
        }
    }
}

