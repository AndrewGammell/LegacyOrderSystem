package command.test.cases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.sogeti.command.Command;
import com.sogeti.model.DetailModel;
import com.sogeti.model.OrderModel;
import com.sogeti.model.UserModel;

public class CreateCommandTestCases {

	private static int		ORDER_ID	= -827736;
	private Gson			gson		= new Gson();
	private Command			command;
	private final String	NULL		= null;
	private final int		CUSTOMER_ID	= -123;
	private String			response;

	@Test
	public void testExecutePostValidOrderCommand() {
		command = new Command();
		command.setBody(getValidOrderJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		System.out.println(gson.toJson(command));

		try {
			response = command.executeCommand();
			OrderModel order = gson.fromJson(response, OrderModel.class);
			ORDER_ID = order.getOrderId();
			assertTrue(order != null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePostInvalidOrderCommand() {
		command = new Command();
		command.setBody(getInvalidOrderJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			OrderModel order = gson.fromJson(response, OrderModel.class);
			ORDER_ID = order.getOrderId();
			assertTrue(order == null);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePostNullOrderCommand() {
		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			OrderModel order = gson.fromJson(response, OrderModel.class);
			ORDER_ID = order.getOrderId();
			assertTrue(order == null);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePostValidDetailCommand() throws SQLException {

		command = new Command();
		command.setBody(getValidDetailsJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		System.out.println(gson.toJson(command));
		try {
			TestResources.createOrderForTest();

			response = command.executeCommand();
			DetailModel order = gson.fromJson(response, DetailModel.class);
			assertTrue(order != null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanDetailsTable();
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePostInvalidDetailCommand() {

		command = new Command();
		command.setBody(getInvalidDetailJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			TestResources.createOrderForTest();

			response = command.executeCommand();
			DetailModel order = gson.fromJson(response, DetailModel.class);
			assertTrue(order == null);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanDetailsTable();
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePostNullDetailCommand() {

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			TestResources.createOrderForTest();

			response = command.executeCommand();
			DetailModel order = gson.fromJson(response, DetailModel.class);
			assertTrue(order == null);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanDetailsTable();
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePostValidUserCommand() throws SQLException {

		command = new Command();
		command.setBody(getValidUserJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {

			response = command.executeCommand();
			UserModel order = gson.fromJson(response, UserModel.class);
			assertTrue(order != null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanUserTable();
		}
	}

	@Test
	public void testExecutePostInvalidUserCommand() {
		command = new Command();
		command.setBody(getInvalidUserJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			UserModel order = gson.fromJson(response, UserModel.class);
			assertTrue(order != null);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanUserTable();
		}
	}

	@Test
	public void testExecutePostNullUserCommand() {

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			UserModel order = gson.fromJson(response, UserModel.class);
			assertTrue(order != null);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanUserTable();
		}
	}

	/////////////////////////////// SETUP ///////////////////////////

	private String getValidOrderJson() {

		OrderModel order = new OrderModel();
		order.setCreatedDate(new Date());
		order.setCreatedStaffId(999);
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(new Date());
		order.setCustomerId(CUSTOMER_ID);
		System.out.println(gson.toJson(order));
		return gson.toJson(order);
	}

	private String getInvalidOrderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(123);
		order.setCreatedDate(null);
		order.setCreatedStaffId(999);
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(null);
		order.setCustomerId(CUSTOMER_ID);

		return gson.toJson(order);
	}

	private String getValidDetailsJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(12345);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId(987654321);
		detail.setCreatedDate(new Date());
		detail.setCustomerId(CUSTOMER_ID);

		return gson.toJson(detail);
	}

	private String getInvalidDetailJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(-172635);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId(987654321);
		detail.setCreatedDate(null);
		detail.setCustomerId(CUSTOMER_ID);

		return gson.toJson(detail);
	}

	private String getValidUserJson() {

		UserModel user = new UserModel();
		user.setId(CUSTOMER_ID);
		user.setFirstName("John");
		user.setLastName("Doenut");
		user.setEmail("test@test.test");
		user.setPassword("secret");
		user.setDateOfBirth(new Date(01 / 01 / 1965));
		user.setCreatedDate(new Date());

		return gson.toJson(user);
	}

	private String getInvalidUserJson() {

		UserModel user = new UserModel();
		user.setId(CUSTOMER_ID);
		user.setFirstName(NULL);
		user.setLastName(NULL);
		user.setEmail(NULL);
		user.setPassword(NULL);
		user.setDateOfBirth(new Date(01 / 01 / 1965));
		user.setCreatedDate(new Date());

		return gson.toJson(user);
	}

}
