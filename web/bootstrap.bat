call npm config set registry http://registry.npmjs.org/
call npm config set proxy http://surf.proxy.agis.allianz:8080/
call npm config set https-proxy http://surf.proxy.agis.allianz:8080/
call npm config set strict-ssl false
set HTTPS_PROXY=http://surf.proxy.agis.allianz:8080/
set HTTP_PROXY=http://surf.proxy.agis.allianz:8080/
npm --proxy http://surf.proxy.agis.allianz:8080/ --without-ssl --insecure install
