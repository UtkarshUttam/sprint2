# Test Runners Guide

This project has two test runners for different purposes:

## 1. pwdRunner.java (Main Test Suite)

**Purpose**: Full regression testing

- Runs ALL feature files
- Generates comprehensive reports
- Used for CI/CD and complete test execution

**Usage**:

```bash
mvn test -Dtest=pwdRunner
```

**Reports Generated**:

- Extent Report: `test-output/ExtentReport.html` (with screenshots)
- Cucumber Report: `target/cucumber-report.html`
- TestNG Report: `target/surefire-reports/index.html`

## 2. SingleTestRunner.java (Development/Debugging)

**Purpose**: Individual scenario testing

- Runs specific feature files or scenarios
- Quick debugging during development
- Faster execution for testing changes

**Usage**:

```bash
mvn test -Dtest=SingleTestRunner
```

**Customization**:

- Change `features` path to target specific files
- Use `tags` to run specific scenarios
- Modify for quick testing needs

## Screenshot Management

- All screenshots are saved to: `screenshots/` folder
- Screenshots are embedded in Extent Reports as Base64
- Old screenshots are automatically timestamped

## Best Practices

1. Use `pwdRunner` for full test execution
2. Use `SingleTestRunner` for development and debugging
3. Clean up old screenshots periodically
4. Check all three report types for comprehensive results
