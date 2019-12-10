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
        <h1>Bienvenue ${userName}</h1>
        
        <div id="infos"></div>

        <script id="infosTemplate" type="text/template">
            {{#records}}
                <form id="codeForm" action="UpdateClientInfo" method="post">
                    <ul>
                        <li>societé : <input id="m_societe" value="{{m_societe}}" name="loginParam" required size="8"></li><br>
                        <li>contact : <input id="m_contact" value="{{m_contact}}" name="loginParam" required size="8"></li><br>
                        <li>fonction : <input id="m_fonction" value="{{m_fonction}}" name="loginParam" required size="8"></li><br>
                        <li>adresse : <input id="m_adresse" value="{{m_adresse}}" name="loginParam" required size="8"></li><br>
                        <li>ville : <input id="m_ville" value="{{m_ville}}" name="loginParam" required size="8"></li><br>
                        <li>region : <input id="m_region" value="{{m_region}}" name="loginParam" required size="8"></li><br>
                        <li>code postal : <input id="m_codePostal" value="{{m_codePostal}}" name="loginParam" required size="8"></li><br>
                        <li>pays : <input id="m_pays" value="{{m_pays}}" name="loginParam" required size="8"></li><br>
                        <li>téléphone : <input id="m_telephone" value="{{m_telephone}}" name="loginParam" required size="8"></li><br>
                        <li>fax : <input id="m_fax" value="{{m_fax}}" name="loginParam" required size="8"></li><br>
                    </ul>
                </form>
            {{/records}}
        </script>
        
        <form method="POST"> 
			<input type='submit' name='action' value='logout'>
		</form>
    </body>
</html>
