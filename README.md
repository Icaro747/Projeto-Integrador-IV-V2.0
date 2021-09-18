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
    Tamanho VARCHAR(2),
    Descricao VARCHAR(150) NOT NULL,
    Tag VARCHAR(150) NOT NULL,
    Quantidade int NOT NULL,
    V_compra DECIMAL(9,2) NOT NULL,
    V_venda DECIMAL(9,2) NOT NULL,
    Statu BOOLEAN NOT NULL
);
```
