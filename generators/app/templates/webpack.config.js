const fs = require('fs');
const path = require('path');
const webpack = require('webpack');
const merge = require('webpack-merge');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const HtmlWebpackHarddiskPlugin = require('html-webpack-harddisk-plugin');
const FaviconsWebpackPlugin = require('favicons-webpack-plugin');
const convert = require('koa-connect');
const history = require('connect-history-api-fallback');
const proxy = require('http-proxy-middleware');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const { parseString } = require('xml2js');

const sourceDir = path.resolve(__dirname, 'src/main/client');

const targetDir = (function getTargetDir() {
  const pomXml = fs.readFileSync('pom.xml', 'utf8');

  let artifactId;
  let version;
  parseString(pomXml, (err, result) => {
    artifactId = result.project.artifactId[0];
    version = result.project.version[0];
  });

  return path.resolve(__dirname, `target/${artifactId}-${version}`);
})();

const NODE_ENV = process.env.NODE_ENV || 'development';
const isProduction = NODE_ENV === 'production';

/**
 * Common Webpack configuration.
 */
const commonConfig = {
  mode: isProduction ? 'production' : 'development',
  output: {
    filename: '[name].js',
    path: `${targetDir}/client`,
    publicPath: '/client/',
  },

  // Enable sourcemaps for debugging webpack's output.
  devtool: 'inline-source-map',

  resolve: {
    extensions: ['.webpack.js', '.web.js', '.js', '.jsx'],
  },

  optimization: {
    splitChunks: {
      chunks: 'all',
    },
  },

  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify(NODE_ENV),
      },
      PRODUCTION: JSON.stringify(isProduction),
      DEVELOPMENT: JSON.stringify(!isProduction),
    }),

    new FaviconsWebpackPlugin({
      // The source icon
      logo: path.resolve(sourceDir, 'images/favicon/original.png'),

      // The prefix for all image files (might be a folder or a name)
      prefix: 'images/favicon-[hash]/',

      // Generate a cache file with control hashes and
      // don't rebuild the favicons until those hashes change
      persistentCache: true,

      // Inject the html into the html-webpack-plugin
      inject: true,
    }),

    // Automatically generates index.html
    new HtmlWebpackPlugin({
      title: '<%= project %>',

      template: path.resolve(sourceDir, 'index.ejs'),

      filename: `<%= _.unescape('${targetDir}')%>/WEB-INF/ftl/index.html`,

      alwaysWriteToDisk: true,
    }),

    // Support for alwaysWriteToDisk config in HtmlWebpackPlugin
    new HtmlWebpackHarddiskPlugin(),
  ],

  module: {
    rules: [
      // All output '.js' files will have any sourcemaps re-processed by 'source-map-loader'.
      {
        enforce: 'pre',
        test: /\.jsx?$/,
        use: [{ loader: 'eslint-loader', options: { emitWarning: true } }, 'source-map-loader'],
        exclude: /node_modules/,
      },

      // Load library CSS styles
      {
        test: /\.css$/,
        use: isProduction ? [MiniCssExtractPlugin.loader, { loader: 'css-loader', options: { sourceMap: true } }] : ['style-loader', 'css-loader'],
        include: /node_modules/,
      },

      // Load Less files
      {
        test: /\.less$/,
        include: [path.resolve(sourceDir, 'less'), path.resolve(sourceDir, 'javascript')],
        use: isProduction
          ? [MiniCssExtractPlugin.loader, 'css-loader', 'postcss-loader', 'less-loader']
          : ['style-loader', 'css-loader', 'postcss-loader', 'less-loader?source-map'],
      },

      // Image loading. Inlines small images as data URIs (i.e. < 10k).
      {
        test: /\.(png|jpg|jpeg|gif|svg)$/,
        loader: 'url-loader',
        options: {
          name: 'images/[name].[hash].[ext]',
          limit: 10000,
        },
      },

      // Inline small woff files and output them at fonts/.
      {
        test: /\.woff2?$/,
        loader: 'url-loader',
        options: {
          name: 'fonts/[hash].[ext]',
          limit: 50000,
          mimetype: 'application/font-woff',
        },
      },

      // Load other font file types at fonts/
      {
        test: /\.(ttf|svg|eot)$/,
        loader: 'file-loader',
        options: {
          name: 'fonts/[hash].[ext]',
        },
      },
    ],
  },
};


/**
 * Development server configuration overrides.
 */
const developmentConfig = {
  devtool: 'cheap-eval-source-map',
  entry: './src/main/client/javascript/index.jsx',
  output: {
    publicPath: '/',
  },

  plugins: [
    // Prints more readable module names in the browser console on HMR updates
    new webpack.NamedModulesPlugin(),
  ],

  module: {
    rules: [
      {
        test: /\.jsx?$/,
        include: path.resolve(sourceDir, 'javascript'),
        use: ['babel-loader'],
      },
    ],
  },
  serve: {
    port: 3000,
    content: [targetDir],
    add: (app) => {
      app.use(convert(proxy('/_ah', { target: 'http://localhost:8080' })));
      app.use(convert(proxy('/api', { target: 'http://localhost:8080' })));
      app.use(convert(proxy('/login/', { target: 'http://localhost:8080' })));
      app.use(convert(proxy('/system', { target: 'http://localhost:8080' })));
      app.use(convert(proxy('/cron', { target: 'http://localhost:8080' })));
      app.use(convert(proxy('/task', { target: 'http://localhost:8080' })));
      app.use(convert(proxy('/error', { target: 'http://localhost:8080' })));
      app.use(
        convert(
          history({
             index: 'WEB-INF/ftl/index.html',
          }),
        ),
      );
    },
  },
};


/**
 * Production/deployment configuration overrides.
 */
const productionConfig = {
  devtool: 'source-map',

  entry: ['./src/main/client/javascript/index.jsx'],

  output: {
    // Hash bundles for easy and agressive caching
    filename: '[name].[chunkhash].js',
  },

  plugins: [
    // Ensure old builds are cleaned out
    new CleanWebpackPlugin(),
    new MiniCssExtractPlugin({
      filename: '[contenthash].css',
    }),
  ],

  module: {
    rules: [
      {
        test: /\.jsx?$/,
        include: path.resolve(sourceDir, 'javascript'),
        use: ['babel-loader'],
      },
    ],
  },
};

// Grab the appropriate configuration for the environment
const environmentConfig = isProduction ? productionConfig : developmentConfig;

// Merge common config with environment specific configuration
module.exports = merge(commonConfig, environmentConfig);
