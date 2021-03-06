import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestDictionary.class);

        // Print failed tests
        for (Failure failure : result.getFailures()) {
            System.out.println("Failures Test: " + failure.toString());
        }
    }
}
