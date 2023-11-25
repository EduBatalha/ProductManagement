<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito">


    <style>
        body{
            font-family: Nunito;
            background-color: #F0E8A5;

        }

        h2, h3{
            color: #422800;
        }

        table {
            border-collapse: collapse;
        }

        th{
            background-color: #422800;
            color: #F0E8A5;
        }

        table, th, td {
            border: 1px solid black;

        }

        .form {
            display: flex;
            flex-wrap: nowrap;
        }

        .linha-form{
            text-align: center;
            background-color: #F0E8A5;
        }

        .edit-form-row{
            text-align: center;
        }

        .form-row{
            width: fit-content;

        }

        .boolean-row{
            height: 3vw;
        }

        .product-row{
            background-color: white;
        }

        .field {
            border: 1px solid #000;
            padding: 5px;
            cursor: pointer;
        }

        /* FORMULÁRIOS */
        .edit-form{
            display: flex;
            flex-direction: row;
            justify-content: center;
            text-align: center;
            gap: 0.5em;
            margin-bottom: 1em;
        }

        .form-cadastro{
            display: flex;
            justify-content: center;
            gap: 1.5em;
        }

        /* BOTÕES */
        .button-save{
            margin-right: -15px;
        }

        button, .button, .button-save {
            background-color: #FFFFFF;
            border: 2px solid #422800;
            border-radius: 15px;
            box-shadow: #422800 4px 4px 0 0;
            color: #422800;
            cursor: pointer;
            display: flex;
            font-weight: 600;
            font-size: 1vw;
            padding: 0 10px;
            text-decoration: none;
            user-select: none;
            -webkit-user-select: none;
            touch-action: manipulation;
            width: 4vw;
            height: auto;
        }

        button:hover, .button, .button-save {
            background-color: #fff;
        }

        button:active, .button, .button-save {
            box-shadow: #422800 2px 2px 0 0;
            transform: translate(2px, 2px);
        }
        .button-delete{
            margin-top: 2px;
            height: auto;
        }

        .button-refresh, .button-delete{
            width: auto;
            height: min-content;
            margin-right: 8px;
            margin-top: 2px;
        }

        .boolean{
            margin-top: 1px;
            padding-bottom: 1px;
            display: flex;
            background-color: white;
            border: 1px solid #707070;
            color: #707070;
            white-space: nowrap;
            height: 18px;
        }

        /* Works on Firefox */
        * {
            scrollbar-width: thin;
            scrollbar-color: #422800 #F0E8A5;;
        }

        /* Works on Chrome, Edge, and Safari */
        *::-webkit-scrollbar {
            width: 12px;
        }

        *::-webkit-scrollbar-track {
            background: #F0E8A5;;
        }

        *::-webkit-scrollbar-thumb {
            background-color: #422800;
            border-radius: 20px;
            border: 3px solid #F0E8A5;;
        }

    </style>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.4/axios.min.js"></script>
</head>
<body>

<table>
    <tbody>
    <tr class="linha-form">
        <td colspan="10">
            <h2>Create a New Product</h2>
            <div class="form-cadastro">
            <form class="form" action="<c:url value='/products/save'/>" method="post" modelAttribute="productRequestDTO">
                <div class="form-row">
                    <label for="name"></label>
                    <input type="text" id="name" name="name" required placeholder="Nome do produto">
                </div>

                <div class="form-row">
                    <label for="description"></label>
                    <input type="text" id="description" name="description" required placeholder="Descrição do produto">
                </div>

                <div class="form-row">
                    <label for="ean13"></label>
                    <input type="text" id="ean13" name="ean13" required placeholder="EAN13">
                </div>

                <div class="boolean-row">
                    <div class="boolean">
                    <label for="active">Produto ativo:</label>
                    <input type="checkbox" id="active" name="active">
                    </div>
                </div>

                <div class="form-row">
                    <label for="minStock"></label>
                    <input type="number" id="minStock" name="minStock" required placeholder="Estoque mínimo">
                </div>

                <div class="form-row">
                    <label for="price"></label>
                    <input type="number" id="price" name="price" step="0.01" required placeholder="Preço">
                </div>

                <div class="form-row">
                    <label for="quantity"></label>
                    <input type="number" id="quantity" name="quantity" required placeholder="Quantidade">
                </div>

                <div class="form-row">
                    <input class="button-save" type="submit" value="Save">
                </div>
            </form>
            <button class="button-refresh" onclick="refreshPage()">↻</button>
            </div>
        </td>
    </tr>

    <tr>
        <th>ID</th>
        <th>Hash</th>
        <th>Name</th>
        <th>Description</th>
        <th>EAN13</th>
        <th>Active</th>
        <th>Min Stock</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Created In:</th>
    </tr>

    <c:forEach var="product" items="${products}">
        <tr class="product-row">
            <td class="field" data-id="${product.id}" onclick="enableEdit(this)">${product.id}</td>
            <td class="field" data-id="${product.id}" onclick="enableEdit(this)">${product.hash}</td>
            <td class="field" data-id="${product.id}" data-field="name" onclick="enableEdit(this)" >${product.name}</td>
            <td class="field" data-id="${product.id}" data-field="description" onclick="enableEdit(this)">${product.description}</td>
            <td class="field" data-id="${product.id}" data-field="ean13" onclick="enableEdit(this)">${product.ean13}</td>
            <td class="field" data-id="${product.id}" data-field="active" onclick="enableEdit(this)">${product.active ? 'Sim' : 'Não'}</td>
            <td class="field" data-id="${product.id}" data-field="minStock" onclick="enableEdit(this)">${product.minStock}</td>
            <td class="field" data-id="${product.id}" data-field="price" onclick="enableEdit(this)">R$ ${product.price}</td>
            <td class="field" data-id="${product.id}" data-field="quantity" onclick="enableEdit(this)">${product.quantity}</td>
            <td class="field" data-id="${product.id}" data-field="dtCreate" onclick="enableEdit(this)">${product.dtCreate}</td>
        </tr>

        <!-- Linha do formulário de edição -->
        <tr style="display: none;" class="edit-form-row" id="edit-form-row-${product.id}">
            <td colspan="10">
                <h3>Product update</h3>
                <!-- Formulário de Edição -->
                <form id="edit-form-${product.hash}" class="edit-form" action="<c:url value='/products/update/${product.hash}'/>" method="post">
                    <input type="text" name="name" value="${product.name}">
                    <input type="text" name="description" value="${product.description}">
                    <input type="text" name="ean13" value="${product.ean13}">
                    <input class="boolean" type="checkbox" name="active" ${product.active ? 'checked' : ''}>
                    <input type="number" name="minStock" value="${product.minStock}">
                    <input type="number" name="price" value="${product.price}">
                    <input type="number" name="quantity" value="${product.quantity}">
                    <!-- Adicione outros campos conforme necessário -->
                    <input class="button" type="submit" value="Save">
                    <button class="button-delete" onclick="confirmAndDelete('${product.hash}')">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script>
    function enableEdit(element) {
        let editFormRows = $('[id^="edit-form-row-"]');
        let productId = $(element).data('id');
        let editFormRow = $('#edit-form-row-' + productId);

        // Se o formulário de edição já estiver visível, oculta-o
        if (editFormRow.is(':visible')) {
            editFormRow.hide();
        } else {
            // Oculta todos os formulários de edição e exibe apenas o formulário do produto clicado
            editFormRows.hide();
            editFormRow.show();
        }
    }

    function confirmAndDelete(productHash) {
        let confirmDelete = confirm("Tem certeza de que deseja excluir este produto?");

        if (confirmDelete) {
            // Passa o hash como parâmetro para a função deleteProduct
            deleteProduct(productHash);
            refreshPage();
        }
    }

    function deleteProduct(productHash) {
        const deleteUrl = '<c:url value="/products/delete/' + productHash + '"/>';

        axios.delete(deleteUrl)
            .then(function (response) {
                console.log('Exclusão bem-sucedida. ProdutoHash:', productHash);
            })
            .catch(function (error) {
                console.error('Erro ao excluir o produto:', error);
            });
    }

    function refreshPage() {
        location.reload();
    }

</script>


</body>
</html>