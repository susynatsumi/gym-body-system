SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "notificacao" CASCADE;
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

-- -----------------------------------------------------------------------------------
            
INSERT INTO notificacao(
            id, created, updated, texto, titulo, treino_data_id)
    VALUES (1000, NOW(), null, 'Notificacao de teste bla bla bla', 'Teste', null);

    
INSERT INTO notificacao(
            id, created, updated, texto, titulo, treino_data_id)
    VALUES (1001, NOW(), null, 'Notificacao de teste blaasdf bla bla', 'Teste 1', null);
    
INSERT INTO notificacao(
            id, created, updated, texto, titulo, treino_data_id)
    VALUES (1002, NOW(), null, 'Notificacao de teste blaasdf bla bla', 'Teste 1', null);
