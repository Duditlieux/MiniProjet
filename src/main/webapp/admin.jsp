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
        <script>

            
            
            
            
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showProducts();
                    }
            );
    
            function showChart1() {
                google.charts.setOnLoadCallback(drawChart1);
            }

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
                var parsedUrl = new URL(window.location.href);
                var debut = parsedUrl.searchParams.get("debut");
                var fin = parsedUrl.searchParams.get("fin");
                
                var jsonData = $.ajax({
                  url: "CaCategorieJson",
                  data: {"debut": debut, "fin": fin},
                  dataType: "json",
                  async: false
                  }).responseText;
                           

                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Catégories');
                data.addColumn('number', 'CA');
                var array = JSON.parse(jsonData);
  
                $.each(array.records, function (i, obj) {
                   data.addRow([obj.m_s, obj.m_n]);
                  });
                  

                // Set chart options
                var options = {'title': 'Chiffre d\'affaire par catégorie d\'articles',
                    'is3D': true,
                    'width': 600,
                    'height': 400};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div1'));
                chart.draw(data, options);
            }

            function drawChart2() {
                var parsedUrl = new URL(window.location.href);
                var debut = parsedUrl.searchParams.get("debut");
                var fin = parsedUrl.searchParams.get("fin");
                
                var jsonData = $.ajax({
                  url: "chiffreDAffaireParPays",
                  data: {"debut": debut, "fin": fin},
                  dataType: "json",
                  async: false
                  }).responseText;

                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Pays');
                data.addColumn('number', 'CA');
                var array = JSON.parse(jsonData);
  
                $.each(array.records, function (i, obj) {
                   data.addRow([obj.m_s, obj.m_n]);
                  });

                // Set chart options
                var options = {'title': 'Chiffre d\'affaire par pays',
                    'is3D': true,
                    'width': 600,
                    'height': 400};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div2'));
                chart.draw(data, options);
            }

            function drawChart3() {
                var parsedUrl = new URL(window.location.href);
                var debut = parsedUrl.searchParams.get("debut");
                var fin = parsedUrl.searchParams.get("fin");
                
                var jsonData = $.ajax({
                  url: "CaClientJson",
                  data: {"debut": debut, "fin": fin},
                  dataType: "json",
                  async: false
                  }).responseText;

                // Create the data table.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Client');
                data.addColumn('number', 'CA');
                var array = JSON.parse(jsonData);
  
                $.each(array.records, function (i, obj) {
                   data.addRow([obj.m_s, obj.m_n]);
                  });

                // Set chart options
                var options = {'title': 'Chiffre d\'affaire par client',
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
                    url: "allProducts",
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
            
            // Supprimer un produit
            function deleteProduct(code) {
                $.ajax({
                    url: "supprProduit",
                    data: {"code": code},
                    dataType: "json",
                    success: 
                            function (result) {
                                alert("ça marche");
                            },
                    error: alert("ça marche pas")
                });
                return false;
            }

            
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
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
            
            .message{
                font-size: 30px;
            }
            
            

        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>yo</title>
    </head>
    <body>
        <h1>Bonjour ${userName}</h1>
        
        <h1 class="message">Voici les dernières statistiques du site : </h1>
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
                <div id="chart_div1"></div>
                <div><form onsubmit="drawChart1()">
                    <input type="date" id="debut" name="debut"/>
                    <input type="date" id="fin" name="fin"/>
                    <input type="submit">
                    </form></div>
            </div>
            <div class="cell">
                <div id="chart_div2"></div>
                <div><form onsubmit="drawChart2()">
                    <input type="date" id="debut" name="debut"/>
                    <input type="date" id="fin" name="fin"/>
                    <input type="submit">
                    </form></div>
            </div>
            <div class="cell">
                <div id="chart_div3"></div>
                <div><form onsubmit="drawChart3()">
                    <input type="date" id="debut" name="debut"/>
                    <input type="date" id="fin" name="fin"/>
                    <input type="submit">
                    </form></div>
            </div>
        </div>
        
        
        <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
        
        <h1 class="message">Et voici la liste des produits affichés aux clients et aux visiteurs : </h1>
        <form method="POST">
                            <input type="submit" name="action" value="Nouveau">
                        </form>
        <div id="codes"></div>

        <!--<div id="chart_div1"></div>
        <div id="chart_div2"></div>
        <div id="chart_div3"></div>-->


        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
            <TABLE border="1">
            <tr><th>Référence</th><th>Nom</th><th>Fournisseur</th><th>Catégorie</th><th>Quantités/Unité</th><th>Prix Unitaire</th><th>Quantité en stock</th><th>Disponibilité</th><th>Action</th></tr>
            {{! Pour chaque enregistrement }}
            {{#records}}
                {{! Une ligne dans la table }}
                    <TR>
                        <TD id="ref2">{{m_reference}}</TD>
                        <TD id="nom">{{m_nom}}</TD>
                        <TD id="fournisseur">{{m_fournisseur}}</TD>
                        <TD id="cat">{{m_categorie}}</TD>
                        <TD id="qtt_unit">{{m_quantiteParUnite}}</TD>
                        <TD id="prix_unit">{{m_prixUnitaire}}</TD>
                        <TD id="en_stock">{{m_uniteEnStock}}</TD>
                        <TD id="indispo">{{m_indisponible}}</TD>
                        <TD>
                        <form method="POST">
                            <input id="ref" name="ref" type="hidden" value="{{m_reference}}">
                            <input type="submit" name="action" value="Editer">
                            <input type="submit" name="action" value="Supprimer">
                        </form>
                        </TD>
                       
                    </TR>
    
            {{/records}}
            </TABLE>
        </script>
            
          
    </body>
</html>
