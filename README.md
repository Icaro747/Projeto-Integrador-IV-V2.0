# Projeto-Integrador-IV

## Banco de dados

```
/*MySql*/
CREATE DATABASE NewManSQL;
USE NewManSQL; 

SET NAMES utf8;

create table Produtos(
    ID_Produto INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    Marca VARCHAR(50) NOT NULL,
    Descricao VARCHAR(150) NOT NULL,
    Quantidade int NOT NULL,
    V_compra DECIMAL(9,2) NOT NULL,
    V_venda DECIMAL(9,2) NOT NULL,
    name_img VARCHAR(150) NOT NULL,
    Statu BOOLEAN NOT NULL
);

create table Tags(
    ID_Tag INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    Nome_tag VARCHAR(50) NOT NULL
);

create table Relacao_Produtos_Tags(
    FK_Produto INT NOT NULL,
    FK_Tag INT NOT NULL,
    FOREIGN KEY (FK_Produto) REFERENCES Produtos (ID_Produto),
    FOREIGN KEY (FK_Tag) REFERENCES Tags (ID_Tag)
);

```
