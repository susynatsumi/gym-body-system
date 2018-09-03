SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "perimetria" CASCADE;

INSERT INTO perimetria(
            id, created, updated, abdomen, antebraco_direito, antebraco_esquerdo, 
            braco_direito_contraido, braco_direito_relaxado, braco_esquerdo_contraido, 
            braco_esquerdo_relaxado, cintura, coxa_distal_direita, coxa_distal_esquerda, 
            coxa_media_direita, coxa_media_esquerda, coxa_proximal_direita, 
            coxa_proximal_esquerda, panturrilha_direita, panturrilha_esquerda, 
            pescoco, quadril, torax)
    VALUES (1000, now(), null, 10, 5, 5, 
            5, 5, 5, 
            5, 7, 6, 3, 
            4, 5, 6, 
            6, 6, 56, 
            5, 6, 7
);



INSERT INTO perimetria(
            id, created, updated, abdomen, antebraco_direito, antebraco_esquerdo, 
            braco_direito_contraido, braco_direito_relaxado, braco_esquerdo_contraido, 
            braco_esquerdo_relaxado, cintura, coxa_distal_direita, coxa_distal_esquerda, 
            coxa_media_direita, coxa_media_esquerda, coxa_proximal_direita, 
            coxa_proximal_esquerda, panturrilha_direita, panturrilha_esquerda, 
            pescoco, quadril, torax)
    VALUES (1001, now(), null, 10, 5, 5, 
            5, 5, 5, 
            5, 7, 6, 3, 
            4, 5, 6, 
            6, 6, 56, 
            5, 6, 7
);


INSERT INTO perimetria(
            id, created, updated, abdomen, antebraco_direito, antebraco_esquerdo, 
            braco_direito_contraido, braco_direito_relaxado, braco_esquerdo_contraido, 
            braco_esquerdo_relaxado, cintura, coxa_distal_direita, coxa_distal_esquerda, 
            coxa_media_direita, coxa_media_esquerda, coxa_proximal_direita, 
            coxa_proximal_esquerda, panturrilha_direita, panturrilha_esquerda, 
            pescoco, quadril, torax)
    VALUES (1002, now(), null, 10, 5, 5, 
            5, 5, 5, 
            5, 7, 6, 3, 
            4, 5, 6, 
            6, 6, 56, 
            5, 6, 7
);


INSERT INTO perimetria(
            id, created, updated, abdomen, antebraco_direito, antebraco_esquerdo, 
            braco_direito_contraido, braco_direito_relaxado, braco_esquerdo_contraido, 
            braco_esquerdo_relaxado, cintura, coxa_distal_direita, coxa_distal_esquerda, 
            coxa_media_direita, coxa_media_esquerda, coxa_proximal_direita, 
            coxa_proximal_esquerda, panturrilha_direita, panturrilha_esquerda, 
            pescoco, quadril, torax)
    VALUES (1003, now(), null, 10, 5, 5, 
            5, 5, 5, 
            5, 7, 6, 3, 
            4, 5, 6, 
            6, 6, 56, 
            5, 6, 7
);