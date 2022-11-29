DROP DATABASE IF EXISTS Cantina;

	CREATE DATABASE Cantina;

	USE Cantina;

	CREATE TABLE Estabelecimento(
			IDEstabelecimento INT NOT NULL auto_increment PRIMARY KEY,
			CNPJ VARCHAR(14) NOT NULL UNIQUE KEY,
			NomeEstabelecimento VARCHAR(100) NOT NULL,
			Endereco VARCHAR(100) NOT NULL,
			Telefone VARCHAR(14) NULL,
			Email VARCHAR(100) NOT NULL,
			Senha VARCHAR(30) NOT NULL

	);

	INSERT INTO Estabelecimento
	VALUES(null, '98563256987456', 'Cantina do Tonhao', 'Rua do Tonhao', '9911547896325', 'tonhao@tonhao.com', 'tonho');

	CREATE TABLE Clientes(
		IDCliente INT auto_increment NOT NULL PRIMARY KEY,
		Nome VARCHAR(100) NOT NULL,
		Telefone BIGINT(14) NOT NULL,
		Email VARCHAR(100) NOT NULL UNIQUE KEY,
        Senha VARCHAR(100) NOT NULL
);


	
	CREATE TABLE Produtos(
			IDProduto INT NOT NULL auto_increment PRIMARY KEY,
			IDEstabelecimento INT NOT NULL,
			NomeProduto VARCHAR(40) NOT NULL,
			PrecoProduto DECIMAL(4,2) NOT NULL,
			QtdeEstoque INT NOT NULL,
			Descricao VARCHAR(250) NOT NULL,
			CONSTRAINT FK_Estabelecimento FOREIGN KEY (IDEstabelecimento) REFERENCES Estabelecimento(IDEstabelecimento)
	);

	

	INSERT INTO Produtos
	VALUES(null, 1, 'Agua', '2.50', '45', 'Agua sem gas'),
	(null, 1, 'Bolo', '6.00', '41', 'Chocolate'),
	(null, 1, 'Refrigerante', '5.00', '20', 'Coca Cola'),
	(null, 1, 'Salada', '7.00', '10', 'Alface e tomate'),
	(null, 1, 'Suco', '5.00', '85', 'Laranja');


	CREATE TABLE Pedidos(
		IDPedido INT NOT NULL auto_increment PRIMARY KEY,
		IDCliente INT NOT NULL,
		DataPedido VARCHAR(25) NOT NULL,
		ValorPedido DECIMAL(5,2) NOT NULL,
		CONSTRAINT FK_Clientes_IDCliente FOREIGN KEY (IDCliente) REFERENCES Clientes(IDCliente)
		
);


	CREATE TABLE ItensPedidos(
		IDPedido INT NOT NULL,
		IDProduto INT NOT NULL,
		QuantidadeVendida INT NOT NULL,
		CONSTRAINT FK_Produtos_IDProduto FOREIGN KEY (IDProduto) REFERENCES Produtos (IDProduto),
		CONSTRAINT FK_Pedidos_IDPedido FOREIGN KEY (IDPedido) REFERENCES Pedidos (IDPedido)
	);

	INSERT INTO Clientes
    VALUES(null, 'Rubesvaldo', 'rubinho.peres@gmail.com', '40028922', '123456');