# 🧪 Data Driven Test Automation Framework

A production-ready **Selenium WebDriver** test automation framework using the **Data-Driven Testing** approach with Java, TestNG, and Maven.

---

## Author

**Prepared by:** Janielle Joy S. Gregorio  
**LinkedIn:** [linkedin.com/in/janiellejoygregorio](https://ph.linkedin.com/in/janiellejoygregorio)

---

## Project Overview

This framework automates browser-based UI testing for a banking web application using externalized test data from Excel spreadsheets. It enables non-technical team members to add test scenarios without modifying code.

**Target Application:** [Way2Automation Angular Banking App](http://www.way2automation.com/angularjs-protractor/banking/#/login)

### Key Features

| Feature | Description |
|---------|-------------|
| 📊 Data-Driven Testing | Test data externalized in Excel via Apache POI |
| 🌐 Multi-Browser Support | Chrome, Firefox, Edge (configurable) |
| 📝 Rich HTML Reports | ExtentReports with screenshots on failure |
| 📋 Structured Logging | Log4j2 with console, file, and HTML output |
| 📸 Auto Screenshots | Captured automatically on test failure |
| 🔧 Object Repository | Locators externalized in properties files |
| ⚡ Parallel Execution | TestNG parallel support ready |
| 🔄 CI/CD Ready | GitHub Actions pipeline included |
| 🖥️ Headless Mode | Supports headless execution for CI environments |

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                    TestNG Runner                          │
│                  (testng.xml)                             │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  ┌──────────────┐    ┌──────────────┐                   │
│  │  Test Cases  │    │  Listeners   │                   │
│  │              │    │              │                   │
│  │ - Login      │    │ - Extent     │                   │
│  │ - AddCust    │    │   Reporting  │                   │
│  │ - OpenAcct   │    │ - Screenshot │                   │
│  └──────┬───────┘    └──────────────┘                   │
│         │                                                │
│  ┌──────▼───────┐                                       │
│  │   TestBase   │  ← Foundation class                   │
│  │              │                                        │
│  │ - WebDriver  │                                        │
│  │ - Config     │                                        │
│  │ - Actions    │                                        │
│  └──────┬───────┘                                       │
│         │                                                │
│  ┌──────▼───────┐    ┌──────────────┐                   │
│  │  Utilities   │    │  Resources   │                   │
│  │              │    │              │                   │
│  │ - ExcelReader│    │ - Config     │                   │
│  │ - TestUtil   │    │ - OR.props   │                   │
│  │ - ExtentMgr  │    │ - testdata   │                   │
│  └──────────────┘    └──────────────┘                   │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Module Responsibilities

| Module | Responsibility |
|--------|---------------|
| `base/` | WebDriver lifecycle, config loading, reusable actions |
| `testcases/` | Actual test methods with assertions |
| `utilities/` | Excel reader, screenshot capture, report config |
| `listeners/` | TestNG event hooks for reporting |
| `resources/` | Configuration, test data, locators |

---

## Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 11+ |
| Build Tool | Maven | 3.6+ |
| Test Framework | TestNG | 7.10.2 |
| Browser Automation | Selenium WebDriver | 4.21.0 |
| Driver Management | WebDriverManager | 5.8.0 |
| Reporting | ExtentReports | 4.0.9 |
| Data Source | Apache POI (Excel) | 5.2.5 |
| Logging | Log4j2 | 2.23.1 |
| CI/CD | GitHub Actions | - |

---

## Folder Structure

```
DataDrivenFramework/
├── .github/
│   └── workflows/
│       └── ci.yml                          # GitHub Actions CI pipeline
├── docs/
│   └── ARCHITECTURE.md                     # Architecture & design documentation
├── src/test/
│   ├── java/io/github/amazing010692/
│   │   ├── base/
│   │   │   └── TestBase.java              # Framework foundation class
│   │   ├── testcases/
│   │   │   ├── AddCustomerTest.java       # Add customer (data-driven)
│   │   │   ├── BankManagerLoginTest.java  # Login verification
│   │   │   └── OpenAccountTest.java       # Open account (data-driven)
│   │   ├── utilities/
│   │   │   ├── ExcelReader.java           # Excel data reader/writer
│   │   │   ├── ExtentManager.java         # Report configuration
│   │   │   └── TestUtil.java              # Screenshots & data provider
│   │   └── listeners/
│   │       └── CustomListeners.java       # TestNG event listener
│   └── resources/
│       ├── excel/
│       │   └── testdata.xlsx              # Test data spreadsheets
│       ├── extentconfig/
│       │   └── ReportsConfig.xml          # ExtentReports XML config
│       ├── logs/
│       │   └── log4j2.xml                 # Log4j2 configuration
│       ├── properties/
│       │   ├── Config.properties          # Browser, URL, timeouts
│       │   └── OR.properties              # Object Repository (locators)
│       └── runner/
│           └── testng.xml                 # TestNG suite definition
├── reports/                                # Generated HTML reports (gitignored)
├── .env.example                            # Environment variable template
├── .gitignore                              # Git exclusion rules
├── CHANGELOG.md                            # Version history
├── CONTRIBUTING.md                         # Contributor guidelines
├── LICENSE                                 # MIT License
├── README.md                               # This file
└── pom.xml                                 # Maven build configuration
```

---

## Prerequisites

Before you begin, ensure you have the following installed:

| Requirement | Minimum Version | Download |
|-------------|----------------|----------|
| Java JDK | 11+ | [Adoptium](https://adoptium.net/) |
| Maven | 3.6+ | [Maven](https://maven.apache.org/download.cgi) |
| Git | Any recent | [Git](https://git-scm.com/) |
| Browser | Chrome (recommended) | [Chrome](https://www.google.com/chrome/) |

Verify installations:
```bash
java -version
mvn -version
git --version
```

---

## Installation Guide

### Step 1: Clone the Repository
```bash
git clone https://github.com/amazing010692/DataDrivenFramework.git
cd DataDrivenFramework
```

### Step 2: Install Dependencies
```bash
mvn clean install -DskipTests
```

### Step 3: Verify Setup
```bash
mvn dependency:tree
```

---

## Environment Variables

Copy the example file and configure:
```bash
cp .env.example .env
```

| Variable | Description | Default |
|----------|-------------|---------|
| `browser` | Browser to use (chrome/firefox/edge) | `chrome` |
| `HEADLESS` | Run in headless mode (true/false) | `false` |

You can also configure via `src/test/resources/properties/Config.properties`:
```properties
browser = chrome
testsiteurl = http://www.way2automation.com/angularjs-protractor/banking/#/login
implicit.wait = 10
```

---

## Running the Project

### Run All Tests
```bash
mvn clean test
```

### Run with Specific Browser
```bash
browser=firefox mvn clean test
```

### Run in Headless Mode (CI)
```bash
HEADLESS=true mvn clean test
```

### Run a Specific Test Class
```bash
mvn test -Dtest=BankManagerLoginTest
```

### Run with Custom TestNG Suite
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/runner/testng.xml
```

---

## Running Tests

### Test Execution Flow
1. `TestBase.setUp()` initializes WebDriver and loads configuration
2. TestNG reads `testng.xml` and executes test classes in order
3. Data-driven tests read parameters from `testdata.xlsx`
4. `CustomListeners` captures results and generates reports
5. `TestBase.tearDown()` closes the browser

### Adding a New Test
1. Add test data to `src/test/resources/excel/testdata.xlsx` (sheet name = method name)
2. Add locators to `src/test/resources/properties/OR.properties`
3. Create test class in `testcases/` extending `TestBase`
4. Register in `src/test/resources/runner/testng.xml`

---

## CI/CD Workflow

This project includes a GitHub Actions pipeline (`.github/workflows/ci.yml`):

| Trigger | Action |
|---------|--------|
| Push to `main` or `develop` | Run full test suite |
| Pull Request to `main` | Run full test suite |

### Pipeline Steps:
1. Checkout code
2. Set up JDK 11
3. Install Chrome browser
4. Run `mvn clean test` in headless mode
5. Upload test reports as artifacts (14-day retention)

---

## Viewing Reports

After test execution:

| Report Type | Location |
|-------------|----------|
| ExtentReports (detailed HTML) | `reports/Extent_*.html` |
| TestNG default reports | `test-output/` |
| Surefire reports | `target/surefire-reports/` |

---

## Contribution Guide

See [CONTRIBUTING.md](CONTRIBUTING.md) for full details.

### Quick Start:
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Make changes and test: `mvn clean test`
4. Commit with clear messages (see conventions below)
5. Push and open a Pull Request

### Commit Conventions:
```
feat: add new test for account deletion
fix: resolve Chrome driver timeout issue
docs: update README setup instructions
refactor: extract common wait logic
test: add negative test cases for login
```

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| `SessionNotCreatedException` | Browser/driver version mismatch. WebDriverManager should auto-resolve. Clear `~/.cache/selenium` |
| `FileNotFoundException` on Config.properties | Ensure you're running from project root directory |
| Tests pass locally but fail in CI | Set `HEADLESS=true` environment variable |
| Excel data not loading | Verify sheet name matches test method name exactly |
| `NoSuchElementException` | Page may not have loaded. Increase `implicit.wait` in Config.properties |
| `WebDriverException: unknown error` | Update your browser to the latest version |
| Maven build fails | Run `mvn clean install -U` to force dependency refresh |

---

## FAQ

**Q: How do I add a new browser?**  
A: Add a new `case` in the `initializeBrowser()` method in `TestBase.java`.

**Q: How do I run tests in parallel?**  
A: Set `parallel="tests"` and `thread-count="3"` in `testng.xml`.

**Q: Where do screenshots go on failure?**  
A: `target/surefire-reports/html/` with a timestamped filename.

**Q: Can I run without Maven?**  
A: Yes — run `testng.xml` directly from your IDE as a TestNG suite.

**Q: How do I add test data?**  
A: Add a new sheet in `testdata.xlsx` with the sheet name matching your test method name. Row 1 = headers, Row 2+ = data.

**Q: How does the Object Repository work?**  
A: Locators are stored in `OR.properties` with a suffix indicating type (`_CSS`, `_XPATH`, `_ID`). The framework auto-detects the locator strategy from the suffix.

---

## Security Notes

- ✅ Log4j upgraded to 2.23.1 (patched against CVE-2021-44228 Log4Shell)
- ✅ No hardcoded credentials in source code
- ✅ No hardcoded file paths (cross-platform compatible)
- ✅ System-scoped JARs replaced with proper Maven dependencies
- ✅ `.gitignore` prevents accidental commit of sensitive files
- ⚠️ Ensure `Config.properties` does not contain production credentials

---

## Future Improvements

| Priority | Improvement |
|----------|-------------|
| High | Implement Page Object Model for better maintainability |
| Medium | Add OWASP dependency-check plugin |
| Medium | Add Docker support for containerized execution |
| Low | Add API test layer |
| Low | Implement test retry mechanism (IRetryAnalyzer) |

---

## Available Commands

| Command | Description |
|---------|-------------|
| `mvn clean test` | Clean build and run all tests |
| `mvn test -Dtest=ClassName` | Run specific test class |
| `mvn dependency:tree` | View dependency tree |
| `mvn versions:display-dependency-updates` | Check for updates |
| `HEADLESS=true mvn test` | Run headless (for CI) |
| `browser=firefox mvn test` | Run with specific browser |

---

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
