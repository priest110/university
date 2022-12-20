<template>
        <v-parallax height="985" src="http://localhost:1337/uploads/login_ad0be631d7.jpg"> 
            <v-row align="center" justify="center">
                <v-col cols="12" sm="4">
                    <v-card class="mx-auto my-10" max-width="874" color="rgb(195, 195, 195)">
                        <v-toolbar class="indigo my-3  text-h1 display-3" dark>
                            <v-toolbar-title><h1>Login</h1></v-toolbar-title>
                            <v-progress-linear :active="loading" absolute color="green" bottom :indeterminate="loading"></v-progress-linear>
                        </v-toolbar>
                        <v-form ref="form" v-model="valid" lazy-validation class="mx-2 mb-1">
                            <v-text-field v-model="identifier" prepend-icon="mdi-account" :counter="30" :rules="nameRules" label="Utilizador" name="utilizador" required></v-text-field>
                            <v-text-field v-model="password" type="password" prepend-icon="mdi-lock"  :rules="passRules" label="Password" name="password" required></v-text-field>
                            <v-btn  :disabled="!valid" color="indigo" dark class="mr-4 mb-3" @click="handleSubmit">Login</v-btn>
                            <v-btn :disabled="loggingIn" color="indigo" dark class="mr-4 mb-3" to="/home" style="text-decoration: none;">Cancelar</v-btn>
                        </v-form>
                    </v-card>   
                </v-col>
            </v-row>
        </v-parallax>
</template>

<script>
import { VContainer, VRow, VCol, VLayout, VCard, VToolbarTitle, VProgressLinear, VForm, VDivider, VTextField, VParallax } from 'vuetify/lib'

export default {
    components : {
        VContainer,
        VRow,
        VCol,
        VLayout,
        VCard,
        VToolbarTitle,
        VProgressLinear,
        VForm,
        VDivider,
        VTextField,
        VParallax
    },

    data () {
        return {
            identifier: '',
            password: '',
            loading: false,
            nameRules: [
              v => !!v || 'Campo obrigatório',
              v => (v && v.length <= 30) || 'Nome tem que ter no máximo 30 caracteres',
            ],
            passRules: [
              v => !!v || 'Campo obrigatório',
            ],
            valid: true
        }
    },
    computed: {
        loggingIn () {
            return this.$store.state.authentication.status.loggingIn;
        },
    },

    methods: {
        async handleSubmit() {
            this.loading = true;
            const { identifier, password } = this;
            if (identifier && password) {
                await this.$store.dispatch('authentication/login', { identifier, password })
                await this.$store.dispatch('users/getUser', {id : this.$store.state.authentication.user.user.id})
                if(this.$store.state.users.user.params.eportfolios.length > 0){
                    await this.$store.dispatch('users/get_eport', { id: this.$store.state.users.user.params.eportfolios[0].id })
                    this.$router.push('/eportfolio').catch((erro) => {console.log(erro)});
                }
                else
                    this.$router.push('/criar').catch((erro) => {console.log(erro)});
            }
        }
    }
};
</script>