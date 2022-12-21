<template>
    <div>
        <v-container fluid >
          <v-row justify="center">
            <v-col cols=4>
              <v-card :loading="loading" class="indigo lighten-5 mx-auto my-10" max-width="874">
                <template slot="progress">
                    <v-progress-linear color="white" height="10" indeterminate></v-progress-linear>
                </template>
                <v-toolbar class="my-3 indigo white--text text-h1 display-3" dark>
                  <v-toolbar-title>
                      <h2>Editar Conta</h2>
                  </v-toolbar-title>
                </v-toolbar>
              <v-form 
               ref="form" v-model="valid" lazy-validation class="mx-2 mb-1">
                <v-text-field type="password" v-model="user.params.eportfolios[0].password" prepend-icon="mdi-account"  :rules="passwordRules" label="Password" name="password" required></v-text-field>
                <v-btn :disabled="!valid" color="success" class="mr-4 mb-3" @click="validate">
                  Editar
                </v-btn>
                <v-btn color="error" class="mr-4 mb-3" @click="reset">
                  Reset
                </v-btn>
              </v-form>
              </v-card>
        </v-col>
        </v-row>
        </v-container>
    </div>
</template>

<script>
import { mapState } from 'vuex'
import { VContainer, VRow, VCol, VLayout, VForm, VTextField, VSelect, VCheckbox, VFileInput, VCard, VCardTitle, VRadio, VRadioGroup, VDivider, VToolbarTitle, VMenu, VDatePicker} from 'vuetify/lib'
import axios from 'axios';

export default {
     components: {
        VContainer,
        VRow,
        VCol,
        VLayout, 
        VForm, 
        VTextField, 
        VSelect, 
        VCheckbox,
        VFileInput,
        VCard,
        VCardTitle,
        VRadio,
        VRadioGroup,
        VToolbarTitle, 
        VDivider,
        VMenu,
        VDatePicker
    },

    data () {
        return {
            loading: false,
            submitted: false,
            valid: true,
            fromDateMenu: false,
            fromDateVal: null,
            passwordRules: [
              v => !!v || 'Campo obrigatório',
              v => (v && v.length >= 6) || 'Password tem que ter no mínimo 6 caracteres',
            ],
        }
    },


    created() {

    },

    computed: { 
        ...mapState('authentication', ['status']),

        user () {
            return this.$store.state.users.user;
        }
    },
    
    methods: {

        validate () {
          console.log(this.$refs.form)
            if(this.$refs.form.validate()){
                const formElement = this.$refs.form.$el
                const formData = new FormData();
                const formElements = formElement.elements;
                const data = {};

                for (let i = 0; i < formElements.length; i++) {
                    const currentElement = formElements[i];
                    console.log(currentElement)
                    if (!['submit', 'file', 'button'].includes(currentElement.type)) {
                        if('radio'.includes(currentElement.type)){
                            if(currentElement.checked)
                                data[currentElement.name] = currentElement.value;
                        }
                        else{
                            data[currentElement.name] = currentElement.value;
                        }

                    } 
                }
                formData.append('data', JSON.stringify(data));
                console.log("FormData: " + JSON.stringify(data))
                this.$store.dispatch('users/editPerfil', { password: data, user : this.$store.state.users.user.params })
            }
        },
        reset () {
            this.$refs.form.reset()
        },
        resetValidation () {
            this.$refs.form.resetValidation()
        }
    }
};
</script>