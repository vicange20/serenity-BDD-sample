@googlesearch @web
Feature: Google Search Demo
  As a test automation engineer
  I want to test web applications
  Because I can!

  @webdemo
  Scenario: Search Google for a keyword
    Given I open the Google home page
    When I search Google for "Deva"
    Then the results I get are related to "Deva"