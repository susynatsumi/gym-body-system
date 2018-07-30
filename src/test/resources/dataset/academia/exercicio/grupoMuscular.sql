SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "grupo_muscular" CASCADE;


INSERT INTO grupo_muscular(
            id, created, updated, descricao, nome)
    VALUES (1000, now(), null, 'Este grupo muscular teste teste teste ', 'Grupo Teste');
    
INSERT INTO grupo_muscular(
            id, created, updated, descricao, nome)
    VALUES (1001, now(), null, 'Este grupo muscular teste teste teste ', 'Grupo superior');

INSERT INTO grupo_muscular(
            id, created, updated, descricao, nome)
    VALUES (1002, now(), null, 'Este grupo muscular teste teste teste ', 'Costas');