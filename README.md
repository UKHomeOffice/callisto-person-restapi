# Callisto Person REST API

## Building the project

### Github Package dependencies
In order to pull in github package dependencies you will need a Github Personal Access Token.
This token will need the minimum of 'packages:read' permissions.

Assign the value of the token to an environment variable with the name GITHUB_TOKEN

Then run the following to build the project

```sh
$ mvn -s ./person_settings.xml clean install
```