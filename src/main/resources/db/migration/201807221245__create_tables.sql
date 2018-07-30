SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

-- Table: academia

-- DROP TABLE academia;

CREATE TABLE academia
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  cep character varying(8) NOT NULL,
  cidade character varying(255) NOT NULL,
  cnpj character varying(18) NOT NULL,
  endereco character varying(255) NOT NULL,
  is_ativa boolean NOT NULL,
  nome_fantasia character varying(255) NOT NULL,
  razao_social character varying(255) NOT NULL,
  telefone character varying(11) NOT NULL,
  pessoa_proprietario_id bigint NOT NULL,
  CONSTRAINT academia_pkey PRIMARY KEY (id),
  CONSTRAINT fkok7andknndmoq2pau2v2jf1e9 FOREIGN KEY (pessoa_proprietario_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_8tvf82ddij8rahqovl1flay5f UNIQUE (cnpj)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE academia
  OWNER TO gym;

-- --------------------------------------
  
-- Table: auditing.academia_audited

-- DROP TABLE auditing.academia_audited;

CREATE TABLE auditing.academia_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  cep character varying(8),
  cidade character varying(255),
  cnpj character varying(18),
  endereco character varying(255),
  is_ativa boolean,
  nome_fantasia character varying(255),
  razao_social character varying(255),
  telefone character varying(11),
  pessoa_proprietario_id bigint,
  CONSTRAINT academia_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkd3yuai5nr087h1plb5jbmqf33 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.academia_audited
  OWNER TO gym;

-- ---------------------------------------------------------
  
-- Table: pessoa_academia

-- DROP TABLE pessoa_academia;

CREATE TABLE pessoa_academia
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  is_ativo boolean NOT NULL,
  academia_id bigint NOT NULL,
  pessoa_id bigint NOT NULL,
  CONSTRAINT pessoa_academia_pkey PRIMARY KEY (id),
  CONSTRAINT fkciprd9bjwmqeiwhgemxan2gdd FOREIGN KEY (pessoa_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkjdxvjrbaqs8ecoijvq92nw0iv FOREIGN KEY (academia_id)
      REFERENCES academia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ukfl0ictxq9ybb26fdfjqwf7vsp UNIQUE (pessoa_id, academia_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pessoa_academia
  OWNER TO gym;

-- Table: auditing.pessoa_academia_audited

-- DROP TABLE auditing.pessoa_academia_audited;

CREATE TABLE auditing.pessoa_academia_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  is_ativo boolean,
  academia_id bigint,
  pessoa_id bigint,
  CONSTRAINT pessoa_academia_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk4e80t6je78wqhaybxlvtdhnjj FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.pessoa_academia_audited
  OWNER TO gym;

-- ----------------------------------------------------------------------

-- Table: equipamento

-- DROP TABLE equipamento;

CREATE TABLE equipamento
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  descricao character varying(255) NOT NULL,
  imagem oid,
  is_ativo boolean NOT NULL,
  CONSTRAINT equipamento_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE equipamento
  OWNER TO gym;

-- Table: auditing.equipamento_audited

-- DROP TABLE auditing.equipamento_audited;

CREATE TABLE auditing.equipamento_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  descricao character varying(255),
  imagem oid,
  is_ativo boolean,
  CONSTRAINT equipamento_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkktr3kuroqloo5s1p7eqkjo48x FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.equipamento_audited
  OWNER TO gym;

  
-- ----------------------------------------------------------------------

-- Table: exercicio

-- DROP TABLE exercicio;

CREATE TABLE exercicio
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  descricao character varying(500) NOT NULL,
  is_ativo boolean NOT NULL,
  link_video character varying(255),
  nome character varying(60) NOT NULL,
  equipamento_id bigint NOT NULL,
  CONSTRAINT exercicio_pkey PRIMARY KEY (id),
  CONSTRAINT fkolc3q2dao59khc947niajy91x FOREIGN KEY (equipamento_id)
      REFERENCES equipamento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE exercicio
  OWNER TO gym;

-- Table: auditing.exercicio_audited

-- DROP TABLE auditing.exercicio_audited;

CREATE TABLE auditing.exercicio_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  descricao character varying(255),
  is_ativo boolean,
  link_video character varying(255),
  nome character varying(60),
  equipamento_id bigint,
  CONSTRAINT exercicio_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkrv5qh1cq2r4c8ge5gatsefyk2 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.exercicio_audited
  OWNER TO gym;

-- ----------------------------------------------

-- Table: grupo_muscular

-- DROP TABLE grupo_muscular;

CREATE TABLE grupo_muscular
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  descricao character varying(500),
  nome character varying(50) NOT NULL,
  CONSTRAINT grupo_muscular_pkey PRIMARY KEY (id),
  CONSTRAINT uk_s3fgx7iq8ciyhtio7mhovov77 UNIQUE (nome)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE grupo_muscular
  OWNER TO gym;

-- Table: auditing.grupo_muscular_audited

-- DROP TABLE auditing.grupo_muscular_audited;

CREATE TABLE auditing.grupo_muscular_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  descricao character varying(255),
  nome character varying(50),
  CONSTRAINT grupo_muscular_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkjwf38wupttxp9l7gi35j1tcxu FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.grupo_muscular_audited
  OWNER TO gym;
  
-- ----------------------------------------------

-- Table: exercicio_grupo_muscular

-- DROP TABLE exercicio_grupo_muscular;

CREATE TABLE exercicio_grupo_muscular
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  exercicio_id bigint NOT NULL,
  grupo_muscular_id bigint NOT NULL,
  CONSTRAINT exercicio_grupo_muscular_pkey PRIMARY KEY (id),
  CONSTRAINT fkg8ak34fsg1x3igacm0d9qm3xn FOREIGN KEY (exercicio_id)
      REFERENCES exercicio (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fksgm2vmq6wauft671jjmf73shb FOREIGN KEY (grupo_muscular_id)
      REFERENCES grupo_muscular (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ukhcxo4cujxbpg2aut356mfq3s6 UNIQUE (exercicio_id, grupo_muscular_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE exercicio_grupo_muscular
  OWNER TO gym;


-- Table: auditing.exercicio_grupo_muscular_audited

-- DROP TABLE auditing.exercicio_grupo_muscular_audited;

CREATE TABLE auditing.exercicio_grupo_muscular_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  exercicio_id bigint,
  grupo_muscular_id bigint,
  CONSTRAINT exercicio_grupo_muscular_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkdal1k5qkci8wj4bjxb0tmsvm1 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.exercicio_grupo_muscular_audited
  OWNER TO gym;

-- ----------------------------------------------

-- Table: treino

-- DROP TABLE treino;

CREATE TABLE treino
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  data_fim date NOT NULL,
  data_inicio date NOT NULL,
  hora_prevista_inicio timestamp without time zone NOT NULL,
  hora_prevista_termino timestamp without time zone NOT NULL,
  nome character varying(200) NOT NULL,
  academia_id bigint NOT NULL,
  CONSTRAINT treino_pkey PRIMARY KEY (id),
  CONSTRAINT fk1ngtt245kpg9208ycme1etc40 FOREIGN KEY (academia_id)
      REFERENCES academia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE treino
  OWNER TO gym;

-- Table: auditing.treino_audited

-- DROP TABLE auditing.treino_audited;

CREATE TABLE auditing.treino_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  data_fim date,
  data_inicio date,
  hora_prevista_inicio timestamp without time zone,
  hora_prevista_termino timestamp without time zone,
  nome character varying(50),
  academia_id bigint,
  CONSTRAINT treino_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk8yq1eeu7ymunv0l777kcp7nyc FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.treino_audited
  OWNER TO gym;
  
-- ----------------------------------------------

-- Table: treino_data

-- DROP TABLE treino_data;

CREATE TABLE treino_data
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  completo boolean NOT NULL,
  data date NOT NULL,
  dia_semana integer NOT NULL,
  hora_inicio timestamp without time zone,
  hora_termino timestamp without time zone,
  treino_id bigint NOT NULL,
  CONSTRAINT treino_data_pkey PRIMARY KEY (id),
  CONSTRAINT fk5jkbwmgei6q5681tep3r4piec FOREIGN KEY (treino_id)
      REFERENCES treino (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ukllxow8s6i1myhfvyloc4woack UNIQUE (data, treino_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE treino_data
  OWNER TO gym;

  
-- Table: auditing.treino_data_audited

-- DROP TABLE auditing.treino_data_audited;

CREATE TABLE auditing.treino_data_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  completo boolean,
  data date,
  dia_semana integer,
  hora_inicio timestamp without time zone,
  hora_termino timestamp without time zone,
  treino_id bigint,
  CONSTRAINT treino_data_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fksdxr4jf7ynmr9545lv33hoaer FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.treino_data_audited
  OWNER TO gym;

  
-- ----------------------------------------------

-- Table: treino_exercicio

-- DROP TABLE treino_exercicio;

CREATE TABLE treino_exercicio
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  carga integer,
  data_inativacao timestamp without time zone,
  is_ativo boolean NOT NULL,
  observacoes character varying(500),
  repeticoes integer,
  tempo_min integer,
  tipo_treino_exercicio integer NOT NULL,
  exercicio_id bigint NOT NULL,
  treino_id bigint NOT NULL,
  CONSTRAINT treino_exercicio_pkey PRIMARY KEY (id),
  CONSTRAINT fk3tsu3uc7djto1lj964dx19xu9 FOREIGN KEY (exercicio_id)
      REFERENCES exercicio (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkhrvgycsvjg1yn5k3ghm8fha2r FOREIGN KEY (treino_id)
      REFERENCES treino (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk7jwceggwsmpqieqbtmppijdp5 UNIQUE (exercicio_id, treino_id),
  CONSTRAINT treino_exercicio_carga_check CHECK (carga >= 0),
  CONSTRAINT treino_exercicio_repeticoes_check CHECK (repeticoes >= 0),
  CONSTRAINT treino_exercicio_tempo_min_check CHECK (tempo_min >= 0)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE treino_exercicio
  OWNER TO gym;

-- Table: auditing.treino_exercicio_audited

-- DROP TABLE auditing.treino_exercicio_audited;

CREATE TABLE auditing.treino_exercicio_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  carga integer,
  data_inativacao timestamp without time zone,
  is_ativo boolean,
  observacoes character varying(150),
  repeticoes integer,
  tempo_min integer,
  tipo_treino_exercicio integer,
  exercicio_id bigint,
  treino_id bigint,
  CONSTRAINT treino_exercicio_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkt9eg3a61ac4nm0vutn2n5gif5 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.treino_exercicio_audited
  OWNER TO gym;

  
-- ----------------------------------------------

-- Table: pessoa_treino

-- DROP TABLE pessoa_treino;

CREATE TABLE pessoa_treino
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  papel integer NOT NULL,
  pessoa_id bigint NOT NULL,
  treino_id bigint NOT NULL,
  CONSTRAINT pessoa_treino_pkey PRIMARY KEY (id),
  CONSTRAINT fkpcp6m81dojg40rmrei7eb6epj FOREIGN KEY (pessoa_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkqoedk1rh3tkeaod6c919aq0fp FOREIGN KEY (treino_id)
      REFERENCES treino (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk96v3ewad5pljedhe3xtuwfse UNIQUE (pessoa_id, papel, treino_id),
  CONSTRAINT ukoowvw9y3xs2qyg5fjgusfswts UNIQUE (treino_id, papel)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pessoa_treino
  OWNER TO gym;

  
-- Table: auditing.pessoa_treino_audited

-- DROP TABLE auditing.pessoa_treino_audited;

CREATE TABLE auditing.pessoa_treino_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  papel integer,
  pessoa_id bigint,
  treino_id bigint,
  CONSTRAINT pessoa_treino_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk414i0r8gqid90da51vdy2iiqt FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.pessoa_treino_audited
  OWNER TO gym;
  
-- ----------------------------------------------

-- Table: exercicio_treino_data

-- DROP TABLE exercicio_treino_data;

CREATE TABLE exercicio_treino_data
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  completo boolean NOT NULL,
  treino_data_id bigint NOT NULL,
  treino_exercicio_id bigint NOT NULL,
  CONSTRAINT exercicio_treino_data_pkey PRIMARY KEY (id),
  CONSTRAINT fkhxgvfxvto9bkt0viiglxtriex FOREIGN KEY (treino_data_id)
      REFERENCES treino_data (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkjkudksrjp1ca094q668js994m FOREIGN KEY (treino_exercicio_id)
      REFERENCES treino_exercicio (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ukorsjtq907sh4kl099rstekeoy UNIQUE (treino_data_id, treino_exercicio_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE exercicio_treino_data
  OWNER TO gym;

-- Table: auditing.exercicio_treino_data_audited

-- DROP TABLE auditing.exercicio_treino_data_audited;

CREATE TABLE auditing.exercicio_treino_data_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  completo boolean,
  treino_data_id bigint,
  treino_exercicio_id bigint,
  CONSTRAINT exercicio_treino_data_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk2ylg9a876e9tup34xo2wkdevh FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.exercicio_treino_data_audited
  OWNER TO gym;

  
-- ----------------------------------------------

-- Table: notificacao

-- DROP TABLE notificacao;

CREATE TABLE notificacao
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  texto character varying(255) NOT NULL,
  titulo character varying(70) NOT NULL,
  treino_data_id bigint,
  CONSTRAINT notificacao_pkey PRIMARY KEY (id),
  CONSTRAINT fkahh8xsngonuutfvwiguvm1mq2 FOREIGN KEY (treino_data_id)
      REFERENCES treino_data (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE notificacao
  OWNER TO gym;

  
-- Table: auditing.notificacao_audited

-- DROP TABLE auditing.notificacao_audited;

CREATE TABLE auditing.notificacao_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  texto character varying(255),
  titulo character varying(70),
  treino_data_id bigint,
  CONSTRAINT notificacao_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fka5h07pef2bsrvdmuytvhp995k FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.notificacao_audited
  OWNER TO gym;

  
-- ----------------------------------------------

-- Table: pessoa_notificacao

-- DROP TABLE pessoa_notificacao;

CREATE TABLE pessoa_notificacao
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  is_destinatario boolean NOT NULL,
  notificacao_id bigint NOT NULL,
  pessoa_id bigint NOT NULL,
  CONSTRAINT pessoa_notificacao_pkey PRIMARY KEY (id),
  CONSTRAINT fkfcknmyy75upj51kr4s935rrlp FOREIGN KEY (notificacao_id)
      REFERENCES notificacao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkfo3efyras08ombkr5q9rwlvmj FOREIGN KEY (pessoa_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ukbku5avcifxjo157s52fa8ym7r UNIQUE (pessoa_id, notificacao_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pessoa_notificacao
  OWNER TO gym;

  
-- Table: auditing.pessoa_notificacao_audited

-- DROP TABLE auditing.pessoa_notificacao_audited;

CREATE TABLE auditing.pessoa_notificacao_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  is_destinatario boolean,
  notificacao_id bigint,
  pessoa_id bigint,
  CONSTRAINT pessoa_notificacao_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkfektv1seey4l9f656hyftskh1 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.pessoa_notificacao_audited
  OWNER TO gym;

-- ----------------------------------------------
  
