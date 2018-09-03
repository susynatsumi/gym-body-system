SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

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
