package hotel;


import java.sql.Connection;
import java.sql.DriverManager;

public class GetConnection {

	Connection connect;

	public Connection getConnection()
	{

		String url,user,pwd;
		url="jdbc:mysql://localhost:3306/hotelmanagementsystem?useSSL=false&allowPublicKeyRetrieval=true";
		user="root";
		pwd="12E2d786@2";

		try {

			 connect =DriverManager.getConnection(url, user, pwd);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return connect;

	}

}
