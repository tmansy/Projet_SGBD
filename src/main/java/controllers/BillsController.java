/**
 * Classe permettant la gestion de la page des factures
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BillsController implements Initializable {
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
    private ComboBox<String> customersComboBox;
    @FXML
    private ComboBox<String> itemsComboBox;
    @FXML
    private TextField txt_quantityLeft;
    @FXML
    private TextField txt_quantity;
    @FXML
    private TextField txt_unitPrice;
    @FXML
    private TextField txt_subTotal;
    @FXML
    private TableView<Bill> billsTable;
    @FXML
    private TableColumn<Bill, Integer> billId;
    @FXML
    private TableView<CartItem> itemsTable;
    @FXML
    private TableColumn<CartItem, String> name;
    @FXML
    private TableColumn<CartItem, String> brand;
    @FXML
    private TableColumn<CartItem, String> quantity;
    @FXML
    private TableColumn<CartItem, String> price;
    @FXML
    private TableColumn<CartItem, Float> totalPrice;
    @FXML
    private Label messageLabel;

    /**
     * Méthode permettant de load différents composants lors du premier chargement de l'écran
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadBillsData();
        initializeCustomers();
        initializeItems();
    }

    /**
     * Méthode permettant de load la tableview itemsTable
     */
    private void loadCartItemDatas(){
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    /**
     * Méthode permettant de load la tableview billsTable
     */
    private void loadBillsData(){
        billId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        billsTable.setItems(getBills());
    }

    /**
     * Méthode permettant d'avoir toutes les factures de l'application
     */
    public ObservableList<Bill> getBills(){
        DAO<Bill> billDAO = DAOFactory.getBillDAO();
        ArrayList<Bill> list = (ArrayList<Bill>) billDAO.getAll();
        ObservableList<Bill> billsObservableList = FXCollections.observableArrayList();
        billsObservableList.addAll(list);
        return billsObservableList;
    }

    /**
     * Méthode permettant de remplir les textfields les données lorsque l'on clique sur un article de la tableview itemsTable
     */
    public void itemsTableOnMouseClicked (MouseEvent event) {
        int index = itemsTable.getSelectionModel().getSelectedIndex();
        if(index == previousIndex) {
            itemsTable.getSelectionModel().clearSelection();
            previousIndex = -1;
            emptyTextField();
        }
        else {
            previousIndex = index;
            itemsComboBox.setValue(String.valueOf(name.getCellData(index)));
            txt_quantity.setText(String.valueOf(quantity.getCellData(index)));
            txt_unitPrice.setText(String.valueOf(price.getCellData(index)));
        }
    }

    /**
     * Méthode permettant de remplir les textfields les données lorsque l'on clique sur une facture de la tableview billsTable
     */
    public void billsTableOnMouseClicked (MouseEvent event) {
        int index = billsTable.getSelectionModel().getSelectedIndex();
        DAO<Item> itemDAO = DAOFactory.getItemDAO();

        if(index == previousIndex) {
            billsTable.getSelectionModel().clearSelection();
            previousIndex = -1;
            customersComboBox.setDisable(false);
            customersComboBox.setValue(null);
            itemsTable.getItems().clear();
            emptyTextField();
        }
        else {
            previousIndex = index;
            Bill selectedBill = billsTable.getSelectionModel().getSelectedItem();
            int billId = selectedBill.getBillId();
            ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
            ArrayList<Item> itemsList = (ArrayList<Item>) itemDAO.getListById(billId);

            for(Item item : itemsList) {
                CartItem cartItem = new CartItem(item.getName(), item.getBrand(), item.getPrice(), item.getQuantity(), item.getLastname());
                cartItems.add(cartItem);
                customersComboBox.setValue(cartItem.getLastname());
            }
            itemsTable.setItems(cartItems);
            calculateSubTotal();
            loadCartItemDatas();
        }
        messageLabel.setText("");
    }

    /**
     * Méthode permettant d'initialiser les clients dans la comboBox customersComboBox
     */
    public void initializeCustomers(){
        DAO<Customer> customerDAO = DAOFactory.getCustomerDAO();
        ArrayList<Customer> customersList = (ArrayList<Customer>) customerDAO.getAll();

        for(Customer customer : customersList){
            String customerName = customer.getLastname();
            customersComboBox.getItems().add(customerName);
        }
    }

    /**
     * Méthode permettant d'initialiser les articles dans la comboBox itemssComboBox
     */
    public void initializeItems(){
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        ArrayList<Item> itemsList = (ArrayList<Item>) itemDAO.getAll();

        for(Item item : itemsList){
            String itemName = item.getName();
            itemsComboBox.getItems().add(itemName);
        }
    }

    /**
     * Méthode permettant de sélectionner un item dans la tableview itemsTable
     */
    public void onItemSelect(){
        String selectedItem = itemsComboBox.getSelectionModel().getSelectedItem();
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        Item item = itemDAO.findByString(selectedItem);

        if(item != null) {
            txt_quantityLeft.setText(String.valueOf(item.getStock()));
            txt_unitPrice.setText(String.valueOf(item.getPrice()));
            txt_quantity.setText("");
        }
    }

    /**
     * Méthode permettant de calculer le sous total de la facture
     */
    public void calculateSubTotal() {
        float subtotal = 0;
        ObservableList<CartItem> items = itemsTable.getItems();

        for(CartItem item : items) {
            subtotal += item.getTotalPrice();
        }

        DecimalFormat df = new DecimalFormat("#.00");
        String subTotalString = df.format(subtotal);
        subTotalString = subTotalString.replace(",", ".");
        txt_subTotal.setText(subTotalString + " €");
    }

    /**
     * Méthode permettant de calculer la quantité maximal d'un article
     */
    public boolean itemQuantity(String itemName, Integer quantity){
        int sum = 0;
        ObservableList<CartItem> items = itemsTable.getItems();
        for(CartItem item : items){
            if(item.getName().equals(itemName)){
                sum += item.getQuantity();
            }
        }
        sum += quantity;

        if(sum > Integer.parseInt(txt_quantityLeft.getText())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Méthode permettant d'ajouter un article dans la tableview itemsTable
     */
    public void addCartItemOnAction(){
        if(customersComboBox.getItems().isEmpty() || itemsComboBox.getItems().isEmpty() || txt_quantity.getText().isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs.");
        }
        else if (itemQuantity(itemsComboBox.getValue(), Integer.parseInt(txt_quantity.getText()))) {
            messageLabel.setText("La quantité restante de cet article est insuffisante.");
        }
        else {
            DAO<Item> itemDAO = DAOFactory.getItemDAO();
            String itemName = itemsComboBox.getValue();
            String brand = String.valueOf(itemDAO.findByString(itemName).getBrand());
            Float itemPrice = itemDAO.findByString(itemName).getPrice();
            Integer quantity = Integer.parseInt(txt_quantity.getText());

            CartItem cartItem = new CartItem(itemName, brand, itemPrice, quantity);
            itemsTable.getItems().add(cartItem);
            messageLabel.setText("");
            loadCartItemDatas();
            calculateSubTotal();
            emptyTextField();
            itemsTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant de supprimer un article dans la tableview itemsTable
     */
    public void deleteCartItemOnAction() {
        CartItem selectedItem = itemsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            itemsTable.getItems().remove(selectedItem);
            calculateSubTotal();
            emptyTextField();
            itemsTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant de modifier un article dans la tableview itemsTable
     */
    public void updateCartItemOnAction() {
        if(customersComboBox.getItems().isEmpty() || itemsComboBox.getItems().isEmpty() || txt_quantity.getText().isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs.");
        }
        else if (itemQuantity(itemsComboBox.getValue(), Integer.parseInt(txt_quantity.getText()))) {
            messageLabel.setText("La quantité restante de cet article est insuffisante.");
        }
        else {
            CartItem selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            String newItemName = itemsComboBox.getValue();
            Integer newQuantity = Integer.parseInt(txt_quantity.getText());
            selectedItem.setName(newItemName);
            selectedItem.setQuantity(newQuantity);

            itemsTable.refresh();
            calculateSubTotal();
            emptyTextField();
            itemsTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Méthode permettant de vider les textfields
     */
    public void emptyTextField(){
        customersComboBox.setDisable(true);
        itemsComboBox.setValue(null);
        txt_quantity.setText("");
        txt_quantityLeft.setText("");
        txt_unitPrice.setText("");
    }

    /**
     * Méthode permettant d'ajouter une facture dans la base de données
     */
    public void addBillOnAction() {
        String subTotal = txt_subTotal.getText().replace("€", "");
        Float totalPrice = Float.parseFloat(subTotal);
        DAO<Customer> customerDAO = DAOFactory.getCustomerDAO();
        int customerId = customerDAO.findByString(customersComboBox.getValue()).getCustomerId();
        DAO<Bill> billDAO = DAOFactory.getBillDAO();
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        billDAO.create(new Bill(totalPrice, customerId));

        ObservableList<CartItem> items = itemsTable.getItems();
        for(CartItem item : items){
            int itemQuantity = item.getQuantity();
            int billId = billDAO.getLastId();
            int itemId = itemDAO.findByString(item.getName()).getItemId();
            billDAO.create(new Bill(itemQuantity, billId, itemId));
        }

        itemsTable.getItems().clear();
        loadBillsData();
        emptyTextField();
        customersComboBox.setDisable(false);
        customersComboBox.setValue(null);
        messageLabel.setText("");
    }

    /**
     * Méthode permettant de modifier une facture dans la base de données
     */
    public void editBillOnAction() {
        String subTotal = txt_subTotal.getText().replace("€", "");
        Float totalPrice = Float.parseFloat(subTotal);
        DAO<Customer> customerDAO = DAOFactory.getCustomerDAO();
        int customerId = customerDAO.findByString(customersComboBox.getValue()).getCustomerId();
        Bill selectedBill = billsTable.getSelectionModel().getSelectedItem();
        DAO<Bill> billDAO = DAOFactory.getBillDAO();
        DAO<Item> itemDAO = DAOFactory.getItemDAO();
        ArrayList<Item> itemsList = (ArrayList<Item>) itemDAO.getListById(selectedBill.getBillId());
        billDAO.deleteAllById(selectedBill.getBillId());
        billDAO.update(new Bill(selectedBill.getBillId(), totalPrice, customerId));

        ObservableList<CartItem> cartItems = itemsTable.getItems();
        itemsList.clear();
        for(CartItem cartItem: cartItems) {
            int itemQuantity = cartItem.getQuantity();
            int billId = selectedBill.getBillId();
            int itemId = itemDAO.findByString(cartItem.getName()).getItemId();
            billDAO.create(new Bill(itemQuantity, billId, itemId));
        }

        itemsTable.getItems().clear();
        loadBillsData();
        emptyTextField();
        customersComboBox.setDisable(false);
        customersComboBox.setValue(null);
        messageLabel.setText("");
    }

    /**
     * Méthode permettant de supprimer une facture dans la base de données
     */
    public void deleteBillOnAction() {
        DAO<Bill> billsDAO = DAOFactory.getBillDAO();
        Bill selectedBill = billsTable.getSelectionModel().getSelectedItem();
        billsDAO.delete(selectedBill);
        emptyTextField();
        billsTable.getSelectionModel().clearSelection();
        loadBillsData();
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
