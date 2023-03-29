/**
 * Classe permettant la gestion de la page de connexion
 */

package controllers;

import DAO.DAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import singleton.PasswordHash;
import DAO.DAOFactory;
import java.io.IOException;

public class LoginController {
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Label signupLabel;

    /**
     * Méthode permettant de se connecter dans l'application après pleins de vérification
     */
    public void loginButtonOnAction() {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            DAO<User> userDAO = DAOFactory.getUserDAO();
            User user = userDAO.findByString(usernameTextField.getText());
            if (user != null) {
                String password = PasswordHash.getSecurePassword(passwordPasswordField.getText());
                if (password.equals(user.getPassword())) {
                    goToHomePage();
                } else {
                    loginMessageLabel.setText("Mot de passe et/ou nom d'utilisateur incorrect.");
                }
            } else {
                loginMessageLabel.setText("Mot de passe et/ou nom d'utilisateur incorrect.");
            }
        } else {
            loginMessageLabel.setText("Veuillez entrer un nom d'utilisateur \nou un mot de passe valide.");
        }
    }

    /**
     * Méthode permettant d'aller vers la page d'accueil
     */
    private void goToHomePage(){
        try{
            Stage stage = (Stage) signupLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/home.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'aller vers la page d'inscription
     */
    public void signupLabelOnMouseClicked() {
        try{
            Stage stage = (Stage) signupLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/signup.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
