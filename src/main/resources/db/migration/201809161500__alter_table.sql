SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

alter table auditing.pessoa_audited add column token_jwt varchar(500);