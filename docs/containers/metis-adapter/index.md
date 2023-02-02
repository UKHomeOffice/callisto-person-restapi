# Metis Adapter container

## METIS

Metis is a suite of applications which include ERP, customer relationship management (CRM) and human capital management (HCM) and is the master for most of the information about an employee that Callisto requires in order to operate.

## What is it?

The METIS Adaptor is responsible for consuming the information supplied by METIS in the form defined by the interface specification and transforming it into the form required by Callisto which, in simple terms is our storage model.

In time there maybe many types of data handled by the Adapter but, for now this design only deals with the Person Profile data which, at high level contains some of the information that describes an employee in terms of contractual & other terms which impact Callisto's processing of their time. For example, we need to know whether and employee is full time or part-time as this impacts both availablilty and meal break allowance for some staff.

## Dependencies

### External

METIS, as described earlier is the master source of this information and will be responsible for supplying the information. Those discussions with METIS have not yet reached a conclusion regarding the form of the transfer but options under consideration include

1. Publish and Subscribe
2. Secure FTP transfer of an XML or flat file

We are keen that any such interface is asynchronous.


## API
TODO - SFTP, REST

## Data model

Callisto will require the following information although this definition is likely to be refined as Callisto is developed. This table is a copy of the information contained in the working document which exists in sharepoint [here](https://homeofficegovuk.sharepoint.com/:w:/s/CALLISTO/EQeNex_Z5JBMgYuWcRuZ9a0B2PJmZ7GNiaTl15GIv22Y2Q?e=h605Ur) and is the master.


| Field Name                             | Length         | Description / Rules                                                                                                              |
| -------------------------------------- | -------------- | -------------------------------------------------------------------------------------------------------------------------------- |
|                                        |                |                                                                                                                                  |
| First Name                             | VARCHAR2 (150) |                                                                                                                                  |
| Preferred First Name                   | VARCHAR2 (150) | Can be null.  If set then is used in preference when displaying the person’s name.                                               |
| Last Name                              | VARCHAR2 (150) |                                                                                                                                  |
| Employee Number                        | VARCHAR2 (30)  |  Metis Staff Number                                                                                                              |
| Business Unit                          | VARCHAR        | Business Unit / Directorate the employee belongs to. i.e BF, UK V & I etc. Needs discussion                                      |
| EmploymentType                         | VARCHAR2 (150) | Value for the People Group Segment are:  AAA, AHA, SDA, IPA, FlexibleWorker. Future values e.g Flexi, Contractor                 |
| EmploymentType Effective Date          |                | Future dated changed to the values in this record                                                                                |
| TemporaryAssignmentType                |                | e.g Detached Duty, TCA, Transferred Out.                                                                                         |
| TemporaryAssignmentStartDate           | DATE (8)       | Date when the temporary assignment starts.                                                                                       |
| TemporaryAssignmentEndDate             | DATE (8)       | Date when the temporary assignment ends                                                                                          |
| TCABusinessGrade                       | VARCHAR2 (240) | The temporary business grade during Tempory Cover [TCA]. Effective for same period as TemporaryAssignment [if TCA]               |
| Assignment Status                      | VARCHAR2 (40)  | Active, Maternity Leave etc                                                                                                      |
| Assignment Status Effective Start Date | DATE (8)       |                                                                                                                                  |
| FTE Value                              | NUMBER (25)    | Decimal format to 4 decimal places                                                                                               |
| FTE Value Effective Start Date         | DATE (8)       | The file should contain any future dated changes on this table                                                                   |
| Modernised                             | VARCHAR(1)     | Y/N                                                                                                                              |
| ModernisedEffectiveStartDate           | DATE (8)       |                                                                                                                                  |
| SalaryBasis                            | ?????          |                                                                                                                                  |
| Working Hours                          | VARCHAR2 (22)  | Takes into account FTEValue. Same Effective Date                                                                                 |
| Business Grade                         | VARCHAR2 (240) |                                                                                                                                  |
| Actual Termination Date                | DATE (8)       | All leavers where actual leaving date is up to 30 days after sysdate. After that point they should no longer appear in the file. |
| Cost Centre                            |                | We would need the Hierarchy CC Feed to load in/maintain.                                                                         |
| HR Line Manager Staff Number           |                | Line Managers Staff Number                                                                                                       |
| Work Email Address                     |                | Required in case of email notifications.                                                                                         |
| Personal Email Address                 |                | Required in case of email notifications.                                                                                         |
| Salary                                 |                | For calculation of On Call and calculating of write off hours etc.                                                               |
| SalaryEffectiveStartDate               |                |                                                                                                                                  |
| Internal_location_code                 |                | See Question section above                                                                                                       |
| LocationEffectiveStartDate             |                | See Question section above                                                                                                       |
