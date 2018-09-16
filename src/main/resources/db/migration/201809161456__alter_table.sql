SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

alter table pessoa drop column jwt_token ; 
alter table pessoa add column token_jwt varchar(500); 