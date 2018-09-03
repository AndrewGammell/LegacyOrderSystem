package command.test.cases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.sogeti.command.Command;

public class DeleteCommandTestCases {

	private Command				command;
	private final String		NULL		= null;
	private final int			ORDER_ID	= TestResources.ORDER_ID;
	private String				response;
	private Map<String, String>	values;

	@Test
	public void testExecuteDeleteValidOrderCommand() {

		try {
			TestResources.createOrderForTest();

			values = new HashMap<String, String>();
			values.put("id", String.valueOf(ORDER_ID));
			command = new Command();
			command.setType(Command.queryType.DELETE);
			command.setTable(Command.queryTable.ORDERS);
			command.setQuantity(Command.queryQuantity.SINGLE);
			command.setValues(values);

			response = command.executeCommand();
			assertTrue(Boolean.valueOf(response));

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecuteDeleteInvalidOrderCommand() {

		try {
			TestResources.createOrderForTest();

			values = new HashMap<String, String>();
			values.put("id", String.valueOf(NULL));
			command = new Command();
			command.setType(Command.queryType.DELETE);
			command.setTable(Command.queryTable.ORDERS);
			command.setQuantity(Command.queryQuantity.SINGLE);
			command.setValues(values);

			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecuteDeleteValidOrderDetailsCommand() {

		try {
			TestResources.createDetailsForTest();

			values = new HashMap<String, String>();
			values.put("id", String.valueOf(ORDER_ID));
			command = new Command();
			command.setType(Command.queryType.DELETE);
			command.setTable(Command.queryTable.ORDERS);
			command.setQuantity(Command.queryQuantity.SINGLE);
			command.setValues(values);

			response = command.executeCommand();
			assertTrue(Boolean.valueOf(response));

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanDetailsTable();
		}
	}

	@Test
	public void testExecuteDeleteValidPersonCommand() {

		try {
			TestResources.createUserForTest();

			values = new HashMap<String, String>();
			values.put("id", String.valueOf(ORDER_ID));
			command = new Command();
			command.setType(Command.queryType.DELETE);
			command.setTable(Command.queryTable.ORDERS);
			command.setQuantity(Command.queryQuantity.SINGLE);
			command.setValues(values);

			response = command.executeCommand();
			assertTrue(Boolean.valueOf(response));

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanUserTable();
		}
	}

}
