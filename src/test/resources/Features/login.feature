Feature: LoginFeature
  This feature deals with the login functionality of the applicaiton

  @P1
  @login
  Scenario: Login existing app with single-user username and password
    Given Login: I click connect with SF button
    And Common: I switch to "Webview" driver
    And Login-Webview: I "do not" send usage to google chrome and "do not" sign in Chrome
    And Login-Webview: I enter the user email address
    And Login-Webview: I "do not" check remember me
    And Login-Webview: I click login button in "login" process
    When Common: I switch to "Native_APP" driver
    And Login: Waiting for Syncing finished in "login" process

  @P1
  @logout
  Scenario: Logout from current user
    When Common: I switch to "Settings" page from tab bar
    And More: I log out from the app

  @P0
  @deleteAccount
  Scenario: Delete account from TactAI app
    Given Common: I switch to "Settings" page from tab bar
    Then More: I delete current account from the app