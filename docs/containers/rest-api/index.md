# Person REST API container

## What is it?
- Exposes Person resource via RESTful API (POST, PUT, GET and DELETE)
- Publishes Person resource via callisto-person-people topic following changes to any of the Person resources that it knows about. Typically a change will be triggered by a POST, PUT or DELETE action


## Dependencies

No hard dependencies
**TODO** - describe soft dependency on metis-adapter to seed and feed the rest api with data

## API

### Synchronous (REST)
**TODO**


### Asynchronous (Kafka)

**TODO** 
- describe effective date changes
- describe person resource changes and how person monitors effective date properties internally

#### Topics

| topic name | message contents | partition key |
|--|--|--|
| callisto-person-people | Person resource | person.id and person.tenantId |
| callisto-person-people | EffectiveDateProperty resource | effectiveDateProperty.personId and effectiveDateProperty.personTenantId |
| callisto-person-people | EffectiveDateProperties resource | effectiveDateProperties.personId and effectiveDateProperties.personTenantId |

#### Consumers
**TODO** link off to accruals-rest-api/containers/

## Data models

### Storage model
**TODO**

### Resource model

**TODO** 
- pull in person and effective date models from https://collaboration.homeoffice.gov.uk/display/EAHW/Person and https://collaboration.homeoffice.gov.uk/display/EAHW/Person+REST+API+component