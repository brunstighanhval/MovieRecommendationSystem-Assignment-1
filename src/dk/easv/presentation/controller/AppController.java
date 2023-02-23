package dk.easv.presentation.controller;

import dk.easv.entities.*;
import dk.easv.presentation.model.AppModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.List;



public class AppController implements Initializable {
    public TableView tableView;
    public TableColumn movies;
    public ImageView logo;
    public ImageView backGroundImage;
    @FXML
    private Label lblSimilarUsers, lblAverageNotSeen, lblAverageSeen;
    @FXML
    private TilePane tilePane1,tilePane2,tilePane3;

        @FXML
    private TextField lblLoggedInUser;

    @FXML
    private Button btnSignOut;

    private List<Movie> getTopAverageRatedMovies;

    private List<Movie> getTopAverageRatedMoviesUserDidNotSee;

    private List<TopMovie> getTopMoviesFromSimilarPeople;

    private AppModel model;

    private String[] liste={"Top Movies","New Movies","Top Series"," "};
    private ObservableList<String> stringObservableList;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i=0; i<liste.length; i++)
                    tableView.getItems().addAll(new MovieShowInList(liste[i]));

        movies.setCellValueFactory(new PropertyValueFactory<>("movieShowInListe"));

        Image logoImage;
        try {
        logoImage =  new Image(new FileInputStream(("Resources/Pictures/Logo.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        logo.setImage(logoImage);

        try {
            logoImage =  new Image(new FileInputStream(("Resources/Pictures/DarkGreyBackground.jpg")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        backGroundImage.setImage(logoImage);
        backGroundImage.setScaleX(700);
        backGroundImage.setScaleY(400);


    }

    public void setModel(AppModel model) throws FileNotFoundException {
        this.model = model;
        showUserName();
        getTopAverageRatedMovies=model.getTopAverageRatedMovies();
        getTopAverageRatedMoviesUserDidNotSee=model.getTopAverageRatedMoviesUserDidNotSee();
        getTopMoviesFromSimilarPeople=model.getTopMoviesFromSimilarPeople();
        setUpGribPanes("getTopAverageRatedMovies",0);
        setUpGribPanes("getTopAverageRatedMoviesUserDidNotSee",1);
        setUpGribPanes("getTopMoviesFromSimilarPeople",2);
    }


    public void setUpGribPanes(String listName, int tilePane) throws FileNotFoundException {


        Random r0=new Random();
        Random r1=new Random();
        Random r2=new Random();
        TilePane[] tilePanes={tilePane1,tilePane2,tilePane3};
        javafx.scene.control.Label movieTitle;
        String line;

        Image picture;

        for (int i = 0; i < 5; i++) {

            if (listName=="getTopAverageRatedMovies")

            line=getTopAverageRatedMovies.get(i).getTitle();

            else if (listName=="getTopAverageRatedMoviesUserDidNotSee")

                line=getTopAverageRatedMoviesUserDidNotSee.get(i).getTitle();

            else

                line=getTopMoviesFromSimilarPeople.get(i).getTitle();




            picture =  new Image(new FileInputStream(("Resources/Pictures/Flower"+r0.nextInt(13)+".jpg")));
            movieTitle = new javafx.scene.control.Label(line);
            movieTitle.setMaxWidth(150);
            movieTitle.setWrapText(true);
            ImageView imageView= new ImageView(picture);
            imageView.setFitWidth(150);
            imageView.setFitHeight(200);
            VBox vbox=new VBox();
            vbox.setMinSize(250,300);
            vbox.setMaxSize(250,300);
            vbox.getChildren().add(imageView);
            vbox.getChildren().add(movieTitle);
            tilePanes[tilePane].getChildren().add(vbox);

        }


    }






    public void handelSignOut(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to Sign Out");
        alert.setContentText("Return back to the Login page");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) btnSignOut.getScene().getWindow();
            stage.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    public void showUserName(){
        lblLoggedInUser.setText(model.getObsLoggedInUser().getName());

    }


}
