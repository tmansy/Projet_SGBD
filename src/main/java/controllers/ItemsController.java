/**
 * Classe permettant la gestion de la page des articles
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
import model.Item;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {
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
    private TableView<Item> itemsTable;
    @FXML
    private TableColumn<Item, String> itemId;
    @FXML
    private TableColumn<Item, String> name;
    @FXML
    private TableColumn<Item, String> brand;
    @FXML
    private TableColumn<Item, Float> price;
    @FXML
    private TableColumn<Item, Integer> stock;
    @FXML
    private TextField txt_itemId;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_brand;
    @FXML
    private TextField txt_price;
    @FXML
    private TextField txt_stock;
    @FXML
    private Label messageLabel;

    /**
     * Méthode permettant de load différents composants lors du premier chargement de l'écran
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){ loadData(); }

    /**
     * Méthode permettant de load la tableview itemsTable
     */
    private void loadData(){
        itemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        itemsTable.setItems(getItems());
    }

    /**
     * Méthode permettant d'avoir tous les articles de l'application
     */
    public static ObservableList<Item> getItems(){
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        ArrayList<Item> list = (ArrayList<Item>) itemDAO.getAll();
        ObservableList<Item> itemsObservableList = FXCollections.observableArrayList();
        itemsObservableList.addAll(list);
        return itemsObservableList;
    }

    /**
     * Méthode permettant de remplir les textfields les données lorsque l'on clique sur un article de la tableview
     */
    public void userTableOnMouseClicked (MouseEvent event) {
        int index = itemsTable.getSelectionModel().getSelectedIndex();
        if(index == previousIndex) {
            itemsTable.getSelectionModel().clearSelection();
            previousIndex = -1;
            emptyTextField();
        }
        else {
            previousIndex = index;
            txt_itemId.setText(String.valueOf(itemId.getCellData(index)));
            txt_name.setText(name.getCellData(index));
            txt_brand.setText(brand.getCellData(index));
            txt_price.setText(String.valueOf(price.getCellData(index)));
            txt_stock.setText(String.valueOf(stock.getCellData(index)));
        }
        messageLabel.setText("");
    }

    /**
     * Méthode permettant d'éditer un article
     */
    public void editOnAction(){
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        if(txt_name.getText().isEmpty() || txt_brand.getText().isEmpty() || txt_price.getText().isEmpty() || txt_stock.getText().isEmpty()){
            messageLabel.setText("ERREUR, veuillez remplir tous les champs.");
        }
        else {
            Integer itemId = Integer.parseInt(txt_itemId.getText());
            String name = txt_name.getText();
            String brand = txt_brand.getText();
            Float price = Float.parseFloat(txt_price.getText());
            Integer stock = Integer.parseInt(txt_stock.getText());

            itemDAO.update(new Item(itemId, name, brand, price, stock));
            emptyTextField();
            messageLabel.setText("");
            loadData();
            itemsTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant d'ajouter un article
     */
    public void addOnAction(){
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        if(txt_name.getText().isEmpty() || txt_brand.getText().isEmpty() || txt_price.getText().isEmpty() || txt_stock.getText().isEmpty()){
            messageLabel.setText("ERREUR, veuillez remplir tous les champs.");
        }
        else {
            String name = txt_name.getText();
            String brand = txt_brand.getText();
            Float price = Float.parseFloat(txt_price.getText());
            Integer stock = Integer.parseInt(txt_stock.getText());

            itemDAO.create(new Item(name, brand, price, stock));
            emptyTextField();
            messageLabel.setText("");
            loadData();
            itemsTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant de supprimer un article
     */
    public void deleteOnAction() {
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        Integer itemId = Integer.parseInt(txt_itemId.getText());
        String name = txt_name.getText();
        String brand = txt_brand.getText();
        Float price = Float.parseFloat(txt_price.getText());
        Integer stock = Integer.parseInt(txt_stock.getText());

        itemDAO.delete(new Item(itemId, name, brand, price, stock));
        emptyTextField();
        loadData();
        itemsTable.getSelectionModel().clearSelection();
    }

    /**
     * Méthode permettant de vider les textfields
     */
    public void emptyTextField() {
        txt_itemId.setText("");
        txt_name.setText("");
        txt_brand.setText("");
        txt_price.setText("");
        txt_stock.setText("");
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
