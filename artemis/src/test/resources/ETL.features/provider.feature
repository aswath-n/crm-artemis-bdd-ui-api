Feature: ETL provider file uploads

  @ETL-CP-28523 @ETL-EE-IN @IN-EB-ETL-Regression @sobir
  Scenario: INEB - Reconcile Provider+Location Attributes for Plan + State Provider Id + Geocode
    Given I create new provider line by name "28523-1a" with data
      | new |[blank]|
    And I create next provider line by name "28523-1b" based on "28523-1a" with data
      | same |[blank]|
    Given I create new provider line by name "28523-2a" with data
      | new |[blank]|
    And I create next provider line by name "28523-2b" based on "28523-2a" with data
      | GroupId | providerGroupId:: |
    Given I create new provider line by name "28523-3a" with data
      | AgeRange | EE |
    And I create next provider line by name "28523-3b" based on "28523-3a" with data
      | AgeRange | CC |
    Given I create new provider line by name "28523-4a" with data
      | AgeRange | EE |
    And I create next provider line by name "28523-4b" based on "28523-4a" with data
      | AgeRange | BB |
    Given I create new provider line by name "28523-5a" with data
      | PanelSizeHold | Y |
    And I create next provider line by name "28523-5b" based on "28523-5a" with data
      | PanelSizeHold | N |
    Given I create new provider line by name "28523-6a" with data
      | panelCount | 150 |
    And I create next provider line by name "28523-6b" based on "28523-6a" with data
      | panelCount | 160 |
    Given I create new provider line by name "28523-7a" with data
      | PanelSize | 250 |
    And I create next provider line by name "28523-7b" based on "28523-7a" with data
      | PanelSize | 260 |
    And I create "PROVIDER" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/Provider/to_Max/" with names list
      | 28523-1a |
      | 28523-1b |
      | 28523-2a |
      | 28523-2b |
      | 28523-3a |
      | 28523-3b |
      | 28523-4a |
      | 28523-4b |
      | 28523-5a |
      | 28523-5b |
      | 28523-6a |
      | 28523-6b |
      | 28523-7a |
      | 28523-7b |
    And I run talent job for environment "QE" project "INEB" job name "planProvider_INEB"
    And I verify warning messages on last ETL provider upload in database with data
      | 28523-1a |[blank]|
      | 28523-3a | Duplicate NPI and Location has different Minimum Age Limit values, using latest non-null value  |
      | 28523-4a | Duplicate NPI and Location has different Maximum Age Limit values, using latest non-null value  |
      | 28523-5a | Duplicate NPI and Location has different Panel Status values, using latest non-null value       |
      | 28523-6a | Duplicate NPI and Location has different Current Panel Size values, using latest non-null value |
      | 28523-7a | Duplicate NPI and Location has different Panel Size Limit values, using latest non-null value   |
#    And I run JDBC

  @ETL-CP-32243 @ETL-EE-IN @IN-EB-ETL-Regression @turcan
  Scenario: ETL Provider reconciliation updates
    Given I create new provider line by name "28523-1a" with data
      | ProviderPMPId | stateproviderid:: |
      | GroupId       | providerGroupId:: |
      | GroupName     | name::            |
      | MCEId         | 755726440         |
    And I create next provider line by name "28523-1b" based on "28523-1a" with data
      | new |[blank]|
    And I create next provider line by name "28523-1c" based on "28523-1a" with data
      | new |[blank]|
    Given I create new provider line by name "28523-2a" with data
      | new |[blank]|
    Given I create new provider line by name "28523-2b" with data
      | new |[blank]|
    Given I create new provider line by name "28523-3a" with data
      | new |[blank]|
    And I create "PROVIDER" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/Provider/to_Max/" with names list
      | 28523-1a |
      | 28523-1b |
      | 28523-1c |
      | 28523-2a |
      | 28523-2b |
      | 28523-3a |
    And I run talent job for environment "QE" project "INEB" job name "planProvider_INEB"
    Then I validate row count according to matching rules

  @ETL-CP-40172 @ETL-EE-IN @IN-EB-ETL-Regression @elshan
  Scenario: Update of provider load response for ERR codes
    Given I create new provider line by name "40172-1a" with data
      | ProviderPMPId | stateproviderid:: |
      | GroupId       | providerGroupId:: |
      | GroupName     | name::            |
      | MCEId         | 755726440         |
    And I create "PROVIDER" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/Provider/to_Max/" with names list
      | 40172-1a |
    And I run talent job for environment "QE" project "INEB" job name "planProvider_INEB"
    Given I create new provider line by name "40172-1b" with data
      | ProviderPMPId | 40172-1a.ProviderPMPId |
      | GroupId       | 40172-1a.GroupId       |
      | GroupName     | 40172-1a.GroupName     |
      | MCEId         | 40172-1a.MCEId         |
    And I create "PROVIDER" file and send to S3 bucket "max-etl-ineb-non-prod" S3folder "QE/Provider/to_Max/" with names list
      | 40172-1b |
    And I run talent job for environment "QE" project "INEB" job name "planProvider_INEB"
    Then I verify error code on last ETL provider upload in database with data
      | errorCode | ERR032, ERR033 |
    Then I validate status code on Response Temp table