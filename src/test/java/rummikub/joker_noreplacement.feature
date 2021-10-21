@tag
Feature: Test Manipulation of Melds with Jokers without Replacement
  I want to use this feature to test manipulating melds with jokers

  @addRemove
  Scenario Outline: Add and remove tiles from meld with a joker in it
    Given Test Server is started
    And Player 1 has played initial points
    And Player 2 has played initial points
    And Player 1 hand starts with "R1 R2 R3 R4 B2 G2 *"
    And Player 2 hand starts with "R5 B1 B4 G1 G3 G4"
    When Player 1 plays <p1play>
    And Player 2 plays <p2play>
    Then table contains <table>
    And Player 2 hand contains <hand>
    Examples:
      | p1play                  | p2play                                  | table                                             | hand                         |
      | "R2 R3 *"               | "1:R2 1:R3 1:* R5"                      | "{ !R2 !R3 !* *R5 }\n"                            | "B1 B4 G1 G3 G4 "            |
      | "R1 R2 R3 *"            | "1:R1 1:R2 1:R3 1:* R5"                 | "{ !R1 !R2 !R3 !* *R5 }\n"                        | "B1 B4 G1 G3 G4 "            |
      | "R2 B2 G2 *"            | "1:G2 G3 G4"                            | "{ R2 B2 G2 * }\n"                                | "R5 B1 B4 G1 G3 G4 ? ? ? "   |
      | "R1 R2 R3 *"            | "1:R1 B1 G1"                            | "{ R1 R2 R3 * }\n"                                | "R5 B1 B4 G1 G3 G4 ? ? ? "   |

  @removeJoker
  Scenario Outline: Reuse Joker from Meld Without Replacing it
    Given Test Server is started
    And Player 1 has played initial points
    And Player 2 has played initial points
    And Player 1 hand starts with "R1 R2 R3 B2 G2 *"
    And Player 2 hand starts with "R12 R13"
    When Player 1 plays <p1play>
    And Player 2 plays <p2play>
    Then table contains <table>
    And Player 2 hand contains <hand>
    Examples:
      | p1play                  | p2play          | table               | hand                 |
      | "R1 R2 R3 *"            | "1:* R12 R13"   | "{ R1 R2 R3 * }\n"  | "R12 R13 ? ? ? "     |
      | "R2 B2 G2 *"            | "1:* R12 R13"   | "{ R2 B2 G2 * }\n"  | "R12 R13 ? ? ? "     |
