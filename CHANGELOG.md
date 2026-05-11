# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [1.0.0] - 2024

### 🔒 Security
- **CRITICAL:** Upgraded Log4j from 2.13.3 to 2.23.1 (fixes CVE-2021-44228 Log4Shell)
- Removed hardcoded Opera binary path with username
- Removed system-scoped JAR (replaced with proper Maven dependency)

### ✨ Added
- Comprehensive README.md with all required sections
- CONTRIBUTING.md with contributor guidelines
- CHANGELOG.md for version tracking
- LICENSE (MIT)
- `.env.example` for environment variable documentation
- GitHub Actions CI pipeline (`.github/workflows/ci.yml`)
- Headless browser support for CI environments
- `docs/PROJECT_ANALYSIS.md` with full architecture analysis
- Javadoc comments on all classes
- Cross-platform path handling throughout

### 🔄 Changed
- Upgraded dependency versions (WebDriverManager 5.3.3, TestNG 7.4.0, Guice 5.1.0, Commons-IO 2.11.0)
- Refactored `TestBase.java` — extracted methods, proper error handling, cross-platform paths
- Refactored `CustomListeners.java` — cleaner date formatting, cross-platform report paths
- Refactored `TestUtil.java` — cross-platform screenshot paths
- Refactored `ExtentManager.java` — dynamic system info
- Fixed `BankManagerLoginTest` — removed intentional `Assert.fail()` and dummy assertions
- Improved `.gitignore` — comprehensive exclusion of IDE, OS, generated files
- Set Java source/target to 11
- Bumped project version from 0.0.1-SNAPSHOT to 1.0.0
- `pom.xml` now uses properties for version management

### 🗑️ Removed
- System-scoped `mail-1.4.7.jar` dependency (replaced with Maven `javax.mail`)
- Unused `mysql-connector-java` dependency
- Unused `protobuf-java` dependency
- Unused `dom4j`, `activation`, `jaxb-api` dependencies
- Hardcoded Windows paths (`\\` separators)
- Empty catch blocks and TODO comments
- Intentional test failures in BankManagerLoginTest

## [0.0.1-SNAPSHOT] - 2020-08-21

### Added
- Initial framework setup
- TestBase with multi-browser support (Chrome, Firefox, Edge, Opera, IE)
- Data-driven testing via Excel (Apache POI)
- ExtentReports integration
- Log4j2 logging
- Screenshot capture on failure
- TestNG suite configuration
- BankManagerLoginTest, AddCustomerTest, OpenAccountTest
