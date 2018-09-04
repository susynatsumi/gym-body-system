SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "destinatario_notificacao" CASCADE;
TRUNCATE "notificacao" CASCADE;
            
INSERT INTO notificacao(
            id, created, updated, texto, titulo)
    VALUES (1000, NOW(), null, 'Notificacao de teste bla bla bla', 'Teste');

    
INSERT INTO notificacao(
            id, created, updated, texto, titulo)
    VALUES (1001, NOW(), null, 'Notificacao de teste blaasdf bla bla', 'Teste 1');
    
INSERT INTO notificacao(
            id, created, updated, texto, titulo)
    VALUES (1002, NOW(), null, 'Notificacao de teste blaasdf bla bla', 'Teste 1');
    
    
-- -------------------------------------------------------------------------------------

INSERT INTO destinatario_notificacao(
            id, created, updated, notificacao_id, pessoa_id)
    VALUES (1000, NOW(), null, 1002, 1012);

INSERT INTO destinatario_notificacao(
            id, created, updated, notificacao_id, pessoa_id)
    VALUES (1001, NOW(), null, 1002, 1011);

