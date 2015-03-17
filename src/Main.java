import java.sql.Connection;

import session.MySQLConnection;
import session.SingletonSession;

public class Main {
	
	public static void main(String[] args) {
		//open session
		SingletonSession session = SingletonSession.getInstance();
		//open db connection
		Login login = new Login();
		
			
	}

}
