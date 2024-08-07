CREATE TABLE cliente (
                        id serial not null primary key,
                        primeiro_nome varchar(70) not null,
                        segundo_nome varchar(70) not null,
                        cpf varchar(14) not null unique,
                        email varchar(100) not null unique,
                        senha varchar(100) not null
);

CREATE TABLE categoria (
                        id serial not null primary key,
                        nome varchar(70) not null,
                        tipo varchar(7) not null,
                        ativo boolean,
                        id_cliente int,
                        FOREIGN KEY (id_cliente) REFERENCES cliente (id)
);

CREATE TABLE lancamento (
                            id serial not null primary key,
                            tipo varchar(7),
                            descricao varchar(200),
                            valor numeric(10,2) not null,
                            data date not null,
                            paga_recebida boolean not null,
                            fixa boolean,
                            parcelada boolean,
                            numero_de_parcelas int,
                            ativo boolean not null,
                            id_cliente int,
                            id_categoria int,
                            FOREIGN KEY (id_cliente) REFERENCES cliente (id),
                            FOREIGN KEY (id_categoria) REFERENCES categoria (id)
);

CREATE TABLE parcela (
                         id serial not null primary key,
                         valor numeric(10,2) not null,
                         paga_recebida boolean not null,
                         data date not null,
                         contador int not null,
                         ativo boolean not null,
                         id_lancamento int,
                         FOREIGN KEY (id_lancamento) REFERENCES lancamento (id)
);

