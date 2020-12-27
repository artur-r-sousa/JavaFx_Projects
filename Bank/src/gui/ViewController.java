package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class ViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemAbout;
	@FXML
	private MenuItem menuItemNewUser;
	@FXML
	private Button btnSearch;
	@FXML
	private TextField txtFieldSearch;
	
	@FXML
	private TextField txtFieldId;
	@FXML
	private TextField txtFieldName;
	@FXML
	private TextField txtFieldAccBal;
	@FXML
	private TextField txtFieldEmail;
	@FXML
	private TextField txtFieldCCV;
	@FXML
	private TextField txtFieldCountry;
	@FXML
	private TextField txtFieldCreatedIn;
	@FXML
	private Label lblWelcome;	
	@FXML
	private Label lblResult;
	@FXML
	private TextField txtFieldDeposit;
	@FXML
	private TextField txtFieldWithdraw;
	@FXML
	private Button btnconfirmDepOrWit;
	@FXML
	private Label lblDeposit;
	@FXML
	private Label lblWithdraw;
	@FXML
	private Label lblDepOrWitConfirm;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnDelete;
	
	@FXML
	private void onAboutAction()  {
		loadView("/gui/About.fxml");
	}
	
	@FXML
	private void onNewUSerAction()  {
		loadView("/gui/CreateUserView.fxml");
	}
	
	@FXML
	private void onDeleteAction() { 
		try {
			UserDao userDao = DaoFactory.createUserDao();
			userDao.deleteById(Integer.parseInt(txtFieldSearch.getText()));
			Alerts.showAlert("Success", null, "User removed from DB", AlertType.INFORMATION);
		} catch(NullPointerException e) {
			Alerts.showAlert("Information", null, "User not in The DB", AlertType.INFORMATION);
		}
		
	}
	
	@FXML
	private void onUpdateAction() {
		UserDao userDao = DaoFactory.createUserDao();
		User obj = userDao.findById(Integer.parseInt(txtFieldSearch.getText()));
		obj.setName(txtFieldName.getText());
		obj.setEmail(txtFieldEmail.getText());
		obj.setCountry(txtFieldCountry.getText());		
		Alerts.showAlert("Confirmation", null, "Please Confirm Alteration", AlertType.CONFIRMATION);
		userDao.update(obj);
	}
	
	@FXML 
	private void onConfirmDepOrWitAction () {
		UserDao userDao = DaoFactory.createUserDao();
		if (!txtFieldWithdraw.getText().equals("") && txtFieldDeposit.getText().equals("")) {
			userDao.withdraw(Double.parseDouble(txtFieldWithdraw.getText()), userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getCreditCard());
			lblDepOrWitConfirm.setText("Withdraw SuccessFul");
		} else if (!txtFieldDeposit.getText().equals("") && txtFieldWithdraw.getText().equals("")) {
			userDao.deposit(Double.parseDouble(txtFieldDeposit.getText()), userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getCreditCard());
			lblDepOrWitConfirm.setText("Deposit SuccessFul");
		} else if (!txtFieldWithdraw.getText().equals("") && !txtFieldDeposit.getText().equals("")) {
			lblDepOrWitConfirm.setText("Only one operation allowed at a time");
		}
		
		txtFieldAccBal.setText(String.valueOf(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getAccountBalance()));
	}
	
	@FXML
	public void onSearchAction() {
		
		try {
			UserDao userDao = DaoFactory.createUserDao();
			txtFieldId.setText(String.valueOf(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getId()));
			txtFieldName.setText(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getName());
			txtFieldAccBal.setText(String.valueOf(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getAccountBalance()));
			txtFieldEmail.setText(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getEmail());
			txtFieldCCV.setText(String.valueOf(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getCreditCard()));
			txtFieldCreatedIn.setText(String.valueOf(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getCreatedIn()));	
			txtFieldCountry.setText(userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getCountry());
			lblWelcome.setText("Hello " + userDao.findById(Integer.parseInt(txtFieldSearch.getText())).getName() + ", which operation would you like to do today?");
			btnconfirmDepOrWit.setOpacity(1);
			lblWithdraw.setOpacity(1);
			lblDeposit.setOpacity(1);
			txtFieldWithdraw.setOpacity(1);
			txtFieldDeposit.setOpacity(1);
			
			
		} catch (NullPointerException e) {
			Alerts.showAlert("User not Found", null, "User does not exist on the DB", AlertType.CONFIRMATION);
			txtFieldId.setText("");
			
		}
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Constraints.setTextFieldInteger(txtFieldSearch);

	}
	
	private void loadView(String absoluteName) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Parent root1 = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();		
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
			
		}
	}
	
}
