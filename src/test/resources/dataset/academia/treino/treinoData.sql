SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "academia" CASCADE;
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
    
INSERT INTO academia(
            id, created, updated, cep, cidade, cnpj, endereco, is_ativa, 
            nome_fantasia, razao_social, telefone)
    VALUES (1000, NOW(), NULL, '89890000', 'Cidade Bl bla', '43.576.756/0001-96', 'Rua XXy, 16', true, 
            'Academia Bl bla ', 'bla Bla asfd', '48966548130');
            
INSERT INTO academia(
            id, created, updated, cep, cidade, cnpj, endereco, is_ativa, 
            nome_fantasia, razao_social, telefone)
    VALUES (1001, NOW(), NULL, '89890000', 'Cidade Bl bla', '34.287.463/0001-06', 'Rua Bl bla, 165', true, 
            'Academia blz blz', 'bla blz blz', '48944548130');
      
         
INSERT INTO academia(
            id, created, updated, cep, cidade, cnpj, endereco, is_ativa, 
            nome_fantasia, razao_social, telefone)
    VALUES (1002, NOW(), NULL, '66654321', 'Cidade sahhsahsa', '53.014.283/0001-97', 'Rua Abra cadabra, 15', false, 
            'Academia Abra Cadabra', 'Abra Cadabra', '56944548130');

            
-- --------------------------------------------------------------------------------------
            
INSERT INTO treino(
            id, created, updated, data_fim, data_inicio, hora_prevista_inicio, 
            hora_prevista_termino, nome, academia_id)
    VALUES (1000, NOW(), null, '2018-12-31', '2018-11-01', '08:00:00', 
            '09:00:00', 'Treino Testes', 1000);
            
            
INSERT INTO treino(
            id, created, updated, data_fim, data_inicio, hora_prevista_inicio, 
            hora_prevista_termino, nome, academia_id)
    VALUES (1001, NOW(), null, '2017-12-31', '2017-11-01', '08:00:00', 
            '09:00:00', 'Treino TESTE', 1000);
            
            
INSERT INTO treino(
            id, created, updated, data_fim, data_inicio, hora_prevista_inicio, 
            hora_prevista_termino, nome, academia_id)
    VALUES (1002, NOW(), null, '2019-12-31', '2019-11-01', '08:00:00', 
            '09:00:00', 'Treino Testes B', 1000);

            
-- ----------------------------------------------------------------------------------
            
INSERT INTO treino_exercicio(
            id, created, updated, carga, data_inativacao, is_ativo, observacoes, 
            repeticoes, tempo_min, academia_id, exercicio_id, treino_id, tipo_treino_exercicio)
    VALUES (1000, NOW(), null, 0, null, true, '', 
            1, 10, 1000, 1000, 1000, 2);

INSERT INTO treino_exercicio(
            id, created, updated, carga, data_inativacao, is_ativo, observacoes, 
            repeticoes, tempo_min, academia_id, exercicio_id, treino_id, tipo_treino_exercicio)
    VALUES (1001, NOW(), null, 0, null, true, '', 
            null, 10, 1000, 1001, 1000,1);

            
INSERT INTO treino_exercicio(
            id, created, updated, carga, data_inativacao, is_ativo, observacoes, 
            repeticoes, tempo_min, academia_id, exercicio_id, treino_id, tipo_treino_exercicio)
    VALUES (1002, NOW(), null, 0, NOW(), false, '', 
            null, 10, 1000, 1001, 1002, 0);

-- ----------------------------------------------------------------------------------
            
INSERT INTO treino_data(
            id, created, updated, completo, data, dia_semana, hora_inicio, 
            hora_termino, academia_id, treino_id)
    VALUES (1000, NOW(), null, false, , ?, ?, 
            ?, ?, ?);
