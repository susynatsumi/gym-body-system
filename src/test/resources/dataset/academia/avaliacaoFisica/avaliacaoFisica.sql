SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "avaliacao_fisica" CASCADE;
TRUNCATE "dobras_cutaneas" CASCADE;
TRUNCATE "predicao_gordura_siri" CASCADE;
TRUNCATE "indice_massa_corporal" CASCADE;

TRUNCATE "perimetria" CASCADE;

TRUNCATE "resposta" CASCADE;

INSERT INTO resposta(
            id, created, updated, cirurgia, doenca_familiar, medicamento, 
            objetivos_atividade_fisica, observacao, pratica_atividade)
    VALUES (1000, now(), null, 'Nuca fez', 'Não possui', 'Paracetamol', 
            'Ganhar massa muscular', 'Iniciante na academia', 'Nunca pratica');

INSERT INTO resposta(
            id, created, updated, cirurgia, doenca_familiar, medicamento, 
            objetivos_atividade_fisica, observacao, pratica_atividade)
    VALUES (1001, now(), null, 'Nuca fez', 'Não possui', 'Paracetamol', 
            'Ganhar massa muscular', 'Iniciante na academia', 'Nunca pratica');

INSERT INTO resposta(
            id, created, updated, cirurgia, doenca_familiar, medicamento, 
            objetivos_atividade_fisica, observacao, pratica_atividade)
    VALUES (1002, now(), null, 'Nuca fez', 'Não possui', 'Paracetamol', 
            'Ganhar massa muscular', 'Iniciante na academia', 'Nunca pratica');

INSERT INTO resposta(
            id, created, updated, cirurgia, doenca_familiar, medicamento, 
            objetivos_atividade_fisica, observacao, pratica_atividade)
    VALUES (1003, now(), null, 'Nuca fez', 'Não possui', 'Paracetamol', 
            'Ganhar massa muscular', 'Iniciante na academia', 'Nunca pratica');


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

INSERT INTO predicao_gordura_siri(
            id, created, updated, densidade_corporal, gordura)
    VALUES (1000, now(), null, 132, 10);
    
INSERT INTO predicao_gordura_siri(
            id, created, updated, densidade_corporal, gordura)
    VALUES (1001, now(), null, 133, 10);
    
INSERT INTO predicao_gordura_siri(
            id, created, updated, densidade_corporal, gordura)
    VALUES (1002, now(), null, 134, 10);


            
INSERT INTO avaliacao_fisica(
            id, created, updated, data, 
            perimetria_id, pessoa_id, resposta_id)
    VALUES (1000, now(), null, '2018-09-01', 1000, 
            1000, 1011, 1000);
            
INSERT INTO avaliacao_fisica(
            id, created, updated, data, 
            perimetria_id, pessoa_id, resposta_id)
    VALUES (1001, now(), null, '2018-09-01', 1001, 
            1001, 1012, 1001);

