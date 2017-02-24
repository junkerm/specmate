call npm run clean
call npm run build
call robocopy src ..\bundles\specmate-ui-core\webcontent /MIR
call npm run clean
exit /b 0