var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var GitRevisionPlugin = require('git-revision-webpack-plugin');
var helpers = require('./helpers');

var gitRevisionPlugin = new GitRevisionPlugin({
    commithashCommand: 'rev-parse --short HEAD'
});

module.exports = {
    entry: {
        'polyfills': './src/polyfills.ts',
        'vendor': './src/vendor.ts',
        'specmate': './src/main.ts',
        'assets': './src/assets.ts',
    },

    resolve: {
        extensions: ['.ts', '.js']
    },

    module: {
        rules: [{
                test: /\.ts$/,
                loaders: [{
                    loader: 'awesome-typescript-loader',
                    options: { configFileName: helpers.root('src', 'tsconfig.json') }
                }, 'angular2-template-loader']
            },
            {
                test: /.*flags.*\.svg/,
                loader: 'svg-url-loader'
            },
            {
                test: /\.(html|svg)$/,
                loader: 'html-loader'
            },
            {
                test: /\.(html|svg)$/,
                loader: 'string-replace-loader',
                query: {
                    search: '@@version',
                    replace: gitRevisionPlugin.commithash()
                }
            },
            {
                test: /\.(png|jpe?g|gif|ico)$/,
                loader: 'file-loader?name=img/[name].[ext]'
            },
            {
                test: /.*i18n.*\.json$/,
                loader: 'file-loader?name=i18n/[name].[ext]'
            },
            {
                test: /\.css$/,
                exclude: helpers.root('src', 'app'),
                use: [ 'style-loader', 'css-loader' ]
            },
            {
                test: /\.css$/,
                include: helpers.root('src', 'app'),
                loader: 'raw-loader'
            },
            {
                test: /\.(ttf|eot)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'file-loader?name=fonts/[name].[ext]'
            },
            { 
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, 
                loader: "url-loader?limit=1000000&mimetype=application/font-woff",
                exclude: helpers.root('node_modules', 'font-awesome')
            },
            { 
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, 
                loader: "file-loader?name=fonts/[name].[ext]",
                include: helpers.root('node_modules', 'font-awesome')
            },
            {
                test: /\.scss$/,
                use: [
                    {
                        loader: "style-loader"
                    },
                    {
                        loader: "postcss-loader",
                        options: {
                            sourceMap: true,
                            plugins: function() {
                                return [require("autoprefixer")];
                            }
                        }
                    },
                    {
                        loader: "sass-loader",
                        options: {
                            sourceMap: true
                        }
                    }
                ]
            }
        ]
    },

    plugins: [
        // Workaround for angular/angular#11580
        new webpack.ContextReplacementPlugin(
            /angular(\\|\/)core(\\|\/)@angular/,
            helpers.root('../src'), {}
        ),

        new webpack.ContextReplacementPlugin(/\@angular(\\|\/)core(\\|\/)esm5/, helpers.root('../src'), {}),

        new webpack.optimize.CommonsChunkPlugin({
            name: ['specmate', 'vendor', 'polyfills', 'assets']
        }),

        new HtmlWebpackPlugin({
            template: 'src/index.html'
        }),

        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery': 'jquery',
            Popper: ['popper.js', 'default'],
            // In case you imported plugins individually, you must also require them here:
            Util: "exports-loader?Util!bootstrap/js/dist/util",
            Button: "exports-loader?Button!bootstrap/js/dist/button",
            Modal: "exports-loader?Modal!bootstrap/js/dist/modal",
        }),

        gitRevisionPlugin
    ]
};