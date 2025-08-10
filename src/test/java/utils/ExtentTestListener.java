package utils;

import org.testng.ITestListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ISuiteListener, ITestListener {

    @Override
    public void onStart(ISuite suite) {
        System.out.println("=== Test Suite started: " + suite.getName() + " ===");
        ExtentManager.reset();
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("=== Test Suite finished: " + suite.getName() + " ===");
        ExtentManager.flush();
        System.out.println("=== Extent Report generated at: test-output/ExtentReport.html ===");
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getMethod().getMethodName());
    }
}