mkdir tmp
mkdir empty
robocopy empty src\lib /mir
robocopy src\app tmp *.js.map *.js /e /move
robocopy empty tmp /mir
rmdir empty
rmdir tmp
del src\main.js*