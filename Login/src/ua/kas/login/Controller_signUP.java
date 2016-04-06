package ua.kas.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Controller_signUP{

	@FXML TextField textLOGIN;
	@FXML TextField textEMAIL;
	@FXML TextField textNAME;
	@FXML TextField textAGE;
	@FXML PasswordField textPASSWORD;
	@FXML PasswordField textREPEAT_PASSWORD;
	@FXML Label password_correct;
	@FXML Label password_uncorrect;
	@FXML Label email_correct;
	@FXML Label email_uncorrect;
	@FXML ImageView imv_avatar;
	String avatar;
	
	public void browse_avatar(ActionEvent e){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
		fileChooser.addChoosableFileFilter(filter);
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			System.out.println(path);
			String ss = path.substring(path.indexOf("&"));
			avatar = path;
			imv_avatar.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/" + ss)));

		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("No Data");
		}
	}
	
	public void sign_up(ActionEvent e) throws SQLException, FileNotFoundException{
		Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/freemove", "root", "root");
		java.sql.PreparedStatement myStmt = myConn
				.prepareStatement("insert into users(user_name,password,email,city,language) values (?,?,?,?,?)");
		
		String city = "no";
		String language = "no";
		
		myStmt.setString(1, textLOGIN.getText());
		myStmt.setString(2, textPASSWORD.getText());	
		myStmt.setString(3, textEMAIL.getText());
		myStmt.setString(4, city);
		myStmt.setString(5, language);
		
		if(textPASSWORD.getText().equals(textREPEAT_PASSWORD.getText())){
			password_correct.setVisible(true);
			password_uncorrect.setVisible(false);
				
			myStmt.executeUpdate();
			
			try{
				java.sql.PreparedStatement S_name = myConn
						.prepareStatement("update users set first_name=? where user_name=?");
				S_name.setString(1, textNAME.getText());
				S_name.setString(2, textLOGIN.getText());
				S_name.executeUpdate();
			}catch(Exception ex){}
			
			try{
				java.sql.PreparedStatement S_age = myConn
						.prepareStatement("update users set age=? where user_name=?");
				S_age.setString(1, textAGE.getText());
				S_age.setString(2, textLOGIN.getText());
				S_age.executeUpdate();
			}catch(Exception ex){}
			
			try{
				java.sql.PreparedStatement S_avatar = myConn
						.prepareStatement("update users set avatar=? where user_name=?");
				InputStream is_avatar = new FileInputStream(new File(avatar));
				S_avatar.setBlob(1, is_avatar);
				S_avatar.setString(2, textLOGIN.getText());
				S_avatar.executeUpdate();
			}catch(Exception ex){}
			
			System.out.println("Complet!");
			
		}
		else{
			password_correct.setVisible(false);
			password_uncorrect.setVisible(true);
		}
	}
}
