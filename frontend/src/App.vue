<template>
  <v-app>
    <v-layout>
      <v-app-bar color="header py-2">
        <Header />
      </v-app-bar>

      <v-main>
        <router-view class="my-5" />
      </v-main>
    </v-layout>

    <v-footer class="footer bg-black">
      <Footer />
    </v-footer>
  </v-app>
</template>

<script>
import Header from './components/com_header.vue'
import Footer from './components/com_footer.vue'

export default {
  name: 'App',

  components: {
    Header, Footer
  },

  data: () => ({
    //
  }),
}
</script>

<style>
  .footer {
    height: 8vh;
    max-height: 8vh;
  }
  .v-main {
    margin: auto;
    max-width: 900px;
  }
</style>
