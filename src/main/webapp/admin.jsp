<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- On charge JQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>

        <!-- On charge l'API Google -->
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript">

            
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }
            
            
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showProducts();
                    }
            );

            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawChart1);
            google.charts.setOnLoadCallback(drawChart2);
            google.charts.setOnLoadCallback(drawChart3);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.
            function drawChart1() {

                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Topping');
                data.addColumn('number', 'Slices');
                data.addRows([
                    ['option 1', 4],
                    ['option 2', 1],
                    ['option 3', 1],
                    ['option 4', 1],
                    ['option 5', 2]
                ]);

                // Set chart options
                var options = {'title': 'Chiffre d\'affaire pas pays',
                    'is3D': true,
                    'width': 600,
                    'height': 400};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('originePays'));
                chart.draw(data, options);
            }

            function drawChart2() {

                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Topping');
                data.addColumn('number', 'Slices');
                data.addRows([
                    ['option 1', 4],
                    ['option 2', 1],
                    ['option 3', 1],
                    ['option 4', 1],
                    ['option 5', 2]
                ]);

                // Set chart options
                var options = {'title': 'Titre du graphique',
                    'is3D': true,
                    'width': 600,
                    'height': 400};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div2'));
                chart.draw(data, options);
            }

            function drawChart3() {

                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Topping');
                data.addColumn('number', 'Slices');
                data.addRows([
                    ['option 1', 4],
                    ['option 2', 1],
                    ['option 3', 1],
                    ['option 4', 1],
                    ['option 5', 2]
                ]);

                // Set chart options
                var options = {'title': 'Titre du graphique',
                    'is3D': true,
                    'width': 600,
                    'height': 400};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div3'));
                chart.draw(data, options);
            }
            


            function showProducts() {
                // On fait un appel AJAX pour chercher les produits
                $.ajax({
                    url: "getNbClientParPays",
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#originePays').html(processedTemplate);
                            }
                });
            }

            
            
            
        </script>
        <style>

            .row {
                clear: both;
                width: 100%;
                margin: 0px;
                padding: 0px;
            }

            .cell {
                float: left;
                min-height:400px;
                width: 24%;
                text-align: center;
                border: 1px solid black;
                min-width: 600px;
            }


            #chart_div1{
                text-align: left;
            }

            #chart_div2{
                text-align: center;
            }

            #chart_div3{
                text-align: right;
            }


        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>yo</title>
    </head>
    <body>
        <h1>Bonjour ${userName}</h1>
        <form method="POST">
            <input type="submit" name="action" value="logout"><br>
        </form>
        <!-- Il faut encore adapter les charts selon les paramètres nécessaires 
        (1er : catégorie d'article, en choisissant la période (date de début / date de fin)
        (2ème : pays, en choisissant la période (date de début / date de fin)
        (3ème : client, en choisissant la période (date de début / date de fin)
        -->

        <div class="row">
            <div class="cell">

                <div id="originePays"></div>
                <%--<script id ="catTemplate" type="text/template">
                    <select id="id_cat">
                        <option onclick="showClient()" value=0>Tout les produits</option> 
                        {{#records}}
                            <option onclick="showProductFrom({{m_code}})" value={{m_code}}>{{m_libelle}}</option>
                        {{/records}}
                    </select>
                </script>--%>
            </div>
            <div class="cell">
                <div id="chart_div2"></div>
            </div>
            <div class="cell">
                <div id="chart_div3"></div>
            </div>
        </div>
        <br>
        <br>

        <div id="codes"></div>

        <!--<div id="chart_div1"></div>
        <div id="chart_div2"></div>
        <div id="chart_div3"></div>-->


        <!-- Le template qui sert à formatter la liste des codes -->
        <!--<script id="codesTemplate" type="text/template">
            <TABLE border="1">
            <tr><th>Nom</th><th>Quantités/Unité</th><th>Fournisseur</th><th>Prix Unitaire</th><c:if test="${not empty sessionScope.code}"><th>Panier</th></c:if></tr>
            {{! Pour chaque enregistrement }}
            {{#records}}
                {{! Une ligne dans la table }}
                <TR><TD id="nom">{{m_nom}}</TD><TD id="prixunit">{{m_quantiteParUnite}}</TD>
                    <TD id="fournisseur">{{m_fournisseur}}</TD><TD id="qtt_unit">{{m_prixUnitaire}} €</TD>
        <c:set var = "indispo" scope = "page" value = "{{m_indisponible}}}"/>
        <c:if test="${indispo == false}"><td><form method="POST"><input id="ref" name="ref" type="hidden" value="{{m_reference}}"><input type='submit' name='action' value='ajouter'></form></td></c:if>
        <c:if test="${indispo == true}"><td>Indisponible</td></c:if><td>{{m_quantitePanier}}</td>
        <c:remove var="indispo" scope="page" />
    </TR>
    
{{/records}}
</TABLE>
</script>-->
    </body>
</html>
