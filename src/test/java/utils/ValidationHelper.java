package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import hooks.ReportHooks;
import java.util.Map;

/**
 * ValidationHelper - Consistent validation handling for Sambhram's tests
 * Matches the existing project's error handling pattern
 */
public class ValidationHelper {

    /**
     * Validates test results with proper error handling and reporting
     * @param testType - Type of test (e.g., "Name Validation", "Registration Button")
     * @param expectedMessage - Expected validation message
     * @param actualMsg - Actual message map from page object
     * @param base64Screenshot - Screenshot for reporting
     * @param allowMissingValidation - If true, logs missing validations as INFO instead of FAIL
     */
    public static void validateResult(String testType, String expectedMessage, 
                                    Map<String, String> actualMsg, String base64Screenshot, 
                                    boolean allowMissingValidation) {
        
        String msgType = actualMsg.get("type");     // "error", "result", or "none"
        String actualMessage = actualMsg.get("message");  // the actual text

        // Log the type and actual message (consistent with existing project)
        System.out.println("[ACTUAL DISPLAYED MESSAGE]" + msgType + " -> " + actualMessage);
        System.out.println("[EXPECTED MESSAGE]" + expectedMessage);

        ReportHooks.test.log(Status.INFO, "Expected: " + expectedMessage);
        ReportHooks.test.log(Status.INFO, "Actual: " + actualMessage);

        // Handle success cases
        if (isSuccessExpected(expectedMessage)) {
            if ("result".equals(msgType) && actualMessage.contains("Registration was successful")) {
                logPass(testType, actualMessage, base64Screenshot);
            } else {
                logFail(testType, "expected success but got [" + msgType + "]: '" + actualMessage + "'", base64Screenshot);
                throw new AssertionError("Expected registration success, but got [" + msgType + "]: " + actualMessage);
            }
        } 
        // Handle error cases
        else {
            if ("error".equals(msgType) && actualMessage.equals(expectedMessage)) {
                logPass(testType, "correct error '" + actualMessage + "'", base64Screenshot);
            } else {
                // Check if application doesn't have the expected validation
                if ("result".equals(msgType) && actualMessage.contains("Registration was successful")) {
                    if (allowMissingValidation) {
                        logInfo(testType, "Application allows '" + expectedMessage + "' case - got success: '" + actualMessage + "'", base64Screenshot);
                        // Don't throw error for missing validations when allowMissingValidation is true
                    } else {
                        logFail(testType, "expected error '" + expectedMessage + "' but got success: '" + actualMessage + "'", base64Screenshot);
                        throw new AssertionError("Expected validation error, but registration succeeded");
                    }
                } else {
                    logFail(testType, "expected error '" + expectedMessage + "' but got [" + msgType + "]: '" + actualMessage + "'", base64Screenshot);
                    throw new AssertionError("Validation failed: expected '" + expectedMessage + "' but got '" + actualMessage + "'");
                }
            }
        }
    }

    private static boolean isSuccessExpected(String expectedMessage) {
        return "Registration was successful".equalsIgnoreCase(expectedMessage) || 
               expectedMessage.contains("Registration was successful");
    }

    private static void logPass(String testType, String message, String base64Screenshot) {
        if (base64Screenshot != null) {
            ReportHooks.test.pass("[" + testType + "] Passed: " + message, 
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } else {
            ReportHooks.test.pass("[" + testType + "] Passed: " + message);
        }
    }

    private static void logFail(String testType, String message, String base64Screenshot) {
        if (base64Screenshot != null) {
            ReportHooks.test.fail("[" + testType + "] FAILED: " + message, 
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } else {
            ReportHooks.test.fail("[" + testType + "] FAILED: " + message);
        }
    }

    private static void logInfo(String testType, String message, String base64Screenshot) {
        if (base64Screenshot != null) {
            ReportHooks.test.info("[" + testType + "] INFO: " + message, 
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } else {
            ReportHooks.test.info("[" + testType + "] INFO: " + message);
        }
    }
}