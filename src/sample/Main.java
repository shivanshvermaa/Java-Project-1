package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Label lb1;
        lb1 = new Label();

        Button insertMedicine = new Button(" Insert a medicine ");
        Button removeMedicine = new Button(" Remove a medicine ");
        Button getMedicine = new Button(" Get a medicine ");
        //Button updateMedicine =  new Button(" Update a medicine " );
        Button showAllMedicine = new Button(" Show all medicine ");

        String host = "jdbc:mysql://localhost:3306/med";
        String user = "root";
        String pass = "#heisenberg@420";

        insertMedicine.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Label lbInsert = new Label("INSERT A MEDICINE");

                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10));
                grid.setVgap(10);
                grid.setHgap(10);

                Label medName = new Label("MEDICINE NAME");
                Label medQuantity = new Label("MEDICINE QUANTITY");
                Label medPrice = new Label("MEDICINE PRICE");
                Label medManufacturer = new Label("MEDICINE MANUFACTURER");
                final Label data = new Label("Insert Data");


                GridPane.setConstraints(medName, 0, 0);
                GridPane.setConstraints(medQuantity, 0, 1);
                GridPane.setConstraints(medPrice, 0, 2);
                GridPane.setConstraints(medManufacturer, 0, 3);

                TextField medNameInput = new TextField();
                TextField medPriceInput = new TextField();
                TextField medQuantityInput = new TextField();
                TextField medManufacturerInput = new TextField();

                GridPane.setConstraints(medNameInput, 1, 0);
                GridPane.setConstraints(medQuantityInput, 1, 1);
                GridPane.setConstraints(medPriceInput, 1, 2);
                GridPane.setConstraints(medManufacturerInput, 1, 3);

                String name = medNameInput.getText();
                String price = medPriceInput.getText();
                String quantity = medQuantityInput.getText();
                String manufacturer = medManufacturerInput.getText();


                Button insert = new Button("INSERT");
                GridPane.setConstraints(insert, 1, 4);

                insert.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (!medNameInput.getText().isEmpty() && !medNameInput.getText().isBlank() && !medPriceInput.getText().isEmpty() && !medPriceInput.getText().isBlank() && !medQuantityInput.getText().isEmpty() && !medQuantityInput.getText().isBlank() && !medManufacturerInput.getText().isEmpty() && !medManufacturerInput.getText().isBlank()) {
                            try {
                                Connection conn = DriverManager.getConnection(host, user, pass);
                                String prep = "INSERT INTO medicine( name , price , quantity , manufacturer ) VALUES ( ? , ? , ? , ? )";
                                PreparedStatement pst = conn.prepareStatement(prep);
                                pst.setString(1, medNameInput.getText());
                                pst.setString(2, medPriceInput.getText());
                                pst.setString(3, medQuantityInput.getText());
                                pst.setString(4, medManufacturerInput.getText());

                                pst.executeUpdate();
                            } catch (SQLException e) {
                                System.out.println(e.toString());
                            }
                            data.setText("INSERTED medicine from " + medManufacturerInput.getText());
                        } else {
                            data.setText("Some fields are blank");
                        }
                    }
                });

                GridPane.setConstraints(data, 0, 4);
                grid.getChildren().add(data);


                grid.getChildren().addAll(medName, medNameInput, medManufacturer, medManufacturerInput, medPrice, medPriceInput, medQuantity, medQuantityInput, insert);

                Scene scene = new Scene(grid, 300, 300);

                Stage s1 = new Stage();
                s1.setTitle("INSERT A MEDICINE");
                s1.setScene(scene);
                s1.show();


            }
        });

        removeMedicine.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                Label lb = new Label("Enter the name of the Medicine");
                TextField name = new TextField();
                Button del = new Button("DELETE MEDICINE");
                Label result = new Label();

                del.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Connection conn = DriverManager.getConnection(host, user, pass);
                            String query = "DELETE FROM medicine WHERE name = '" + name.getText() + "' ";
                            Statement st = conn.createStatement();
                            int r = st.executeUpdate(query);

                            if (r == 0) {
                                System.out.println(name.getText());
                                result.setText("NOT PRESENT");
                            } else {
                                result.setText("DELETED");
                            }


                        } catch (SQLException e) {
                            System.out.println(e.toString());
                        }


                    }
                });

                GridPane.setConstraints(result, 2, 0);
                gridPane.getChildren().add(result);


                GridPane.setConstraints(lb, 0, 0);
                GridPane.setConstraints(name, 1, 0);
                GridPane.setConstraints(del, 1, 1);
                gridPane.getChildren().addAll(lb, name, del);

                Scene scene = new Scene(gridPane, 300, 300);

                Stage s1 = new Stage();
                s1.setTitle("Remove a medicine");
                s1.setScene(scene);
                s1.show();


            }
        });

        getMedicine.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                Label lb = new Label("Enter the name of the Medicine");
                TextField name = new TextField();
                Button get = new Button("Get MEDICINE");
                Label result = new Label();
                GridPane.setConstraints(lb, 0, 0);
                GridPane.setConstraints(name, 1, 0);
                GridPane.setConstraints(get, 1, 1);

                gridPane.getChildren().addAll(lb, name, get);


                get.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Connection conn = DriverManager.getConnection(host, user, pass);
                            String query = "SELECT * FROM medicine WHERE name = '" + name.getText() + "' ";
                            Statement st = conn.createStatement();
                            ResultSet rs = st.executeQuery(query);
                           /* if( rs.next() == false )
                            {
                                Label lb1 = new Label("No present");
                                GridPane.setConstraints(lb1,0,1) ;
                                gridPane.getChildren().addAll(lb1);
                            }*/

                            while (rs.next()) {
                                //meds m =  new meds(rs.getString("idmedicine" ).toString() , rs.getString("name") , rs.getString("price" ) ,rs.getString("quantity") , rs.getString("manufacturer") );

                                Label medName = new Label(rs.getString("name"));
                                Label medQuantity = new Label(rs.getString("Quantity"));
                                Label medPrice = new Label(rs.getString("price"));
                                Label medManufacturer = new Label(rs.getString("manufacturer"));
                                final Label data = new Label("Insert Data");


                                GridPane.setConstraints(medName, 0, 2);
                                GridPane.setConstraints(medQuantity, 0, 3);
                                GridPane.setConstraints(medPrice, 0, 4);
                                GridPane.setConstraints(medManufacturer, 0, 5);
                                gridPane.getChildren().addAll(medName, medQuantity, medPrice, medManufacturer);
                            }


                        } catch (SQLException e) {
                            System.out.println(e.toString());
                        }
                    }
                });
                Scene scene = new Scene(gridPane, 300, 300);

                Stage s1 = new Stage();
                s1.setTitle("Get a medicine");
                s1.setScene(scene);
                s1.show();

            }
        });

        showAllMedicine.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                TableView<meds> table;

                TableColumn<meds, String> id = new TableColumn<>("ID");
                TableColumn<meds, String> name = new TableColumn<>("Name");
                TableColumn<meds, String> price = new TableColumn<>("Price");
                TableColumn<meds, String> quantity = new TableColumn<>("Quantity");
                TableColumn<meds, String> manufacturer = new TableColumn<>("Manufacturer");


                id.setMinWidth(200);
                name.setMinWidth(200);
                price.setMinWidth(200);
                quantity.setMinWidth(200);
                manufacturer.setMinWidth(200);

                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                price.setCellValueFactory(new PropertyValueFactory<>("price"));
                quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

                ObservableList<meds> med = FXCollections.observableArrayList();

                try {
                    Connection conn = DriverManager.getConnection(host, user, pass);
                    String query = "SELECT * FROM  medicine";
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        System.out.println(rs.getString("idmedicine"));
                        med.add(new meds(rs.getString("idmedicine"), rs.getString("name"), rs.getString("price"), rs.getString("quantity"), rs.getString("manufacturer")));
                    }
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
                table = new TableView<>();
                table.setItems(med);
                table.getColumns().addAll(id, name, price, quantity, manufacturer);


                VBox vbox = new VBox();
                vbox.getChildren().addAll(table);

                Stage s1 = new Stage();

                Scene scene = new Scene(vbox);
                s1.setScene(scene);
                s1.setTitle("ALL MEDICINES");

                s1.show();
            }
        });


        HBox hb = new HBox(insertMedicine, removeMedicine, getMedicine, showAllMedicine);
        hb.setSpacing(10);
        hb.setPadding(new Insets(20));

        FlowPane root = new FlowPane();
        root.getChildren().add(hb);
        root.getChildren().add(lb1);


        primaryStage.setTitle("Java Programming DA2");
        primaryStage.setScene(new Scene(root, 550, 150));
        primaryStage.show();
    }
}
