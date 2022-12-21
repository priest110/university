<template>
    <div>
      <NavBar/>
        <v-container fluid >
        <v-row justify="center">
        <v-col cols=3>
        <h1>Perfil</h1>
        <div class="perfil">
            <ul v-if="user.user">
                <span>Nome: {{user.user.username}}</span>
                <br/>
                <span>Email: {{user.user.email}}</span>
            </ul>
        </div>
        <h1>Eportfolio</h1>
        <div class="eportfolio">
            <ul v-if="user.user.eportfolios.length">
                <li v-for="eport in user.user.eportfolios" :key="eport.id">
                    <span>Nome: {{eport.nome}}</span>
                     <br/>
                    <span>Género: {{eport.genero}}</span>
                     <br/>
                    <span>Nacionalidade: {{eport.nacionalidade}}</span>
                     <br/>
                    <span>Email: {{eport.email}}</span>
                     <br/>
                    <span>Telemóvel: {{eport.telemovel}}</span>
                     <br/>
                    <span>Profissão: {{eport.profissao}}</span>
                     <br/>
                    <span>Data de Nascimento: {{eport.data_nasc}}</span>
                </li>
            </ul>
            <ul v-else>
                <span>Ainda não tem um Eportfolio criado.</span>
            </ul>
            <router-link to="/criar-eportfolio" class="btn btn-link">Carregar aqui para criar um Eportfolio</router-link>
        </div>

        </v-col>
        </v-row>
        </v-container>
       
    </div>
    
</template>

<script>
import { VContainer, VRow, VCol, VLayout } from 'vuetify/lib'
import NavBar from '../components/NavBar'

export default {
    components: {
        VContainer,
        VRow,
        VCol,
        VLayout,
        NavBar
    },

    computed: {
        user () {
            return this.$store.state.authentication.user;
        }
    }
    
}
</script>
