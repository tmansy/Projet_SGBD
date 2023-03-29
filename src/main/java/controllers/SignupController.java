/**
 * Classe permettant la gestion de la page des inscriptions
 */

package controllers;

import DAO.DAO;
import DAO.DAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import java.io.IOException;
import singleton.Regex;

public class SignupController {
    @FXML
    private Label loginLabel;
    @FXML
    private Label signupMessageLabel;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField passwordConfirmationPasswordField;
    @FXML
    private TextField emailTextField;

    /**
     * Méthode permettant de s'inscrire dans l'application après pleins de vérification
     */
    public void signupButtonOnAction(){
        if(!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank() && !passwordConfirmationPasswordField.getText().isBlank() && !emailTextField.getText().isBlank()){
            if(passwordPasswordField.getText().equals(passwordConfirmationPasswordField.getText())){
                if(Regex.isValidUsername(usernameTextField.getText())){
                    DAO<User> userDAO = DAOFactory.getUserDAO();
                    if(userDAO.findByString(usernameTextField.getText()) == null){
                        if(Regex.isValidPassword(passwordPasswordField.getText())){
                            if(Regex.isValidEmail(emailTextField.getText())){
                                if(userDAO.findByString(emailTextField.getText()) == null){
                                    User user = new User(null, firstnameTextField.getText(), lastnameTextField.getText(), usernameTextField.getText(), passwordPasswordField.getText(), emailTextField.getText());
                                    userDAO.create(user);
                                    goToHomePage();
                                }
                                else{
                                    signupMessageLabel.setText("Cette adresse mail est déjà utilisée.");
                                }
                            }
                            else{
                                signupMessageLabel.setText("Veuillez entrer une adresse mail valide.");
                            }
                        }
                        else{
                            signupMessageLabel.setText("Le mot de passe doit contenir\n au moins une majuscule et un chiffre.");
                        }
                    }
                    else{
                        signupMessageLabel.setText("Ce nom d'utilisateur existe déjà.");
                    }
                }
                else{
                    signupMessageLabel.setText("Veuillez entrez un nom d'utilisateur valide.");
                }
            }
            else{
                signupMessageLabel.setText("Les mots de passes ne sont pas identiques.");
            }
        }
        else{
            signupMessageLabel.setText("Veuillez remplir tous les champs.");
        }
    }

    /**
     * Méthode permettant d'aller vers la page d'accueil
     */
    private void goToHomePage(){
        try{
            Stage stage = (Stage) loginLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/home.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'aller vers la page de connexion
     */
    public void loginLabelOnMouseClicked() {
        try{
            Stage stage = (Stage) loginLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
