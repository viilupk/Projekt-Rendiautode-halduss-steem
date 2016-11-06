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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private Scene põhiScene;
    private Scene optionsScene;
    private Stage stage;


    @Override
    public void start(Stage primaryStage) {
        // KELLAAJA KUVAMINE - hetkel kuvab ainult konsoolis
        //Calendar kalender = Calendar.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //System.out.println(sdf.format(kalender.getTime()));


        BorderPane põhi = new BorderPane();
        Scene scene = new Scene(põhi, 500,300, Color.WHITE);

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

        Label autodvalikus = new Label("Vabad rendiautod");
        GridPane.setHalignment(autodvalikus, HPos.CENTER);
        autodvalikus.setStyle("-fx-font-size: 10pt;");
        gridalus.add(autodvalikus, 0, 0);

        Label valitudautod = new Label("Valitud rendiautod");
        gridalus.add(valitudautod, 2, 0);
        GridPane.setHalignment(valitudautod, HPos.CENTER);
        valitudautod.setStyle("-fx-font-size: 10pt;");

        final ObservableList<String> valik = FXCollections
                .observableArrayList("892 AAS", "892 SAL", "943 KAS", "921 LAS", "892 KLS");
        final ListView<String> candidatesListView = new ListView<>(valik);
        gridalus.add(candidatesListView, 0, 1);

        final ObservableList<String> valitud = FXCollections
                .observableArrayList();
        final ListView<String> heroListView = new ListView<>(valitud);
        gridalus.add(heroListView, 2, 1);

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

        btnEdasi.setOnAction((ActionEvent event) -> {

                VBox vbox2 = new VBox();
                Scene edasi = new Scene(vbox2, 500, 300, Color.ANTIQUEWHITE);

                primaryStage.setScene(edasi);
                primaryStage.show();


                Label edasitekst = new Label("valisid auto " + valitud );
                vbox2.getChildren().addAll(edasitekst);
                primaryStage.show();


        });


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



}
