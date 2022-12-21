<template>
    <v-app>
        <v-app-bar fixed app dark max-height="70px" clipped-left class="indigo" >
            <v-img class="mx-2" src="http://localhost:1337/uploads/7b46b1d08dba4532bfdeaaedbe943252_1_b27d38c443.png" max-height="40" max-width="40" contain></v-img>
            <div v-if="current_route != '/home' && current_route != '/eportfolio'">
                <v-toolbar-title v-if="typeof user === 'undefined'" class="text-no-wrap text-h5" style="cursor: pointer" @click="$router.push('/home')" > 
                    <span> Minhopass </span>
                </v-toolbar-title>
                <v-toolbar-title v-else class="text-no-wrap text-h5" style="cursor: pointer" @click="$router.push('/eportfolio')" > 
                    <span> Minhopass </span>
                </v-toolbar-title>
            </div>
            <div v-else>
                <v-toolbar-title class="text-no-wrap text-h5" > 
                    <span> Minhopass </span>
                </v-toolbar-title>
            </div>
            <v-spacer></v-spacer>
            <v-btn v-if="current_route != '/home' && current_route != '/eportfolio' && typeof user === 'undefined'" to="/home" text class="indigo">
                <span> Home </span>
            </v-btn>
            <v-btn v-else-if="current_route != '/home' && current_route != '/eportfolio'" to="/eportfolio" text class="indigo">
                <span> Home </span>
            </v-btn>
            <v-btn v-else text class="indigo">
                <span> Home </span>
            </v-btn>
            <v-btn to="/sobre_nos" text class="indigo">
                <span> Sobre Nós </span>
            </v-btn>
            <v-btn to="/ajuda" text class="indigo">
                <span> Ajuda </span>
            </v-btn>

            <div v-if="current_route != '/login' && current_route != '/home' && current_route != '/registar' && (user)">
                <v-btn text @click="handle_logout" class="indigo">
                    <span> Logout</span>  
                </v-btn>
            </div>
        </v-app-bar>

        <v-main>
            <div v-if="alert.message" :class="`alert ${alert.type}`">{{alert.message}}</div>
            <router-view></router-view>
        </v-main>
        <v-footer app dark padless fixed height="90px">
            <v-card text tile class="indigo white--text text-center" width="100%">

            <v-card-text  class="white--text my-n1">
                Gestor de CV's e ePortefolios desenvolvido por alunos de Engenharia Informática(MIEI), Universidade do Minho, no âmbito da unidade curricular Laboratório de Informática(LEI).
            </v-card-text>
            <v-divider class="my-n1"></v-divider>

            <v-card-text class="white--text mt-n3">
                {{ new Date().getFullYear() }}  &ensp; —  &ensp; <strong>Gestor Minhopass</strong> &ensp; —
                <v-btn style="text-decoration: none;" href="https://github.com/Sentinela24/LEI" target="_blank" class="white--text" icon >
                    <v-icon size="24px"> {{ icons[0] }} </v-icon>
                </v-btn>
            </v-card-text>


            </v-card>

        </v-footer>
    </v-app>  
</template>

<script>
import { VApp, VToolbar, VSpacer, VBtn, VAppBar, VToolbarTitle, VMain , VFooter, VDivider, VCard, VCardText, VImg } from 'vuetify/lib'

export default {
    name: 'app',
    computed: {
        alert () {
            return this.$store.state.alert
        },
        user(){
            return this.$store.state.authentication.user
        },
        loggedIn () {
            return this.$store.state.authentication.status;
        },
    },

    data () {
        return {
            current_route : this.$route.path,
            icons: ['mdi-git','mdi-twitter','mdi-linkedin','mdi-instagram'
      ],
        }
    },

    watch:{
        $route (to, from){
            // clear alert on location change
            this.$store.dispatch('alert/clear');
            this.current_route = to.path
        }

    },

    methods:{
        handle_logout(){
            // reset login status
            this.$store.dispatch('authentication/logout')
                .then(this.$router.push("/home"));
        },
    },

    components: {
        VApp,
        VToolbarTitle,  
        VToolbar, 
        VSpacer, 
        VBtn,
        VAppBar,
        VMain,
        VFooter,
        VDivider, 
        VCard,
        VCardText,
        VImg
    }
};
</script>
<style>
.v-toolbar-title {
  font-family: 'Mansalva', cursive;
}
</style>