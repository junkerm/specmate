@echo off
REM echo CLEANING...
REM call npm run clean >nul
REM echo BUILD...
call npm run build
echo DIST...
call robocopy src ..\bundles\specmate-ui-core\webcontent /MIR >nul
echo ============================
echo FINISHED DIST TASK
date /T
time /T
echo ============================
exit /b 0