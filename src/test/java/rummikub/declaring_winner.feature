@tag
Feature: Declaring Winner
  I want to use this feature to test player using all tiles and declaring winner

  @winner
  Scenario: Test Player Winning and Ending Game
    Given Test Server is started
    And Player 1 hand starts with "G1 G2 O2 R3 B3 B3 R5 B6 O7 R9 R10 B11 B12 B13"
    And Player 2 hand starts with "R2 B2 G2 O2 G3 G4 G6 G7 O4 O5 O6 O7 O8 O9"
    And Player 3 hand starts with "R4 O6 B6 B7 R7 G8 R10 R11 R12 R13 B10 B11 B12 B13"
    When Player 1 draws "R2"
    And Player 2 draws "G5"
    And Player 3 plays "R10 R11 R12 R13,B10 B11 B12 B13"
    And Player 1 plays "G2 O2 R2,B11 B12 B13"
    And Player 2 plays "R2 B2 G2 O2,G3 G4 G5 G6 G7,O4 O5 O6 O7 O8 O9"
    Then Player 2 wins
    And Player 1 score is -47
    And Player 2 score is 0
    And Player 3 score is -38
