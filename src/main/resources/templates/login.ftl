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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" crossorigin="anonymous">

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

    <!-- Navigation bar
    <div id="nav" class="row">
    </div>
    -->

</div>

<!-- main content -->
<main id="content" class="container">
    <h2 class="row">Herzlich Willkommen auf der Klassenwebsite der Klasse 9C</h2>
        <form action="/login" method="post">
            <p style="text-align: left">Benutzername</p>
            <input type="text" name="usr" title="username">
            <br>
            <br>
            <p style="text-align: left">Passwort</p>
            <input type="password" name="psw" title="password"><br>
            <input type="submit" name="Submit">
        </form>
</main>

<!-- Contains the three news elements -->
<div class="container">
    <div class="row">

        <div class="news col-xs-12 col-md-6">

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
