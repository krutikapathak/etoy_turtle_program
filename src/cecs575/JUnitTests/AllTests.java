package cecs575.JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommandTest.class, EtoyProgramTest.class, ExpressionsTest.class, ParserTest.class, VisitorTest.class })
public class AllTests {

}
