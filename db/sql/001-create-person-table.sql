
CREATE SCHEMA IF NOT EXISTS person;

CREATE TABLE IF NOT EXISTS person.person (
    id VARCHAR(36)  PRIMARY KEY,
    tenant_id VARCHAR(36) NOT NULL,
    version INT NOT NULL default 1,
    first_name VARCHAR(50) NOT NULL ,
    last_name VARCHAR(50) NOT NULL,
    fte_value NUMERIC (1,1) NOT NULL,
    terms_and_conditions VARCHAR(30) NOT NULL
    );

-- just test data, will be removed
INSERT INTO person.person (id,tenant_id,version,first_name,last_name,fte_value,terms_and_conditions ) VALUES
   ('00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000000',
    1,
    'Adam',
    'Smith',
    0.5,
    'PRE-MODERNISED'
    );
