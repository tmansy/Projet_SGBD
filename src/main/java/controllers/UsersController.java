/**
 * Classe permettant la gestion de la page des utilisateurs
 */

package controllers;

import DAO.DAO;
import DAO.DAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import singleton.Regex;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    private int previousIndex = -1;
    @FXML
    private Label disconnectLabel;
    @FXML
    private Label homeLabel;
    @FXML
    private Label customersLabel;
    @FXML
    private Label usersLabel;
    @FXML
    private Label itemsLabel;
    @FXML
    private Label billsLabel;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> idUser;
    @FXML
    private TableColumn<User, String> lastname;
    @FXML
    private TableColumn<User, String> firstname;
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TextField txt_idUser;
    @FXML
    private TextField txt_lastname;
    @FXML
    private TextField txt_firstname;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_password;
    @FXML
    private Label messageLabel;

    /**
     * Méthode permettant de load différents composants lors du premier chargement de l'écran
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){ loadData(); }

    /**
     * Méthode permettant de load la tableview userTable
     */
    private void loadData(){
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTable.setItems(getUsers());
    }

    /**
     * Méthode permettant d'avoir tous les utilisateurs de l'application
     */
    public static ObservableList<User> getUsers(){
        DAO<User> userDAO = DAOFactory.getUserDAO();
        ArrayList<User> list = (ArrayList<User>) userDAO.getAll();
        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        userObservableList.addAll(list);
        return userObservableList;
    }

    /**
     * Méthode permettant de remplir les textfields les données lorsque l'on clique sur un utilisateur de la tableview
     */
    public void userTableOnMouseClicked (MouseEvent event) {
        int index = userTable.getSelectionModel().getSelectedIndex();
        if(index == previousIndex) {
            userTable.getSelectionModel().clearSelection();
            previousIndex = -1;
            emptyTextField();
        }
        else {
            previousIndex = index;
            txt_idUser.setText(String.valueOf(idUser.getCellData(index)));
            txt_firstname.setText(firstname.getCellData(index));
            txt_lastname.setText(lastname.getCellData(index));
            txt_username.setText(username.getCellData(index));
            txt_email.setText(email.getCellData(index));
        }
        messageLabel.setText("");
    }

    /**
     * Méthode permettant d'éditer un utilisateur
     */
    public void editOnAction(){
        DAO<User> userDAO = DAOFactory.getUserDAO();
        if(txt_firstname.getText().isEmpty() || txt_lastname.getText().isEmpty() || txt_username.getText().isEmpty() || txt_email.getText().isEmpty() || txt_password.getText().isEmpty()){
            messageLabel.setText("ERREUR, veuillez remplir tous les champs.");
        }
        else {
            Integer idUser = Integer.parseInt(txt_idUser.getText());
            String firstname = txt_firstname.getText();
            String lastname = txt_lastname.getText();
            String username = txt_username.getText();
            String email = txt_email.getText();
            String password = txt_password.getText();

            if(Regex.isValidUsername(username)) {
                if(Regex.isValidEmail(email)) {
                    if(Regex.isValidPassword(password)) {
                        userDAO.update(new User(idUser, firstname, lastname, username, password, email));
                        emptyTextField();
                        messageLabel.setText("");
                        loadData();
                        userTable.getSelectionModel().clearSelection();
                    }
                    else {
                        messageLabel.setText("Le mot de passe doit contenir\n au moins une majuscule et un chiffre.");
                    }
                }
                else {
                    messageLabel.setText("Cette adresse mail est déjà utilisée.");
                }
            }
            else {
                messageLabel.setText("Veuillez entrez un nom d'utilisateur valide.");
            }
        }
    }

    /**
     * Méthode permettant d'ajouter un utilisateur
     */
    public void addOnAction(){
        DAO<User> userDAO = DAOFactory.getUserDAO();
        if(txt_firstname.getText().isEmpty() || txt_lastname.getText().isEmpty() || txt_username.getText().isEmpty() || txt_email.getText().isEmpty() || txt_password.getText().isEmpty()) {
            messageLabel.setText("ERREUR, veuillez remplir tous les champs.");
        }
        else {
            String firstname = txt_firstname.getText();
            String lastname = txt_lastname.getText();
            String username = txt_username.getText();
            String email = txt_email.getText();
            String password = txt_password.getText();

            userDAO.create(new User(firstname, lastname, username, password, email));
            emptyTextField();
            messageLabel.setText("");
            loadData();
            userTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant de supprimer un utilisateur
     */
    public void deleteOnAction() {
        DAO<User> userDAO = DAOFactory.getUserDAO();
        Integer idUser = Integer.parseInt(txt_idUser.getText());
        String firstname = txt_firstname.getText();
        String lastname = txt_lastname.getText();
        String username = txt_username.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();

        userDAO.delete(new User(idUser, firstname, lastname, username, email, password));
        emptyTextField();
        loadData();
        userTable.getSelectionModel().clearSelection();
    }

    /**
     * Méthode permettant de vider les textfields
     */
    public void emptyTextField() {
        txt_idUser.setText("");
        txt_firstname.setText("");
        txt_lastname.setText("");
        txt_username.setText("");
        txt_email.setText("");
        txt_password.setText("");
    }

    /**
     * Méthode permettant d'aller vers la page d'accueil
     */
    public void homeLabelOnMouseClicked(){
        try{
            Stage stage = (Stage) homeLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/home.fxml"));
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
    public void customersLabelOnMouseClicked(){
        try{
            Stage stage = (Stage) customersLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/customers.fxml"));
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
    public void usersLabelOnMouseClicked(){
        try{
            Stage stage = (Stage) usersLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/users.fxml"));
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
            Stage stage = (Stage) itemsLabel.getScene().getWindow();
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
            Stage stage = (Stage) billsLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/bills.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de se déconnecter et d'aller vers la page de connexion
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

}
