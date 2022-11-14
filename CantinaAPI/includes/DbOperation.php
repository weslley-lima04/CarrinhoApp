<?php
 
class DbOperation
{
    
    private $con;
 
 
    function __construct()
    {
  
        require_once dirname(__FILE__) . '/DbConnect.php';
 
     
        $db = new DbConnect();
 

        $this->con = $db->connect();
    }

	function createProduto($NomeProduto, $PrecoProduto, $QtdeEstoque, $Descricao){
		$stmt = $this->con->prepare("INSERT INTO Produtos (NomeProduto, IDEstabelecimento, PrecoProduto, QtdeEstoque, Descricao) VALUES (?, 1, ?, ?, ?)");
		$stmt->bind_param("sdis", $NomeProduto, $PrecoProduto, $QtdeEstoque, $Descricao);
		if($stmt->execute())
			return true; 
		return false; 

		
	}
	
	function createPedido($IDCliente, $DataPedido, $ValorPedido){
		$stmt = $this->con->prepare("INSERT INTO Pedidos (IDCliente, DataPedido, ValorPedido) VALUES (?, ?, ?)");
		$stmt->bind_param("isd", $IDCliente, $DataPedido, $ValorPedido);
		if($stmt->execute())
			return true; 
		return false; 
	}
		
	function getProdutos(){
		$stmt = $this->con->prepare("SELECT IDProduto, NomeProduto, PrecoProduto, QtdeEstoque, Descricao FROM Produtos");
		$stmt->execute();
		$stmt->bind_result($id, $NomeProduto, $PrecoProduto, $QtdeEstoque, $Descricao);
		
		$produtos = array(); 
		
		while($stmt->fetch()){
			$produto  = array();
			$produto['IDProduto'] = $id; 
			$produto['NomeProduto'] = $NomeProduto; 
			$produto['PrecoProduto'] = $PrecoProduto; 
			$produto['QtdeEstoque'] = $QtdeEstoque; 
			$produto['Descricao'] = $Descricao; 
			
			array_push($produtos, $produto); 
		}
		
		return $produtos; 
	}
	function getPedidos(){
		$stmt = $this->con->prepare("SELECT IDPedido, IDCliente, DataPedido, ValorPedido FROM Pedidos");
		$stmt->execute();
		$stmt->bind_result($IDPedido, $IDCliente, $DataVenda, $ValorPedido);
		
		$pedidos = array(); 
		
		while($stmt->fetch()){
			$pedido  = array();
			$pedido['IDPedido'] = $IDPedido; 
			$pedido['IDCliente'] = $IDCliente; 
			$pedido['DataPedido'] = $DataVenda; 
			$pedido['ValorPedido'] = $ValorPedido; 
			
			array_push($pedidos, $pedido); 
		}
		
		return $pedidos; 
	}

	function selectProdutos($search){
		$stmt = $this->con->prepare("SELECT IDProduto, NomeProduto, PrecoProduto, QtdeEstoque, Descricao
		FROM Produtos WHERE IDProduto LIKE '%$search%' or
		IDEstabelecimento LIKE '%$search%' or
		NomeProduto LIKE '%$search%' or
		PrecoProduto LIKE '%$search%' or
		QtdeEstoque LIKE '%$search%' or
		Descricao LIKE '%$search%'");
		$stmt->execute();
		$stmt->bind_result($id, $NomeProduto, $PrecoProduto, $QtdeEstoque, $Descricao);
		
		$produtos = array(); 
		
		while($stmt->fetch()){
			$produto  = array();
			$produto['IDProduto'] = $id; 
			$produto['NomeProduto'] = $NomeProduto; 
			$produto['PrecoProduto'] = $PrecoProduto; 
			$produto['QtdeEstoque'] = $QtdeEstoque; 
			$produto['Descricao'] = $Descricao; 
			
			array_push($produtos, $produto); 
			
	}
	return $produtos; 
}
	
	
	function updateProdutos($id, $NomeProduto, $PrecoProduto, $QtdeEstoque, $Descricao){
		$stmt = $this->con->prepare("UPDATE Produtos SET NomeProduto = ?, PrecoProduto = ?, QtdeEstoque = ?, Descricao = ? WHERE IDProduto = ?");
		$stmt->bind_param("sdisi", $NomeProduto, $PrecoProduto, $QtdeEstoque, $Descricao, $id);
		if($stmt->execute())
			return true; 
		return false; 
	} 
	
	
	function deleteProdutos($id){
		$stmt = $this->con->prepare("DELETE FROM Produtos WHERE IDProduto = ? ");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}
}