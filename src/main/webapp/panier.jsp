<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panier</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showPanier();
                    }
            );

            function showPanier() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "viewPanier",
                    withCredentials: true,
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#panierTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#panier').html(processedTemplate);
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
                border-radius: 1em;
                width: 400px;
            }
            
            li{
                width: 700px;
            }
           
        </style>
    </head>
    <body>
        <form method="POST"> 
            <input type='submit' name='action' value='accueil'><br>
	</form>
        <div id="panier"></div>
        
        <script id="panierTemplate" type="text/template">
            <TABLE border="1">
            <tr><th>Nom</th><th>Quantités/Unité</th><th>Fournisseur</th><th>Prix Unitaire</th><th>Quantité</th></tr>
            {{! Pour chaque enregistrement }}
            {{#records}}
                {{! Une ligne dans la table }}
                <TR><TD id="nom">{{m_nom}}</TD><TD id="prixunit">{{m_quantiteParUnite}}</TD>
                    <TD id="fournisseur">{{m_fournisseur}}</TD><TD id="qtt_unit">{{m_prixUnitaire}} €</TD>
                    
                    <TD><form method="POST">
                        <input id="idP" name="idP" type="hidden" value="{{m_reference}}">
                        <button type="submit" name="action" value="retrait">-</button>
                        {{m_quantitePanier}}
                        <button type="submit" name="action" value="ajouter">+</button></form></TD>
                    
                    
                </TR>
            {{/records}}
            </TABLE>
            <form method="POST"> 
            <input type='submit' name='action' value='commander'><br>
            </form>
        </script>
    </body>
</html>
