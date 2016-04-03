package ua.kas.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_login {

	@FXML TextField textLOGIN;
	@FXML PasswordField textPASSWORD;
	@FXML Label uncorrect;
	
	public void log_in(ActionEvent e) throws SQLException{
		
	Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/freemove", "root", "root");
		
		java.sql.PreparedStatement myStmt;
		myStmt = myConn.prepareStatement("select * from users where user_name = ? and password = ? ");
		myStmt.setString(1, textLOGIN.getText());
		myStmt.setString(2, textPASSWORD.getText());
		ResultSet result = myStmt.executeQuery();
			
		if(result.next()){
			System.out.println("Complete!");
		}
			
		else{
			uncorrect.setText("Uncorrect Login or Password!");
		}
	}
	
	public void sign_up(ActionEvent e) throws IOException{
		Scene sign_up_scene = new Scene(FXMLLoader.load(getClass().getResource("Sign up.fxml")));
		sign_up_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		app_stage.setScene(sign_up_scene);
		app_stage.show();
	}
}
