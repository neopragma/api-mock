Feature: Reverse Polish Notation calculator service

Scenario: As a person, I want to know what I can do with the RPN service.

Given I want to know how to call the RPN service
When I invoke the RPN service
Then I receive usage documentation

Scenario: As a person, I want to do calculations so that I can have numbers.

Given I push "6"
And "4"
And "5"
And "+"
And "*"
When I invoke the RPN service
Then the result is "54.0"
