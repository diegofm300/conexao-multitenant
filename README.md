# API para atender requisições multitenant
Este é um exemplo de implementação de uma API REST, utilizando Spring Boot, que funciona com múltiplos bancos de dados.

Foi feita para fins didáticos e atualmente está recebendo o filtro de nome do banco no body das requisições. 
Existem formas melhores e mais seguras para receber esta informação, porém para esta finalidade atendeu ao seu propósito.



# Getting Started

### Para realizar os testes criar os seguintes bancos e tabelas: 

* Criar as bases de dados:
  * create database "banco1";
  * create database "banco2";
* Criar a tabela pessoa em todos os bancos criados acima bem como o índice:
  * create TABLE pessoa (
    id integer,
    nome character varying(200),
    primary key (id));
  * create sequence public.pessoa_seq
    increment by 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1
    no cycle;