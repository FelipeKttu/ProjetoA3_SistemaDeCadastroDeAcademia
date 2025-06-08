-- criação banco de dados
CREATE DATABASE IF NOT EXISTS SistemaCadastroAcademia
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE SistemaCadastroAcademia;

-- tabela de Membros
CREATE TABLE IF NOT EXISTS Membros (
                                       ID            INT AUTO_INCREMENT PRIMARY KEY,
                                       Nome          VARCHAR(255) NOT NULL,
    CPF           VARCHAR(14)  NOT NULL UNIQUE,
    Telefone      VARCHAR(20),
    Endereco      VARCHAR(255),
    Data_Cadastro DATE         NOT NULL
    ) ENGINE = InnoDB;

-- tabela de Funcionários
CREATE TABLE IF NOT EXISTS Funcionarios (
                                            ID    INT AUTO_INCREMENT PRIMARY KEY,
                                            Nome  VARCHAR(255) NOT NULL,
    Cargo VARCHAR(100) NOT NULL,
    Login VARCHAR(50)  NOT NULL UNIQUE,
    Senha VARCHAR(255) NOT NULL
    ) ENGINE = InnoDB;

-- tabela de Treinos
CREATE TABLE IF NOT EXISTS Treinos (
                                       ID                       INT AUTO_INCREMENT PRIMARY KEY,
                                       ID_Membro                INT          NOT NULL,
                                       ID_Funcionario_Instrutor INT          NULL,
                                       Tipo                     VARCHAR(100) NOT NULL,
    Descricao                TEXT,
    Duracao_Minutos          INT,
    Data_Inicio              DATE         NOT NULL,
    CONSTRAINT fk_treino_membro FOREIGN KEY (ID_Membro) REFERENCES Membros (ID) ON DELETE CASCADE,
    CONSTRAINT fk_treino_funcionario FOREIGN KEY (ID_Funcionario_Instrutor) REFERENCES Funcionarios (ID) ON DELETE SET NULL
    ) ENGINE = InnoDB;

-- tabela de Pagamentos
CREATE TABLE IF NOT EXISTS Pagamentos (
                                          ID                      INT AUTO_INCREMENT PRIMARY KEY,
                                          ID_Membro               INT            NOT NULL,
                                          ID_Funcionario_Registro INT            NULL,
                                          Valor                   DECIMAL(10, 2) NOT NULL,
    Data_Pagamento          DATE           NOT NULL,
    Status                  VARCHAR(50)    NOT NULL,
    Metodo_Pagamento        VARCHAR(50)    NULL,
    Data_Vencimento         DATE           NULL,
    CONSTRAINT fk_pagamento_membro FOREIGN KEY (ID_Membro) REFERENCES Membros (ID) ON DELETE CASCADE,
    CONSTRAINT fk_pagamento_funcionario FOREIGN KEY (ID_Funcionario_Registro) REFERENCES Funcionarios (ID) ON DELETE SET NULL
    ) ENGINE = InnoDB;

-- tabela de Histórico de Atividades
CREATE TABLE IF NOT EXISTS Historico_Atividades (
                                                    ID                         INT AUTO_INCREMENT PRIMARY KEY,
                                                    ID_Membro                  INT          NOT NULL,
                                                    ID_Funcionario_Responsavel INT          NULL,
                                                    Atividade                  VARCHAR(255) NOT NULL,
    Data_Hora_Atividade        DATETIME     NOT NULL,
    Tempo_Execucao_Minutos     INT          NULL,
    Observacoes                TEXT         NULL,
    CONSTRAINT fk_historico_membro FOREIGN KEY (ID_Membro) REFERENCES Membros (ID) ON DELETE CASCADE,
    CONSTRAINT fk_historico_funcionario FOREIGN KEY (ID_Funcionario_Responsavel) REFERENCES Funcionarios (ID) ON DELETE SET NULL
    ) ENGINE = InnoDB;


-- limpeza dos dados antigos para reexecutar
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Historico_Atividades;
TRUNCATE TABLE Pagamentos;
TRUNCATE TABLE Treinos;
TRUNCATE TABLE Membros;
TRUNCATE TABLE Funcionarios;
SET FOREIGN_KEY_CHECKS = 1;


-- Inserir 40 Membros com CPFs Aleatórios
INSERT INTO Membros (Nome, CPF, Telefone, Endereco, Data_Cadastro) VALUES
                                                                       ('Anitta', '245.876.912-33', '(21) 91111-1111', 'Rua da Lapa, 10, Rio de Janeiro, RJ', '2024-01-05'),
                                                                       ('Gilberto Gil', '876.123.456-78', '(21) 91111-1112', 'Av. Atlântica, 200, Copacabana, Rio de Janeiro, RJ', '2024-01-10'),
                                                                       ('Caetano Veloso', '998.765.432-10', '(21) 91111-1113', 'Rua Visconde de Pirajá, 300, Ipanema, Rio de Janeiro, RJ', '2024-01-12'),
                                                                       ('Ivete Sangalo', '123.456.789-99', '(71) 92222-2221', 'Av. Oceânica, 400, Barra, Salvador, BA', '2024-01-15'),
                                                                       ('Chico Buarque', '345.678.901-23', '(21) 91111-1114', 'Rua Dias Ferreira, 500, Leblon, Rio de Janeiro, RJ', '2024-01-20'),
                                                                       ('Marisa Monte', '567.890.123-45', '(21) 91111-1115', 'Rua Jardim Botânico, 600, Rio de Janeiro, RJ', '2024-02-01'),
                                                                       ('Lulu Santos', '789.012.345-67', '(21) 91111-1116', 'Av. Niemeyer, 700, Vidigal, Rio de Janeiro, RJ', '2024-02-05'),
                                                                       ('Rita Lee', '901.234.567-89', '(11) 93333-3331', 'Rua Augusta, 800, Consolação, São Paulo, SP', '2024-02-10'),
                                                                       ('Renato Russo', '432.109.876-54', '(61) 94444-4441', 'SQS 308 Bloco K, Asa Sul, Brasília, DF', '2024-02-15'),
                                                                       ('Cazuza', '654.321.098-76', '(21) 91111-1117', 'Baixo Leblon, Rio de Janeiro, RJ', '2024-02-20'),
                                                                       ('Djavan', '876.543.210-98', '(82) 95555-5551', 'Av. da Paz, 900, Jaraguá, Maceió, AL', '2024-03-01'),
                                                                       ('Milton Nascimento', '098.765.432-11', '(31) 96666-6661', 'Rua da Bahia, 1000, Lourdes, Belo Horizonte, MG', '2024-03-05'),
                                                                       ('Gal Costa', '210.987.654-32', '(21) 91111-1118', 'Ladeira da Glória, Rio de Janeiro, RJ', '2024-03-10'),
                                                                       ('Maria Bethânia', '432.109.876-53', '(21) 91111-1119', 'Rua do Ouvidor, 110, Centro, Rio de Janeiro, RJ', '2024-03-15'),
                                                                       ('Elis Regina', '654.321.098-75', '(11) 93333-3332', 'Rua dos Pinheiros, 120, Pinheiros, São Paulo, SP', '2024-03-20'),
                                                                       ('Tom Jobim', '876.543.210-97', '(21) 91111-1120', 'Rua Vinicius de Moraes, Ipanema, Rio de Janeiro, RJ', '2024-04-01'),
                                                                       ('Vinicius de Moraes', '112.233.445-56', '(21) 91111-1121', 'Rua Nascimento Silva, 107, Ipanema, Rio de Janeiro, RJ', '2024-04-02'),
                                                                       ('Jorge Ben Jor', '334.455.667-78', '(21) 91111-1122', 'Rua do Lavradio, Feira do Rio Antigo, Rio de Janeiro, RJ', '2024-04-05'),
                                                                       ('Tim Maia', '556.677.889-90', '(21) 91111-1123', 'Rua da Tijuca, 130, Rio de Janeiro, RJ', '2024-04-10'),
                                                                       ('Raul Seixas', '778.899.001-12', '(11) 93333-3333', 'Av. São João, 140, Centro, São Paulo, SP', '2024-04-15'),
                                                                       ('Zeca Pagodinho', '990.011.223-34', '(21) 91111-1124', 'Rua da Carioca, 150, Centro, Rio de Janeiro, RJ', '2024-04-20'),
                                                                       ('Alcione', '112.233.445-55', '(21) 91111-1125', 'Rua do Catete, 160, Rio de Janeiro, RJ', '2024-05-01'),
                                                                       ('Pitty', '334.455.667-77', '(11) 93333-3334', 'Rua Teodoro Sampaio, 170, Pinheiros, São Paulo, SP', '2024-05-05'),
                                                                       ('Seu Jorge', '556.677.889-99', '(21) 91111-1126', 'Morro do Vidigal, Rio de Janeiro, RJ', '2024-05-10'),
                                                                       ('Nando Reis', '778.899.001-11', '(11) 93333-3335', 'Vila Madalena, São Paulo, SP', '2024-05-15'),
                                                                       ('Zélia Duncan', '990.011.223-33', '(21) 91111-1127', 'Rua General Glicério, Laranjeiras, Rio de Janeiro, RJ', '2024-05-20'),
                                                                       ('Adriana Calcanhotto', '123.321.456-65', '(51) 97777-7771', 'Rua da Praia, 180, Centro Histórico, Porto Alegre, RS', '2024-06-01'),
                                                                       ('Gusttavo Lima', '234.432.567-76', '(62) 98888-8881', 'Av. T-10, 190, Setor Bueno, Goiânia, GO', '2024-06-05'),
                                                                       ('Marília Mendonça', '345.543.678-87', '(62) 98888-8882', 'Parque Vaca Brava, Goiânia, GO', '2024-06-10'),
                                                                       ('Chitãozinho', '456.654.789-98', '(19) 99999-9991', 'Av. Brasil, 200, Campinas, SP', '2024-06-15'),
                                                                       ('Xororó', '567.765.890-09', '(19) 99999-9992', 'Lagoa do Taquaral, Campinas, SP', '2024-06-16'),
                                                                       ('Zezé Di Camargo', '678.876.901-10', '(62) 98888-8883', 'Rua 85, Setor Marista, Goiânia, GO', '2024-07-01'),
                                                                       ('Luciano', '789.987.012-21', '(62) 98888-8884', 'Rua 9, Setor Oeste, Goiânia, GO', '2024-07-02'),
                                                                       ('Fagner', '890.098.123-32', '(85) 96666-5551', 'Av. Beira Mar, 210, Meireles, Fortaleza, CE', '2024-07-05'),
                                                                       ('Elba Ramalho', '901.109.234-43', '(83) 95555-4441', 'Praia de Tambaú, João Pessoa, PB', '2024-07-10'),
                                                                       ('Geraldo Azevedo', '012.210.345-54', '(81) 94444-3331', 'Rua da Aurora, Recife, PE', '2024-07-15'),
                                                                       ('Wesley Safadão', '123.321.456-66', '(85) 96666-5552', 'Porto das Dunas, Aquiraz, CE', '2024-07-20'),
                                                                       ('Ney Matogrosso', '234.432.567-77', '(21) 91111-1128', 'Parque Lage, Jardim Botânico, Rio de Janeiro, RJ', '2024-08-01'),
                                                                       ('Hermeto Pascoal', '345.543.678-88', '(82) 95555-5552', 'Lagoa do Roteiro, Alagoas', '2024-08-05'),
                                                                       ('MC Pikachu', '456.654.789-99', '(11) 93333-3336', 'Av. Engenheiro Roberto Freire, Ponta Negra, Natal, RN', '2024-08-10');

-- Inserir dados de exemplo em Treinos
INSERT INTO Treinos (ID_Membro, ID_Funcionario_Instrutor, Tipo, Descricao, Duracao_Minutos, Data_Inicio) VALUES
                                                                                                             (1, 4, 'Treino Pop Star - Cardio', 'Foco em dança e resistência para shows.', 75, '2024-02-01'),
                                                                                                             (2, 5, 'Voz e Violão - Postura', 'Exercícios de fortalecimento do core para performance.', 45, '2024-03-01'),
                                                                                                             (3, 6, 'Poesia em Movimento', 'Treino funcional com foco em expressão corporal.', 60, '2024-03-12'),
                                                                                                             (5, 7, 'Funcional ao Ar Livre', 'Treino utilizando peso corporal e elásticos.', 50, '2024-04-12'),
                                                                                                             (8, 8, 'Rock de Garagem - Resistência', 'Circuito de alta intensidade (HIIT).', 50, '2024-05-10'),
                                                                                                             (10, 9, 'Resistência Muscular', 'Treino com muitas repetições e pouco descanso.', 55, '2024-05-12'),
                                                                                                             (15, 10, 'Treino de Mobilidade', 'Foco em articulações e amplitude de movimento.', 40, '2024-06-01'),
                                                                                                             (20, 4, 'Manutenção de Força', 'Treino full-body 3x por semana.', 60, '2024-06-15'),
                                                                                                             (23, 6, 'Power Rock Workout', 'Treino de alta energia com foco em cardio e força.', 50, '2024-06-20'),
                                                                                                             (25, 7, 'Equilíbrio e Postura', 'Exercícios inspirados no Pilates e Yoga.', 60, '2024-07-01'),
                                                                                                             (30, 5, 'Sertanejo Fitness', 'Mix de dança e exercícios aeróbicos.', 55, '2024-07-10'),
                                                                                                             (38, 8, 'Performance de Palco', 'Trabalho de cardio e respiração para artistas.', 70, '2024-08-05');

-- Inserir dados de exemplo em Pagamentos
INSERT INTO Pagamentos (ID_Membro, ID_Funcionario_Registro, Valor, Data_Pagamento, Status, Metodo_Pagamento, Data_Vencimento) VALUES
                                                                                                                                  (1, 2, 250.00, '2024-07-01', 'Pago', 'PIX', '2024-07-05'),
                                                                                                                                  (2, 2, 250.00, '2024-07-03', 'Pago', 'Cartão de Débito', '2024-07-05'),
                                                                                                                                  (3, 3, 199.90, '2024-07-05', 'Pendente', 'Boleto', '2024-07-10'),
                                                                                                                                  (4, 3, 199.90, '2024-06-10', 'Atrasado', 'Boleto', '2024-06-10'),
                                                                                                                                  (5, 2, 250.00, '2024-07-01', 'Pago', 'Dinheiro', '2024-07-05'),
                                                                                                                                  (6, 2, 250.00, '2024-07-02', 'Pago', 'Cartão de Crédito', '2024-07-05'),
                                                                                                                                  (7, 3, 199.90, '2024-07-03', 'Pago', 'PIX', '2024-07-10'),
                                                                                                                                  (8, 3, 199.90, '2024-07-04', 'Pago', 'Boleto', '2024-07-10'),
                                                                                                                                  (9, 2, 199.90, '2024-07-05', 'Pago', 'Dinheiro', '2024-07-10'),
                                                                                                                                  (10, 2, 250.00, '2024-06-20', 'Atrasado', 'Boleto', '2024-06-20'),
                                                                                                                                  (11, 3, 199.90, '2024-07-01', 'Pago', 'PIX', '2024-07-01'),
                                                                                                                                  (12, 2, 199.90, '2024-07-05', 'Pendente', 'Boleto', '2024-07-10'),
                                                                                                                                  (13, 3, 199.90, '2024-07-02', 'Pago', 'Cartão de Crédito', '2024-07-10'),
                                                                                                                                  (14, 2, 250.00, '2024-07-03', 'Pago', 'Dinheiro', '2024-07-05'),
                                                                                                                                  (15, 3, 199.90, '2024-07-04', 'Pago', 'PIX', '2024-07-10'),
                                                                                                                                  (16, 2, 250.00, '2024-07-05', 'Pago', 'Boleto', '2024-07-05'),
                                                                                                                                  (17, 3, 250.00, '2024-07-06', 'Pendente', 'Boleto', '2024-07-10'),
                                                                                                                                  (18, 2, 199.90, '2024-07-07', 'Pago', 'Cartão de Débito', '2024-07-10'),
                                                                                                                                  (19, 3, 199.90, '2024-07-08', 'Pago', 'PIX', '2024-07-10'),
                                                                                                                                  (20, 2, 250.00, '2024-07-09', 'Atrasado', 'Dinheiro', '2024-07-05'),
                                                                                                                                  (21, 2, 199.90, '2024-07-10', 'Pago', 'Cartão de Crédito', '2024-07-10'),
                                                                                                                                  (22, 3, 199.90, '2024-07-11', 'Pendente', 'Boleto', '2024-07-15'),
                                                                                                                                  (23, 2, 199.90, '2024-07-12', 'Pago', 'PIX', '2024-07-15'),
                                                                                                                                  (24, 3, 250.00, '2024-07-13', 'Pago', 'Dinheiro', '2024-07-15'),
                                                                                                                                  (25, 2, 199.90, '2024-07-14', 'Atrasado', 'Boleto', '2024-07-10'),
                                                                                                                                  (26, 3, 199.90, '2024-07-15', 'Pago', 'Cartão de Débito', '2024-07-20'),
                                                                                                                                  (27, 2, 250.00, '2024-07-16', 'Pago', 'PIX', '2024-07-20'),
                                                                                                                                  (28, 3, 199.90, '2024-07-17', 'Pendente', 'Boleto', '2024-07-20'),
                                                                                                                                  (29, 2, 199.90, '2024-07-18', 'Pago', 'Dinheiro', '2024-07-20'),
                                                                                                                                  (30, 3, 250.00, '2024-07-19', 'Pago', 'Cartão de Crédito', '2024-07-20'),
                                                                                                                                  (31, 2, 250.00, '2024-07-20', 'Atrasado', 'Boleto', '2024-07-15'),
                                                                                                                                  (32, 3, 199.90, '2024-07-21', 'Pago', 'PIX', '2024-07-25'),
                                                                                                                                  (33, 2, 199.90, '2024-07-22', 'Pago', 'Dinheiro', '2024-07-25'),
                                                                                                                                  (34, 3, 250.00, '2024-07-23', 'Pendente', 'Boleto', '2024-07-28'),
                                                                                                                                  (35, 2, 199.90, '2024-07-24', 'Pago', 'Cartão de Débito', '2024-07-28'),
                                                                                                                                  (36, 3, 199.90, '2024-07-25', 'Pago', 'PIX', '2024-07-30'),
                                                                                                                                  (37, 2, 250.00, '2024-07-26', 'Atrasado', 'Boleto', '2024-07-20'),
                                                                                                                                  (38, 3, 199.90, '2024-07-27', 'Pago', 'Cartão de Crédito', '2024-07-30'),
                                                                                                                                  (39, 2, 199.90, '2024-07-28', 'Pago', 'Dinheiro', '2024-08-01'),
                                                                                                                                  (40, 3, 250.00, '2024-07-29', 'Pendente', 'Boleto', '2024-08-01');

-- Inserir dados de exemplo em Historico_Atividades
INSERT INTO Historico_Atividades (ID_Membro, ID_Funcionario_Responsavel, Atividade, Data_Hora_Atividade, Tempo_Execucao_Minutos, Observacoes) VALUES
                                                                                                                                                  (1, 2, 'Check-in', '2024-07-05 08:00:15', NULL, 'Início do treino de cardio.'),
                                                                                                                                                  (1, 4, 'Execução de Treino Pop Star', '2024-07-05 08:05:00', 75, 'Sessão com instrutor Tony Stark.'),
                                                                                                                                                  (2, 3, 'Check-in', '2024-07-05 09:15:20', NULL, NULL),
                                                                                                                                                  (3, 2, 'Check-in', '2024-07-06 18:30:00', NULL, 'Treino de pernas agendado.'),
                                                                                                                                                  (3, 6, 'Execução de Treino de Força', '2024-07-06 18:35:10', 90, 'Aumento de carga no agachamento.'),
                                                                                                                                                  (5, 3, 'Avaliação Física Inicial', '2024-01-20 10:00:00', 45, 'Avaliação realizada pelo instrutor Steve Rogers (ID 7).'),
                                                                                                                                                  (8, 2, 'Check-in', '2024-07-05 19:30:00', NULL, 'Aula de Yoga.'),
                                                                                                                                                  (8, 8, 'Participou da aula de Yoga', '2024-07-05 19:30:45', 60, 'Turma com instrutora Lara Croft.'),
                                                                                                                                                  (10, 3, 'Check-in', '2024-07-08 20:00:00', NULL, NULL),
                                                                                                                                                  (15, 2, 'Check-in', '2024-07-09 07:00:00', NULL, 'Treino de mobilidade.'),
                                                                                                                                                  (15, 9, 'Execução de Treino de Mobilidade', '2024-07-09 07:05:30', 40, 'Foco em ombros e quadril.'),
                                                                                                                                                  (20, 3, 'Check-in', '2024-07-10 12:00:00', NULL, NULL),
                                                                                                                                                  (23, 2, 'Check-in', '2024-07-11 19:00:00', NULL, 'Treino de alta energia.'),
                                                                                                                                                  (25, 3, 'Check-in', '2024-07-12 09:00:00', NULL, NULL),
                                                                                                                                                  (30, 2, 'Check-in', '2024-07-15 18:00:00', NULL, 'Aula de dança.'),
                                                                                                                                                  (30, 5, 'Participou da aula Sertanejo Fitness', '2024-07-15 18:02:15', 55, 'Instrutor Peter Parker.'),
                                                                                                                                                  (38, 3, 'Check-in', '2024-08-03 16:00:00', NULL, 'Ensaio de performance.'),
                                                                                                                                                  (40, 2, 'Check-in', '2024-08-11 15:00:00', NULL, NULL),
                                                                                                                                                  (1, 2, 'Check-in na academia', '2024-08-12 08:00:15', NULL, 'Início do treino de cardio.'),
                                                                                                                                                  (1, 4, 'Execução de Treino Pop Star', '2024-08-12 08:05:00', 75, 'Sessão com instrutor Tony Stark.'),
                                                                                                                                                  (2, 3, 'Check-in na academia', '2024-08-12 09:15:20', NULL, NULL),
                                                                                                                                                  (3, 2, 'Check-in na academia', '2024-08-12 18:30:00', NULL, 'Treino de pernas agendado.'),
                                                                                                                                                  (3, 6, 'Execução de Treino de Força', '2024-08-12 18:35:10', 90, 'Aumento de carga no agachamento.'),
                                                                                                                                                  (4, 3, 'Check-in na academia', '2024-08-13 07:00:00', NULL, NULL),
                                                                                                                                                  (5, 3, 'Avaliação Física Inicial', '2024-01-20 10:00:00', 45, 'Avaliação realizada pelo instrutor Steve Rogers (ID 7).'),
                                                                                                                                                  (6, 2, 'Check-in na academia', '2024-08-13 08:00:00', NULL, NULL),
                                                                                                                                                  (7, 3, 'Check-in na academia', '2024-08-13 09:00:00', NULL, NULL),
                                                                                                                                                  (8, 2, 'Check-in na academia', '2024-08-13 19:30:00', NULL, 'Aula de Yoga.'),
                                                                                                                                                  (8, 8, 'Participou da aula de Yoga', '2024-08-13 19:30:45', 60, 'Turma com instrutora Lara Croft.'),
                                                                                                                                                  (9, 3, 'Check-in na academia', '2024-08-14 10:00:00', NULL, NULL),
                                                                                                                                                  (10, 3, 'Check-in na academia', '2024-08-14 20:00:00', NULL, NULL),
                                                                                                                                                  (10, 9, 'Execução de Treino de Resistência', '2024-08-14 20:05:12', 55, 'Concluído com sucesso.'),
                                                                                                                                                  (11, 2, 'Check-in na academia', '2024-08-14 06:30:00', NULL, NULL),
                                                                                                                                                  (12, 3, 'Check-in na academia', '2024-08-14 07:30:00', NULL, NULL),
                                                                                                                                                  (13, 2, 'Check-in na academia', '2024-08-15 11:00:00', NULL, NULL),
                                                                                                                                                  (14, 3, 'Check-in na academia', '2024-08-15 12:00:00', NULL, NULL),
                                                                                                                                                  (15, 2, 'Check-in na academia', '2024-08-15 07:00:00', NULL, 'Treino de mobilidade.'),
                                                                                                                                                  (15, 9, 'Execução de Treino de Mobilidade', '2024-08-15 07:05:30', 40, 'Foco em ombros e quadril.'),
                                                                                                                                                  (16, 3, 'Check-in na academia', '2024-08-15 14:00:00', NULL, NULL),
                                                                                                                                                  (17, 2, 'Check-in na academia', '2024-08-16 15:00:00', NULL, NULL),
                                                                                                                                                  (18, 3, 'Check-in na academia', '2024-08-16 16:00:00', NULL, NULL),
                                                                                                                                                  (19, 2, 'Check-in na academia', '2024-08-16 17:00:00', NULL, NULL),
                                                                                                                                                  (20, 3, 'Check-in na academia', '2024-08-17 12:00:00', NULL, NULL),
                                                                                                                                                  (20, 4, 'Execução de Manutenção de Força', '2024-08-17 12:05:00', 60, NULL),
                                                                                                                                                  (21, 2, 'Check-in na academia', '2024-08-17 13:00:00', NULL, NULL),
                                                                                                                                                  (22, 3, 'Check-in na academia', '2024-08-17 14:00:00', NULL, NULL),
                                                                                                                                                  (23, 2, 'Check-in na academia', '2024-08-18 19:00:00', NULL, 'Treino de alta energia.'),
                                                                                                                                                  (23, 6, 'Execução Power Rock Workout', '2024-08-18 19:05:00', 50, NULL),
                                                                                                                                                  (24, 3, 'Check-in na academia', '2024-08-18 08:30:00', NULL, NULL),
                                                                                                                                                  (25, 2, 'Check-in na academia', '2024-08-19 09:00:00', NULL, NULL),
                                                                                                                                                  (26, 3, 'Check-in na academia', '2024-08-19 10:00:00', NULL, NULL),
                                                                                                                                                  (27, 2, 'Avaliação Física de Rotina', '2024-08-19 11:00:00', 30, 'Realizada com instrutor Luke Skywalker (ID 10)'),
                                                                                                                                                  (28, 3, 'Check-in na academia', '2024-08-20 18:00:00', NULL, NULL),
                                                                                                                                                  (29, 2, 'Check-in na academia', '2024-08-20 19:00:00', NULL, NULL),
                                                                                                                                                  (30, 2, 'Check-in na academia', '2024-08-20 18:00:00', NULL, 'Aula de dança.'),
                                                                                                                                                  (30, 5, 'Participou da aula Sertanejo Fitness', '2024-08-20 18:02:15', 55, 'Instrutor Peter Parker.'),
                                                                                                                                                  (31, 3, 'Check-in na academia', '2024-08-21 07:00:00', NULL, NULL),
                                                                                                                                                  (32, 2, 'Check-in na academia', '2024-08-21 08:00:00', NULL, NULL),
                                                                                                                                                  (33, 3, 'Check-in na academia', '2024-08-21 09:00:00', NULL, NULL),
                                                                                                                                                  (34, 2, 'Check-in na academia', '2024-08-22 10:00:00', NULL, NULL),
                                                                                                                                                  (35, 3, 'Check-in na academia', '2024-08-22 11:00:00', NULL, NULL),
                                                                                                                                                  (36, 2, 'Check-in na academia', '2024-08-22 12:00:00', NULL, NULL),
                                                                                                                                                  (37, 3, 'Check-in na academia', '2024-08-23 13:00:00', NULL, NULL),
                                                                                                                                                  (38, 3, 'Check-in na academia', '2024-08-23 16:00:00', NULL, 'Ensaio de performance.'),
                                                                                                                                                  (38, 8, 'Execução de Treino de Performance', '2024-08-23 16:05:00', 70, NULL),
                                                                                                                                                  (39, 2, 'Check-in na academia', '2024-08-23 17:00:00', NULL, NULL),
                                                                                                                                                  (40, 3, 'Check-in na academia', '2024-08-24 15:00:00', NULL, NULL);

-- Seleciona o banco de dados
SELECT * FROM Membros;
SELECT * FROM Funcionarios;
SELECT * FROM Treinos;
SELECT * FROM Pagamentos;
SELECT * FROM Historico_Atividades;