package gui;

import java.io.IOException;

import gui.util.Alerts;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EntryViewController {
	
	@FXML
	private Button btnBank;
	
	@FXML
	private Button btnEdit;
	
	@FXML
	private Button btnQuit;
	
	@FXML
	private void onBtnBankAction() {
		loadView("/gui/MainControlView.fxml");
	}
	
	@FXML
	private void onBtnEditAction() {
		loadView("/gui/View.fxml");
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
	
	@FXML
	private void onBtnQuitAction() {
		Platform.exit();
		System.exit(0);
	}
}
