<template>
    <div>
        <NavBar/>
        <v-container v-if="(typeof user.params !== 'undefined')" fluid>
          <v-card flat class="mx-auto my-3" max-width="920">
            <v-toolbar flat class="indigo--text text-h1 display-3" >
              <v-toolbar-title>
                <h2>A minha conta</h2>
              </v-toolbar-title>
            </v-toolbar>
            <v-divider class="my-1 mx-2"></v-divider>
            <v-card-text class="black--text mt-n2">
              <h6>Consulta e edita os dados da tua conta sempre que quiseres.</h6>
            </v-card-text>
            <v-card class="indigo lighten-5 mx-auto my-2" max-width="830">
                <v-toolbar class="my-3 indigo white--text text-h1 display-3" dark>
                  <v-toolbar-title>
                      <h2>Dados</h2>
                  </v-toolbar-title>

                  <v-spacer></v-spacer>

                  <v-menu offset-y>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn color="indigo" dark text v-bind="attrs" v-on="on">
                        <v-icon color="white"> mdi-dots-vertical </v-icon> 
                      </v-btn>
                    </template>
                    <v-list>
                      <v-list-item  v-for="(item, index) in items" :key="index" @click="selectSection(index)">
                        <v-list-item-title :to= "item.path" >{{ item.title }}</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </v-menu>
                  <v-progress-linear :active="loading" absolute color="green" bottom :indeterminate="loading"></v-progress-linear>
                </v-toolbar>

                <v-list flat class="indigo lighten-5" v-for="(a,i) in account" :key="i">
                    <v-text-field class="ml-3" v-model="user.params[a.type]" :prepend-icon="a.icon" :label="a.type" :name="a.type"></v-text-field>
                 </v-list>
                <v-btn plain class=" ml-1 mb-1" color="indigo" text @click="handleSubmit"><v-icon size="20">mdi-pencil</v-icon>&nbsp;<span class="font-weight-bold">Editar</span></v-btn>
              </v-card>

              <v-card v-if="edit_pass" class="indigo lighten-5 mx-auto my-10" max-width="830">
                <v-toolbar class="my-3 indigo white--text text-h1 display-3" dark>
                  <v-toolbar-title>
                      <h2>Alterar Password</h2>
                  </v-toolbar-title>
                  <v-progress-linear :active="loading" absolute color="green" bottom :indeterminate="loading"></v-progress-linear>
                </v-toolbar>
                <v-form ref="form" v-model="valid" lazy-validation class="mx-2 mb-1">
                  <v-text-field type="password" v-model="user.params.eportfolios[0].password" prepend-icon="mdi-lock"  :rules="passwordRules" label="Nova password" name="password" required></v-text-field>  
                  <v-btn plain class=" ml-1 mb-1" color="indigo" text @click="validate"><v-icon size="20">mdi-pencil</v-icon>&nbsp;<span class="font-weight-bold">Alterar</span></v-btn>
                </v-form>
              </v-card>
            </v-card>
        </v-container>
    </div>
</template>

<script>
import { VContainer, VRow, VCol, VLayout, VImg, VCard, VCardText, VCardTitle, VCardSubtitle, VDivider, VCardActions, VProgressLinear, VList, VListItem, VListItemTitle,  VListItemAction, VListItemContent, VAppBarNavIcon, VToolbarTitle, VMenu, VCheckbox, VListItemAvatar, VTextField, VListItemGroup, VForm } from 'vuetify/lib'
import NavBar from '../components/NavBar'
import html2pdf from 'html2pdf.js'

export default {
    data: () => ({
      loading: false,
      selection: 1,
      model: null,
      account: [
          { type: 'username', icon: 'mdi-account' },
          { type: 'email', icon: 'mdi-email'}
      ],
      items: [
        { title: 'Alterar Password' },
        { title: 'Apagar conta' },
      ],
      username: '',
      email: '',
      education: false,
      work_experience: false,
      address: false,
      personal_skills: false,
      personal_info: true,
      strapi_url: 'http://localhost:1337',
      edit_pass: false,
      passwordRules: [
        v => (v && v.length >= 6) || 'Password tem que ter no mínimo 6 caracteres',
      ],
      valid: true
    }),

    components: {
        VContainer,
        VRow,
        VCol,
        VLayout,
        NavBar,
        VImg,
        VCard,
        VCardText,
        VCardTitle,
        VCardSubtitle,
        VDivider,
        VCardActions,
        VProgressLinear,
        VList,
        VListItem,
        VListItemTitle,
        VListItemAction,
        VListItemContent,
        VToolbarTitle,
        VAppBarNavIcon,
        VMenu,
        VCheckbox,
        VListItemAvatar,
        VTextField,
        VListItemGroup,
        VForm
    },

    computed: {
        user () {
          if(typeof this.$store.state.users.user.params !== 'undefined' && (this.username == '' || this.email == '')){
            this.username = this.$store.state.users.user.params.username
            this.email = this.$store.state.users.user.params.email
          }
          return this.$store.state.users.user;
        },
        users () {
            return this.$store.state.users.all;
        },
    },
    
    methods: {
        criar () {
            this.loading = true

            setTimeout(() => (this.loading = false), 2000)
        },


        exportToPDF () {
            var element = document.getElementById('document');
            var opt = {
                margin: 1,
                filename:'eportefolio.pdf',
                image: { type: 'jpeg', quality: 0.95 },
                html2canvas:  { scale: 2 },
                jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
            };
            // New Promise-based usage:
            //html2pdf().set(opt).from(element).save();
            // Old monolithic-style usage:
            html2pdf(element, opt);
        },

        async selectSection(index) {
            if (this.items[index].title == 'Alterar Password')
                this.edit_pass = true
            else
                await this.$store.dispatch('users/delete_account', {user : this.user.params})
        },

        async handleSubmit() {
            if ((this.username != '' && this.username != this.user.params.username) || (this.username != '' && this.email != this.user.params.email)) {
                this.loading = true;
                console.log(this.user.params)
                await this.$store.dispatch('users/edit_account',{user : this.user.params} );
                this.loading = false;
            }
        },

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
    },

    created () {
        this.$store.dispatch('users/getAll');
    }
    
};
</script>