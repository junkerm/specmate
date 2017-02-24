# Specmate

## How to set up your development environment
1. Install Eclipse Neon.2 Modeling Tools
2. From the Eclipse Marketplace, install bndtools 3.3
3. Install node js
4. In the folder web, run npm install. If this does not work on the Allianz-laptops, do the following:

```
npm config set registry http://registry.npmjs.org/
npm config set proxy http://surf.proxy.agis.allianz:8080
npm config set https-proxy http://surf.proxy.agis.allianz:8080
npm config set strict-ssl false
set HTTPS_PROXY=http://surf.proxy.agis.allianz:8080
set HTTP_PROXY=http://surf.proxy.agis.allianz:8080

npm --proxy http://surf.proxy.agis.allianz:8080 --without-ssl --insecure install
```

## Developing the GUI

See ```web/README.md```