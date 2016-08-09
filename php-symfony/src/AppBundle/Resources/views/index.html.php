<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">

    <title>The HTML5 Herald</title>
    <meta name="description" content="php symfony ts app">
    <meta name="author" content="tradeshift">

    <link rel="stylesheet" href="css/styles.css?v=1.0">

    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script src="//d5wfroyti11sa.cloudfront.net/prod/client/ts-5.0.2-beta.3.min.js"></script>

</head>
<body>
<header ts-topbar="ts.ui.TopBarSpirit"
        class="ts-topbar ts-toolbar ts-spirit ts-bg-dark ts-macro ts-hasleft ts-nosearch"
        style="transform: translate3d(0px, 0px, 0px);">
    <menu class="ts-toolbar-menu ts-left" id="key946751729-items">
        <li class="ts-toolbar-item ts-toolbar-title " id="key946751729-title">
            <label>PHP Symfony sample App</label>
        </li>
    </menu>
</header>
<main ts-main>
    <div ts-main-content>
        <h1>PHP Symfony sample App</h1>

        <div ts-panel>
            <p>Company card</p>

            <?php dump ($content) ?>
        </div>
    </div>
</main>
<script>
    ts.ui.ready(function() {
        boostrap_everything();
    });
</script>
</body>
</html>