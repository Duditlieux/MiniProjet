<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Espace Client</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showInfosClient("${code}");
                    }
            );

            function showInfosClient(code) {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "InfoClient",
                    data: {"code": code},
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#infosTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#infos').html(processedTemplate);
                            }
                });
            }

            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
        </script>
    </head>
    <body>
        <h1>Bienvenue ${userName}</h1>
        <div>${code}</div>
        <div id="infos"></div>

        <script id="infosTemplate" type="text/template">
            {{#records}}
                <div>
                    <div>Bienvenue {{m_contact}} !</div>
                    <div>societé : {{m_societe}}</div>
                    <div>contact : {{m_contact}}</div>
                    <div>fonction : {{m_fonction}}</div>
                    <div>adresse : {{m_adresse}}</div>
                    <div>ville : {{m_ville}}</div>
                    <div>region : {{m_region}}</div>
                    <div>code postal : {{m_codePostal}}</div>
                    <div>pays : {{m_pays}}</div>
                    <div>téléphone : {{m_telephone}}</div>
                    <div>fax : {{m_fax}}</div>
                </div>
            {{/records}}
        </script>
        
        <form action="<c:url value="/"/>" method="POST"> 
			<input type='submit' name='action' value='logout'>
		</form>
    </body>
</html>
