
SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

alter table avaliacao_antropometrica drop constraint fkkaqop4boktw01d9tqmvcdlixe;
alter table avaliacao_antropometrica drop column predicao_gordura_siri_id;
alter table auditing.avaliacao_antropometrica_audited drop column predicao_gordura_siri_id;

drop table predicao_gordura_siri;
DROP TABLE auditing.predicao_gordura_siri_audited;