SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

alter table treino_data add column tempo_gasto timestamp without time zone;
alter table auditing.treino_data_audited add column tempo_gasto timestamp without time zone;