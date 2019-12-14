<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    </head>
    <body>
        <form id="codeForm" method="post">
                    <ul>
                        <li>Nom : <input id="m_nom" name="m_nom" required size="8"></li><br>
                        <li>Fournisseur : <input id="m_fournisseur" type="number" name="m_fournisseur" required size="8"></li><br>
                        <li>Catégorie : <input id="m_categorie" type="number" name="m_categorie" size="8"></li><br>
                        <li>Quantité par Unité : <input id="m_quantiteParUnite" name="m_quantiteParUnite" size="8"></li><br>
                        <li>Prix Unitaire : <input id="m_prixUnitaire" type="number" name="m_prixUnitaire" size="8"></li><br>
                        <li>Unités en stock : <input id="m_uniteEnStock" type="number" name="m_uniteEnStock" size="8"></li><br>
                        <li>Unités commandées : <input id="m_uniteCommandees" type="number" name="m_uniteCommandees" size="8"></li><br>
                        <li>Niveau de Réapprovisionnement : <input id="m_niveauDeReapprovisionnement" type="number" name="m_niveauDeReapprovisionnement" size="8"></li><br>
                        <li>Indisponible : <input id="m_indisponible" name="m_indisponible" size="8"></li><br>
                        <input type='submit' name='action' value='CreerP'>
                    </ul>
        </form>
    </body>
</html>
