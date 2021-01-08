package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerFormController implements Initializable{
	
	private Seller entity;
	private SellerService selService;
	private DepartmentService depService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private Button btnSaveButton;
	@FXML
	private Button btnCancelButton;
	@FXML
	private Label lblErrorName;
	@FXML
	private Label lblErrorEmail;
	@FXML
	private Label lblErrorBirthDate;
	@FXML
	private Label lblErrorBaseSalary;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private DatePicker datePickerBirthDate;
	@FXML
	private TextField txtBaseSalary;
	@FXML
	private ComboBox<Department> comboBoxDepartment;
	
	private ObservableList<Department> obsList;
	
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void setServices(SellerService selService, DepartmentService depService) {
		this.selService = selService;
		this.depService = depService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		if (selService == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		try {
			entity = getFormData();
			selService.saveOrUpdate(entity);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
			
		}catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}catch(DbException e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), AlertType.ERROR);
		}
		
		
	}
	
	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	
	private Seller getFormData() {
		Seller obj = new Seller();

		ValidationException exception = new ValidationException("Validation error");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "Field can't be empty");
		}
		obj.setEmail(txtEmail.getText());
		
		if (datePickerBirthDate.getValue() == null) {
			exception.addError("birthDate", "Field can't be empty");
		}
		else {
			Instant instant = Instant.from(datePickerBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setBirthDate(Date.from(instant));
		}
		
		if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
			exception.addError("baseSalary", "Field can't be empty");
		}
		obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));
		
		obj.setDepartment(comboBoxDepartment.getValue());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();	
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 70);
		Utils.formatDatePicker(datePickerBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if (entity.getBirthDate() != null) {
			datePickerBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		if (entity.getDepartment() == null) {
			comboBoxDepartment.getSelectionModel().selectFirst();
		} else {
			comboBoxDepartment.setValue(entity.getDepartment());
		}
	}
	
	public void loadAssociatedObjects() {
		if(depService == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = depService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxDepartment.setItems(obsList);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		lblErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
		lblErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
		lblErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate") : ""));
		lblErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary") : ""));

		
		
		if(fields.contains("email")) {
			lblErrorEmail.setText(errors.get("email"));			
		}
		if(fields.contains("baseSalary")) {
			lblErrorBaseSalary.setText(errors.get("baseSalary"));			
		}
		if(fields.contains("birthDate")) {
			lblErrorBirthDate.setText(errors.get("birthDate"));			
		}
	}
	
	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};		
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}
}
