# Black Box Testing Report - Assignment 2

**Student Name:** [Steve Astudillo]  
**ASU ID:** [sastudil]  
**Date:** [06/04/2026]

---

## Part 1: Equivalence Partitioning (EP)

Identify equivalence partitions for the `checkoutBook(Book book, Patron patron)` method based on the specification (JavaDoc).

Create **multiple tables**, one per partition category (e.g., book state, patron state, renewal, limits, etc.).

Do **not** put everything into one table.

**Column Explanations:**
- **Partition ID**: Unique identifier (e.g., EP 1.1, EP 2.1)
- **State**: The specific state/value for this partition (e.g., "Unavailable", "Available")
- **Valid/Invalid**: Whether this partition represents valid or invalid input
- **Input Condition**: Precise condition that defines this partition
- **Expected Return**: What return code you expect
- **Expected Behavior**: What should happen

### Example EP Table: Book Availability

| Partition ID | State | Valid/Invalid | Input Condition | Expected Return | Expected Behavior |
|--------------|-------|---------------|----------------|-----------------|------------------|
| EP 1.1 | Unavailable (0 copies) | Invalid | availableCopies == 0 AND other conditions allow checkout | 2.0 | No copies to checkout |
| EP 1.2 | Available (1+ copies) | Valid | availableCopies > 0 AND other conditions allow checkout | Success | Book can be checked out |

**Example test cases:** `testBookAvailable()`, `testUnavailableBook()`

---

### EP Table 1: Book State

| Partition ID | State            | Valid/Invalid | Input Condition        | Expected Return | Expected Behavior |
|--------------|------------------|---------------|------------------------|-----------------|-------------------|
| EP 1.1       | Book Available   | Valid         | availableCopies >0     | Success         | Checkout Proceeds |
| EP 1.2       | Book Unavailable | Invalid       | availableCopies =0     | 2.0             | No checkout       |
| EP 1.3       | Book Null        | Invalid       | book == null           | 2.1             | No change         |
| EP 1.4       | Reference Only   | Invalid       | book.isReferenceOnly() | 5.0             | No checkout       |

---
### EP Table 2: Patron Eligibility

| Partition ID | State               | Valid/Invalid | Input Condition   | Expected Return      | Expected Behavior    |
|--------------|---------------------|---------------|-------------------|----------------------|----------------------|
| EP 2.1       | Eligable            | Valid         | No restrictions   | condition validation | Checkout may Proceed |
| EP 2.2       | Null                | Invalid       | patron == null    | 3.1                  | No state change      |
| EP 2.3       | Suspended           | Invalid       | suspended == true | 3.0                  | No checkout          |
| EP 2.4       | 3+ overdue books    | Invalid       | overdueCount >= 3 | 4.0                  | No checkout          |
| EP 2.5       | Fines >= 10 dollars | Invalid       | fineBalance >= 10 | 4.1                  | No checkout|
---
### EP Table 3: Renewal

| Partition ID | State        | Valid/Invalid | Input Condition             | Expected Return | Expected Behavior         |
|--------------|--------------|---------------|-----------------------------|-----------------|---------------------------|
| EP 3.1       | New Checkout | Valid         | Patron does not have a book | Success         | Book copies will decrease |
| EP 3.2       | Renewal      | Valid         | Patron has a book           | 0.1             | Due date updated          |
---
### EP Table 4: Checkout Limits

| Partition ID | State                  | Valid/Invalid | Input Condition                      | Expected Return      | Expected Behavior              |
|--------------|------------------------|---------------|--------------------------------------|----------------------|--------------------------------|
| EP 4.1       | Below Limit            | Valid         | checkoutCount < maxLimit             | condition validation | Checkout may Proceed           |
| EP 4.2       | At the limit           | Invalid       | checkoutCount >= maxLimit          | 3.2                  | No checkout                    |
| EP 4.3       | Near the limit warning | Valid         | Within 2 of max limit after checkout | 1.1                  | Checkout succeeds with warning |
---
## Part 2: Boundary Value Analysis (BVA)

Important BVA cases may overlap with EP. That is OK. You can reference all relevant EP/BVA coverage in Part 3.

### Example BVA Table: Overdue Count (Threshold: 3)

| Test ID | Boundary | Input Value | Expected Return | Rationale |
|---------|----------|-------------|-----------------|-----------|
| BVA 1.1 | Below | overdueCount = 0 | Success (depends on other setup) | Below warning threshold |
| BVA 1.2 | Warning High | overdueCount = 2 | 1.0 | Just below reject threshold |
| BVA 1.3 | At | overdueCount = 3 | 4.0 | At rejection boundary |
| BVA 1.4 | Above | overdueCount = 4 | 4.0 | Above rejection boundary |
---
### BVA Table 1: Overdue

| Test ID | Boundary | Input Value | Expected Return | Rationale                   |
|---------|----------|-------------|-----------------|-----------------------------|
| BVA 1.1 | Below    | overdueCount = 2    | Success         | Just below reject threshold |
| BVA 1.2 | At       | overdueCount = 3       | 4.0             | threshold |
| BVA 1.3 | Above    | overdueCount = 4      | 4.0             | Above rejection boundary    |
---

### BVA Table 1: Fine Balance

| Test ID | Boundary | Input Value | Expected Return | Rationale                   |
|---------|----------|-------------|-----------------|-----------------------------|
| BVA 2.1 | Below    | 9.99        | Success         | Just below reject threshold |
| BVA 2.2 | At       | 10.00       | 4.1             | threshold |
| BVA 2.3 | Above    | 10.01       | 4.1             | Above rejection boundary    |

---
### BVA Table 2: Student Limit

| Test ID | Boundary | Input Value | Expected Return | Rationale         |
|---------|----------|-----------|-----------------|-------------------|
| BVA 3.1 | Warning  | 8 books   | 1.1             | Within 2 of max   |
| BVA 3.2 | Warning  | 9 books   | 1.1             | Within 2 of max   |
| BVA 3.3 | At Max   | 10 books  | 3.2             | Max limit reached |

---
### BVA Table 3: Child Checkout Limit

| Test ID | Boundary | Input Value        | Expected Return | Rationale         |
|---------|---------|--------------------|-----------------|-------------------|
| BVA 4.1 | Warning | 1 book checked out | 1.1             | Within 2 of max   |
| BVA 4.2 | Warning | 2 book checked out | 1.1             | Within 2 of max   |
| BVA 4.3 | At   | 3 book checked out | 3.2             | Max limit reached |

---

## Part 3: Test Cases Designed

List at least **20** test cases you designed based on your EP/BVA analysis.

Each test case should include:
- EP/BVA coverage
- specific inputs / setup
- expected return code
- expected **observable state changes** (if any)

> Do not test console output.
> 

### Test Case Table
At least some of your tests should verify observable state changes, not just return values.

**Checkout0-3 Columns:** Mark each implementation as Pass (✓) or Fail (✗) for this test case. This helps you track which implementations have bugs and will be useful for Part 4 analysis.

| Test ID Name                 | EP/BVA         | Input Description                             | Expected Return | Expected State Changes                          | Checkout0 | Checkout1 | Checkout2 | Checkout3 |
|------------------------------|----------------|-----------------------------------------------|----------------|-------------------------------------------------|-|-|-|-|
| T1 testBookAvailable         | EP 1.1, EP 2.1 | Available book, eligible patron,  | 0.0            | Patron gains book, abailableCopies decrease by 1 | ✓ | ✓ | ✗ | ✓ |
| T2 testRenewal               | EP 3.2         | Patron already has book checked out           | 0.1            | due date changed                                | ✗ | ✗ | ✓ | ✓ |
| T3 testUnavailableBook       | EP 1.2         | Book has 0 available copies                   | 2.0            | No state change                                 | ✓ | ✓ | ✗ | ✓ |
| T4 testNullBook              | EP 1.3         | Book is null                                  | 2.1            | No state change                                 |✓ | ✓ | ✓ | ✓ |
| T5 testReferenceBook         | EP 1.4         | Reference-only book                           | 5.0            | No state change                                 | ✗ | ✓ | ✓ | ✓ |
| T6 testNullPatron            | EP 2.2         | Patron is null                                | 3.1            | No state change                                 | ✓ | ✓ | ✓ | ✓ |
| T7 testSuspednedPatron       | EP 2.3         | Suspended patron                              | 3.0            | No state change                                 | ✓ | ✓ | ✓ | ✓ |
| T8 testOverdueLimit          | EP 2.4         | Patron has 3 overdue books                    | 4.0            | No state change                                 | ✓ | ✓ | ✗ | ✓ |
| T9 testFineThreshold         | EP 2.5         | Patron owes $10.00                            | 4.1            | No state change                                 | | | | |
| T10 testCheckoutLimitReached | EP 4.2         | Patron at max limit                           | 3.2            | No state change                                 | | | | |
| T11 testFineBelowThreshold   | BVA 2.1        | Fine = 9.99                                   | Success        | Checkout proceeds                               | | | | |
| T12 testFineAboveThreshold   | BVA 2.3        | Fine = 10.01                                  | 4.1            | No state change                                 | | | | |
| T13 TestOverdueBelowBoundary | BVA 1.1        | Overdue count = 2                             | Success        | Checkout proceeds                               | | | | |
| T14 testOverdueAboveBoundary | BVA 1.3        | Overdue count == 4                            | 4.0            | no state change                                 | | | | |
| T15 testStudentLimit8        | BVA 3.1        | Student == 8 books                            | 1.1            | checkout succeeds                               | | | | |
| T16 testStudentLimit9        | BVA 3.2        | Student == 9 books                            | 1.1            | checkout succeeds                               | | | | |
| T17 testChildLimit1          | BVA 4.1        | Child has 1 books                             | 1.1            | Checkout succeeds                               | | | | |
| T18 testChildLimit2          | BVA 4.2        | Child has 2 books                             | 1.1            | Checkout succeeds                               | | | | |
| T19 testFacultyWarning18     | BVA 4.3        | Faculty has 18 books                          | 1.1            | Checkout succeeds                               | | | | |
| T20 testStaffWarning13       | BVA 4.3        | Faculty has 13 books                          | 1.1            | Checkout succeeds                               | | | | |
---

## Part 4: Bug Analysis

### Easter Eggs Found
List any easter egg messages you observed:
- [EASTER EGG #19]: 'Availability testing finds the books that aren't there.'
- [EASTER EGG #19]: 'Can't check out what isn't there.'
- [EASTER EGG #19]: 'Good EP testing checks all partitions.'
- [EASTER EGG #10.1]: 'Testing can show the presence of bugs,'
- [EASTER EGG #19]: 'Testing the sad path matters.'
 - [EASTER EGG #18]: 'Null checking: because null pointer exceptions are not fun.'
 - [EASTER EGG #18]: 'Remember to test all the edge cases.'
 - [EASTER EGG #18]: 'The best code is no code at all... but this isn't it.'
 - [EASTER EGG #15.2]: '...xvFZjo5PgG0 (test renewal to complete!)'
 - [EASTER EGG #10.1/3]: 'Testing can show the presence of bugs,'
 - [EASTER EGG #17]: 'The happy path matters too.'
- [EASTER EGG #10.1]: 'Testing can show the presence of bugs,'
- [EASTER EGG #20]: 'These books stay home.'
 - [EASTER EGG #20]: 'Reference materials: look but don't touch.'
 - [EASTER EGG #20]: 'Stay in the library, book!'
- [EASTER EGG #19]: 'Testing the sad path matters.'
- [EASTER EGG #20]: 'Reference books are meant to be consulted, not carried home.'

### Implementation Results

| Implementation | Bugs Found (count) |
|----------------|--------------------|
| Checkout0      | 2                  |
| Checkout1      | 1                  |
| Checkout2      | 1                  |
| Checkout3      | 0                  |

### Bugs Discovered
List distinct bugs you identified for each implementation. Each bug must cite at least one test case that revealed it.

**Checkout0:**
- Bug 1: [a successful checkout does not reduce book availability] — Revealed by: [Test T2]
- Bug 2: [Reference books return 2.0 when it should be 5.0] — Revealed by: [Test T5]

**Checkout1:**
- Bug 1: [Successful checkout does not add a book to the patron checkout list] — Revealed by: [Test T2]

**Checkout2:**
- Bug 1: [unavailable books can be checked out] — Revealed by: [Test T1]

**Checkout3:**
- Bug 1: [NONE]

### Comparative Analysis
Compare the four implementations:
- Which bugs are most critical (cause the worst failures)? : Checkout2 allowing unavailable books to check out since it ruins the library inventory. 
- Which implementation would you use if you had to choose? : Checkout3
- Why? Justify your choice considering bug severity and frequency. : This implementation contained no bugs which made it the most effective code implemented.
---

## Part 5: Reflection

**Which testing technique was most effective for finding bugs?**
- EP was the most effective becuase I was able to find the most bugs using this technique.
- 
**What was the most challenging aspect of this assignment?**
- Writing the code and finding bugs without writing code. I am still iffy on identifying what could be wrong if I solely read code without running it.
**How did you decide on your EP and BVA?**
- Overlooking the javadoc for checkoutBook() and looked for the most used conditions. I then used BVA for boundaries testing such as the 10 dollar in fines. 
**Describe one test where checking only the return value would NOT have been sufficient to detect a bug.**
checkout test shows that Checkout1 had correct code, however it still failed to add the books to the patrons checkout list. 
