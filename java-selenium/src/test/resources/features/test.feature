Feature: Automation_practice
    A site to practice automation.

    Background:
        Given we are on the automation practice page

    # Test 1
    @test_case_1
    Scenario Outline: Fill_the_pool_with_user
        Then we go to the pool section
        And we select the year <year>
        Then we select the user <user>
        And we select the week <week>
        Then we select our pool options
        And we save the pool configuration
        Then we verify the username <user> is in the dialog
        And we save the pool settings
        And we verified the pool is saved successfully

        Examples:
        | year | week      | user     |
        | 2023 | Semana 4  | Aaron QA |
        | 2023 | Semana 5  | Aaron QA |
        | 2023 | Semana 6  | Aaron QA |
        | 2023 | Semana 7  | Aaron QA |
        | 2023 | Semana 8  | Aaron QA |
        | 2023 | Semana 9  | Aaron QA |
        | 2023 | Semana 10 | Aaron QA |
        | 2023 | Semana 11 | Aaron QA |
        | 2023 | Semana 12 | Aaron QA |
        | 2023 | Semana 13 | Aaron QA |
        | 2023 | Semana 14 | Aaron QA |
        | 2023 | Semana 15 | Aaron QA |
        | 2023 | Semana 16 | Aaron QA |
        | 2023 | Semana 17 | Aaron QA |


    @test_case_2
    Scenario: Fill_the_pool_without_user
        Then we go to the pool section
        And we select the year 2023
        Then we select our pool options
        And we save the pool configuration
        And we verified username is missing text


    @test_case_3
    Scenario: Leave_unfilled_pool
        Then we go to the pool section
        And we select the year 2023
        Then we select the user Aaron QA
        Then we fill almost all of our pool options
        And we save the pool configuration
        And we verified unfulfilled options text


    @test_case_4
    Scenario Outline: Fill_the_duplicate_pool
        Then we go to the pool section
        And we select the year <year>
        Then we select the user <user>
        And we select the week <week>
        Then we select our pool options
        And we save the pool configuration
        Then we verify the username <user> is in the dialog
        And we save the pool settings
        And we verified the pool is saved successfully
        Then we retry send the pool configuration with same user
        And we verify the error message
    
        Examples:
        | year | week      | user     |
        | 2024 | Semana 18 | Diana QA |


    @test_case_5
    Scenario Outline: Fill_the_pool_with_invalid_user
        When we go to my score section
        Then we select the user <user>
        And we search the data
        Then we verify my score is correct
    
        Examples:
        | user        |
        | Cristian QA |