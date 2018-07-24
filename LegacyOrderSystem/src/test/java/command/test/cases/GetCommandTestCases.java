package command.test.cases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.sogeti.command.Command;

// TODO WRITE INVALID AND NULL TEST CASES
public class GetCommandTestCases {

	private final int	ORDER_ID	= TestResources.ORDER_ID;
	private final int	CUSTOMER_ID	= TestResources.USER_ID;

	@Test
	public void testExecuteGetOrderCommand() {

		try {
			TestResources.createOrderForTest();

			String response = getOrderCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			System.out.println(e);
			fail();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecuteGetAllOrdersCommand() {

		try {
			TestResources.createOrderForTest();

			String response = getAllOrdersCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecuteGetDetailCommand() {

		try {
			TestResources.createOrderForTest();
			TestResources.createDetailsForTest();

			String response = getDetailCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		} finally {
			TestResources.cleanDetailsTable();
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecuteGetAllDetailsCommand() {

		try {
			TestResources.createOrderForTest();
			TestResources.createDetailsForTest();

			String response = getAllDetailsCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		} finally {
			TestResources.cleanDetailsTable();
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecuteGetuserCommand() {

		try {
			TestResources.createUserForTest();

			String response = getUserCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		} finally {
			TestResources.cleanUserTable();
		}
	}

	@Test
	public void testExecuteGetOrderCommandInvlaidId() {

		try {
			TestResources.createOrderForTest();

			getOrderCommandInvalidId().executeCommand();
			fail();
		} catch (SQLException e) {
			assertTrue(true);
		} finally {
			TestResources.cleanOrderTable();
		}

	}

	@Test
	public void testExecutegetOrderCommandNullValue() {

		try {
			TestResources.createOrderForTest();

			getOrderCommandNullValue().executeCommand();
			fail("No exception throw using invalid id");
		} catch (SQLException e) {
			assertTrue(true);
		} catch (NumberFormatException nfe) {
			assertTrue(true);
		} finally {
			TestResources.cleanOrderTable();
		}

	}

	/////////////////////////////// SETUP ///////////////////////////

	private Command getOrderCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("id", String.valueOf(ORDER_ID));

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
	}

	private Command getAllOrdersCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("customerId", String.valueOf(CUSTOMER_ID));

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setQuantity(Command.queryQuantity.MULTIPLE);
		command.setTable(Command.queryTable.ORDERS);
		command.setValues(values);

		return command;
	}

	private Command getDetailCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("id", String.valueOf(ORDER_ID));

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
	}

	private Command getAllDetailsCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("customerId", String.valueOf(CUSTOMER_ID));

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.MULTIPLE);
		command.setValues(values);

		return command;
	}

	private Command getUserCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("email", "test@test.test");
		values.put("password", "secret");

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
	}

	private Command getOrderCommandInvalidId() {

		Map<String, String> values = new HashMap<>();
		values.put("id", "-1");

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);
		return command;
	}

	private Command getOrderCommandNullValue() {

		Map<String, String> values = new HashMap<>();
		values.put("id", null);

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
	}

}