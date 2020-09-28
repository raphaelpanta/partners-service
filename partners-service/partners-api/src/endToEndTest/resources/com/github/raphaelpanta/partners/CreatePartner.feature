Feature: Creating a Partner

  Scenario: Partner created successfully
    Given a partner named "Adega da Cerveja - Pinheiros"
    And its owner named "Zé da Silva"
    And its brazilian legal document is "1432132123891/0001"
    And its coverage area is a MultiPolygon with coordinates
      | 30.0 | 20.0 |
      | 45.0 | 40.0 |
      | 10.0 | 40.0 |
      | 30.0 | 20.0 |
    And its coverage area is a MultiPolygon with coordinates
      | 15.0 | 5.0  |
      | 40.0 | 10.0 |
      | 10.0 | 20.0 |
      | 5.0  | 10.0 |
      | 15   | 5    |
    And its address is a "Point" with coordinates [-46.57421, -21.785741]
    Then Partner should be created successfully