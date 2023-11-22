<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        .editable {
            border: 1px solid #000;
            padding: 5px;
            cursor: pointer;
        }
    </style>
    <script>
        function enableEdit(id) {
            var fields = document.querySelectorAll(".editable[data-id='" + id + "']");
            fields.forEach(function (field) {
                field.contentEditable = true;
                field.style.backgroundColor = "#FFFFCC";
            });
        }

        function saveChanges(id) {
            var fields = document.querySelectorAll(".editable[data-id='" + id + "']");
            var data = {};

            fields.forEach(function (field) {
                data[field.getAttribute("data-field")] = field.innerText;
                field.contentEditable = false;
                field.style.backgroundColor = "inherit";
            });

            // Validar campos
            if (!data.name || !data.description || !data.ean13 || !data.minStock || !data.price || !data.quantity) {
                alert("Preencha todos os campos obrigatórios.");
                return;
            }

            // Enviar dados para o servidor (implementação necessária)
            console.log("Dados a serem enviados:", data);
        }

        function addProduct() {
            var name = document.getElementById("newName").value;
            var description = document.getElementById("newDescription").value;
            var ean13 = document.getElementById("newEAN13").value;
            var active = document.getElementById("newActive").checked;
            var minStock = document.getElementById("newMinStock").value;
            var price = document.getElementById("newPrice").value;
            var quantity = document.getElementById("newQuantity").value;

            // Validar campos
            if (!name || !description || !ean13 || !minStock || !price || !quantity) {
                alert("Preencha todos os campos obrigatórios.");
                return;
            }

            // Enviar dados para o servidor
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/save", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            // Construir dados a serem enviados
            var data = "name=" + encodeURIComponent(name) +
                "&description=" + encodeURIComponent(description) +
                "&ean13=" + encodeURIComponent(ean13) +
                "&active=" + encodeURIComponent(active) +
                "&minStock=" + encodeURIComponent(minStock) +
                "&price=" + encodeURIComponent(price) +
                "&quantity=" + encodeURIComponent(quantity);

            // Definir a função de retorno de chamada
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // Lógica de sucesso (pode redirecionar ou fazer outra coisa)
                    console.log("Novo produto adicionado com sucesso!");
                }
            };

            // Enviar a solicitação
            xhr.send(data);

            // Limpar os campos após adicionar
            document.getElementById("newName").value = "";
            document.getElementById("newDescription").value = "";
            document.getElementById("newEAN13").value = "";
            document.getElementById("newActive").checked = false;
            document.getElementById("newMinStock").value = "";
            document.getElementById("newPrice").value = "";
            document.getElementById("newQuantity").value = "";
        }
    </script>
</head>
<body>

<h2>Product List</h2>

<c:if test="${not empty products}">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>EAN13</th>
            <th>Active</th>
            <th>Min Stock</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Action</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td class="editable" data-id="${product.id}" data-field="name" onclick="enableEdit(${product.id})">${product.name}</td>
                <td class="editable" data-id="${product.id}" data-field="description" onclick="enableEdit(${product.id})">${product.description}</td>
                <td class="editable" data-id="${product.id}" data-field="ean13" onclick="enableEdit(${product.id})">${product.ean13}</td>
                <td class="editable" data-id="${product.id}" data-field="active" onclick="enableEdit(${product.id})">${product.active}</td>
                <td class="editable" data-id="${product.id}" data-field="minStock" onclick="enableEdit(${product.id})">${product.minStock}</td>
                <td class="editable" data-id="${product.id}" data-field="price" onclick="enableEdit(${product.id})">${product.price}</td>
                <td class="editable" data-id="${product.id}" data-field="quantity" onclick="enableEdit(${product.id})">${product.quantity}</td>
                <td><button onclick="saveChanges(${product.id})">Save</button></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<!-- Formulário para adicionar novo produto -->
<h2>Add Product</h2>
<form>
    Name: <input type="text" id="newName" required><br>
    Description: <input type="text" id="newDescription" required><br>
    EAN13: <input type="text" id="newEAN13" required><br>
    Active: <input type="checkbox" id="newActive"><br>
    Min Stock: <input type="number" id="newMinStock" required><br>
    Price: <input type="number" id="newPrice" required><br>
    Quantity: <input type="number" id="newQuantity" required><br>
    <button type="button" onclick="addProduct()">Add Product</button>
</form>

</body>
</html>
