/**
 * System configuration for Angular samples
 * Adjust as necessary for your application needs.
 */
(function(global) {
    System.config({
        defaultJSExtensions: true,
        defaultExtension: 'js',
        paths: {
            // paths serve as alias
            'npm:': 'lib/js/'
        },
        // map tells the System loader where to look for things
        map: {
            // our app is within the app folder
            app: 'app',

            // angular bundles
            '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
            '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
            '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
            '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
            '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
            '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
            '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',

            // other libraries
            'rxjs': 'npm:rxjs',
            'angular-in-memory-web-api': 'npm:angular-in-memory-web-api/bundles/in-memory-web-api.umd.js',

            // Bootstrap
            '@ng-bootstrap/ng-bootstrap': 'npm:@ng-bootstrap/ng-bootstrap/bundles/ng-bootstrap.js',

            // Angular split
            'angular-split': 'npm:angular-split/index.js',

            // UUID
            'angular2-uuid': 'npm:angular2-uuid/index.js',

            // Traceur for compiling ES6 to ES5 (Needed for D3)
            'plugin-traceur': 'npm:traceur/plugin-traceur.js',
            'traceur': 'npm:traceur/traceur.js',
            'traceur-runtime': 'npm:traceur/traceur-runtime.js',
        },

        meta: {
            'traceur': {
                format: 'global',
                exports: 'traceur',
                scriptLoad: false
            },
            'traceur-runtime': {
                format: 'global',
                exports: '$traceurRuntime'
            }
        },

        transpiler: 'plugin-traceur',
        tracepilerRuntime: false,

        // packages tells the System loader how to load when no filename and/or no extension
        packages: {
            defaultExtension: 'js',
            app: {
                defaultExtension: 'js'
            },
            rxjs: {
                defaultExtension: 'js'
            },
            'angular-split': {
                main: './index.js',
                defaultExtension: 'js'
            }
        }
    });
})(this);