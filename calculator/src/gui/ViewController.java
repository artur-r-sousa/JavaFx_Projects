package gui;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ViewController implements Initializable{
	
	@FXML
	private Button btnZero;
	@FXML
	private Button btnOne;
	@FXML
	private Button btnTwo;
	@FXML
	private Button btnThree;
	@FXML
	private Button btnFour;
	@FXML
	private Button btnFive;
	@FXML
	private Button btnSix;
	@FXML
	private Button btnSeven;
	@FXML
	private Button btnEight;
	@FXML
	private Button btnNine;
	@FXML
	private TextField screen;
	@FXML
	private Label lblFirstEntry;
	@FXML
	private Button btnSum;
	@FXML
	private Button btnMinus;
	@FXML
	private Button btnMulti;
	@FXML
	private Button btnDivide;
	@FXML
	private Button btnDot;
	@FXML
	private Button btnSubTotal;
	@FXML
	private Button btnTotal;
	@FXML
	private Button btnClear;
	@FXML
	private Button btnClearAll;
	
	
	
	
	private void setScreenText(String txt) {
		screen.setText(screen.getText() + txt);
	}

	@FXML
	private void onBtnOneAction() {
		setScreenText("1");
	}
	
	@FXML
	private void onBtnTwoAction() {
		setScreenText("2");
	}
	
	@FXML
	private void onBtnThreeAction() {
		setScreenText("3");
	}
	
	@FXML
	private void onBtnFourAction() {
		setScreenText("4");
	}
	
	@FXML
	private void onBtnFiveAction() {
		setScreenText("5");
	}
	

	@FXML
	private void onBtnSixAction() {
		setScreenText("6");
	}
	
	@FXML
	private void onBtnSevenAction() {
		setScreenText("7");
	}
	
	@FXML
	private void onBtnEigthAction() {
		setScreenText("8");
	}
	
	@FXML
	private void onBtnNineAction() {
		setScreenText("9");
	}
	
	@FXML
	private void onBtnZeroAction() {
		setScreenText("0");
	}
	
	@FXML
	private void onBtnDotAction() {
		setScreenText(".");
	}
	
	@FXML 
	private void onSumAction() {
		if (screen.getText().length() > 0) {
			Double screenEntry = Double.parseDouble(screen.getText());
			lblFirstEntry.setText(String.valueOf(screenEntry) + "+");
			screen.setText("");
		} else if (lblFirstEntry.getText().length() > 0) {
			lblFirstEntry.setText(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1) + "+");
		}
	}
	@FXML 
	private void onMultiAction() {
		if (screen.getText().length() > 0) {
			Double screenEntry = Double.parseDouble(screen.getText());
			lblFirstEntry.setText(String.valueOf(screenEntry) + "*");
			screen.setText("");
		} else if (lblFirstEntry.getText().length() > 0) {
			lblFirstEntry.setText(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1) + "*");
		}
	}
	
	@FXML 
	private void onDivideAction() {
		if (screen.getText().length() > 0) {
			Double screenEntry = Double.parseDouble(screen.getText());
			lblFirstEntry.setText(String.valueOf(screenEntry) + "/");
			screen.setText("");
		} else if (lblFirstEntry.getText().length() > 0) {
			lblFirstEntry.setText(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1) + "/");
		}
	}
	
	@FXML 
	private void onMinusAction() {
		if (screen.getText().length() > 0) {
			Double screenEntry = Double.parseDouble(screen.getText());
			lblFirstEntry.setText(String.valueOf(screenEntry) + "-");
			screen.setText("");
		} else if (lblFirstEntry.getText().length() > 0) {
			lblFirstEntry.setText(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1) + "-");
		}
		
	}
	
	@FXML 
	private void clearScreen() {
		screen.setText("");
	}
	
	@FXML 
	private void clearLabel() {
		lblFirstEntry.setText("");
	}
	
	@FXML 
	private void clearAll() {
		screen.setText("");
		lblFirstEntry.setText("");
	}
	
	@FXML
	private void onSubTotalAction () {
		Locale.setDefault(Locale.US);
		if (lblFirstEntry.getText().length() > 0) {
			double first = 0;
			double second = 0;
			switch(lblFirstEntry.getText().charAt(lblFirstEntry.getText().length() - 1)) {
			case('+'):
				first = Double.parseDouble(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1));
				second = Double.parseDouble(screen.getText());
				screen.setText(String.valueOf(first + second));
				break;
			case('-'):
				first = Double.parseDouble(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1));
				second = Double.parseDouble(screen.getText());
				screen.setText(String.valueOf(first - second));
				break;
			case('*'):
				first = Double.parseDouble(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1));
				second = Double.parseDouble(screen.getText());
				screen.setText(String.valueOf(first * second));
				break;
			case('/'):
				first = Double.parseDouble(lblFirstEntry.getText().substring(0, lblFirstEntry.getText().length() - 1));
				second = Double.parseDouble(screen.getText());
				screen.setText(String.valueOf(first / second));
				break;
			} 
			
		}	
	}
	
	@FXML
	private void onTotalAction() {
		onSubTotalAction();
		clearLabel();
	}
	
	@FXML
	private void onCalcAction() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Constraints.setTextFieldDouble(screen);
		
	}
	
}
