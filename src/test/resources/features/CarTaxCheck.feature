Feature: Validate vehicle registration details


  Scenario: vehicle registration details
    Given read and extract vehicle registration numbers from car input file
    And I am on car tax check page
    When I enter each registration and extract the vehicle details
    Then compare vehicle details with output file
