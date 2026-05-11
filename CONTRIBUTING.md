# Contributing to DataDrivenFramework

Thank you for your interest in contributing! This guide will help you get started.

## Getting Started

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/<your-username>/DataDrivenFramework.git
   cd DataDrivenFramework
   ```
3. Install dependencies:
   ```bash
   mvn clean install -DskipTests
   ```
4. Create a branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
5. Make your changes
6. Test locally:
   ```bash
   mvn clean test
   ```
7. Commit and push
8. Open a Pull Request against `main`

## Branching Strategy

| Branch | Purpose |
|--------|---------|
| `main` | Stable, production-ready code |
| `develop` | Integration branch |
| `feature/<name>` | New features or tests |
| `bugfix/<name>` | Bug fixes |
| `docs/<name>` | Documentation updates |

## Commit Conventions

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: add new test for account deletion
fix: resolve Chrome driver timeout issue
docs: update README setup instructions
refactor: extract common wait logic
test: add negative test cases for login
chore: update dependencies
```

## Code Standards

- Follow existing naming conventions (camelCase for methods/variables, PascalCase for classes)
- All test classes must extend `TestBase`
- Locators go in `OR.properties`, not hardcoded in test classes
- Test data goes in `testdata.xlsx` with sheet name matching the test method name
- Use cross-platform paths (`Paths.get()` or `File.separator`)
- No `Thread.sleep()` — use explicit waits where possible
- Add Javadoc comments to new classes

## Adding a New Test

1. Add test data to `src/test/resources/excel/testdata.xlsx`
   - Sheet name must match your test method name exactly
   - Row 1 = column headers
   - Row 2+ = test data
2. Add locators to `src/test/resources/properties/OR.properties`
   - Use suffix convention: `_CSS`, `_XPATH`, or `_ID`
3. Create test class in `src/test/java/.../testcases/` extending `TestBase`
4. Register the test in `src/test/resources/runner/testng.xml`

## Pull Request Checklist

- [ ] Tests pass locally (`mvn clean test`)
- [ ] No hardcoded credentials or absolute paths
- [ ] New locators added to `OR.properties`
- [ ] Test data added to Excel if data-driven
- [ ] New test registered in `testng.xml`
- [ ] Code is readable with appropriate Javadoc
- [ ] Cross-platform compatible (no Windows-only paths)

## Reporting Issues

When reporting bugs, include:
- Steps to reproduce
- Expected vs actual behavior
- Browser and OS version
- Java and Maven versions
- Relevant log output from `src/test/resources/logs/`

## Questions?

Open a GitHub Issue with the `question` label.
