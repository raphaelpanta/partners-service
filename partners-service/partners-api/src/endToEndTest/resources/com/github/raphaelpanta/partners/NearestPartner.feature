Feature: Nearest Partner

  Scenario: User located the nearest partner
    Given partners registered in service
    When user gives his location [-46.563892, -23.509415]
    Then server localizes partner "Ze da Ambev"