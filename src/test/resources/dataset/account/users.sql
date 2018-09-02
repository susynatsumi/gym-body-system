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
	9999, 
	NOW(), 
	null, 
	now(), 
	'admin@email.com', 
	0, 
	true, 
    null, 
    'admin@email.com', 
    'admin', 
    null, 
    '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC'
);
  
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (9999, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (9999, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (9999, 2);


--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (9999, NOW() , null, 'admin@email.com', true, null, 'admin', 'Administrador', 
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
	1000, 
	NOW(), 
	null, 
	now(), 
	'user001@testing.com', 
	0, 
	true, 
    null, 
    'user001', 
    'user001', 
    null, 
    '$2a$04$r0laAzBSKnwe7/7rC1KD7upN2l2gaGyQRS5BK1mh0G/uEJR3iBuaC'
);
  
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1000, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1000, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1000, 2);    
 
--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (1000, NOW() , null, 'user001@testing.com', true, null, 'User 001', 'Administrador', 
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
	1001, 
	NOW(), 
	null, 
	now(), 
	'user002@testing.com', 
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
    VALUES (1001, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1001, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1001, 2);        
    
--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (1001, NOW() , null, 'user002@testing.com', true, null, 'User 002', 'User 002', 
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
	1002, 
	NOW(), 
	null, 
	now(), 
	'xova@testing.com', 
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
    VALUES (1002, 0);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1002, 1);
    
INSERT INTO pessoa_papeis(
            pessoa_id, papeis)
    VALUES (1002, 2);     

--INSERT INTO pessoa(
--            id, created, updated, email, is_ativo, last_login, login, nome, 
--            objetivo, papel, password_reset_token, password_reset_token_expiration, 
--            senha, data_nascimento, genero)
--    VALUES (1002, NOW() , null, 'xova@testing.com', true, null, 'Xóva :x', 'Xóva :x', 
--            null, 0, null , null, 
--           'd1bd2f08fead38a982aed9d4ca060152400b1b8f', NOW(), 0
--    );
