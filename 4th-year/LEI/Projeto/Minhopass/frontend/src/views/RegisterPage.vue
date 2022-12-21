<template>
    <v-parallax height="985" src="http://localhost:1337/uploads/login_ad0be631d7.jpg"> 
        <v-row align="center" justify="center">
            <v-col cols="12" sm="4">
                <v-card class="mx-auto my-10" max-width="874" color="rgb(195, 195, 195)">
                    <v-toolbar class="my-3 indigo text-h1 display-3" dark>
                        <v-toolbar-title><h1>Registar</h1></v-toolbar-title>
                        <v-progress-linear :active="loading" absolute color="green" bottom :indeterminate="loading"></v-progress-linear>
                    </v-toolbar>
                    <v-form ref="form" v-model="valid" lazy-validation class="mx-2 mb-1">
                        <v-text-field v-model="user.username" prepend-icon="mdi-account" :counter="30" :rules="nameRules" label="Utilizador" name="utilizador" required></v-text-field>
                        <v-text-field v-model="user.email" prepend-icon="mdi-email"  :rules="emailRules" label="E-mail" name="email" required></v-text-field>
                        <v-text-field v-model="user.password" type="password" prepend-icon="mdi-lock"  :rules="passRules" label="Password" name="password" required></v-text-field>
                        <v-btn :disabled="!valid" color="indigo" class="mr-4 mb-3" @click="handleSubmit" dark>Registar</v-btn>
                        <v-btn :disabled="loggingIn" color="indigo" class="mr-4 mb-3" to="/home" dark style="text-decoration: none;">Cancelar</v-btn>
                    </v-form>
                </v-card>
            </v-col>
        </v-row>
    </v-parallax>
</template>

<script>
import { mapState, mapActions } from 'vuex'
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
            user: {
                email: '',
                username: '',
                password: '', 
            },
            valid: true,
            nameRules: [
              v => !!v || 'Campo obrigatório',
              v => (v && v.length <= 30) || 'Nome tem que ter no máximo 30 caracteres',
            ],
            emailRules: [
              v => !!v || 'Campo obrigatório',
              v => /.+@.+\..+/.test(v) || 'E-mail tem que ser válido',
            ],
            passRules: [
              v => !!v || 'Campo obrigatório',
            ],
            loading: false
        }
    },
    computed: {
        loggingIn () {
            return this.$store.state.authentication.status.loggingIn;
        },
    },
    methods: {
        ...mapActions('authentication', ['register']),
        handleSubmit(e) {
            this.loading = true;
            this.$validator.validate().then(valid => {
                if (valid) {
                    console.log(this.user);
                    this.register(this.user);
                }
            });
        }
    }
};
</script>