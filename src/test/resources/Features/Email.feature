Feature: EmailFeature
  This feature deals with the Email functionality of the applicaiton

  @P0
  @Connect
  @Exchange
  Scenario: Connect Exchange in Tact Email tab bar
    Given Common: I switch to "Email" page from tab bar
    When Email: I connect with "Exchange" email account inside Email tab bar
    When Settings: I sign in the Exchange account
#    Then Settings: I switch to "Exchange" option in Sources settings page
#    And Settings: I disconnect the "Exchange" account

  @P0
  @Connect  
  @Gmail
  Scenario: Add Gmail dataSources in Tact
    Given Common: I switch to "Email" page from tab bar
    When Email: I connect with "Gmail" email account inside Email tab bar
    When Common: I switch to "Webview" driver
    Then Settings: I sign in the Gmail account
#    Then Settings: I switch to "Gmail" option in Sources settings page
#    And Settings: I disconnect the "Gmail" account
  
  @P1
  @ExchangeSend
  Scenario: send an email from exchange account in Tact
    Given Common: I switch to "Email" page from tab bar
    When Email: I send an new email from "" mailType, "All Inboxes" option in MailBoxes page
    Then Email: I create a simply email To "anotherPlatformExchangeEmail", Subject "Subject" and body ""
#    And Email: I "do not" send email and "w/o" save draft
    And Email: I "do" send email and "w/o" save draft
    And Email: I verify the email

  @P1
  @gmailSend
  Scenario: send an email from exchange account in Tact
    Given Common: I switch to "Email" page from tab bar
    When Email: I send an new email from "gmail" mailType, "Inbox" option in MailBoxes page
    Then Email: I create a simply email To "anotherPlatformExchangeEmail", Subject "Subject" and body ""
#    And Email: I "do not" send email and "w/o" save draft
    And Email: I "do" send email and "w/o" save draft
    And Email: I verify the email