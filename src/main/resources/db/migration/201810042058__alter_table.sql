SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

alter table indice_massa_corporal add column resultado numeric(19,2) NOT NULL;
alter table auditing.indice_massa_corporal_audited add column resultado numeric(19,2) NOT NULL;