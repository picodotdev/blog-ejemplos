module.exports = {
  mode: 'development',
  entry: {
    index: './src/index.ts'
  },
  output: {
    filename: '[name].js',
    path: __dirname + '/build/webpack/'
  },
  module: {
    rules: [
      { test: /\.(ts|tsx)$/, use: 'ts-loader' },
      { test: /\.less$/, use: [
        { loader: 'style-loader' },
        { loader: 'css-loader' },
        { loader: 'less-loader' }
      ]}
    ]
  },
  resolve: {
    extensions: ['.js', '.ts', '.tsx']
  }
};