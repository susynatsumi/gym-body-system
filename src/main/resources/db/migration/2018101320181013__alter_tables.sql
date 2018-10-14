
SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

alter table exercicio drop column link_video;
alter table auditing.exercicio_audited drop column link_video;

alter table exercicio add column imagem oid;
alter table auditing.exercicio_audited add column imagem oid;