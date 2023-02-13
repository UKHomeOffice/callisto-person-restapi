
CREATE SCHEMA IF NOT EXISTS person;

CREATE TYPE TermAndConditions as ENUM ('MODERNISED', 'PRE_MODERNISED');

CREATE TABLE IF NOT EXISTS person.person (
    id VARCHAR(36)  PRIMARY KEY,
    tenant_id VARCHAR(36) NOT NULL,
    version INT NOT NULL default 1,
    first_name VARCHAR(50) NOT NULL ,
    last_name VARCHAR(50) NOT NULL,
    fte_value NUMERIC (5,4) NOT NULL,
    terms_and_conditions TermAndConditions NOT NULL
    );
