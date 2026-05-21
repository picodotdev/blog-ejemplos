const path = require("path");
const webpack = require("webpack");

module.exports = {
  mode: 'development',
  entry: {
    index: './src/index.tsx'
  },
  output: {
    filename: '[name].js',
    path: __dirname + '/build/webpack/'
  },
  module: {
    rules: [
      { test: /\.(ts|tsx)$/, use: 'ts-loader' },
      { test: /\.(css|less)$/, use: [
        { loader: 'style-loader' },
        { loader: 'css-loader' },
        { loader: 'less-loader' }
      ]}
    ]
  },
  resolve: {
    extensions: ['.js', '.ts', '.tsx']
  },
  devServer: {
    contentBase: path.join(__dirname, "public"),
    port: 3000,
    publicPath: "/",
    hotOnly: true
  }
};
