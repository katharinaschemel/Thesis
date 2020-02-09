'use strict';

var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

/**
 * Get configuration for Webpack
 *
 * @see http://webpack.github.io/docs/configuration
 *      https://github.com/petehunt/webpack-howto
 *
 * @param {boolean} release True if configuration is intended to be used in
 * a release mode, false otherwise
 * @return {object} Webpack configuration
 */
module.exports = {
  entry: {
    app: './src/app.js'
  },

  output: {
    filename: 'app.js',
    path: path.resolve(__dirname, 'build')
    
  },

  devtool: false,

  stats: {
    colors: true,
    reasons: true
  },

  plugins: [
    new HtmlWebpackPlugin({template: 'src/assets/index.html'})
  ],

  resolve: {
    extensions: ['.webpack.js', '.web.js', '.js', '.jsx'],
	modules: [
      path.resolve(__dirname,'node_modules')
    ]
  },

  module: {
    rules: [
      {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      },
      {
        test:  /\.(jpe?g|png|gif|svg)$/i,
        use: [
          {
            loader: 'file-loader'
          },
		]
      },
      {
        test: /\.js|\.jsx/,
        exclude: /node_modules|bower_components/,
        loader: 'babel-loader'
      }
    ]
  }
};

