<#-- @ftlvariable name="events" type="java.util.List<SimpleHash>" -->
<!Doctype html>
<html lang="de">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#ffc000">

    <title>Klassenwebsite - GyDo9C</title>
    <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab|Open+Sans+Condensed:300|Teko|Khand' rel='stylesheet'
          type='text/css'>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          crossorigin="anonymous">

    <!-- Bootstrap and jQuery JavaScript libraries -->
    <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" crossorigin="anonymous"></script>
</head>
<body>
<!-- Container for header and nav bar -->
<div class="container">

    <!-- Header -->
    <header class="row">
        <h1 class="col-xs-12">Klassenwebsite</h1>
    </header>

</div>

<nav class="navbar-default container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-index"
                aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Navigation</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="navbar-index">
        <ul class="nav navbar-nav">
            <li><a href="/">Startseite</a></li>
            <li class="active"><a href="#">Termine <span class="sr-only">(current)</span></a></li>
            <li><a href="#">Stundenplan</a></li>
            <li><a href="#">Hausaufgaben</a></li>
            <li><a href="#">Links</a></li>
            <li><a href="#">Kontakt</a></li>
            <li><a href="#">Impressum</a></li>
        </ul>
    </div><!-- /.navbar-collapse -->
</nav>

<!-- main content -->
<main id="content" class="container">
    <marquee><span style="color:red">Morgen: Deutsch Schulaufgabe!</span>
        --- Bitte denkt an das Geld f&uuml;r die Hefte (13,20 &euro;)
    </marquee>
    <h1>Termine</h1>
    <h3>Hier seht ihr eine List bevorstehender Termine</h3>
<#list events as event>
    <p class="date">${event["dateString"]}</p>
    <p>${event["description"]}</p>
</#list>
</main>

<!-- Contains the three news elements -->
<div class="container">
    <div class="row">

        <div class="news col-xs-12 col-md-6">
            <hr>
            <h4>Wichtige Termine:</h4>
            <p>
                <b>27.02.2016</b><br>&nbsp;&nbsp;Tag der offenen T&uuml;r<br>
                <b>14.03.2016-18.03.2016</b><br>&nbsp;&nbsp;Betriebspraktikum<br><br><br><br>
                <a href="#">mehr</a>
            </p>
        </div>

        <div class="news col-xs-12 col-md-6">
            <hr>
            <h4>Gymnasium Dorfen:</h4>
            <p>Adresse:&nbsp;<a
                    href="https://www.google.de/maps/place/Gymnasium+Dorfen/data=!4m2!3m1!1s0x0:0xcbeefa7a7d451fa3?sa=X&ved=0CDYQrwswA2oVChMIudmngfqjyAIVp8ByCh3M-wRy"
                    target="_blank">Josef-Martin-Bauer-Str.18, 84405 Dorfen</a></p>
            <p>Tel.: 08081 9572-0<br>
                Fax: 08081 9572-299</p>
            <p>E-Mail:&nbsp;<a href="mailto:sekretariat@gymnasiumdorfen.de"
                               target="_blank">sekretariat@<br>gymnasiumdorfen.de</a>
            </p>
            <p>Zur Homepage:&nbsp;<a href="http://gymnasiumdorfen.de" target="_blank">gymnasiumdorfen.de</a></p>
        </div>

    </div>
</div>

<footer class="container">
    <div class="row">
        <p class="col-xs-4">Text: CC-BY Mollimop Organisation.</p>
        <p class="col-xs-4">Developed by Andi, Thomas and Vogte in Germany</p>
        <p class="col-xs-4">Source code available at <a
                href="https://github.com/tombom4/schoolclasswebsite">Github.com</a></p>
    </div>
</footer>

</body>
</html>

