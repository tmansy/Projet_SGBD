/**
 * Classe permettant la gestion de la page des clients
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
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    private int previousIndex = -1;
    @FXML
    private Label disconnectLabel;
    @FXML
    private Label homeLabel;
    @FXML
    private Label usersLabel;
    @FXML
    private Label customersLabel;
    @FXML
    private Label itemsLabel;
    @FXML
    private Label billsLabel;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> customerId;
    @FXML
    private TableColumn<Customer, String> lastname;
    @FXML
    private TableColumn<Customer, String> firstname;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Customer, String> phoneNumber;
    @FXML
    private TextField txt_customerId;
    @FXML
    private TextField txt_lastname;
    @FXML
    private TextField txt_firstname;
    @FXML
    private TextField txt_address;
    @FXML
    private TextField txt_phoneNumber;
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
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customersTable.setItems(getCustomers());
    }

    /**
     * Méthode permettant d'avoir tous les clients de l'application
     */
    public static ObservableList<Customer> getCustomers(){
        DAO<Customer> customerDAO = DAOFactory.getCustomerDAO();
        ArrayList<Customer> list = (ArrayList<Customer>) customerDAO.getAll();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        customerObservableList.addAll(list);
        return customerObservableList;
    }

    /**
     * Méthode permettant de remplir les textfields les données lorsque l'on clique sur un client de la tableview
     */
    public void userTableOnMouseClicked (MouseEvent event) {
        int index = customersTable.getSelectionModel().getSelectedIndex();
        if(index == previousIndex) {
            customersTable.getSelectionModel().clearSelection();
            previousIndex = -1;
            emptyTextField();
        }
        else {
            previousIndex = index;
            txt_customerId.setText(String.valueOf(customerId.getCellData(index)));
            txt_firstname.setText(firstname.getCellData(index));
            txt_lastname.setText(lastname.getCellData(index));
            txt_address.setText(address.getCellData(index));
            txt_phoneNumber.setText(phoneNumber.getCellData(index));
        }
        messageLabel.setText("");
    }

    /**
     * Méthode permettant d'éditer un client
     */
    public void editOnAction(){
        DAO<Customer> customerDAO = DAOFactory.getCustomerDAO();
        if(txt_firstname.getText().isEmpty() || txt_lastname.getText().isEmpty() || txt_address.getText().isEmpty() || txt_phoneNumber.getText().isEmpty()){
            messageLabel.setText("ERREUR, veuillez remplir tous les champs.");
        }
        else {
            Integer customerId = Integer.parseInt(txt_customerId.getText());
            String firstname = txt_firstname.getText();
            String lastname = txt_lastname.getText();
            String address = txt_address.getText();
            String phoneNumber = txt_phoneNumber.getText();

            customerDAO.update(new Customer(customerId, firstname, lastname, address, phoneNumber));
            emptyTextField();
            messageLabel.setText("");
            loadData();
            customersTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant d'ajouter un client
     */
    public void addOnAction(){
        DAO<Customer> customerDAO = DAOFactory.getCustomerDAO();
        if(txt_firstname.getText().isEmpty() || txt_lastname.getText().isEmpty() || txt_address.getText().isEmpty() || txt_phoneNumber.getText().isEmpty()) {
            messageLabel.setText("ERREUR, veuillez remplir tous les champs.");
        }
        else {
            String firstname = txt_firstname.getText();
            String lastname = txt_lastname.getText();
            String address = txt_address.getText();
            String phoneNumber = txt_phoneNumber.getText();

            customerDAO.create(new Customer(firstname, lastname, address, phoneNumber));
            emptyTextField();
            messageLabel.setText("");
            loadData();
            customersTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant de supprimer un client
     */
    public void deleteOnAction() {
        DAO<Customer> customerDAO = DAOFactory.getCustomerDAO();
        Integer customerId = Integer.parseInt(txt_customerId.getText());
        String firstname = txt_firstname.getText();
        String lastname = txt_lastname.getText();
        String address = txt_address.getText();
        String phoneNumber = txt_phoneNumber.getText();

        customerDAO.delete(new Customer(customerId, firstname, lastname, address, phoneNumber));
        emptyTextField();
        loadData();
        customersTable.getSelectionModel().clearSelection();
    }

    /**
     * Méthode permettant de vider les textfields
     */
    public void emptyTextField() {
        txt_customerId.setText("");
        txt_firstname.setText("");
        txt_lastname.setText("");
        txt_address.setText("");
        txt_phoneNumber.setText("");
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
