@tag
Feature: Simple Table Reuse
  I want to use this feature to test simple table reuse

  @setReuse
  Scenario Outline: Test Simple Table Reuse for Sets
    Given Test Server is started
    And Player 1 hand starts with "R11 B11 G11 O11"
    And Player 2 hand starts with "R12 B12 G12 B11 G11 R12 R13"
    And Player 3 hand starts with "O7 O8 O9 O10 O11 O12 O13"
    When Player 1 plays "R11 B11 G11 O11"
    And Player 2 plays "R12 B12 G12"
    And Player 3 plays "O7 O8 O9 O10 O11 O12 O13"
    And Player 1 chooses to draw
    And Player 2 plays <play>
    Then table contains <table>
    And Player 2 hand contains <hand>
    Examples:
    | play            | table                                                                                     | hand          |
    | "1:R11 G11 B11" | "{ B11 G11 O11 }\n{ R12 B12 G12 }\n{ O7 O8 O9 O10 O11 O12 O13 }\n{ !R11 *B11 *G11 }\n"    | "R12 R13 "    |
    | "1:R11 R12 R13" | "{ B11 G11 O11 }\n{ R12 B12 G12 }\n{ O7 O8 O9 O10 O11 O12 O13 }\n{ !R11 *R12 *R13 }\n"    | "G11 B11 "    |

  @runReuse
  Scenario Outline: Test Simple Table Reuse for Runs
    Given Test Server is started
    And Player 1 hand starts with "R11 B11 G11 O11 B7 R7 R13 B13 O8 O9"
    And Player 2 hand starts with "R12 B12 G12"
    And Player 3 hand starts with "O7 O8 O9 O10 O11 O12 O13"
    When Player 1 plays "R11 B11 G11 O11"
    And Player 2 plays "R12 B12 G12"
    And Player 3 plays "O7 O8 O9 O10 O11 O12 O13"
    And Player 1 plays <play>
    Then table contains <table>
    And Player 1 hand contains <hand>
    Examples:
      | play            | table                                                                                     | hand                |
      | "3:O7 R7 B7"    | "{ R11 B11 G11 O11 }\n{ R12 B12 G12 }\n{ O8 O9 O10 O11 O12 O13 }\n{ *R7 *B7 !O7 }\n"      | "R13 B13 O8 O9 "    |
      | "3:O13 R13 B13" | "{ R11 B11 G11 O11 }\n{ R12 B12 G12 }\n{ O7 O8 O9 O10 O11 O12 }\n{ *R13 *B13 !O13 }\n"    | "R7 B7 O8 O9 "      |
      | "O8 O9 3:O10"   | "{ R11 B11 G11 O11 }\n{ R12 B12 G12 }\n{ O11 O12 O13 }\n{ O7 O8 O9 }\n{ *O8 *O9 !O10 }\n" | "R7 R13 B7 B13 "    |
