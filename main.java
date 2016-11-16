import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class main extends Application {
    String[] rendipunktid = {"Alfa", "Bravo", "Charlie", "Delta"};
    String[] autopunktA = {"111 aaa", "222 aaa", "333 aaa", "444 aaa"};
    String[] autopunktB = {"111 bbb", "222 bbb", "333 bbb"};
    String[] autopunktC = {"111 ccc", "222 ccc", "333 ccc"};
    String[] autopunktD = {"111 ddd", "222 ddd", "333 ddd"};

    String valitudrendipunkt;
    final ObservableList<String> valik = FXCollections
            .observableArrayList();
    final ListView<String> candidatesListView = new ListView<>(valik);


    final ObservableList<String> valitud = FXCollections
            .observableArrayList();
    final ListView<String> heroListView = new ListView<>(valitud);



    @Override
    public void start(Stage primaryStage) {
        // KELLAAJA KUVAMINE
        DateTime();



        BorderPane põhi = new BorderPane();
        Scene scene = new Scene(põhi, 500,300, Color.WHITE);
        primaryStage.setTitle("Rendipunkti ja auto valik");
        primaryStage.setScene(scene);
        primaryStage.show();


        GridPane gridalus = new GridPane();
        gridalus.setPadding(new Insets(15));
        gridalus.setHgap(10);
        gridalus.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(50);
        ColumnConstraints column3 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);
        gridalus.getColumnConstraints().addAll(column1, column2, column3);




        //COMBOBOXI LOOMINE

        ComboBox autovalikutecb = new ComboBox();
            autovalikutecb.getItems().addAll(rendipunktid);
        autovalikutecb.setPromptText("Rendipunkti valik");
        autovalikutecb.setMinSize(200, 30);

        // ComboBox valik
        autovalikutecb.setOnAction((event) ->{
            valitudrendipunkt = (String) autovalikutecb.getSelectionModel().getSelectedItem();
            if (valitudrendipunkt == "Alfa") {
                valik.removeAll(valik);
                valik.addAll(autopunktA);
            }
            else if (valitudrendipunkt == "Bravo"){
                valik.removeAll(valik);
                valik.addAll(autopunktB);
            }
            else if (valitudrendipunkt == "Charlie"){
                valik.removeAll(valik);
                valik.addAll(autopunktC);
            }
            else if (valitudrendipunkt == "Delta"){
                valik.removeAll(valik);
                valik.addAll(autopunktD);
            }
            else{
                System.out.println("ERROR");
            }
            System.out.println(("ComboBox Action (selected: " + valitudrendipunkt.toString() + ")"));


        });


        //paigutan valikutulbad gridalusele
        gridalus.add(candidatesListView, 0, 1);
        gridalus.add(heroListView, 2, 1);

        // KÜSI SIIN, ET KUIDAS MUUTA COMBOBOXI Suurust võltuvaks valiku boxist
        gridalus.add(autovalikutecb, 0, 0);

        //Label autodvalikus = new Label("Vabad rendiautod");
        //GridPane.setHalignment(autodvalikus, HPos.CENTER);
        //autodvalikus.setStyle("-fx-font-size: 10pt;");
        //gridalus.add(autodvalikus, 0, 0);

        Label valitudautod = new Label("Valitud rendiautod");
        gridalus.add(valitudautod, 2, 0);
        GridPane.setHalignment(valitudautod, HPos.CENTER);
        valitudautod.setStyle("-fx-font-size: 10pt;");


        //Edasi ja tagasi nupud

        Button btnEdasi = new Button("Edasi");
        // MUUDA "EDASI KLAHV MITTEAKTIIVSEKS KUI "VALITUD" ON TÜHI
        String d = heroListView.getSelectionModel().getSelectedItem();
        if(d == null){
            btnEdasi.setDisable(true);
        }
        else{
            btnEdasi.setDisable(false);
        }

        Button btnKatkesta = new Button("Katkesta");
        btnEdasi.setStyle("-fx-font-size: 10pt;");
        btnKatkesta.setStyle("-fx-font-size: 10pt;");

        btnEdasi.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnKatkesta.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //ComboBox cb = new ComboBox(FXCollections.observableArrayList(rendipunktid));
        //ComboBox cb = new ComboBox();
        //cb.getItems().addAll(rendipunktid);
        //cb.setPromptText("Rendipunktid");





        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setPadding(new Insets(20,10,20,0));
        tileButtons.setHgap(10.0);
        tileButtons.setVgap(8.0);
        tileButtons.setAlignment(Pos.CENTER);
        tileButtons.getChildren().addAll(btnEdasi, btnKatkesta);



        Button sendRightButton = new Button(" > ");
        sendRightButton.setStyle("-fx-font-size: 10pt;");
        sendRightButton.setOnAction((ActionEvent event) -> {
            String potential = candidatesListView.getSelectionModel()
                    .getSelectedItem();
            if (potential != null) {
                candidatesListView.getSelectionModel().clearSelection();
                valik.remove(potential);
                valitud.add(potential);
            }
            // MUUDA "EDASI KLAHV MITTEAKTIIVSEKS KUI "VALITUD" ON TÜHI
            if(valitud == null){
                btnEdasi.setDisable(true);
            }
            else{
                btnEdasi.setDisable(false);
            }

        });

        Button sendLeftButton = new Button(" < ");
        sendLeftButton.setStyle("-fx-font-size: 10pt;");
        sendLeftButton.setOnAction((ActionEvent event) -> {
            String s = heroListView.getSelectionModel().getSelectedItem();
            if (s != null) {
                heroListView.getSelectionModel().clearSelection();
                valitud.remove(s);
                valik.add(s);

            }
            // MUUDA "EDASI KLAHV MITTEAKTIIVSEKS KUI "VALITUD" ON TÜHI
            String f = heroListView.getSelectionModel().getSelectedItem();
            if(f == null){
                btnEdasi.setDisable(true);
            }
            else{
                btnEdasi.setDisable(false);
            }


        });


        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(sendRightButton, sendLeftButton);


        //Edasi nupu event




        // Katkesta nupu event

        btnKatkesta.setOnAction((ActionEvent event) -> {
            primaryStage.close();
        });






        gridalus.add(vbox, 1, 1);
        põhi.setCenter(gridalus);



        gridalus.add(tileButtons, 2, 2);
        põhi.setCenter(gridalus);




        GridPane.setVgrow(põhi, Priority.ALWAYS);
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public void DateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));



    }


}
