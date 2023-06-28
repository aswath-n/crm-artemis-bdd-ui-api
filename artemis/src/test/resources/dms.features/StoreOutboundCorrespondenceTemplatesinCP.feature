Feature: Outbound Correspondence Definition part2

  @CP-39673 @CP-39673-01 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint to store template object for pdf file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | templateObject     | pdf |
    And I validate the languagetemplateobject response
      | statuscode | 201                                          |
      | message    | Stored the template data object successfully |
    Then I initiated get languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | statuscode         | 200 |


  @CP-39673 @CP-39673-02 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint to store template object for tif file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | templateObject     | tif |
    And I validate the languagetemplateobject response
      | statuscode | 201                                          |
      | message    | Stored the template data object successfully |
    Then I initiated get languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | statuscode         | 200 |

  @CP-39673 @CP-39673-03 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint to store template object for docx file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 3    |
      | templateObject     | docx |
    And I validate the languagetemplateobject response
      | statuscode | 201                                          |
      | message    | Stored the template data object successfully |
    Then I initiated get languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | statuscode         | 200 |

  @CP-39673 @CP-39673-04 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint to store template object for doc file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | templateObject     | doc |
    And I validate the languagetemplateobject response
      | statuscode | 201                                          |
      | message    | Stored the template data object successfully |
    Then I initiated get languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | statuscode         | 200 |

  @CP-39673 @CP-39673-05 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint to store template object for txt file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | templateObject     | txt |
    And I validate the languagetemplateobject response
      | statuscode | 201                                          |
      | message    | Stored the template data object successfully |
    Then I initiated get languagetemplateobject endpoint
      | languageTemplateId | 3   |
      | statuscode         | 200 |


  @CP-39673 @CP-39673-06 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint to store template object for 18mb pdf file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 3        |
      | templateObject     | 18mbfile |
    And I validate the languagetemplateobject response
      | statuscode | 400                                      |
      | message    | Could not upload the file: 18mbfile.pdf! |

  @CP-39673 @CP-39673-07 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint without languageTemplateId
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId |     |
      | templateObject     | pdf |
    And I validate the languagetemplateobject response
      | statuscode | 400                                        |
      | message    | languageTemplateId cannot be null or empty |

  @CP-39673 @CP-39673-08 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint with invalid languageTemplateId
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 9999999999999 |
      | templateObject     | pdf           |
    And I validate the languagetemplateobject response
      | statuscode | 400                                  |
      | message    | Could not upload the file: text.pdf! |

  @CP-39673 @CP-39673-09 @API-ECMS @Keerthi
  Scenario: Validate get languagetemplateobject endpoint with invalid template id
    Then I initiated get languagetemplateobject endpoint
      | languageTemplateId | 9999999999999 |
      | statuscode         | 404           |

  @CP-39673 @CP-39673-10 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint with invalid languageTemplateId datatype
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | templateid |
      | templateObject     | pdf        |
    And I validate the languagetemplateobject response
      | statuscode | 400                                  |
      | message    | Could not upload the file: text.pdf! |

  @CP-39673 @CP-39673-11 @API-ECMS @Keerthi
  Scenario: Validate languagetemplateobject endpoint with  filename length >128
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated post languagetemplateobject endpoint
      | languageTemplateId | 3         |
      | templateObject     | 130length |
    And I validate the languagetemplateobject response
      | statuscode | 400                                                                                                                                                            |
      | message    | Could not upload the file: GLLq4eFOV0v6ca4T4oNj17XMLOgFR1wFjT0kuHONgEoy9v5lwEMHh4NtqAI7tcYmmgeHP2AmtPsOH6Bpam46NFODIKh7DxfobBtMdOQ2ClfI5fQDXgM8keJzpdqO.docx!" |


    