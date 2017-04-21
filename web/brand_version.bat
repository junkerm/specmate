@echo off
git log --pretty=%%h -1 > revnum.txt
set /p REFNUM=<revnum.txt
del revnum.txt

setlocal enabledelayedexpansion
set INTEXTFILE=src\app\config\config.js
set OUTTEXTFILE=tmp_version_brand.txt
set SEARCHTEXT=-SPECMATE-VERSION-

for /f "tokens=1,* delims=¶" %%A in ( '"type %INTEXTFILE%"') do (
SET string=%%A
SET modified=!string:%SEARCHTEXT%=%REFNUM%!

echo !modified! >> %OUTTEXTFILE%
)
move /Y %OUTTEXTFILE% %INTEXTFILE%