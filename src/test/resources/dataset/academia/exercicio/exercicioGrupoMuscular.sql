SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "equipamento" CASCADE;
TRUNCATE "exercicio" CASCADE;
TRUNCATE "grupo_muscular" CASCADE;

INSERT INTO equipamento(
            id, created, updated, descricao, imagem, is_ativo)
    VALUES (1000, NOW(), null, 'Equipamento teste', null, true);

INSERT INTO equipamento(
            id, created, updated, descricao, imagem, is_ativo)
    VALUES (1001, NOW(), null, 'Equipamento teste 2', null, true);    

INSERT INTO equipamento(
            id, created, updated, descricao, imagem, is_ativo)
    VALUES (1002, NOW(), null, 'Esteira', null, true);
    
 -- ----------------------------------------------------
 
INSERT INTO exercicio(
    id, created, updated, descricao, is_ativo, link_video, nome, 
    equipamento_id)
VALUES (1000, NOW(), NULL, 'Corrida na esteira, para asdfafsadfafasdfasfsasafdsaf', true, null, 'Corrida', 
    1002);
    
INSERT INTO exercicio(
    id, created, updated, descricao, is_ativo, link_video, nome, 
    equipamento_id)
VALUES (1001, NOW(), NULL, 'Corrida na esteira', true, null, 'Corrida abc', 
    1001);
    
INSERT INTO exercicio(
    id, created, updated, descricao, is_ativo, link_video, nome, 
    equipamento_id)
VALUES (1002, NOW(), NULL, 'sdfaklfdjalkjfdsajfa', true, null, 'Corrida abc', 
    1001);

    
-- ----------------------------------------------------------------------

INSERT INTO grupo_muscular(
            id, created, updated, descricao, nome)
    VALUES (1000, now(), null, 'Este grupo muscular teste teste teste ', 'Teste');
    
INSERT INTO grupo_muscular(
            id, created, updated, descricao, nome)
    VALUES (1001, now(), null, 'Este grupo muscular teste teste teste ', 'Grupo superior');

INSERT INTO grupo_muscular(
            id, created, updated, descricao, nome)
    VALUES (1002, now(), null, 'Este grupo muscular teste teste teste ', 'Costas');
    
-- ----------------------------------------------------------------------
    
INSERT INTO exercicio_grupo_muscular(
            id, created, updated, exercicio_id, grupo_muscular_id)
    VALUES (1000, NOW(), null, 1000, 1000);
    
    
INSERT INTO exercicio_grupo_muscular(
            id, created, updated, exercicio_id, grupo_muscular_id)
    VALUES (1001, NOW(), null, 1000, 1001);
    
INSERT INTO exercicio_grupo_muscular(
            id, created, updated, exercicio_id, grupo_muscular_id)
    VALUES (1002, NOW(), null, 1001, 1001);
