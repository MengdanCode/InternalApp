package org.IFInternal.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.IFInternal.security.CryptographicHelper;

public class loginAuthentication {
	public String authentication(String userName, String password) {
		System.out.println("IFInterna:loginAuthentication: started");
		CryptographicHelper cry = new CryptographicHelper();
		//String retrievedUserName = "";
		String retrievedPassword = "";
		String retrievedUserId = "";
		String status = "";
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/IFdatabase?zeroDateTimeBehavior=convertToNull", "root",
					"");
			PreparedStatement statement = con
					.prepareStatement("SELECT * FROM User WHERE userId = '"
							+ userName + "'");
			ResultSet result = statement.executeQuery();

			String retrievedUserName="";
			while (result.next()) {
				retrievedUserId = result.getString("userId");
				retrievedPassword = result.getString("pwd");
				retrievedUserName = result.getString("firstName")+" " +result.getString("lastName");
			}

			if (retrievedUserId.equals(userName)
					&& retrievedPassword.equals(cry.doMD5Hashing(password))) {
				status = "Success!" + retrievedUserName;
				System.out.println("loginAuthentication: successed!");
			}

			else {
				status = "IFInternal:Login fail!!!";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

}