# Projeto-Integrador-IV

## Banco de dados

```
/*MySql*/
CREATE DATABASE NewManSQL;
USE NewManSQL; 

SET NAMES utf8;

CREATE TABLE Produtos(
    ID_Produto INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    Marca VARCHAR(50) NOT NULL,
    Descricao VARCHAR(150) NOT NULL,
    Quantidade int NOT NULL,
    V_compra DECIMAL(9,2) NOT NULL,
    V_venda DECIMAL(9,2) NOT NULL,
    name_img VARCHAR(150) NOT NULL,
    Status BOOLEAN NOT NULL
);

CREATE TABLE Tags(
    ID_Tag INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Nome_tag VARCHAR(50) NOT NULL,
    IMG VARCHAR(150) NOT NULL
);

CREATE TABLE Relacao_Produtos_Tags(
    FK_Produto INT NOT NULL,
    FK_Tag INT NOT NULL,
    FOREIGN KEY (FK_Produto) REFERENCES Produtos (ID_Produto),
    FOREIGN KEY (FK_Tag) REFERENCES Tags (ID_Tag)
);

CREATE TABLE Funcionario (
    ID_Funcionario INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL,
    Sobrenome VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Senha VARCHAR(200) NOT NULL,
    CPF VARCHAR(11) NOT NULL,
    Atuacao VARCHAR(50) NOT NULL,
    Status BOOLEAN NOT NULL
);

```
