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
TRUNCATE "treino" CASCADE;
TRUNCATE "treino_exercicio" CASCADE;
TRUNCATE "treino_data" CASCADE;

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

-- --------------------------------------------------------------------------------
     
INSERT INTO treino(
            id, created, updated, data_fim, data_inicio, hora_prevista_inicio, 
            hora_prevista_termino, nome, aluno_id, personal_id)
    VALUES (1000, now(), null, '2018-10-31', '2018-08-01', '2018-01-01 22:00:00', 
            '2018-01-01 23:00:00', 'Treino de teste', 1011, 1012);
     
INSERT INTO treino(
            id, created, updated, data_fim, data_inicio, hora_prevista_inicio, 
            hora_prevista_termino, nome, aluno_id, personal_id)
    VALUES (1001, now(), null, '2018-10-31', '2018-08-01', '2018-01-01 22:00:00', 
            '2018-01-01 23:00:00', 'Treino de teste', 1011, 1012);
            
INSERT INTO treino(
            id, created, updated, data_fim, data_inicio, hora_prevista_inicio, 
            hora_prevista_termino, nome, aluno_id, personal_id)
    VALUES (1002, now(), null, '2018-10-31', '2018-09-01', '2018-01-01 22:00:00', 
            '2018-01-01 23:00:00', 'Treino de teste', 1011, 1012);
            
-- ----------------------------------------------------------------------------------
      
INSERT INTO treino_exercicio(
            id, created, updated, carga, observacoes, repeticoes, series, 
            tempo_min, tipo_treino_exercicio, exercicio_id, treino_id)
    VALUES (1000, now(), null, 10, null, 30, 3, 
            10, 0, 1000, 1000);

INSERT INTO treino_exercicio(
            id, created, updated, carga, observacoes, repeticoes, series, 
            tempo_min, tipo_treino_exercicio, exercicio_id, treino_id)
    VALUES (1001, now(), null, 10, null, 30, 3, 
            10, 1, 1001, 1000);

INSERT INTO treino_exercicio(
            id, created, updated, carga, observacoes, repeticoes, series, 
            tempo_min, tipo_treino_exercicio, exercicio_id, treino_id)
    VALUES (1002, now(), null, 10, null, 30, 3, 
            10, 0, 1002, 1000);
