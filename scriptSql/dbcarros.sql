/*
Criação do banco de dados
*/
DROP DATABASE IF EXISTS auto_manager;

CREATE DATABASE auto_manager;

USE auto_manager;

CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    rg VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    dt_nasc DATETIME NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    telefone1 VARCHAR(255) NOT NULL,
    telefone2 VARCHAR(255)
);

CREATE TABLE funcionario (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    rg VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    dt_nasc DATETIME NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    telefone1 VARCHAR(255) NOT NULL,
    telefone2 VARCHAR(255),
    tipo_acesso TINYINT NOT NULL,
    login VARCHAR(255) UNIQUE,
    senha VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE
   );

CREATE TABLE carro (
    id_carro INT AUTO_INCREMENT PRIMARY KEY,
    renavan VARCHAR(255) UNIQUE,
    numero VARCHAR(255) NOT NULL,
    placa VARCHAR(255) NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    ano INT NOT NULL,
    marca VARCHAR(255) NOT NULL,
    cor VARCHAR(255) NOT NULL,
    valor_carro DECIMAL NOT NULL,
    custo DECIMAL NOT NULL,
    data_entrada DATETIME NOT NULL,
    status INT NOT NULL
);

CREATE TABLE compra (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_carro INT NOT NULL,
    dt_compra DATETIME NOT NULL,
    local_compra VARCHAR(255) NOT NULL,
    custo DECIMAL NOT NULL,
    nome_do_fornecedor VARCHAR(255) NOT NULL,

    FOREIGN KEY (id_carro) REFERENCES carro (id_carro)  ON DELETE CASCADE  
);

CREATE TABLE manutencao (
    id_manutencao INT AUTO_INCREMENT PRIMARY KEY,
    id_carro INT NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    dt_manutencao DATETIME NOT NULL,
    custo DECIMAL NOT NULL,

    FOREIGN KEY (id_carro) REFERENCES carro (id_carro) ON DELETE CASCADE
);

CREATE TABLE venda (
    id_venda INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_carro INT NOT NULL,
    id_funcionario INT NOT NULL,
    dt_venda DATETIME NOT NULL,
    num_venda VARCHAR(255) NOT NULL,

    FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente) ON DELETE CASCADE,
    FOREIGN KEY (id_carro) REFERENCES carro (id_carro) ON DELETE CASCADE,
    FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario) ON DELETE CASCADE
);

CREATE TABLE finaciamento (
    id_financiamento INT AUTO_INCREMENT PRIMARY KEY,
    id_venda INT NOT NULL ,
    banco VARCHAR(255) NOT NULL,
    valorFinanciamento DECIMAL NOT NULL,
    nParcelas INT NOT NULL,

    FOREIGN KEY (id_venda) REFERENCES venda (id_venda) ON DELETE CASCADE
);


CREATE TABLE valor_entrada (
    id_entrada INT AUTO_INCREMENT PRIMARY KEY,
    id_venda INT NOT NULL,
    descricao LONGTEXT,
    valor_entrada DECIMAL NOT NULL,

    FOREIGN KEY (id_venda) REFERENCES venda (id_venda) ON DELETE CASCADE
);

CREATE TABLE troca_carro (
    id_troca INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(255) NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    marca VARCHAR(255) NOT NULL,
    cor VARCHAR(255) NOT NULL,
    local VARCHAR(255),
    ano INT NOT NULL,
    valor_carro DECIMAL NOT NULL,
    data_entrada DATETIME NOT NULL,
    
    id_venda INT NOT NULL,

    FOREIGN KEY (id_venda) REFERENCES venda (id_venda) ON DELETE CASCADE
);

CREATE TABLE nota_fiscal (
    id_venda INT NOT NULL,
    endereco_arquivo VARCHAR(255),

    FOREIGN KEY (id_venda) REFERENCES venda (id_venda) ON DELETE CASCADE
);


INSERT INTO cliente (id_cliente,rg,cpf,nome,dt_nasc,endereco,telefone1) 
VALUES 
(1,'96.728.285-8','377.381.336/60','May','10-07-19','209-8565 Dolor. St.','1-476-728-2998'),
(2,'56.577.584-7','487.374.262/22','Zelda','09-03-18','P.O. Box 467, 9232 Nullam Road','1-389-762-9617'),
(3,'73.006.757-0','518.876.764/75','Ora','01-02-18','Ap #866-6924 In Road','1-532-627-6434'),
(4,'41.304.172-6','771.929.433/47','Hilda','24-02-18','P.O. Box 768, 7983 Lorem, Road','1-688-528-0967'),
(5,'65.169.811-1','973.199.632/57','Reese','08-02-18','P.O. Box 338, 9570 Ligula. Rd.','736-9528'),
(6,'79.965.013-6','152.775.139/42','Idona','08-08-18','792 Rutrum, St.','300-2876'),
(7,'08.274.587-4','207.707.694/60','Basil','05-11-17','P.O. Box 210, 3775 Eu Rd.','1-966-652-8296'),
(8,'83.054.990-4','278.521.650/89','Quyn','08-09-19','Ap #866-7446 Egestas, Avenue','1-936-567-8853'),
(9,'48.660.744-9','147.199.476/54','Astra','12-12-17','Ap #325-6763 Nec Av.','344-9055'),
(10,'31.003.147-0','223.667.995/91','Ginger','16-05-19','564-7441 Tincidunt Avenue','488-7245');

INSERT INTO cliente (id_cliente,rg,cpf,nome,dt_nasc,endereco,telefone1) 
VALUES 
(11,'33.743.257-2','788.501.692/65','Quentin','17-04-18','P.O. Box 205, 183 Elit Rd.','500-3518'),
(12,'79.153.822-9','306.462.722/88','Candice','12-09-18','P.O. Box 313, 2238 Suspendisse St.','1-792-525-7770'),
(13,'29.646.144-3','275.002.848/70','Jordan','06-03-18','Ap #257-2938 Turpis Avenue','668-4891'),
(14,'09.776.628-1','102.610.228/13','Reece','11-01-19','P.O. Box 229, 6944 Erat. Av.','116-3003'),
(15,'94.018.514-9','117.714.845/98','Illana','11-02-19','Ap #419-2903 Phasellus Rd.','302-6995'),
(16,'01.715.581-0','627.133.410/22','Frances','27-10-17','Ap #250-2647 Vulputate, St.','1-392-873-7159'),
(17,'03.454.697-0','299.201.612/85','Idola','06-12-18','711-1381 In Ave','1-406-921-7433'),
(18,'75.418.821-0','782.874.520/96','Stone','18-05-18','Ap #657-6282 Egestas Road','1-171-561-7069'),
(19,'71.378.804-4','546.250.207/28','Susan','08-11-18','P.O. Box 478, 3438 Nunc St.','1-387-719-0450'),
(20,'47.492.284-6','431.105.897/95','Halla','10-05-19','921-7434 Morbi Road','293-3238');

INSERT INTO funcionario (id_funcionario,rg,cpf,nome,dt_nasc,endereco,telefone1,tipo_acesso,login,senha,email)
VALUES
(19,'71.308.804-4','546.111.207/28','Gerente','08-11-18','P.O. Box 478, 3438 Nunc St.','1-387-719-0450','1','gerente','1234','gerente@cmtop.br'),
(20,'47.492.214-X','431.445.897/55','Funcionario','10-05-19','921-7434 Morbi Road','293-3238','2','funcionario','1234','funcionario@cmtop.br');


INSERT INTO carro (id_carro,renavan,numero,placa,modelo,ano,marca,cor,valor_carro,custo,data_entrada,status) 
VALUES 
(1,'624939181','5580022','WSK25KUB9ST','Keelie V. Gallegos','9636','Desiree','Kylee','89937.36','41734.29','2018-05-05 10:58:37','8'),
(2,'332645726','6117710','FFH42YQA3SX','Griffith A. Lynch','2336','Whoopi','Maite','73611.85','06309.78','2017-11-18 19:57:26','5'),
(3,'661597546','9283851','VUN10MDU5PT','Adria A. Patterson','7442','Cheyenne','Adele','00723.57','72003.46','2019-03-31 11:53:56','9'),
(4,'515790061','4826140','RPL51EVR7XN','Ivor I. Porter','9831','Kirk','Michelle','93216.76','26584.25','2017-11-16 14:25:26','6'),
(5,'870715768','9954005','QTK96YMR0XL','Brian R. Perkins','3270','Lacota','Blossom','88256.56','65197.73','2019-07-05 08:16:53','7'),
(6,'883233196','2040198','MTR35SVP0WY','Dane G. Holcomb','3901','Damian','Elvis','49442.86','06000.53','2018-10-15 04:17:22','0'),
(7,'424431424','0284994','ZCC52ZGI5XO','Hilary K. Parker','8325','Timothy','Paul','81055.50','06254.72','2018-09-15 15:58:36','7'),
(8,'441312962','3858306','YDN57MFX4HP','Elliott V. Collier','5571','Brady','Erica','56763.85','69558.70','2018-05-14 04:42:48','6'),
(9,'857747047','3644934','IQM42HBD2HM','Bernard X. Contreras','6852','Michael','Kasimir','67928.45','76022.36','2019-04-07 17:02:42','3'),
(10,'239867892','1200022','UKB51MQP4GV','Gavin P. Copeland','7787','Hyatt','Xerxes','73477.56','97537.02','2019-03-27 13:26:12','9');



