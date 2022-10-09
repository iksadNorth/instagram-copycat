<template>
  <v-app>
    <v-layout>
      <v-app-bar color="header bg-black">
        <Header />
      </v-app-bar>

      <v-main>
        <router-view />
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
    max-height: 50px;
  }
</style>
