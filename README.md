# Sistema de Cadastro de Academia 

Projeto desenvolvido como parte da Avaliação A3 das Unidade Curricular **Programação e Soluções Computacionais**.

##  Descrição

Este sistema tem como objetivo realizar o gerenciamento completo de uma academia, incluindo o cadastro de membros, controle de treinos, registro de pagamentos e histórico de atividades. Desenvolvido em **Java**, utilizando **Java Swing** para a interface gráfica e **MySQL** como banco de dados, o projeto segue o padrão de arquitetura **MVC** e adota boas práticas de segurança e usabilidade.

## Requisitos Atendidos

### Funcionais

- [x] Cadastro de membros
- [x] Gerenciamento de planos de treino
- [x] Registro de pagamentos
- [x] Consulta ao histórico de atividades
- [x] Tela de login para funcionários

### Não Funcionais

- [x] Segurança (Hashing de senhas com **BCrypt**)
- [x] Desempenho (uso de conexões otimizadas)
- [x] Usabilidade (tema visual unificado, mensagens e feedbacks claros)

##  Tecnologias Utilizadas

- Java 17+
- Java Swing (UI)
- MySQL 8+
- JDBC
- BCrypt para hashing de senhas
- VSCode como IDE
- GitHub para versionamento

##  Estrutura do Projeto

```bash
src/
├── controller/
│   └── MembroController.java, TreinoController.java, ...
├── model/
│   └── Membro.java, Treino.java, ...
├── view/
│   └── TelaLogin.java, TelaCadastroMembro.java, ...
├── util/
│   └── Conexao.java, Criptografia.java
├── main/
│   └── App.java
