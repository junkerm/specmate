// Angular
import '@angular/common';
import '@angular/common/http';
import '@angular/core';
import '@angular/platform-browser';
import '@angular/platform-browser-dynamic';
import '@angular/router';
import '@ng-bootstrap/ng-bootstrap';
// Angular translations (i18n)
import '@ngx-translate/core';
import '@ngx-translate/http-loader';

import 'angular-split';
import 'angular2-uuid';
// RxJS - Do not import here as a whole, since we only use very limited functionality
// and UglifyJs' tree shaking does not remove all dead code
// import 'rxjs';
// Other vendors for example jQuery, Lodash or Bootstrap
// You can import js, ts, css, sass, ...
import 'jquery/dist/jquery.js';
import 'ng2-dragula';



