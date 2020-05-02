package oop;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EdetabeliAken extends Dialog<ButtonType> {


    public EdetabeliAken(String tekstifail) {
        super();
        Alert tühiFail = new Alert(Alert.AlertType.ERROR);
        tühiFail.setTitle("Tekkis viga");
        tühiFail.setHeaderText("Ühtegi mängu pole veel tehtud!");
        tühiFail.setContentText("Esmalt tuleb tulemusi saada et edetabelit näha.");

        try {
            File myObj = new File(tekstifail);
            if (myObj.length()<3){
                tühiFail.show();
            }else {
                DialogPane dialogPane = new DialogPane();
                VBox outer = new VBox();
                Button button = new Button("Sulge");
                setResult(new ButtonType("NO", ButtonBar.ButtonData.LEFT));
                button.setOnMousePressed(e -> close());
                ListView<Text> inner = new ListView<Text>();
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] tükid =data.split(":");
                    String aeg = tükid[0]+":"+tükid[1];
                    String nimi = tükid[2];
                    inner.getItems().add(new Text(aeg+"\t"+nimi));
                }
                myReader.close();
                inner.setMaxHeight(80);
                outer.getChildren().addAll(button, inner);
                dialogPane.setContent(outer);
                this.setDialogPane(dialogPane);
            }
        } catch (FileNotFoundException e) {
            tühiFail.showAndWait();
        }
    }
}
