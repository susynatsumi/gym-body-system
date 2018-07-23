SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

-- Table: avaliacao_fisica

-- DROP TABLE avaliacao_fisica;

CREATE TABLE avaliacao_fisica
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  data date NOT NULL,
  CONSTRAINT avaliacao_fisica_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE avaliacao_fisica
  OWNER TO gym;

-- Table: auditing.avaliacao_fisica_audited

-- DROP TABLE auditing.avaliacao_fisica_audited;

CREATE TABLE auditing.avaliacao_fisica_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  data date,
  CONSTRAINT avaliacao_fisica_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk6hngwbhd08ipckcpa975mdyp1 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.avaliacao_fisica_audited
  OWNER TO gym;

  
-- --------------------------------------------------

-- Table: avaliacao_antropometrica

-- DROP TABLE avaliacao_antropometrica;

CREATE TABLE avaliacao_antropometrica
(
  dtype character varying(31) NOT NULL,
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  genero integer NOT NULL,
  CONSTRAINT avaliacao_antropometrica_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE avaliacao_antropometrica
  OWNER TO gym;

  
-- Table: auditing.avaliacao_antropometrica_audited

-- DROP TABLE auditing.avaliacao_antropometrica_audited;

CREATE TABLE auditing.avaliacao_antropometrica_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  dtype character varying(31) NOT NULL,
  revision_type smallint,
  genero integer,
  CONSTRAINT avaliacao_antropometrica_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkmf37fsbojpumachv5bderol02 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.avaliacao_antropometrica_audited
  OWNER TO gym;
  
-- --------------------------------------------------

-- Table: anamnese

-- DROP TABLE anamnese;

CREATE TABLE anamnese
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  is_ativo boolean NOT NULL,
  nome character varying(255) NOT NULL,
  CONSTRAINT anamnese_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE anamnese
  OWNER TO gym;

  
-- Table: auditing.anamnese_audited

-- DROP TABLE auditing.anamnese_audited;

CREATE TABLE auditing.anamnese_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  is_ativo boolean,
  nome character varying(255),
  CONSTRAINT anamnese_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fksj0xt38syc1vyppc1lj1nxjvt FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.anamnese_audited
  OWNER TO gym;
  
-- --------------------------------------------------

-- Table: dobras_cutaneas

-- DROP TABLE dobras_cutaneas;

CREATE TABLE dobras_cutaneas
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  abdominal numeric(19,2) NOT NULL,
  axilar_media numeric(19,2) NOT NULL,
  bicital numeric(19,2) NOT NULL,
  coxa numeric(19,2) NOT NULL,
  panturrilha_medial numeric(19,2) NOT NULL,
  subescapular numeric(19,2) NOT NULL,
  supra_iliaca numeric(19,2) NOT NULL,
  toracica numeric(19,2) NOT NULL,
  tricipal numeric(19,2) NOT NULL,
  avaliacao_antropometrica_id bigint NOT NULL,
  CONSTRAINT dobras_cutaneas_pkey PRIMARY KEY (id),
  CONSTRAINT fk1mwqte7extrqioapo28b0hho8 FOREIGN KEY (avaliacao_antropometrica_id)
      REFERENCES avaliacao_antropometrica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dobras_cutaneas
  OWNER TO gym;

-- Table: auditing.dobras_cutaneas_audited

-- DROP TABLE auditing.dobras_cutaneas_audited;

CREATE TABLE auditing.dobras_cutaneas_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  abdominal numeric(19,2),
  axilar_media numeric(19,2),
  bicital numeric(19,2),
  coxa numeric(19,2),
  panturrilha_medial numeric(19,2),
  subescapular numeric(19,2),
  supra_iliaca numeric(19,2),
  toracica numeric(19,2),
  tricipal numeric(19,2),
  avaliacao_antropometrica_id bigint,
  CONSTRAINT dobras_cutaneas_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk73du8noewi05o0c1jl768gaho FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.dobras_cutaneas_audited
  OWNER TO gym;

  
-- --------------------------------------------------

-- Table: perimetria

-- DROP TABLE perimetria;

CREATE TABLE perimetria
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  abdomen numeric(19,2) NOT NULL,
  antebraco_direito numeric(19,2) NOT NULL,
  antebraco_esquerdo numeric(19,2) NOT NULL,
  braco_direito_contraido numeric(19,2) NOT NULL,
  braco_direito_relaxado numeric(19,2) NOT NULL,
  braco_esquerdo_contraido numeric(19,2) NOT NULL,
  braco_esquerdo_relaxado numeric(19,2) NOT NULL,
  cintura numeric(19,2) NOT NULL,
  coxa_distal_direita numeric(19,2) NOT NULL,
  coxa_distal_esquerda numeric(19,2) NOT NULL,
  coxa_media_direita numeric(19,2) NOT NULL,
  coxa_media_esquerda numeric(19,2) NOT NULL,
  coxa_proximal_direita numeric(19,2) NOT NULL,
  coxa_proximal_esquerda numeric(19,2) NOT NULL,
  panturrilha_direita numeric(19,2) NOT NULL,
  panturrilha_esquerda numeric(19,2) NOT NULL,
  pescoco numeric(19,2) NOT NULL,
  quadril numeric(19,2) NOT NULL,
  torax numeric(19,2) NOT NULL,
  CONSTRAINT perimetria_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE perimetria
  OWNER TO gym;

  
-- Table: auditing.perimetria_audited

-- DROP TABLE auditing.perimetria_audited;

CREATE TABLE auditing.perimetria_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  abdomen numeric(19,2),
  antebraco_direito numeric(19,2),
  antebraco_esquerdo numeric(19,2),
  braco_direito_contraido numeric(19,2),
  braco_direito_relaxado numeric(19,2),
  braco_esquerdo_contraido numeric(19,2),
  braco_esquerdo_relaxado numeric(19,2),
  cintura numeric(19,2),
  coxa_distal_direita numeric(19,2),
  coxa_distal_esquerda numeric(19,2),
  coxa_media_direita numeric(19,2),
  coxa_media_esquerda numeric(19,2),
  coxa_proximal_direita numeric(19,2),
  coxa_proximal_esquerda numeric(19,2),
  panturrilha_direita numeric(19,2),
  panturrilha_esquerda numeric(19,2),
  pescoco numeric(19,2),
  quadril numeric(19,2),
  torax numeric(19,2),
  CONSTRAINT perimetria_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkmfrp6fshi8n6p0jf5ldlm0iqn FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.perimetria_audited
  OWNER TO gym;

  
-- --------------------------------------------------

 -- Table: predicao_gordura_siri

-- DROP TABLE predicao_gordura_siri;

CREATE TABLE predicao_gordura_siri
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  densidade_corporal double precision NOT NULL,
  gordura double precision NOT NULL,
  siri numeric(19,2) NOT NULL,
  avaliacao_antropometrica_id bigint NOT NULL,
  CONSTRAINT predicao_gordura_siri_pkey PRIMARY KEY (id),
  CONSTRAINT fkjruw4ros8p84nrbygvmxnosx5 FOREIGN KEY (avaliacao_antropometrica_id)
      REFERENCES avaliacao_antropometrica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE predicao_gordura_siri
  OWNER TO gym;

-- Table: auditing.predicao_gordura_siri_audited

-- DROP TABLE auditing.predicao_gordura_siri_audited;

CREATE TABLE auditing.predicao_gordura_siri_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  densidade_corporal double precision,
  gordura double precision,
  siri numeric(19,2),
  avaliacao_antropometrica_id bigint,
  CONSTRAINT predicao_gordura_siri_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkgl22v4b9u391lnra9ipxgd1x6 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.predicao_gordura_siri_audited
  OWNER TO gym;
  
-- --------------------------------------------------

-- Table: pergunta

-- DROP TABLE pergunta;

CREATE TABLE pergunta
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  obrigatorio boolean NOT NULL,
  ordem integer NOT NULL,
  pergunta character varying(255) NOT NULL,
  tipo_pergunta integer NOT NULL,
  tipo_resposta integer NOT NULL,
  anamnese_id bigint,
  CONSTRAINT pergunta_pkey PRIMARY KEY (id),
  CONSTRAINT fkj7986xwwote0kyofcavqlntao FOREIGN KEY (anamnese_id)
      REFERENCES anamnese (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pergunta
  OWNER TO gym;

  
-- Table: auditing.pergunta_audited

-- DROP TABLE auditing.pergunta_audited;

CREATE TABLE auditing.pergunta_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  obrigatorio boolean,
  ordem integer,
  pergunta character varying(255),
  tipo_pergunta integer,
  tipo_resposta integer,
  anamnese_id bigint,
  CONSTRAINT pergunta_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkjau53e3lbkmb47w4j80jym61l FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.pergunta_audited
  OWNER TO gym;
  
-- --------------------------------------------------

-- Table: opcao_resposta

-- DROP TABLE opcao_resposta;

CREATE TABLE opcao_resposta
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  texto character varying(255) NOT NULL,
  pergunta_id bigint NOT NULL,
  CONSTRAINT opcao_resposta_pkey PRIMARY KEY (id),
  CONSTRAINT fkm7gaamq2hj58puxa7ciy6pjw2 FOREIGN KEY (pergunta_id)
      REFERENCES pergunta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opcao_resposta
  OWNER TO gym;

  
-- Table: auditing.opcao_resposta_audited

-- DROP TABLE auditing.opcao_resposta_audited;

CREATE TABLE auditing.opcao_resposta_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  texto character varying(255),
  pergunta_id bigint,
  CONSTRAINT opcao_resposta_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkt6e600o8mr2iblfvv1xtlx3eg FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.opcao_resposta_audited
  OWNER TO gym;

  
-- --------------------------------------------------

-- Table: resposta

-- DROP TABLE resposta;

CREATE TABLE resposta
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  texto character varying(255) NOT NULL,
  opcao_resposta_id bigint NOT NULL,
  pergunta_id bigint NOT NULL,
  CONSTRAINT resposta_pkey PRIMARY KEY (id),
  CONSTRAINT fkhl4axbv8d1yoixs9vtq7yui20 FOREIGN KEY (pergunta_id)
      REFERENCES pergunta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fksjpl0ckh18ujx9xqkii1sbpq8 FOREIGN KEY (opcao_resposta_id)
      REFERENCES opcao_resposta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE resposta
  OWNER TO gym;

-- Table: auditing.resposta_audited

-- DROP TABLE auditing.resposta_audited;

CREATE TABLE auditing.resposta_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  texto character varying(255),
  opcao_resposta_id bigint,
  pergunta_id bigint,
  CONSTRAINT resposta_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkm077uj1p8jv9q4rkeud2h6uri FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.resposta_audited
  OWNER TO gym;
  
-- --------------------------------------------------
