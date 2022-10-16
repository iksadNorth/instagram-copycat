const { defineConfig } = require('@vue/cli-service')
// https://velog.io/@ingdol2/vue-%EC%98%A4%EB%A5%98-Module-not-found-Error-Cant-resolve-crypto
// 1번 솔루션
const NodePolyfillPlugin = require("node-polyfill-webpack-plugin");

module.exports = defineConfig({
  transpileDependencies: true,
  // <1번 솔루션>
  lintOnSave: false,
  configureWebpack: {
    plugins: [new NodePolyfillPlugin()],
    optimization: {
      splitChunks: {
        chunks: "all",
      },
    },
  },
  // </1번 솔루션>

  pluginOptions: {
    vuetify: {
			// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
		}
  }
})
