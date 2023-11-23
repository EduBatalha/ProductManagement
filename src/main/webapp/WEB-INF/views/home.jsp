<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        table, th, td {
            border: 1px solid black;
        }

        .form {
            display: flex;
        }

        .field {
            border: 1px solid #000;
            padding: 5px;
            cursor: pointer;
        }


        .edit-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 5px 10px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 4px 2px;
            cursor: pointer;
        }
    </style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.4/axios.min.js"></script>
</head>
<body>

<table>
    <tbody>
    <tr>
        <td colspan="9">
            <h2>Create a New Product</h2>

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

                <div class="form-row">
                    <label for="active">Produto ativo:</label>
                    <input type="checkbox" id="active" name="active">
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
                    <input type="submit" value="Save">
                </div>
            </form>
            <button onclick="refreshPage()">Refresh</button>
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
    </tr>

    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.hash}</td>
            <td class="field" data-id="${product.id}" data-field="name" onclick="enableEdit(this)">${product.name}</td>
            <td class="field" data-id="${product.id}" data-field="description" onclick="enableEdit(this)">${product.description}</td>
            <td class="field" data-id="${product.id}" data-field="ean13" onclick="enableEdit(this)">${product.ean13}</td>
            <td class="field" data-id="${product.id}" data-field="active" onclick="enableEdit(this)">${product.active ? 'Sim' : 'Não'}</td>
            <td class="field" data-id="${product.id}" data-field="minStock" onclick="enableEdit(this)">${product.minStock}</td>
            <td class="field" data-id="${product.id}" data-field="price" onclick="enableEdit(this)">${product.price}</td>
            <td class="field" data-id="${product.id}" data-field="quantity" onclick="enableEdit(this)">${product.quantity}</td>
        </tr>

        <!-- Linha do formulário de edição -->
        <tr style="display: none;" id="edit-form-row-${product.id}">
            <td colspan="9">
                <!-- Formulário de Edição -->
                <form id="edit-form-${product.hash}" class="edit-form" action="<c:url value='/products/update/${product.hash}'/>" method="post">
                    <input type="text" name="name" value="${product.name}">
                    <input type="text" name="description" value="${product.description}">
                    <input type="text" name="ean13" value="${product.ean13}">
                    <input type="checkbox" name="active" ${product.active ? 'checked' : ''}>
                    <input type="number" name="minStock" value="${product.minStock}">
                    <input type="number" name="price" value="${product.price}">
                    <input type="number" name="quantity" value="${product.quantity}">
                    <!-- Adicione outros campos conforme necessário -->
                    <input type="submit" value="Save">
                </form>
                <button onclick="confirmAndDelete('${product.hash}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script>
    function enableEdit(element) {
        var editFormRows = $('[id^="edit-form-row-"]');

        editFormRows.hide();

        var productId = $(element).data('id');
        var editFormRow = $('#edit-form-row-' + productId);
        editFormRow.show();
    }

    function confirmAndDelete(productHash) {
        var confirmDelete = confirm("Tem certeza de que deseja excluir este produto?");

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