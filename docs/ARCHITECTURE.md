# Architecture & Design Documentation

## Overview

This is a **Data-Driven Test Automation Framework** built with Selenium WebDriver, Java, TestNG, and Maven. It follows the Data-Driven Testing pattern where test logic is separated from test data.

## Design Patterns Used

| Pattern | Implementation |
|---------|---------------|
| Data-Driven Testing | Excel spreadsheets via Apache POI |
| Object Repository | Properties file (`OR.properties`) with locator suffixes |
| Template Method | `TestBase` provides lifecycle hooks, test classes override |
| Listener Pattern | `CustomListeners` hooks into TestNG events |
| Singleton | `ExtentManager` creates single report instance |

## Data Flow

```
testdata.xlsx → DataProvider → Test Method → WebDriver → Browser → Assertions → Report
```

## Configuration Hierarchy

Environment variables override properties file values:

```
1. System Environment Variables (highest priority)
2. Config.properties (default values)
```

## Locator Strategy

Locators in `OR.properties` use a suffix convention:
- `elementName_CSS` → resolved via `By.cssSelector()`
- `elementName_XPATH` → resolved via `By.xpath()`
- `elementName_ID` → resolved via `By.id()`

The `TestBase.findElement()` method auto-detects the strategy from the suffix.

## Test Data Convention

The `@DataProvider` reads from Excel where:
- **Sheet name** = test method name (e.g., `addCustomerTest`)
- **Row 1** = column headers
- **Row 2+** = test data rows (each row = one test iteration)

## Reporting Architecture

Dual reporting system:
1. **ExtentReports** — Rich HTML with screenshots, collapsible stack traces
2. **ReportNG** — Lightweight TestNG HTML reports

Both are generated automatically via `CustomListeners`.

## Security Considerations

- Log4j 2.23.1 (patched against Log4Shell CVE-2021-44228)
- No credentials in source code
- Cross-platform paths (no hardcoded OS-specific paths)
- `.gitignore` prevents accidental commit of sensitive files
