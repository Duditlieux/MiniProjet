<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion produit</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showInfosProduit("${sessionScope.refEdit}");
                        showCategorie();
                    }
            );

            function showInfosProduit(code) {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "InfoProduit",
                    data: {"code": code},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#editTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#produit').html(processedTemplate);
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

            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
        </script>
        <style>
          input{
                width: 18%;
            }
            
            #codeForm{
                border: 1px solid #CCC;
                border-radius: 0.5em;
                width: 400px;
            }
            
            li{
                width: 700px;
            }
           
        </style>
    </head>
    <body>
        <div id="produit"></div>

        <script id="editTemplate" type="text/template">
            {{#records}}
                <form id="codeForm" method="post">
                    <ul>
                        <li>Nom : <input id="m_nom" value="{{m_nom}}" name="m_nom" required size="8"></li><br>
                        <li>Fournisseur : <input id="m_fournisseur" type="number" value="{{m_fournisseur}}" name="m_fournisseur" required size="8"></li><br>
                        <li>Catégorie : <input id="m_categorie" type="number" value="{{m_categorie}}" name="m_categorie" size="8"></li><br>
                        <li>Quantité par Unité : <input id="m_quantiteParUnite" value="{{m_quantiteParUnite}}" name="m_quantiteParUnite" size="8"></li><br>
                        <li>Prix Unitaire : <input id="m_prixUnitaire" type="number" value="{{m_prixUnitaire}}" name="m_prixUnitaire" size="8"></li><br>
                        <li>Unités en stock : <input id="m_uniteEnStock" type="number" value="{{m_uniteEnStock}}" name="m_uniteEnStock" size="8"></li><br>
                        <li>Unités commandées : <input id="m_uniteCommandees" type="number" value="{{m_uniteCommandees}}" name="m_uniteCommandees" size="8"></li><br>
                        <li>Niveau de Réapprovisionnement : <input id="m_niveauDeReapprovisionnement" type="number" value="{{m_niveauDeReapprovisionnement}}" name="m_niveauDeReapprovisionnement" size="8"></li><br>
                        <li>Indisponible : <input id="m_indisponible" value="{{m_indisponible}}" name="m_indisponible" size="8"></li><br>
                        <input id="code" name="code" type="hidden" value="{{m_reference}}">
                        <input type='submit' name='action' value='Modifier'>
                    </ul>
                </form>
            {{/records}}
        </script>
        <%--<div id="cat"> --%>
            <script id ="catTemplate" type="text/template">
            <select id="id_cat"> 
                {{#records}}
                    <option value={{m_code}}>{{m_libelle}}</option>
                {{/records}}
            </select>
        </script>
    </body>
</html>
