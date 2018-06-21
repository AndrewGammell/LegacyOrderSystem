package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import command.test.cases.GetCommandTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({GetCommandTestCases.class})
public class TestSuite {

}
