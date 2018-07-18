SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "notificacao" CASCADE;
TRUNCATE "academia" CASCADE;
TRUNCATE "pessoa" CASCADE;
TRUNCATE "pessoa_notificacao" CASCADE;

INSERT INTO pessoa(
            id, created, updated, email, is_ativo, last_login, login, nome, 
            objetivo, papel, password_reset_token, password_reset_token_expiration, 
            senha, data_nascimento, genero)
    VALUES (1011, NOW() , null, 'admin@email.com', true, null, 'admin', 'Administrador', 
            null, 0, null , null, 
           'testestes', NOW(), 0
    );

INSERT INTO pessoa(
            id, created, updated, email, is_ativo, last_login, login, nome, 
            objetivo, papel, password_reset_token, password_reset_token_expiration, 
            senha, data_nascimento, genero)
    VALUES (1012, NOW() , null, 'user001@testing.com', true, null, 'User 001', 'Administrador', 
            null, 0, null , null, 
           'd1bd2f08fead38a982aed9d4ca060152400b1b8f', NOW(), 0
    );
    
INSERT INTO pessoa(
            id, created, updated, email, is_ativo, last_login, login, nome, 
            objetivo, papel, password_reset_token, password_reset_token_expiration, 
            senha, data_nascimento, genero)
    VALUES (1013, NOW() , null, 'user002@testing.com', true, null, 'User 002', 'User 002', 
            null, 0, null , null, 
           'd1bd2f08fead38a982aed9d4ca060152400b1b8f', NOW(), 0
    );
    
INSERT INTO pessoa(
            id, created, updated, email, is_ativo, last_login, login, nome, 
            objetivo, papel, password_reset_token, password_reset_token_expiration, 
            senha, data_nascimento, genero)
    VALUES (1014, NOW() , null, 'xova@testing.com', true, null, 'Xóva :x', 'Xóva :x', 
            null, 0, null , null, 
           'd1bd2f08fead38a982aed9d4ca060152400b1b8f', NOW(), 0
    );


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

-- -----------------------------------------------------------------------------------
            
INSERT INTO notificacao(
            id, created, updated, texto, titulo, academia_id, treino_data_id)
    VALUES (1000, NOW(), null, 'Notificacao de teste bla bla bla', 'Teste', 1000, null);

    
INSERT INTO notificacao(
            id, created, updated, texto, titulo, academia_id, treino_data_id)
    VALUES (1001, NOW(), null, 'Notificacao de teste blaasdf bla bla', 'Teste 1', 1000, null);
    
INSERT INTO notificacao(
            id, created, updated, texto, titulo, academia_id, treino_data_id)
    VALUES (1002, NOW(), null, 'Notificacao de teste blaasdf bla bla', 'Teste 1', 1001, null);
