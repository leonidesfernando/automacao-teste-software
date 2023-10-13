Feature: Check login for valid and invalid credentials
  Cases to validate:
  1. valid credentials, then access the system
  2. invalid user, valid password, then get error message
  3. invalid user, invalid password, then get error message
  4. valid user, invalid password, then get error message


  Scenario: Login in the system with
    Given The follow credentials then
      | User         | Password         |
      | @ValidUser   | @ValidPassword   |
      | @InvalidUser | @ValidPassword   |
      | @InvalidUser | @InvalidPassword |
      | @ValidUser   | @InvalidPassword |
