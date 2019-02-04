const SPECMATE_VERSION = '0.2.11'

const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const GitRevisionPlugin = require('git-revision-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const helpers = require('./helpers');


const gitRevisionPlugin = new GitRevisionPlugin({
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
                test: /\.(html|svg)$/,
                loader: 'html-loader',
                exclude: [helpers.root('node_modules', 'flag-icon-css'), helpers.root('node_modules', 'font-awesome')]
            },
            {
                test: /\.svg/,
                loader: 'file-loader?name=img/[name]_[hash].[ext]',
                include: [helpers.root('node_modules', 'flag-icon-css'), helpers.root('node_modules', 'font-awesome')]
            },
            {
                test: /\.(html|svg)$/,
                loader: 'string-replace-loader',
                query: {
                    search: '@@version',
                    replace: SPECMATE_VERSION
                }
            },
            {
                test: /\.(png|jpe?g|gif|ico)$/,
                loader: 'file-loader?name=img/[name]_[hash].[ext]'
            },
            {
                test: /\.css$/,
                exclude: helpers.root('src', 'app'),
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.css$/,
                include: helpers.root('src', 'app'),
                loader: 'raw-loader'
            },
            {
                test: /\.(ttf|eot)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                // We cannot use [hash] or anything similar here, since ng-split will not work then.
                loader: 'file-loader?name=fonts/[name].[ext]'
            },
            {
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: "file-loader?name=fonts/[name].[ext]",
            },
            {
                test: /\.scss$/,
                use: [{
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
        new webpack.ContextReplacementPlugin(/angular(\\|\/)core(\\|\/)@angular/, helpers.root('../src'), {}),
        new webpack.ContextReplacementPlugin(/\@angular(\\|\/)core(\\|\/)esm5/, helpers.root('../src'), {}),
        new webpack.optimize.CommonsChunkPlugin({ name: ['specmate', 'vendor', 'polyfills', 'assets'] }),
        new HtmlWebpackPlugin({
            template: 'src/index.html',
            favicon: 'src/assets/img/favicon.ico'
        }),

        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery': 'jquery'
        }),

        new CopyWebpackPlugin([{
            from: helpers.root('src', 'assets', 'i18n'),
            to: 'i18n',
            copyUnmodified: true
        }])
    ]
};
