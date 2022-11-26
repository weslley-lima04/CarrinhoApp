<?php 

	require_once "conexao.php";

    $db = connect();

    $response['error'] = true; 

    $idPedido = intval($_POST['IDPedido']);
	$idProduto = intval($_POST['IDProduto']);
	$Qtd = intval($_POST['QuantidadeVendida']);


	$sql_itenspedidos = "INSERT INTO ItensPedidos (IDPedido, IDProduto, QuantidadeVendida)
                        VALUES (:IDPEDIDO, :IDPRODUTO, :QUANTVENDIDA)";

    $stmt = $db->prepare($sql_itenspedidos);                    
	$stmt->bindParam(':IDPEDIDO', $idPedido);
	$stmt->bindParam(':IDPRODUTO', $idProduto);
	$stmt->bindParam(':QUANTVENDIDA', $Qtd);

    if ($stmt->execute())
    {
       $response['error'] = false;
    }

    echo json_encode($response);

    
?>