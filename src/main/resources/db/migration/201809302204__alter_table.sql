SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

alter table avaliacao_antropometrica add column densidade_corporal double precision not null;
alter table auditing.avaliacao_antropometrica_audited add column densidade_corporal double precision not null;