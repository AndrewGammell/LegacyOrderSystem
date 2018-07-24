package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import command.test.cases.CreateCommandTestCases;
import command.test.cases.DeleteCommandTestCases;
import command.test.cases.GetCommandTestCases;
import command.test.cases.UpdateCommandTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({ GetCommandTestCases.class, CreateCommandTestCases.class, DeleteCommandTestCases.class,
		UpdateCommandTestCases.class })
public class TestSuite {

}
