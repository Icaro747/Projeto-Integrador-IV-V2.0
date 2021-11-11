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
    Descricao VARCHAR(500) NOT NULL,
    Quantidade int NOT NULL,
    V_compra DECIMAL(9,2) NOT NULL,
    V_venda DECIMAL(9,2) NOT NULL,
    Status BOOLEAN NOT NULL
);

CREATE TABLE Pruduto_Imagens(
    ID_Imagen INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name_img VARCHAR(150) NOT NULL,
    FK_Produto INT NOT NULL,
    FOREIGN KEY (FK_Produto) REFERENCES Produtos (ID_Produto)
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
    CPF VARCHAR(14) NOT NULL,
    Atuacao VARCHAR(50) NOT NULL,
    Status BOOLEAN NOT NULL
);

CREATE TABLE Cliente (
    ID_Cliente INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL,
    Sobrenome VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Senha VARCHAR(200) NOT NULL,
    CPF VARCHAR(14) NOT NULL,
    Nasimeto DATE NOT NULL,
    Sexo VARCHAR(1) NOT NULL
);

CREATE TABLE Enderecos (
    ID_Endereco INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    CEP VARCHAR(9) NOT NULL,
    Endereco VARCHAR(100) NOT NULL,
    Numero VARCHAR(5) NOT NULL,
    Complemento VARCHAR(50) ,
    Bairro VARCHAR(50) NOT NULL,
    Cidade VARCHAR(50) NOT NULL,
    Estado  VARCHAR(2) NOT NULL,
    Status BOOLEAN NOT NULL,
    Desativado BOOLEAN NOT NULL,
    FK_Cliente INT NOT NULL,
    FOREIGN KEY (FK_Cliente) REFERENCES Cliente (ID_Cliente)
);

CREATE TABLE EnderecoFatura (
    ID_Endereco INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    CEP VARCHAR(9) NOT NULL,
    Endereco VARCHAR(100) NOT NULL,
    Numero VARCHAR(5) NOT NULL,
    Complemento VARCHAR(50) ,
    Bairro VARCHAR(50) NOT NULL,
    Cidade VARCHAR(50) NOT NULL,
    Estado  VARCHAR(2) NOT NULL,    
    FK_Cliente INT NOT NULL,
    FOREIGN KEY (FK_Cliente) REFERENCES Cliente (ID_Cliente)
);

CREATE TABLE Vendas (
ID_Venda INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
data_Venda DATE NOT NULL,
V_total DECIMAL(9,2) NOT NULL,
V_frete DECIMAL(9,2) NOT NULL,
StatusPedido VARCHAR(50) NOT NULL,
FK_Cliente INT NOT NULL,
FOREIGN KEY (FK_Cliente) REFERENCES Cliente (ID_Cliente)
);

CREATE TABLE Item (
ID_Item INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
Quantidade INT NOT NULL,
Desconto INT NOT NULL,
V_item DECIMAL(9,2) NOT NULL,
FK_Venda INT NOT NULL,
FOREIGN KEY (FK_Venda) REFERENCES Vendas (ID_Venda),
FK_Produto INT NOT NULL,
FOREIGN KEY (FK_Produto) REFERENCES Produtos (ID_Produto)
);

delimiter $$
create trigger BaixaEstoque after insert on Item for each row
begin
update Produtos set Quantidade = Quantidade - new.Quantidade where ID_Produto = new.FK_Produto;
update Vendas set V_total = V_total + (new.V_item * new.Quantidade) where ID_Venda = new.FK_Venda;
end $$
