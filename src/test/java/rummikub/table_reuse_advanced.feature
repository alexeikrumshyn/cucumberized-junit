@tag
Feature: Advanced Table Reuse
  I want to use this feature to test advanced table reuse

  @simple3
  Scenario Outline: Test simple3 reuse video
    Given Test Server is started
    And Player 1 hand starts with "R11 O11 G11 B11 O4 R4 R8"
    And Player 2 hand starts with "R12 B12 G12"
    And Player 3 hand starts with "R4 G4 B4 R5 R6 R7"
    When Player 1 plays "R11 B11 G11 O11"
    And Player 2 plays "R12 B12 G12"
    And Player 3 plays "R4 G4 B4,R5 R6 R7"
    And Player 1 plays <play>
    Then table contains <table>
    And Player 1 hand contains <hand>
    Examples:
      | play                                     | table                                                                                     | hand          |
      | "O4 3:R4 3:B4 3:G4,R4 3:R5 3:R6 3:R7 R8" | "{ R11 B11 G11 O11 }\n{ R12 B12 G12 }\n{ !R4 !B4 !G4 *O4 }\n{ *R4 !R5 !R6 !R7 *R8 }\n"    | ""            |

  @simple1
  Scenario Outline: Test simple1 reuse video
    Given Test Server is started
    And Player 1 hand starts with "R11 O11 B11 G1 B12 B13"
    And Player 2 hand starts with "G11 G12 G13"
    And Player 3 hand starts with "R4 G4 B4 R5 R6 R7"
    When Player 1 plays "R11 B11 O11"
    And Player 2 plays "G11 G12 G13"
    And Player 3 plays "R4 G4 B4,R5 R6 R7"
    And Player 1 plays <play>
    Then table contains <table>
    And Player 1 hand contains <hand>
    Examples:
      | play                                                         | table                                                                                        | hand          |
      | "2:G11 2:G12 2:G13 G1,4:G11 1:R11 1:B11 1:O11,4:B11 B12 B13" | "{ R4 B4 G4 }\n{ R5 R6 R7 }\n{ !G12 !G13 *G1 }\n{ !R11 !G11 !O11 }\n{ !B11 *B12 *B13 }\n"    | ""            |

  @simple2
  Scenario Outline: Test simple2 reuse video
    Given Test Server is started
    And Player 1 hand starts with "R11 O11 B11 G10 B10 R13"
    And Player 2 hand starts with "O12 B12 G12"
    And Player 3 hand starts with "R7 R8 R9 R10 R11 R12"
    When Player 1 plays "R11 B11 O11"
    And Player 2 plays "B12 G12 O12"
    And Player 3 plays "R7 R8 R9 R10 R11 R12"
    And Player 1 plays <play>
    Then table contains <table>
    And Player 1 hand contains <hand>
    Examples:
      | play                                                 | table                                                                                            | hand          |
      | "3:R7 3:R8 3:R9 3:R10 3:R11 3:R12 R13,B10 G10 3:R10" | "{ R11 B11 O11 }\n{ B12 G12 O12 }\n{ !R11 !R12 *R13 }\n{ !R7 !R8 !R9 }\n{ !R10 *B10 *G10 }\n"    | ""            |

  @complex
  Scenario Outline: Test complex reuse video
    Given Test Server is started
    And Player 1 hand starts with "R11 B11 G11 R3 R4 R5 B1 B2 B3 B4 G4 B4 O5 O13"
    And Player 2 hand starts with "O1 O2 O3 O4 R3 O3 G3 B3 G3 G4 G5"
    And Player 3 hand starts with "O7"
    When Player 1 plays "R11 B11 G11,R3 R4 R5,B1 B2 B3 B4"
    And Player 2 plays "O1 O2 O3 O4,R3 B3 G3 O3,G3 G4 G5"
    And Player 3 has to draw
    And Player 1 plays <play>
    Then table contains <table>
    And Player 1 hand contains <hand>
    Examples:
      | play                                                                            | table                                                                                                                                        | hand          |
      | "2:R4 B4 G4,2:R5 O5 6:G5,O13 4:O1 4:O2 4:O3 4:O4,8:O4 3:B4 5:G4,5:G3 2:R3 4:B3" | "{ R11 B11 G11 }\n{ B1 B2 B3 }\n{ R3 G3 O3 }\n{ !R4 *B4 *G4 }\n{ !R5 !G5 *O5 }\n{ *O13 !O1 !O2 !O3 }\n{ !B4 !G4 !O4 }\n{ !R3 !B3 !G3 }\n"    | ""            |
