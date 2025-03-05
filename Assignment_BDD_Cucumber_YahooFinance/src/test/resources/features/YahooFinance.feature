Feature: Yahoo Finance Stock Search
  Scenario: Search and Verify Tesla Stock Details
    Given User is on Yahoo Finance homepage
    When User searches for "TSLA"
    Then First autosuggest entry should be "TESLA Inc."
    When User clicks on the first autosuggested entry
    Then Stock price should be greater than 200
    Then Capture additional stock details

  Scenario: Empty Search Query
    Given User is on Yahoo Finance homepage
    When User clicks on the search box but does not enter any text
    Then No autosuggest should be displayed

  Scenario: Invalid Stock Symbol
    Given User is on Yahoo Finance homepage
    When User searches for "XYZ123"
    Then Search results should show "No results found"

  Scenario: Slow Network Handling
    Given User is on Yahoo Finance homepage
    When The network is slow
    Then The test should wait for results up to 10 seconds

  Scenario: Broken Search Functionality
    Given User is on Yahoo Finance homepage
    When The search feature is disabled
    Then The test should log an error message