SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "academia" CASCADE;

INSERT INTO academia(
            id, created, updated, cep, cidade, cnpj, endereco, is_ativa, 
            nome_fantasia, razao_social, telefone, pessoa_proprietario_id)
    VALUES (1000, NOW(), NULL, '89890000', 'Cidade Bl bla', '43.576.756/0001-96', 'Rua XXy, 16', true, 
            'Academia Bl bla ', 'bla Bla asfd', '48966548130', 1011 );
            
INSERT INTO academia(
            id, created, updated, cep, cidade, cnpj, endereco, is_ativa, 
            nome_fantasia, razao_social, telefone, pessoa_proprietario_id)
    VALUES (1001, NOW(), NULL, '89890000', 'Cidade Bl bla', '34.287.463/0001-06', 'Rua Bl bla, 165', true, 
            'Academia blz blz', 'bla blz blz', '48944548130', 1012);
      
         
INSERT INTO academia(
            id, created, updated, cep, cidade, cnpj, endereco, is_ativa, 
            nome_fantasia, razao_social, telefone, pessoa_proprietario_id)
    VALUES (1002, NOW(), NULL, '66654321', 'Cidade sahhsahsa', '53.014.283/0001-97', 'Rua Abra cadabra, 15', false, 
            'Academia Abra Cadabra', 'Abra Cadabra', '56944548130', 1013);
      
         
INSERT INTO academia(
            id, created, updated, cep, cidade, cnpj, endereco, is_ativa, 
            nome_fantasia, razao_social, telefone, pessoa_proprietario_id)
    VALUES (1003, NOW(), NULL, '66654321', 'Cidadsdafe sahhsahsa', '53.014.283/0001-99', 'Rua Abra cadabra, 15', false, 
            'Academia Abra Cadabra', 'Abra Cadabra', '56944548130', 1013);
