@tag
Feature: Test if first play meets initial point threshold
  I want to use this feature to test if initial play meets 30 point threshold

  @basicInitialPoints
  Scenario Outline: Test Basic Initial Point Threshold
    Given Test Server is started
    And Player 1 hand starts with <initialHand>
    When Player 1 plays <tiles>
    Then table contains <table>
    And Player 1 hand contains <hand>
    Examples:
      | initialHand                       | tiles                 | table                                     | hand                                |
      | "R8 R9 R10 R11 R12 B9 B11 O9 O11" | "R10 R11 R12" 		  | "{ *R10 *R11 *R12 }\n"		              | "R8 R9 B9 B11 O9 O11 "              |
      | "R8 R9 R10 R11 B9 B11 O9 O11"     | "R11 B11 O11" 		  | "{ *R11 *B11 *O11 }\n"		              | "R8 R9 R10 B9 O9 "                  |
      | "R3 R4 R5 R6 R5 B5 G5"            | "R4 R5 R6,R5 B5 G5"   | "{ *R4 *R5 *R6 }\n{ *R5 *B5 *G5 }\n"      | "R3 "                               |
      | "R1 R7 B1 B7 G1 G7 O7"            | "R1 B1 G1,R7 B7 G7 O7"| "{ *R1 *B1 *G1 }\n{ *R7 *B7 *G7 *O7 }\n"  | ""                                  |
      | "R8 R9 R10 R11 B9 B11 O9 O11"     | "R8 R9 R10" 		  | ""		                                  | "R8 R9 R10 R11 B9 B11 O9 O11 ? ? ? "|
      | "R8 R9 R10 R11 B9 B11 O9 O11"     | "R9 B9 O9" 		      | ""		                                  | "R8 R9 R10 R11 B9 B11 O9 O11 ? ? ? "|
      | "R3 R4 R5 R5 R6 B5 G5"            | "R3 R4 R5,R5 B5 G5"   | ""		                                  | "R3 R4 R5 R5 R6 B5 G5 ? ? ? "       |

  @jokerInitialPoints
  Scenario Outline: Test Initial Point Threshold with Jokers
    Given Test Server is started
    And Player 1 hand starts with <initialHand>
    When Player 1 plays <tiles>
    Then table contains <table>
    And Player 1 hand contains <hand>
    Examples:
      | initialHand                       | tiles                 | table                                     | hand                                  |
      | "R8 R9 R10 R11 B9 B11 O9 O11 *"   | "R9 R10 *"            | "{ *R9 *R10 ** }\n"                       | "R8 R11 B9 B11 O9 O11 "               |
      | "R8 R9 R10 R11 B9 B11 O9 O11 *"   | "* B11 O11"           | "{ ** *B11 *O11 }\n"                      | "R8 R9 R10 R11 B9 O9 "                |
      | "R3 R4 R5 R5 R6 B5 G5 *"          | "R4 R5 R6,R5 B5 *"    | "{ *R4 *R5 *R6 }\n{ *R5 *B5 ** }\n"       | "R3 G5 "                              |
      | "R8 R9 R10 R11 B9 B11 O9 O11 *"   | "R11 * O11,R8 R9 R10" | "{ *R11 ** *O11 }\n{ *R8 *R9 *R10 }\n"    | "B9 B11 O9 "                          |
      | "R8 R9 R10 R11 B9 B11 O9 O11 *"   | "* R9 R10"            | ""                                        | "R8 R9 R10 R11 B9 B11 O9 O11 * ? ? ? "|
      | "R8 R9 R10 R11 B9 B11 O9 O11 *"   | "R9 * O9"             | ""                                        | "R8 R9 R10 R11 B9 B11 O9 O11 * ? ? ? "|
      | "R3 R4 R5 R5 R6 B5 G5 *"          | "R3 R4 R5,* B5 G5"    | ""                                        | "R3 R4 R5 R5 R6 B5 G5 * ? ? ? "       |
