SET HTTP_PROXY=surf.proxy.agis.allianz
SET HTTPS_PROXY=surf.proxy.agis.allianz

call npm config set proxy http://surf.proxy.agis.allianz:8080
call npm config set https-proxy http://surf.proxy.agis.allianz:8080

call npm install

call npm run clean
call npm run build-copy
call npm run dist