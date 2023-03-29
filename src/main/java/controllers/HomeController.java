/**
 * Classe permettant la gestion de la page d'acceuil
 */

package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {
    @FXML
    private Label disconnectLabel;
    @FXML
    private Button memberButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button itemsButton;
    @FXML
    private Button billsButton;

    /**
     * Méthode permettant de se déconnecter et d'être renvoyé vers la page de connexion
     */
    public void disconnectLabelOnMouseClicked(){
        try{
            Stage stage = (Stage) disconnectLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'aller vers la page des utilisateurs
     */
    public void memberButtonOnAction() {
        try{
            Stage stage = (Stage) memberButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/users.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'aller vers la page des clients
     */
    public void customersButtonOnAction() {
        try{
            Stage stage = (Stage) customersButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/customers.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'aller vers la page des articles
     */
    public void itemsLabelOnMouseClicked(){
        try{
            Stage stage = (Stage) itemsButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/items.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'aller vers la page des factures
     */
    public void billsLabelOnMouseClicked(){
        try{
            Stage stage = (Stage) billsButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/bills.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
