package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private void onMenuItemSellerAction() {
		System.out.println("teste seller");
	}
	
	@FXML
	private void onMenuItemDepartmentAction() {
		System.out.println("teste department");
	}
	
	@FXML
	private void onMenuItemAboutAction() {
		System.out.println("teste about");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		
	}
	
}
