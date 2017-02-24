call npm run clean
call npm run build
call robocopy src ..\bundles\specmate-ui-core\webcontent /MIR
exit /b 0