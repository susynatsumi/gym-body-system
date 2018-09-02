SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "pessoa" CASCADE;


  
INSERT INTO pessoa(
    id, 
    created, 
    updated, 
    data_nascimento, 
    email, 
    genero, 
    is_ativo, 
    last_login, 
    login, 
    nome, 
    objetivo, 
    senha
)
VALUES (
	1011, 
	NOW(), 
	null, 
	now(), 
	'admin@email.com', 
	0, 
	true, 
    null, 
    'admin', 
    'Administrador', 
    null, 
    '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC'
);
  
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1011, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1011, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1011, 2);

--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (1011, NOW() , null, 'admin@email.com', true, null, 'admin', 'Administrador', 
--            null, 0, null , null, 
--           'testestes', NOW(), 0
--    );

    
INSERT INTO pessoa(
    id, 
    created, 
    updated, 
    data_nascimento, 
    email, 
    genero, 
    is_ativo, 
    last_login, 
    login, 
    nome, 
    objetivo, 
    senha
)
VALUES (
	1012, 
	NOW(), 
	null, 
	now(), 
	'teste012@admin.com', 
	0, 
	true, 
    null, 
    'User 012', 
    'Administrador', 
    null, 
    '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC'
);
  
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1012, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1012, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1012, 2);
    
--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (1012, NOW() , null, 'user001@testing.com', true, null, 'User 001', 'Administrador', 
--            null, 0, null , null, 
--           'd1bd2f08fead38a982aed9d4ca060152400b1b8f', NOW(), 0
--    );
    
    
INSERT INTO pessoa(
    id, 
    created, 
    updated, 
    data_nascimento, 
    email, 
    genero, 
    is_ativo, 
    last_login, 
    login, 
    nome, 
    objetivo, 
    senha
)
VALUES (
	1013, 
	NOW(), 
	null, 
	now(), 
	'user002@admin.com', 
	0, 
	true, 
    null, 
    'user002', 
    'user002', 
    null, 
    '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC'
);
  
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1013, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1013, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1013, 2);
    
--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (1013, NOW() , null, 'user002@testing.com', true, null, 'User 002', 'User 002', 
--            null, 0, null , null, 
--           'd1bd2f08fead38a982aed9d4ca060152400b1b8f', NOW(), 0
--    );
    
    

INSERT INTO pessoa(
    id, 
    created, 
    updated, 
    data_nascimento, 
    email, 
    genero, 
    is_ativo, 
    last_login, 
    login, 
    nome, 
    objetivo, 
    senha
)
VALUES (
	1014, 
	NOW(), 
	null, 
	now(), 
	'xova@admin.com', 
	0, 
	true, 
    null, 
    'xova', 
    'xova', 
    null, 
    '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC'
);
  
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1014, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1014, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1014, 2);
    
--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (1014, NOW() , null, 'xova@testing.com', true, null, 'Xóva :x', 'Xóva :x', 
--            null, 0, null , null, 
--           'd1bd2f08fead38a982aed9d4ca060152400b1b8f', NOW(), 0
--    );
