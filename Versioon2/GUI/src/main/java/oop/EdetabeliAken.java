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
                Label metaInf = new Label("  Koht\tAeg(min:sek)\t   Mängija nimi");
                Button button = new Button("Sulge");
                setResult(new ButtonType("Sulge",ButtonBar.ButtonData.FINISH));
                button.setOnMousePressed(e -> close());
                button.translateXProperty().bind(button.prefWidthProperty().divide(-2));

                ListView<Text> inner = new ListView<Text>();
                Scanner myReader = new Scanner(myObj);
                int koht = 1;
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] tükid =data.split(":");
                    String aeg = tükid[0]+":"+tükid[1];
                    if (tükid[0].length()==1)
                        aeg="0"+aeg;
                    String nimi = tükid[2];
                    inner.getItems().add(new Text(koht+".\t\t"+aeg+"\t\t"+nimi));
                    koht++;
                }
                myReader.close();
                inner.setMaxHeight(100);
                outer.getChildren().addAll(metaInf,inner,button);
                dialogPane.setContent(outer);
                this.setDialogPane(dialogPane);
            }
        } catch (FileNotFoundException e) {
            tühiFail.showAndWait();
        }
    }
}
