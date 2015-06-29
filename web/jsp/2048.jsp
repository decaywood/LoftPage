<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<html>
<head>
  <meta charset="utf-8">
  <title>2048</title>

  <link href="<%=path%>/css/2048/main.css" rel="stylesheet" type="text/css">
  <link rel="shortcut icon" href="<%=path%>/images/2048/favicon.ico">
  <link rel="apple-touch-icon" href="<%=path%>/images/2048/apple-touch-icon.png">
  <link rel="apple-touch-startup-image" href="<%=path%>/images/2048/apple-touch-startup-image-640x1096.png" media="(device-width: 320px) and (device-height: 568px) and (-webkit-device-pixel-ratio: 2)"> <!-- iPhone 5+ -->
  <link rel="apple-touch-startup-image" href="<%=path%>/images/2048/apple-touch-startup-image-640x920.png"  media="(device-width: 320px) and (device-height: 480px) and (-webkit-device-pixel-ratio: 2)"> <!-- iPhone, retina -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">

  <meta name="HandheldFriendly" content="True">
  <meta name="MobileOptimized" content="320">
  <meta name="viewport" content="width=device-width, target-densitydpi=160dpi, initial-scale=1.0, maximum-scale=1, user-scalable=no, minimal-ui">
</head>
<body>
  <div>
    <div id="home">
      <div class="container">
        <div class="heading">
          <h1 class="title">HOME</h1>
          <div class="scores-container">
            <div class="score-container" id="home-score-container">0</div>
            <div class="best-container" id="home-best-container">0</div>
          </div>
        </div>

        <div class="above-game">
          <p class="game-intro">Join the numbers and get to the <strong>2048 tile!</strong></p>
          <a class="restart-button">New Game</a>
        </div>

        <div class="game-container">
          <div class="game-message" id="home-game-message">
            <p></p>
            <div class="lower">
              <a class="keep-playing-button">Keep going</a>
              <a class="retry-button">Try again</a>
            </div>
          </div>

          <div class="grid-container">
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
          </div>

          <div class="tile-container" id="home-tile-container">

          </div>
        </div>
      </div>
    </div>


    <div id="guest">
      <div class="container">
        <div class="heading">
          <h1 class="title">GUEST</h1>
          <div class="scores-container">
            <div class="score-container"id="guest-score-container">0</div>
            <div class="best-container"id="guest-best-container">0</div>
          </div>
        </div>

        <div class="above-game">
          <p class="guest-ip"><strong>guest IP : </strong></p>
        </div>

        <div class="game-container">
          <div class="game-message" id="guest-game-message">
            <p></p>
            <div class="lower">
              <a class="keep-playing-button">Keep going</a>
              <a class="retry-button">Try again</a>
            </div>
          </div>

          <div class="grid-container">
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
            <div class="grid-row">
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
              <div class="grid-cell"></div>
            </div>
          </div>

          <div class="tile-container" id="guest-tile-container">

          </div>
        </div>



      </div>
    </div>

  </div>

  <div>
    <p class="game-explanatio" id="footer">
      <strong class="important">How to play:</strong> Use your <strong>arrow keys</strong> to move the tiles. When two tiles with the same number touch, they <strong>merge into one!</strong>
    </p>
  </div>
  <script src="<%=path%>/js/dependency/uuid.js"></script>
  <script src="<%=path%>/js/dependency/stomp.js"></script>
  <script src="<%=path%>/js/dependency/sockjs.js"></script>
  <script src="<%=path%>/js/dependency/jquery.js"></script>
  <script src="<%=path%>/js/2048/bind_polyfill.js"></script>
  <script src="<%=path%>/js/2048/classlist_polyfill.js"></script>
  <script src="<%=path%>/js/2048/animframe_polyfill.js"></script>
  <script src="<%=path%>/js/2048/keyboard_input_manager.js"></script>
  <script src="<%=path%>/js/2048/html_actuator.js"></script>
  <script src="<%=path%>/js/2048/grid.js"></script>
  <script src="<%=path%>/js/2048/tile.js"></script>
  <script src="<%=path%>/js/2048/local_storage_manager.js"></script>
  <script src="<%=path%>/js/2048/game_manager.js"></script>
  <script src="<%=path%>/js/2048/application.js"></script>
</body>
</html>
