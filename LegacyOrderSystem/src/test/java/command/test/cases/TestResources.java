package command.test.cases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;

import com.sogeti.connectors.RepositoryConnector;

public class TestResources {

	private static final Connection connector = RepositoryConnector.getConnection();

	// insert queries for DB
	private static final String	INSERT_ORDER_TO_DB		= "INSERT INTO orders values(%d,'%s','%s','%s',%d,'%s',%d,'%s',%s);";
	private static final String	INSERT_DETAILS_TO_DB	= "INSERT INTO orders_details value(%d,%d,%d,%d,%d,'%s',%d,'%s',%d)";
	private static final String	INSERT_USER_TO_DB		= "INSERT INTO person values(%d,'%s','%s','%s','%s','%s','%s','%s')";

	// delete queries for DB
	private static final String	REMOVE_ORDER_FROM_DB	= "DELETE FROM orders WHERE orderId=%s";
	private static final String	REMOVE_DETAIL_FROM_DB	= "DELETE FROM orders_details WHERE orderId=%s";
	private static final String	REMOVE_USER_FROM_DB		= "DELETE FROM person WHERE id=%s";

	// common values for Insertion
	protected static final int		USER_ID				= -123456789;
	protected static final int		ORDER_ID			= -827736;
	private static final int		CREATED_STAFF_ID	= -123;
	private static final LocalDate	CREATED_DATE		= LocalDate.now();
	private static final int		UPDATED_STAFF_ID	= -123;
	private static final LocalDate	UPDATED_DATE		= LocalDate.now();

	// order values for insertion
	private static final LocalDate	DATE_ORDERED	= LocalDate.now();
	private static final LocalDate	DATE_RECIEVED	= LocalDate.now();
	private static final String		STATUS			= "SHIPPED";

	// details values for insertion
	private static final int	PRODUCT_ID	= -123;
	private static final int	QUANTITY	= 123;
	private static final int	UNIT_PRICE	= 123;

	// person values for insertion
	private static final String		FIRSTNAME		= "John";
	private static final String		LASTNAME		= "Dohnut";
	private static final String		EMAIL			= "test@test.test";
	private static final String		PASSWORD		= "secret";
	private static final LocalDate	DATE_OF_BIRTH	= LocalDate.of(1965, Month.DECEMBER, 9);

	public static void createOrderForTest() {

		Statement statement;

		try {
			statement = connector.createStatement();

			statement.executeUpdate(String.format(INSERT_ORDER_TO_DB, ORDER_ID, DATE_ORDERED, DATE_RECIEVED, STATUS,
					CREATED_STAFF_ID, CREATED_DATE, UPDATED_STAFF_ID, UPDATED_DATE, USER_ID));
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}

	}

	public static void cleanOrderTable() {

		Statement statement;

		try {
			statement = connector.createStatement();
			statement.execute(String.format(REMOVE_ORDER_FROM_DB, ORDER_ID));
		} catch (SQLException sqle) {

		}
	}

	public static void createDetailsForTest() {

		Statement statement;

		try {
			statement = connector.createStatement();

			statement.executeUpdate(String.format(INSERT_DETAILS_TO_DB, ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE,
					CREATED_STAFF_ID, CREATED_DATE, UPDATED_STAFF_ID, UPDATED_DATE, USER_ID));
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}

	}

	public static void cleanDetailsTable() {

		Statement statement;

		try {
			statement = connector.createStatement();
			statement.execute(String.format(REMOVE_DETAIL_FROM_DB, ORDER_ID));
		} catch (SQLException sqle) {

		}
	}

	public static void createUserForTest() {

		Statement statement;

		try {
			statement = connector.createStatement();

			statement.executeUpdate(String.format(INSERT_USER_TO_DB, USER_ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD,
					DATE_OF_BIRTH, CREATED_DATE, UPDATED_DATE));
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}

	}

	public static void cleanUserTable() {

		Statement statement;

		try {
			statement = connector.createStatement();
			statement.execute(String.format(REMOVE_USER_FROM_DB, USER_ID));
		} catch (SQLException sqle) {

		}
	}
}
