<template>
    <v-navigation-drawer v-if="(typeof user.params !== 'undefined')"  app clipped class="indigo lighten-5" v-model="drawer" :mini-variant.sync="mini" permanent fixed>
      <v-list-item class="px-2 mb-n2">
        <v-list-item-avatar v-if="user.params.eportfolios.length && user.params.eportfolios[0].avatar != null" >
            <v-img :src="image"></v-img>
        </v-list-item-avatar>

        <v-list-item-avatar v-else>          
            <v-img src="http://localhost:1337/uploads/user_icon_aaaea38ffd.png"></v-img>
        </v-list-item-avatar>

        <v-list-item-title class="indigo--text mt-3" v-if="user.params.eportfolios.length">
            <h4>{{ user.params.eportfolios[0].nome }}</h4>
        </v-list-item-title>

        <v-list-item-title class="indigo--text mt-3" v-else>
            <h4>Utilizador</h4>
        </v-list-item-title>

        <v-btn class="indigo--text" icon @click.stop="mini = !mini">
          <v-icon class="indigo--text" size="30">mdi-chevron-left</v-icon>
        </v-btn>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense>
        <v-list-item v-for="item in items" :key="item.title" link>
          <v-list-item-icon class="mr-5">
            <v-icon class="indigo--text" size="30">{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content >
            <v-list-item-title><router-link :to="item.to" tag="li"><h5>{{ item.title }}</h5></router-link></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
</template>


<script>
import { VList, VIcon, VListItem, VListItemTitle, VCard, VNavigationDrawer, VListItemAvatar, VImg, VDivider, VListItemContent, VListItemIcon} from 'vuetify/lib'

export default {
    components: {
        VList,
        VIcon,
        VListItem,
        VListItemTitle,
        VCard, 
        VNavigationDrawer,
        VListItemAvatar, 
        VImg,
        VDivider, 
        VListItemContent, 
        VListItemIcon
    },
    
    computed: {
        user () {
            return this.$store.state.users.user;
        },

        image : function () {
            return this.user.params.eportfolios[0].avatar.url.split('/uploads/').join(`${this.strapi_url}/uploads/`);
            //return 'http://localhost:1337/uploads/5859205_0_d076e0d6a8.jpg'
        }
    },

    data () {
      return {
        drawer: true,
        items: [
          { title: 'Biblioteca', icon: 'mdi-book-open-variant', to: '/biblioteca'},
          { title: 'Conta', icon: 'mdi-settings', to:'/conta' },
          { title: 'ePortefolio', icon: 'mdi-clipboard-account', to: '/eportfolio'},
          { title: 'Feed', icon: 'mdi-bell', to:'/feed' },
        ],
        mini: true,
        strapi_url: 'http://localhost:1337'
      }
    }
}
</script>   
<style scoped>
  v-list li:hover,
    nav li.router-link-active,
    nav li.router-link-exact-active {
      color:rgb(63, 81, 181);
      cursor: pointer;
  }
</style>