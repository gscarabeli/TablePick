package br.edu.fesa.tablepick;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("first_page"), 400, 680);
        stage.setTitle("TablePick");
        //stage.getIcons().add("")
        stage.setScene(scene);
        stage.show();
        
        iniciarPainel();
    }
    
    private void iniciarPainel() throws IOException {
        Stage painelStage = new Stage();
        Parent painelRoot = loadFXML("panel");
        Scene painelScene = new Scene(painelRoot);
        painelStage.setTitle("Painel de Senhas");
        painelStage.setScene(painelScene);
        painelStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}