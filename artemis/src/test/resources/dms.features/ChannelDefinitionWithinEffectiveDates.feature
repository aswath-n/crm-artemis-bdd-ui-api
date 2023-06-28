@CP-7292
@umid
Feature: Channel Definition Effective Dates Must Be Within Definition Effective Dates

    @CP-7292-01 @ui-ecms1
    Scenario: start date before Start Date of parent Definition
        Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
        And I navigate to the Correspondence Definitions Screen of Project:"current"
        When I click on given CorrespondenceDefinition ID "3G6o3SVei6"
        And I store Correspondence definition Start and End Dates
        And Click at Add Channel button
        And I try entering in start date before Start Date of parent Definition
        Then the system does not save, and issues an error "Start Date of Channel Definition cannot be earlier than Start Date of parent Correspondence Definition"

    @CP-7292-02 @ui-ecms1
    Scenario: end date after End Date of parent Definition
        Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
        And I navigate to the Correspondence Definitions Screen of Project:"current"
        When I click on given CorrespondenceDefinition ID "3QiTRAxh2e"
        And I store Correspondence definition Start and End Dates
        And Click at Add Channel button
        And I try entering in end date after End Date of parent Definition
        Then the system does not save, and issues an error "End Date of Channel Definition cannot be later than End Date of parent Correspondence Definition"

    @CP-7292-04 @ui-ecms2
    Scenario: end date before Start Date of parent Definition
        Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
        And I navigate to the Correspondence Definitions Screen of Project:"current"
        When I click on given CorrespondenceDefinition ID "234jk"
        And I store Correspondence definition Start and End Dates
        And Click at Add Channel button
        And I try entering in end date before Start Date of parent Definition
        Then the system does not save, and issues an error "END DATE cannot be earlier than START DATE."

    @CP-7292-04 @ui-ecms2
    Scenario: Copy Definition End Date to Channel Definition
        Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
        And I navigate to the Correspondence Definitions Screen of Project:"current"
        When I click on given CorrespondenceDefinition ID "3G6o3SVei6"
        And I Successfully update Correspondence Definition End Date
        Then system updates any child Channel Definitions whose End Date is blank or later
