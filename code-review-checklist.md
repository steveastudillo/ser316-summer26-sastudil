# Code Review Checklist

**Reviewer Name:** [Steve Astudillo]
**Date:** [06/04/2026]
**Branch:** Review

## Instructions
Review ALL source files (in main not test) in the project and identify defects using the categories below. Log at least 5 defects total:
- At least 1 from CS (Coding Standards)
- At least 1 from CG (Code Quality/General)
- At least 1 from FD (Functional Defects)
- Remaining can be from any category

## Review Categories

- **CS**: Coding Standards (naming conventions, formatting, style violations)
- **CG**: Code Quality/General (design issues, code smells, maintainability)
- **FD**: Functional Defects (logic errors, incorrect behavior, bugs)
- **MD**: Miscellaneous (documentation, comments, other issues)

## Defect Log

| Defect ID | File          | Line(s)             | Category | Description                                                                                      | Severity |
|-----------|---------------|---------------------|----------|--------------------------------------------------------------------------------------------------|----------|
| 1 | Checkout.java | checkoutBook()      | FD       | The method will return 0 and ignore anything else.                                               | Critical |
| 2 | Checkout.java | usPatronType()      | FD       | Uses == instead of .equals() when comparing Strings. This may return incorrect results because String references are compared rather than String values.                                      | High     |
| 3 | Book.java     | import section      | CG       | unused imports ArrayList and list. This increase clutter and reduce maintainability              | Low      |
| 4 | Checkout.java | MAX_FINE_AMOUNT     | CS       | constant is declased public statis insteadf of private static final.                             | Low      |
| 5 | Patron.java   | addFine()           | MD       | Contains an empty else statemetn that needs to be removed.                                       | Medium   |
| 6 | Checkout.java | 17                  | MD       | Empty // comment line. Provides no useful docuemntation.                                         | Low      |
| 7 | Patron.java   | hasBookCheckedOut() | CG       | the method has unnecessary boolean comparisons unnecessary if / elfe statements.                 | Low      |
| 8 | Checkout.java | bookList            | CG       | variable name bookList needs to be changed has it's misleading. A map not a list                 | Medium   |
| 9 | Book.java     | hashCode()          | CS       | magic # 31 needs to be a named constant. Not used in hashcode.                                   | Low      |
| 10 | Patron        | toString()          | CS       | concatenation makes a unnecessary line. does not follow the recommended formattnig in guidelines | Low      |

**Severity Levels:**
- **Critical**: Causes system failure, data corruption, or security issues
- **High**: Major functional defect or significant quality issue
- **Medium**: Moderate issue affecting maintainability or minor functional problem
- **Low**: Minor style issue or cosmetic problem

## Example Entry

| Defect ID | File          | Line(s) | Category | Description                                | Severity |
|-----------|---------------|---------|----------|--------------------------------------------|----------|
| 1 | Checkout.java | 17      | CS       | Variable bookList misleading - Map not List | Medium |
| 2 | Book.java     | 107     | FD       | Magic number 100 should be totalCopies      | High |

## Notes
- Be specific with line numbers
- Provide clear, actionable descriptions
- Consider: readability, maintainability, correctness, performance, security
- Focus on issues that impact code quality or functionality
