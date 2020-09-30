Feature: Find a partner

  Scenario: User finds a partner by its id
    Given that a partner named Adega da Cerveja - Pinheiros exists
    And use its id
    Then return a partner "Adega da Cerveja - Pinheiros"
