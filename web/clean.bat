mkdir tmp
mkdir empty
call robocopy empty ..\bundles\specmate-ui-core\webcontent /MIR
robocopy empty src\lib /MIR
rmdir src\lib
robocopy src\app tmp *.js.map *.js /e /move
robocopy empty tmp /MIR
rmdir empty
rmdir tmp
del src\main.js*
