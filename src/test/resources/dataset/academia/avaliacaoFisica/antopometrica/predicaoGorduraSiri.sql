SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_with_oids = false;

SET search_path TO public;

TRUNCATE "predicao_gordura_siri" CASCADE;

INSERT INTO predicao_gordura_siri(
            id, created, updated, densidade_corporal, gordura)
    VALUES (1000, now(), null, 132, 10);
    
INSERT INTO predicao_gordura_siri(
            id, created, updated, densidade_corporal, gordura)
    VALUES (1001, now(), null, 132, 10);
    
INSERT INTO predicao_gordura_siri(
            id, created, updated, densidade_corporal, gordura)
    VALUES (1002, now(), null, 132, 10);
