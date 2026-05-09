-- Habilita o suporte a chaves estrangeiras no SQLite
PRAGMA foreign_keys = ON;

-- -----------------------------------------------------
-- Table estado
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS estado (
  idestado VARCHAR(45) NOT NULL,
  coef_esg DOUBLE NOT NULL,
  consumo_medio DOUBLE NOT NULL,
  nome VARCHAR(45) NOT NULL,
  PRIMARY KEY (idestado),
  UNIQUE (nome)
);

-- -----------------------------------------------------
-- Table referencia_sustentavel_onu
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS referencia_sustentavel_onu (
  idreferencia_sustentavel_onu INTEGER PRIMARY KEY AUTOINCREMENT,
  nivel_acesso VARCHAR(45) NOT NULL,
  litro_pessoa INT NOT NULL
);

-- -----------------------------------------------------
-- Table usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
  idusuario INTEGER PRIMARY KEY AUTOINCREMENT,
  nome VARCHAR(45) NOT NULL,
  num_residentes INT NOT NULL,
  estado_idestado VARCHAR(45) NOT NULL,
  FOREIGN KEY (estado_idestado) REFERENCES estado (idestado) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table consumo
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS consumo (
  idconsumo INTEGER PRIMARY KEY AUTOINCREMENT,
  m3_gastos DOUBLE NOT NULL,
  data_leitura DATE NOT NULL,
  usuario_idusuario INT NOT NULL,
  FOREIGN KEY (usuario_idusuario) REFERENCES usuario (idusuario) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table faixa_consumo
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS faixa_consumo (
  estado_idestado VARCHAR(45) PRIMARY KEY,
  fixo DOUBLE NULL,
  vol_inc INT NULL,
  step_1 DOUBLE NULL,
  faixa_1 INT NULL,
  step_2 DOUBLE NULL,
  faixa_2 INT NULL,
  step_3 DOUBLE NULL,
  faixa_3 INT NULL,
  step_4 DOUBLE NULL,
  faixa_4 INT NULL,
  step_5 DOUBLE NULL,
  faixa_5 INT NULL,
  concessionaria VARCHAR(45) NOT NULL,
  FOREIGN KEY (estado_idestado) REFERENCES estado (idestado) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION
);