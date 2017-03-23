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

            // Angular2 Modal
            'angular2-modal': 'npm:angular2-modal/bundles/angular2-modal.umd.js',
            'angular2-modal/plugins/bootstrap': 'npm:angular2-modal/bundles',
            'angular2-modal/plugins/js-native': 'npm:angular2-modal/bundles',

            // Traceur for compiling ES6 to ES5 (Needed for D3)
            'plugin-traceur': 'npm:traceur/plugin-traceur.js',
            'traceur': 'npm:traceur/traceur.js',
            'traceur-runtime': 'npm:traceur/traceur-runtime.js',

            // D3 Service
            'd3-ng2-service': 'npm:d3/d3-ng2-service/index.js',
            'd3-array': 'npm:d3/d3-array/index.js',
            'd3-axis': 'npm:d3/d3-axis/index.js',
            'd3-brush': 'npm:d3/d3-brush/index.js',
            'd3-chord': 'npm:d3/d3-chord/index.js',
            'd3-collection': 'npm:d3/d3-collection/index.js',
            'd3-color': 'npm:d3/d3-color/index.js',
            'd3-dispatch': 'npm:d3/d3-dispatch/index.js',
            'd3-drag': 'npm:d3/d3-drag/index.js',
            'd3-dsv': 'npm:d3/d3-dsv/index.js',
            'd3-ease': 'npm:d3/d3-ease/index.js',
            'd3-force': 'npm:d3/d3-force/index.js',
            'd3-format': 'npm:d3/d3-format/index.js',
            'd3-geo': 'npm:d3/d3-geo/index.js',
            'd3-hierarchy': 'npm:d3/d3-hierarchy/index.js',
            'd3-interpolate': 'npm:d3/d3-interpolate/index.js',
            'd3-path': 'npm:d3/d3-path/index.js',
            'd3-polygon': 'npm:d3/d3-polygon/index.js',
            'd3-quadtree': 'npm:d3/d3-quadtree/index.js',
            'd3-queue': 'npm:d3/d3-queue/index.js',
            'd3-random': 'npm:d3/d3-random/index.js',
            'd3-scale': 'npm:d3/d3-scale/index.js',
            'd3-selection': 'npm:d3/d3-selection/index.js',
            'd3-selection-multi': 'npm:d3/d3-selection-multi/index.js',
            'd3-shape': 'npm:d3/d3-shape/index.js',
            'd3-time': 'npm:d3/d3-time/index.js',
            'd3-time-format': 'npm:d3/d3-time-format/index.js',
            'd3-timer': 'npm:d3/d3-timer/index.js',
            'd3-transition': 'npm:d3/d3-transition/index.js',
            'd3-voronoi': 'npm:d3/d3-voronoi/index.js',
            'd3-zoom': 'npm:d3/d3-zoom/index.js'
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
            'd3-ng2-service': {
                defaultExtension: 'js'
            },
            'angular2-modal/plugins/bootstrap': {
                defaultExtension: 'js',
                main: `angular2-modal.bootstrap.umd`
            },
            'angular2-modal/plugins/js-native': {
                defaultExtension: 'js',
                main: `angular2-modal.js-native.umd`
            }
        }
    });
})(this);