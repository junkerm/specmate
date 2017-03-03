@echo off
echo CLEANING...
call npm run clean >nul
echo BUILD...
call npm run build
echo DIST...
call robocopy src ..\bundles\specmate-ui-core\webcontent /MIR >nul
echo ==================
echo FINISHED DIST TASK
echo ==================
exit /b 0