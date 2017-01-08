import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class Main extends Application {
    // Kuna ma ei saanud andmebaasi valmis siis tegin ise lihtsalt listid näitamiseks.
    private String[] rendipunktid = {"Alfa", "Bravo", "Charlie", "Delta"};
    private String[] autopunktA = {"111 aaa", "222 aaa", "333 aaa", "444 aaa"};
    private String[] autopunktB = {"111 bbb", "222 bbb", "333 bbb"};
    private String[] autopunktC = {"111 ccc", "222 ccc", "333 ccc"};
    private String[] autopunktD = {"111 ddd", "222 ddd", "333 ddd"};


    private String valitudrendipunkt;


    private ObservableList<String> valik = FXCollections
            .observableArrayList();
    private ListView<String> candidatesListView = new ListView<>(valik);


    private ObservableList<String> valitud = FXCollections
            .observableArrayList();
    private ListView<String> heroListView = new ListView<>(valitud);



    @Override
    public void start(Stage primaryStage) {

        BorderPane põhi = new BorderPane();
        primaryStage.setTitle("Rendiautode broneerimssüsteem");
        Scene scene = new Scene(põhi, 600, 500, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();

        //gridaluse Layout
        GridPane gridalus = new GridPane();
        gridalus.setPadding(new Insets(15));
        gridalus.setHgap(10);
        gridalus.setVgap(10);
        gridalus.add(candidatesListView, 0, 2);
        gridalus.add(heroListView, 2, 2);


        Label valiauto = new Label("Vali rendiauto");
        gridalus.add(valiauto, 0, 1);
        GridPane.setHalignment(valiauto, HPos.CENTER);
        valiauto.setStyle("-fx-font-size: 10pt;");

        Label valitudautod = new Label("Valitud rendiauto");
        gridalus.add(valitudautod, 2, 1);
        GridPane.setHalignment(valitudautod, HPos.CENTER);
        valitudautod.setStyle("-fx-font-size: 10pt;");

        ColumnConstraints column1 = new ColumnConstraints(100, 150,
                Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(50);
        ColumnConstraints column3 = new ColumnConstraints(100, 150,
                Double.MAX_VALUE);
        //ColumnConstraints column4 = new ColumnConstraints(150, 150,
        //       Double.MAX_VALUE); // Kui tahan teha rohkem tulpasid valikute juurde, siis lisan siit.

        column1.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);
        //column4.setHgrow(Priority.ALWAYS);
        gridalus.getColumnConstraints().addAll(column1, column2, column3);

        //Drop - down menüü loomine
        final ComboBox autovalikutecb = new ComboBox();
        autovalikutecb.getItems().addAll(rendipunktid);
        autovalikutecb.setPromptText("Rendipunkti valik");
        autovalikutecb.setMinSize(200, 30);
        gridalus.add(autovalikutecb, 0, 0);

        // DROP DOWN MENÜÜ VALIKUVÕIMALUSED
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

        //EDASI JA TAGASI NUPUD
        Button btnEdasi = new Button("Edasi");
        btnEdasi.setStyle("-fx-font-size: 10pt;");
        btnEdasi.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        String d = heroListView.getSelectionModel().getSelectedItem();
        if(d == null){
            btnEdasi.setDisable(true);
        }
        else{
            btnEdasi.setDisable(false);
        }

        Button btnKatkesta = new Button("Katkesta");
        btnKatkesta.setStyle("-fx-font-size: 10pt;");
        btnKatkesta.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnKatkesta.setOnAction((ActionEvent event) -> {
            primaryStage.close();
        });

        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setPadding(new Insets(20,10,20,0));
        tileButtons.setHgap(10.0);
        tileButtons.setVgap(8.0);
        tileButtons.setAlignment(Pos.CENTER);
        tileButtons.getChildren().addAll(btnEdasi, btnKatkesta);

        // SEND RIGHT, SEND LEFT NUPUD
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
            //DISABLE EDASI NUPP KUI VALITUD EI OLE ÜHTEGI AUTOT
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

        // KUI RENDIPUNKTIST ON AUTO VALITUD SIIS VAJUTADES NUPPU "EDASI" SAAB VALIDA SÕIDU AJALISE KESTUSE/SELLEKS AVANEB UUS AKEN
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(sendRightButton, sendLeftButton);

         class currentTime {
            public  void main(String[] args) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
            }
        }

        btnEdasi.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Stage stage = new Stage();
                stage.setTitle("Rendisõidu kestus ");

                VBox vbox = new VBox(20);
                vbox.setStyle("-fx-padding: 10;");
                Scene scene = new Scene(vbox, 400, 300);
                stage.setScene(scene);

                DatePicker sõidualgusevalik = new DatePicker();
                sõidualgusevalik.setValue(LocalDate.now());

                final Callback<DatePicker, DateCell> dayCellFactory =
                        new Callback<DatePicker, DateCell>() {
                            @Override
                            public DateCell call(final DatePicker datePicker){

                                return new DateCell(){
                                    @Override
                                    public void updateItem(LocalDate item, boolean empty){
                                        super.updateItem(item, empty);

                                        if (item.isBefore(
                                                sõidualgusevalik.getValue().plusDays(1)))
                                        {
                                            setDisable(true);
                                            setStyle("-fx-background-color: #00FFFF;");
                                        }
                                    }
                                };
                            }
                        };
                DatePicker sõidulõppkp = new DatePicker();
                sõidulõppkp.setDayCellFactory(dayCellFactory);
                sõidulõppkp.setValue(sõidualgusevalik.getValue().plusDays(1));


                final ComboBox sõiduAlgus = new ComboBox();
                sõiduAlgus.getItems().addAll(
                        "0.00",
                        "1.00",
                        "2.00",
                        "3.00",
                        "4.00",
                        "5.00",
                        "6.00",
                        "7.00",
                        "8.00",
                        "9.00",
                        "10.00",
                        "11.00",
                        "12.00",
                        "13.00",
                        "14.00",
                        "15.00",
                        "16.00",
                        "17.00",
                        "18.00",
                        "19.00",
                        "20.00",
                        "21.00",
                        "22.00",
                        "23.00"

                );

                final ComboBox sõiduLõpp = new ComboBox();
                sõiduLõpp.getItems().addAll(
                        "0.00",
                        "1.00",
                        "2.00",
                        "3.00",
                        "4.00",
                        "5.00",
                        "6.00",
                        "7.00",
                        "8.00",
                        "9.00",
                        "10.00",
                        "11.00",
                        "12.00",
                        "13.00",
                        "14.00",
                        "15.00",
                        "16.00",
                        "17.00",
                        "18.00",
                        "19.00",
                        "20.00",
                        "21.00",
                        "22.00",
                        "23.00"

                );


                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                Label checkInlabel = new Label("Sõiduaja algus:");
                gridPane.add(checkInlabel, 0, 0);
                Label checkoutlabel = new Label ("Sõiduaja lõpp:");
                gridPane.add(checkoutlabel, 1, 0);
                Label vabadkellaajadalgus = new Label ("Kellaaeg(algus):");
                gridPane.add(vabadkellaajadalgus, 0, 2);
                gridPane.add(sõiduAlgus, 0, 3);
                Label broneeringulõpp = new Label("Kellaaeg(lõpp): ");
                gridPane.add(broneeringulõpp, 1, 2);
                gridPane.add(sõiduLõpp, 1, 3);

                Button btnSalvesta = new Button("Salvesta");
                btnSalvesta.setStyle("-fx-font-size: 10pt;");
                Button btnSulge = new Button("Sulge");
                btnSulge.setStyle("-fx-font-size: 10pt;");
                btnSulge.setOnAction((ActionEvent event) -> {
                    stage.close();
                });

                TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
                tileButtons.setPadding(new Insets(20,10,20,0));
                tileButtons.setHgap(8.0);
                tileButtons.setVgap(8.0);
                tileButtons.getChildren().addAll(btnSalvesta, btnSulge);
                gridPane.add(tileButtons, 0,4);

                gridPane.setHalignment(checkInlabel, HPos.LEFT);
                gridPane.setHalignment(checkoutlabel, HPos.LEFT);
                gridPane.add(sõidualgusevalik, 0, 1);
                gridPane.add(sõidulõppkp, 1, 1);
                vbox.getChildren().addAll(gridPane, tileButtons);
                stage.show();
            }
        });

        gridalus.add(vbox, 1, 2);
        //põhi.setCenter(gridalus);

        gridalus.add(tileButtons, 2, 4);
        põhi.setCenter(gridalus);
        GridPane.setVgrow(põhi, Priority.ALWAYS);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    }
