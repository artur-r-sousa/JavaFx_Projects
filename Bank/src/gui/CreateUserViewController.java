package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.joda.time.LocalDate;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;

public class CreateUserViewController implements Initializable {
	@FXML
	private TextField txtNName;
	@FXML
	private TextField txtNAccBal;
	@FXML
	private TextField txtNEmail;
	@FXML
	private TextField txtNCCV;
	@FXML
	private TextField txtNCountry;
	@FXML
	private TextField txtNCreatedIn;
	@FXML
	private ComboBox<String> cbCountry;
	@FXML
	private LocalDate localDate;
	@FXML
	private Button btConfirm;
	
	private void clearScreen() {
		txtNName.setText("");
		txtNAccBal.setText("");
		txtNEmail.setText("");
		txtNCCV.setText("");
		txtNCreatedIn.setText("");
	}
	
	@FXML
	private void onConfirmAction() {
		localDate = new LocalDate();
		UserDao userDao = DaoFactory.createUserDao();		
		User nUser = new User(null, txtNName.getText(), Double.parseDouble(txtNAccBal.getText()), txtNEmail.getText(), Long.parseLong(txtNCCV.getText()), cbCountry.getValue(), localDate.toDate());
		userDao.insert(nUser);
		nUser.setId(userDao.findByCCV(Long.parseLong(txtNCCV.getText())).getId());
		Alerts.showAlert("Confirmation",null,  "New User registered with Id: " + nUser.getId(), AlertType.CONFIRMATION);
		clearScreen();

	}
	
	private Long generateCCV() {
		long lowerLimit = 5256000000000001L;
		long upperLimit = 5256999999999999L;
		Random r = new Random();
		long number = lowerLimit + ((long)(r.nextDouble()*(upperLimit-lowerLimit)));
		return number;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<String> obsList;		
		List<String> list = new ArrayList<>();
		list.add("Argentina");
		list.add("Portugal");
		list.add("New Zealand");
		list.add("United Kingdom");
		list.add("Canada");
		obsList = FXCollections.observableArrayList(list);
		cbCountry.setItems(obsList);
		txtNCreatedIn.setText(String.valueOf(new LocalDate()));
		txtNCCV.setText(String.valueOf(generateCCV()));
		Constraints.setTextFieldDouble(txtNAccBal);
		
	}
}
