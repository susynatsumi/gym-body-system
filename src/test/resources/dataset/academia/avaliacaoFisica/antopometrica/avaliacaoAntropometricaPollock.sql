SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "dobras_cutaneas" CASCADE;
TRUNCATE "indice_massa_corporal" CASCADE;

INSERT INTO indice_massa_corporal(
            id, created, updated, altura, peso, resultado)
    VALUES (1000, now(), null, 1.16, 30, 1);
    
INSERT INTO indice_massa_corporal(
            id, created, updated, altura, peso, resultado)
    VALUES (1001, now(), null, 1.89, 85, 2);
    
INSERT INTO indice_massa_corporal(
            id, created, updated, altura, peso, resultado)
    VALUES (1002, now(), null, 1.90, 80, 2);


INSERT INTO dobras_cutaneas(
            id, created, updated, abdominal, axilar_media, bicital, coxa, 
            panturrilha, peitoral, subescapular, supra_iliaca, toracica, 
            tricipal)
    VALUES (1000, NOW(), null, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 
            1);

INSERT INTO dobras_cutaneas(
            id, created, updated, abdominal, axilar_media, bicital, coxa, 
            panturrilha, peitoral, subescapular, supra_iliaca, toracica, 
            tricipal)
    VALUES (1001, NOW(), null, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 
            1);

INSERT INTO dobras_cutaneas(
            id, created, updated, abdominal, axilar_media, bicital, coxa, 
            panturrilha, peitoral, subescapular, supra_iliaca, toracica, 
            tricipal)
    VALUES (1002, NOW(), null, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 
            1);
