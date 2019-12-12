<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accueil</title>
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showProducts();
                        showCategorie();
                        showUser();
                    }
            );
    
                
            
            

            function showProducts() {
                // On fait un appel AJAX pour chercher les produits
                $.ajax({
                    url: "allProducts",
                    withCredentials: true,
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#codes').html(processedTemplate);
                            }
                });
            }
            
            function showProductsPan(panier) {
                // On fait un appel AJAX pour chercher les produits
                $.ajax({
                    url: "allProducts",
                    data: {"panier": panier},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#codes').html(processedTemplate);
                            }
                });
            }
            

            function showCategorie() {
                $.ajax({
                    url: "AllCategories",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#catTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#cat').html(processedTemplate);
                            }
                });
            }
            
            function showProductFrom(id_cat) {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "ProductCat",
                    data: {"categorie": id_cat},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#codes').html(processedTemplate);
                            }
                });
            }

            // Supprimer un code
            function deleteCode(code) {
                $.ajax({
                    url: "deleteCode",
                    data: {"code": code},
                    dataType: "json",
                    success: 
                            function (result) {
                                showCodes();
                                console.log(result);
                            },
                    error: showError
                });
                return false;
            }
            
            function showUser() {
                var code = "${code}";
                if(code==="") {
                    var template = $('#loginTemplate').html();
                    var processedTemplate = Mustache.to_html(template);
                    $('#login').html(processedTemplate);
                } else {
                    var template = $('#userTemplate').html();
                    var processedTemplate = Mustache.to_html(template);
                    $('#login').html(processedTemplate);
            }
        }

            // Fonction qui traite les erreurs de la requête
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }

        </script>
        <style>
            #h1-2 {
                font-size: 25px;
            }
            
            p {
                font-size: 20px;
            }
            
            #h1-1{
                letter-spacing: 1.5px;
            }
            
            #field {
                width: 280px;
            }
            
            #pseudo {
                width: 189px;
                margin-bottom: 1px;
            }
            
            #mdp {
                width : 150px;
                margin-top: 1px;
                margin-bottom: 1px;
            }
            
            select{
                width: 245px;
                font-size: 20px;
            }
            
            th {
                background-color: #ccc;
            }
            
            #prixunit {
                
            }
            
            #nom {
                
            }
        </style>
        
        <link rel="stylesheet" type="text/css" href="css/tableformat.css">
    </head>
    <body>
        <!-- On montre le formulaire de saisie -->
        <div id="login"></div>
        <p>Vous pouvez aussi consulter nos produits ci-dessous :</p>
        
        
        <p>Et les filtrer avec les catégories suivantes : </p><div id="cat"></div>
        <script id ="catTemplate" type="text/template">
            <select id="id_cat">
                <option onclick="showProducts()" value=0>Tout les produits</option> 
                {{#records}}
                    <option onclick="showProductFrom({{m_code}})" value={{m_code}}>{{m_libelle}}</option>
                {{/records}}
            </select>
        </script>
        
        
        <div id="codes"></div>
        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
            <table border="1">
            <tr><th>Nom</th><th>Quantités/Unité</th><th>Fournisseur</th><th>Prix Unitaire</th>
            <c:if test="${not empty sessionScope.code}"><th>Panier</th></c:if></tr>
            {{! Pour chaque enregistrement }}
            {{#records}}
                {{! Une ligne dans la table }}
                <TR><TD id="nom">{{m_nom}}</TD><TD id="prixunit">{{m_quantiteParUnite}}</TD>
                    <TD id="fournisseur">{{m_fournisseur}}</TD><TD id="prix_unit">{{m_prixUnitaire}} €</TD>
                    
                    <c:set var = "indispo" scope = "page" value = "{{m_indisponible}}}"/>
                    <c:if test="${(not empty sessionScope.code) and (indispo == false)}">
                        <td><form method="POST">
                        <input id="idP" name="idP" type="hidden" value="{{m_reference}}">
                        <button type="submit" name="action" value="retrait">-</button>
                        {{m_quantitePanier}}
                        <button type="submit" name="action" value="ajouter">+</button></form></td>
                    </c:if>
                    <c:if test="${indispo == true}"><td>Indisponible</td></c:if>
                    <c:remove var="indispo" scope="page" />
                    
                </TR>
                
            {{/records}}
            </table>
        </script>
        
        <script id="loginTemplate" type="text/template">
            <h1 id="h1-2">N'hésitez pas à vous connecter ci-dessous pour accéder à toutes nos fonctionnalités</h1>
            <form id="codeForm" method="post">
            <fieldset id="field"><legend>Saisie des informations de connection</legend>
                Pseudo : <input id="loginParam" name="loginParam" required size="8"><br>
                Mot de Passe : <input id="passwordParam" name="passwordParam" type="password"  title="Rappelez vous de ne jamais communiquer votre mot de passe"><br/>
                  <input type="hidden" id="action" name="action" value="login"> 
                <input type="submit" value="Se connecter">
            </fieldset>
            </form>
            {{#records}}
            {{/records}}
        </script>
        
        <script id="userTemplate" type="text/template">
            <h1>Bienvenue ${userName}</h1>
            <form method="POST"> 
            <input type='submit' name='action' value='profil'>
            <input type='submit' name='action' value='panier'>
            <input type='submit' name='action' value='logout'>
            </form>
            {{#records}}
            {{/records}}
        </script>
    </body>
</html>
