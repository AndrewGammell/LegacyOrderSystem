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

public class UpdateCommandTestCases {

	private Gson			gson		= new Gson();
	private Command			command;
	private final String	NULL		= null;
	private final int		ORDER_ID	= TestResources.ORDER_ID;
	private String			response;

	@Test
	public void testExecutePutValidOrderCommand() throws SQLException {

		TestResources.createOrderForTest();

		command = new Command();
		command.setBody(updateValidOrderJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			OrderModel order = gson.fromJson(response, OrderModel.class);
			assertTrue(order != null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePutInvalidOrderCommand() throws SQLException {

		TestResources.createOrderForTest();

		command = new Command();
		command.setBody(updateInvalidOrderJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			OrderModel order = gson.fromJson(response, OrderModel.class);
			assertTrue(order == null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePutNullOrderCommand() throws SQLException {

		TestResources.createOrderForTest();

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			OrderModel order = gson.fromJson(response, OrderModel.class);
			assertTrue(order == null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePutValidDetailCommand() throws SQLException {

		TestResources.createOrderForTest();
		TestResources.createDetailsForTest();

		command = new Command();
		command.setBody(updateValidDetailsJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
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
	public void testExecutePutInvalidDetailCommand() throws SQLException {

		TestResources.createOrderForTest();

		command = new Command();
		command.setBody(updateInvalidDetailJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			DetailModel order = gson.fromJson(response, DetailModel.class);
			assertTrue(order == null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	@Test
	public void testExecutePutNullDetailsCommand() throws SQLException {

		TestResources.createOrderForTest();

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			DetailModel order = gson.fromJson(response, DetailModel.class);
			assertTrue(order == null);

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		} finally {
			TestResources.cleanOrderTable();
		}
	}

	///////////////////////////// SETUP////////////////////////////

	private String updateValidOrderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(ORDER_ID);
		order.setCreatedDate(new Date());
		order.setCreatedStaffId(999);
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(new Date());
		order.setUpdatedDate(new Date());
		order.setUpdatedStaffId(12345);

		return gson.toJson(order);
	}

	private String updateInvalidOrderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(123);
		order.setCreatedDate(null);
		order.setCreatedStaffId(999);
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(null);
		order.setUpdatedDate(new Date());
		order.setUpdatedStaffId(12345);

		return gson.toJson(order);
	}

	private String updateValidDetailsJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(12345);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId(987654321);
		detail.setCreatedDate(new Date());
		detail.setUpdatedDate(new Date());
		detail.setUpdatedStaffId(56789);

		return gson.toJson(detail);
	}

	private String updateInvalidDetailJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(-172635);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId(987654321);
		detail.setCreatedDate(null);
		detail.setUpdatedDate(new Date());
		detail.setUpdatedStaffId(56789);

		return gson.toJson(detail);
	}

}
