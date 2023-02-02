
# Person REST API container

- [Person REST API container](#person-rest-api-container)
  * [What is it?](#what-is-it-)
  * [Dependencies](#dependencies)
  * [API](#api)
    + [Synchronous (REST)](#synchronous--rest-)
    + [Asynchronous (Kafka)](#asynchronous--kafka-)
  * [Data models](#data-models)
    + [Resource model](#resource-model)
      - [Person](#person)
      - [PersonEffectiveDatePropertySet](#personeffectivedatepropertyset)
  * [Effective dates](#effective-dates)

## What is it?
- Exposes [Person](#person) resource via RESTful API (POST, PUT, GET and DELETE)
- Publishes Person resource via [callisto-person-people topic](#topics) following changes to any of the Person resources that it knows about. Typically a change will be triggered by a POST, PUT or DELETE request made against the RESTful API.
- Manages the updating of Person when the [effective date for properties passes](#effective-dates)
- Exposes[PersonEffectiveDatePropertySet](#personeffectivedatepropertyset) resource via RESTful API (POST, GET and DELETE). 
- Publishes [PersonEffectiveDatePropertySet](#personeffectivedatepropertyset) resource via [callisto-person-people topic](#topics) following creation of new effective date properties, the removal of existing property resources or the removal of the owning person resource


## Dependencies

No hard dependencies. There is however a soft dependency on [metis-adapter](../metis-adapter/index.md) to seed and feed the REST API container with data. 

## API

### Synchronous (REST)
The endpoints will be implemented on top of the JPARest Java library therefore the response codes, payloads and query params are expected to be governed by the conventions that JPARest lays out.

#### [Person](#person) resource
POST, PUT, GET and DELETE

#### [PersonEffectiveDatePropertySet](#personeffectivedatepropertyset) resource
POST, GET and DELETE
Note that it is not possible to update a property. Note also that a property cannot exist without a person record therefore if the "owning" Person record is deleted then so too should all related properties (this should be a transactional delete)

### Asynchronous (Kafka)

#### Topics

| topic name | message contents | partition key | trigger(s) |
|--|--|--|--|
| callisto-person-people | Person resource | person.id and person.tenantId | creation of a new Person, deletion of an existing person and updating of an existing person. Note that the Person RESR API container can trigger updates internally (see [effective dates](#effective-dates) |
| callisto-person-people | PersonEffectiveDatePropertySet resource | personEffectiveDatePropertySet.personId and personEffectiveDatePropertySet.personTenantId | creation of a new PersonEffectiveDateProperty. See [effective dates](#effective-dates) 


#### Consumers
**TODO** link off to [accruals-rest-api/docs/containers/](https://github.com/UKHomeOffice/callisto-accruals-restapi/) when the mid-year agreement design has been documented

## Data models

### Resource model

#### Person
| property name | description | type | cardinality |
|--|--|--|--|
| id | Identifier assigned by person service Unique within the person service's data store  |  | 1..1 |
| tenantId | Tenant that this person record belongs to |  |  |
| version | The version number of the given Person. Each time a Person is updated the value increments by one. A brand new Person has a starting value of 1 | Integer | 1..1 |
| firstName |  | String | 1..1 |
| lastName |  | String | 1..1 |
| fteValue | Full Time Equivalent value Expressed as a fraction between 0 and 1 For example a full time worker would have a value of 1 but a half time worker would have a value of 0.5 | Double | 1..1 |
| termsAndConditions | MODERNISED or PRE-MODERNISED | enum  | 1..1 |

#### PersonEffectiveDatePropertySet
Note that this resource cannot exist without a Person resource

| property name | description | type | cardinality |
|--|--|--|--|
| personId| id of the person that owns the properties | | 1..1 |
| personTenantId| tenant id of the person that owns the properties | | 1..1 |
| personVersionId| version the person that owns the properties | Integer| 1..1 |
| personSchema| the schema for the Person that this property is a member of (should include version of schema) | uri| 1..1 |
| properties| a collection of property resources that all belong to the given version of the person. All properties should be declared on the schema. No duplicate properties are allowed i.e. property.name must be unique within a given PersonEffectiveDatePropertySet | property | 1..* |
| effectiveDate| the effective date that all property instances in properties share| datetime (ISO 8601)| 1..1 |

#### Property
Note that this resource is not exposed directly via the RESTful API. Instead it will always be contained within the PersonEffectiveDatePropertySet resource

| property name | description | type | cardinality |
|--|--|--|--|
| name | the name of the property as it appears in the schema| String  | 1..1 |
| value| the value of the property. The schema can be used to determine the value's concrete type| Object| 1..1 |

## Effective dates
At the point when an effective date passes the property it is associated with will be updated to hold the new value. The Person record in the datastore will be updated with this new version of the Person. This in turn will result in a new event being published for the now updated Person. Note that the logic around effective dates and subsequently updating the Person record must be written to deal with optimistic locking errors

Consider the case where the current date is 1st May and a new value for FTEValue Effective From 1st October is received. 

In this case whilst it is correct to emit the FTEValue as a PersonEffectiveDatePropertySet. **However** if this is the ONLY change to the Person data there is no point emitting the complete Person record as it has not changed as of 1st May although it will on 1st October. 

| effective date | value| 
|--|--|
|1st April|1.000|
|1st October|0.500|

Person record as of 1st May (some properties omitted for clarity) -
```
{
  "id": 12,
  "tenantId": "6B29FC40-CA47-1067-B31D-00DD010662DA",
  "version": 1,
  "fteValue": 1.000
  }
```
PersonEffectiveDatePropertySet published upon receipt of the new value for FTEValue - 

```
{
  "personId": 12,
  "personTenantId": "6B29FC40-CA47-1067-B31D-00DD010662DA",
  "personVersion": 1,
  "personSchema" "https://callisto.digital.homeoffice.gov.uk/person/schemas/person?version=1.0.0"
  "effectiveDate": "2023-10-01T00:00:00+00:00",
  "properties" : [{
    "name": "fteValue",
    "value": 0.500,
  }]
  ```

On the 1st October the Person REST API should update the Person record with the new FTEValue which should then in turn trigger the publication of a new Person record on the callisto-person-people topic (some properties omitted for clarity) - 

```
{
  "id": 12,
  "tenantId": "6B29FC40-CA47-1067-B31D-00DD010662DA",
  "version": 2,
  "fteValue": 0.500
  }
```
Note how the version number has been incremented

