SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';

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

-- -----------------------------------------------------
 
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
  descricao character varying(500),
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

-- -------------------------------------------------------------

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
  descricao character varying(500),
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

-- --------------------------------------------------

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
  
-- --------------------------------------------------

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
  aluno_id bigint NOT NULL,
  personal_id bigint NOT NULL,
  CONSTRAINT treino_pkey PRIMARY KEY (id),
  CONSTRAINT fkgl48tq3ah9kqki6i4naknbycf FOREIGN KEY (personal_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkh3pdduukjjn2c462um392k2mx FOREIGN KEY (aluno_id)
      REFERENCES pessoa (id) MATCH SIMPLE
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
  nome character varying(200),
  aluno_id bigint,
  personal_id bigint,
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
  
-- --------------------------------------------------

-- Table: treino_exercicio

-- DROP TABLE treino_exercicio;

CREATE TABLE treino_exercicio
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  carga integer,
  observacoes character varying(500),
  repeticoes integer,
  series integer NOT NULL,
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
  CONSTRAINT treino_exercicio_series_check CHECK (series >= 1),
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
  observacoes character varying(500),
  repeticoes integer,
  series integer,
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
  
-- --------------------------------------------------

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
  
-- --------------------------------------------------

-- Table: exercicio_realizado

-- DROP TABLE exercicio_realizado;

CREATE TABLE exercicio_realizado
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  completo boolean NOT NULL,
  treino_data_id bigint NOT NULL,
  treino_exercicio_id bigint NOT NULL,
  CONSTRAINT exercicio_realizado_pkey PRIMARY KEY (id),
  CONSTRAINT fkdmbdbtl9xwbyihfo7igxidw1m FOREIGN KEY (treino_exercicio_id)
      REFERENCES treino_exercicio (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkkifckoo41hn3277e6jwv6vi9i FOREIGN KEY (treino_data_id)
      REFERENCES treino_data (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ukqjryttioyfcei82810nv32y8n UNIQUE (treino_data_id, treino_exercicio_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE exercicio_realizado
  OWNER TO gym;

-- Table: auditing.exercicio_realizado_audited

-- DROP TABLE auditing.exercicio_realizado_audited;

CREATE TABLE auditing.exercicio_realizado_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  completo boolean,
  treino_data_id bigint,
  treino_exercicio_id bigint,
  CONSTRAINT exercicio_realizado_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk3evxc8huj8oj23ndexbgr5pw3 FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.exercicio_realizado_audited
  OWNER TO gym;
  
-- --------------------------------------------------

-- Table: notificacao

-- DROP TABLE notificacao;

CREATE TABLE notificacao
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  texto character varying(255) NOT NULL,
  titulo character varying(70) NOT NULL,
  CONSTRAINT notificacao_pkey PRIMARY KEY (id)
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

-- --------------------------------------------------

-- Table: destinatario_notificacao

-- DROP TABLE destinatario_notificacao;

CREATE TABLE destinatario_notificacao
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  notificacao_id bigint NOT NULL,
  pessoa_id bigint NOT NULL,
  CONSTRAINT destinatario_notificacao_pkey PRIMARY KEY (id),
  CONSTRAINT fkfrpdy9rv1kh752md4fjyo779b FOREIGN KEY (pessoa_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkhryrlbt2lib7meosuv22iom3v FOREIGN KEY (notificacao_id)
      REFERENCES notificacao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk5kh8jebht0x4bx1cpwykvpwq UNIQUE (pessoa_id, notificacao_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE destinatario_notificacao
  OWNER TO gym;

-- Table: auditing.destinatario_notificacao_audited

-- DROP TABLE auditing.destinatario_notificacao_audited;

CREATE TABLE auditing.destinatario_notificacao_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  notificacao_id bigint,
  pessoa_id bigint,
  CONSTRAINT destinatario_notificacao_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk7gdibv0gpp84uh99lxjn4dkwj FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.destinatario_notificacao_audited
  OWNER TO gym;
  
-- --------------------------------------------------

-- Table: perimetria

-- DROP TABLE perimetria;

CREATE TABLE perimetria
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  abdomen double precision NOT NULL,
  antebraco_direito double precision NOT NULL,
  antebraco_esquerdo double precision NOT NULL,
  braco_direito_contraido double precision NOT NULL,
  braco_direito_relaxado double precision NOT NULL,
  braco_esquerdo_contraido double precision NOT NULL,
  braco_esquerdo_relaxado double precision NOT NULL,
  cintura double precision NOT NULL,
  coxa_distal_direita double precision NOT NULL,
  coxa_distal_esquerda double precision NOT NULL,
  coxa_media_direita double precision NOT NULL,
  coxa_media_esquerda double precision NOT NULL,
  coxa_proximal_direita double precision NOT NULL,
  coxa_proximal_esquerda double precision NOT NULL,
  panturrilha_direita double precision NOT NULL,
  panturrilha_esquerda double precision NOT NULL,
  pescoco double precision NOT NULL,
  quadril double precision NOT NULL,
  torax double precision NOT NULL,
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
  abdomen double precision,
  antebraco_direito double precision,
  antebraco_esquerdo double precision,
  braco_direito_contraido double precision,
  braco_direito_relaxado double precision,
  braco_esquerdo_contraido double precision,
  braco_esquerdo_relaxado double precision,
  cintura double precision,
  coxa_distal_direita double precision,
  coxa_distal_esquerda double precision,
  coxa_media_direita double precision,
  coxa_media_esquerda double precision,
  coxa_proximal_direita double precision,
  coxa_proximal_esquerda double precision,
  panturrilha_direita double precision,
  panturrilha_esquerda double precision,
  pescoco double precision,
  quadril double precision,
  torax double precision,
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

-- Table: resposta

-- DROP TABLE resposta;

CREATE TABLE resposta
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  cirurgia character varying(255) NOT NULL,
  doenca_familiar character varying(255) NOT NULL,
  medicamento character varying(255) NOT NULL,
  objetivos_atividade_fisica character varying(255) NOT NULL,
  observacao character varying(255) NOT NULL,
  pratica_atividade character varying(255) NOT NULL,
  CONSTRAINT resposta_pkey PRIMARY KEY (id)
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
  cirurgia character varying(255),
  doenca_familiar character varying(255),
  medicamento character varying(255),
  objetivos_atividade_fisica character varying(255),
  observacao character varying(255),
  pratica_atividade character varying(255),
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

-- Table: avaliacao_fisica

-- DROP TABLE avaliacao_fisica;

CREATE TABLE avaliacao_fisica
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  data date NOT NULL,
  perimetria_id bigint,
  pessoa_id bigint NOT NULL,
  resposta_id bigint NOT NULL,
  CONSTRAINT avaliacao_fisica_pkey PRIMARY KEY (id),
  CONSTRAINT fk7le88jsjsrasakio3qmthlmkt FOREIGN KEY (perimetria_id)
      REFERENCES perimetria (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkd8o6qn0jhq9204i9s5iwm37jw FOREIGN KEY (pessoa_id)
      REFERENCES pessoa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkj1ia23qiujw6wclmth7xgcmqq FOREIGN KEY (resposta_id)
      REFERENCES resposta (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
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
  perimetria_id bigint,
  pessoa_id bigint,
  resposta_id bigint,
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

-- Table: predicao_gordura_siri

-- DROP TABLE predicao_gordura_siri;

CREATE TABLE predicao_gordura_siri
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  densidade_corporal double precision NOT NULL,
  gordura double precision NOT NULL,
  CONSTRAINT predicao_gordura_siri_pkey PRIMARY KEY (id)
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

-- Table: dobras_cutaneas

-- DROP TABLE dobras_cutaneas;

CREATE TABLE dobras_cutaneas
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  abdominal double precision NOT NULL,
  axilar_media double precision NOT NULL,
  bicital double precision NOT NULL,
  coxa double precision NOT NULL,
  panturrilha double precision NOT NULL,
  peitoral double precision NOT NULL,
  subescapular double precision NOT NULL,
  supra_iliaca double precision NOT NULL,
  toracica double precision NOT NULL,
  tricipal double precision NOT NULL,
  CONSTRAINT dobras_cutaneas_pkey PRIMARY KEY (id)
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
  abdominal double precision,
  axilar_media double precision,
  bicital double precision,
  coxa double precision,
  panturrilha double precision,
  peitoral double precision,
  subescapular double precision,
  supra_iliaca double precision,
  toracica double precision,
  tricipal double precision,
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

-- Table: indice_massa_corporal

-- DROP TABLE indice_massa_corporal;

CREATE TABLE indice_massa_corporal
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  altura numeric(19,2) NOT NULL,
  peso numeric(19,2) NOT NULL,
  CONSTRAINT indice_massa_corporal_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE indice_massa_corporal
  OWNER TO gym;

-- Table: auditing.indice_massa_corporal_audited

-- DROP TABLE auditing.indice_massa_corporal_audited;

CREATE TABLE auditing.indice_massa_corporal_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  altura numeric(19,2),
  peso numeric(19,2),
  CONSTRAINT indice_massa_corporal_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkf7fhufgifuomdht6rab73j1ek FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.indice_massa_corporal_audited
  OWNER TO gym;

-- --------------------------------------------------

-- Table: abstract_entity_avaliacao_antropometrica

-- DROP TABLE abstract_entity_avaliacao_antropometrica;

CREATE TABLE abstract_entity_avaliacao_antropometrica
(
  id bigserial NOT NULL,
  created timestamp without time zone NOT NULL,
  updated timestamp without time zone,
  avaliacao_fisica_id bigint NOT NULL,
  dobras_cutaneas_id bigint NOT NULL,
  indice_massa_corporal_id bigint NOT NULL,
  predicao_gordura_siri_id bigint NOT NULL,
  CONSTRAINT abstract_entity_avaliacao_antropometrica_pkey PRIMARY KEY (id),
  CONSTRAINT fkdhdxh0lifrverot3vb3gfxosl FOREIGN KEY (dobras_cutaneas_id)
      REFERENCES dobras_cutaneas (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkny13j359cnks0b2m6pek53g38 FOREIGN KEY (indice_massa_corporal_id)
      REFERENCES indice_massa_corporal (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fktkhi84d9236kojlayd8glxeev FOREIGN KEY (avaliacao_fisica_id)
      REFERENCES avaliacao_fisica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fktm0e0jep0botdp2wuhwuqapqy FOREIGN KEY (predicao_gordura_siri_id)
      REFERENCES predicao_gordura_siri (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE abstract_entity_avaliacao_antropometrica
  OWNER TO gym;

-- Table: auditing.abstract_entity_avaliacao_antropometrica_audited

-- DROP TABLE auditing.abstract_entity_avaliacao_antropometrica_audited;

CREATE TABLE auditing.abstract_entity_avaliacao_antropometrica_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  revision_type smallint,
  avaliacao_fisica_id bigint,
  dobras_cutaneas_id bigint,
  indice_massa_corporal_id bigint,
  predicao_gordura_siri_id bigint,
  CONSTRAINT abstract_entity_avaliacao_antropometrica_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkcmgqo7k8mrwnnvjmqsrk2ppka FOREIGN KEY (revision)
      REFERENCES auditing.revision (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.abstract_entity_avaliacao_antropometrica_audited
  OWNER TO gym;

-- --------------------------------------------------

-- Table: protocolo_guedes

-- DROP TABLE protocolo_guedes;

CREATE TABLE protocolo_guedes
(
  id bigint NOT NULL,
  CONSTRAINT protocolo_guedes_pkey PRIMARY KEY (id),
  CONSTRAINT fkrek55fp9b7x19uu494k2736n7 FOREIGN KEY (id)
      REFERENCES abstract_entity_avaliacao_antropometrica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE protocolo_guedes
  OWNER TO gym;

-- Table: auditing.protocolo_guedes_audited

-- DROP TABLE auditing.protocolo_guedes_audited;

CREATE TABLE auditing.protocolo_guedes_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  CONSTRAINT protocolo_guedes_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fkgphoi84x0cwn8u1pk8p7yx18 FOREIGN KEY (id, revision)
      REFERENCES auditing.abstract_entity_avaliacao_antropometrica_audited (id, revision) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.protocolo_guedes_audited
  OWNER TO gym;

-- --------------------------------------------------

-- Table: protocolo_pollock

-- DROP TABLE protocolo_pollock;

CREATE TABLE protocolo_pollock
(
  id bigint NOT NULL,
  CONSTRAINT protocolo_pollock_pkey PRIMARY KEY (id),
  CONSTRAINT fkdvsh8i6pfshe2hklhes9u1h8t FOREIGN KEY (id)
      REFERENCES abstract_entity_avaliacao_antropometrica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE protocolo_pollock
  OWNER TO gym;

-- Table: auditing.protocolo_pollock_audited

-- DROP TABLE auditing.protocolo_pollock_audited;

CREATE TABLE auditing.protocolo_pollock_audited
(
  id bigint NOT NULL,
  revision bigint NOT NULL,
  CONSTRAINT protocolo_pollock_audited_pkey PRIMARY KEY (id, revision),
  CONSTRAINT fk6up3ktoroy23jukm360p8vt7c FOREIGN KEY (id, revision)
      REFERENCES auditing.abstract_entity_avaliacao_antropometrica_audited (id, revision) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE auditing.protocolo_pollock_audited
  OWNER TO gym;

-- --------------------------------------------------

  
  