<template>
  <div>
    <NavBar/>
    <v-container v-if="(typeof user.params !== 'undefined')" fluid>
      <v-card flat class="mx-auto my-3" max-width="1180px">
        <v-toolbar flat class="indigo--text text-h1 display-3" >
          <v-toolbar-title>
              <h2>Feed de operações</h2>
          </v-toolbar-title>
        </v-toolbar>
        <v-divider class="my-1 mx-2"></v-divider>
        <v-card-text class="black--text mt-n2">
          <h6>Consulta o registo da tua atividade no gestor Minhopass.</h6>
        </v-card-text>
        <v-card flat class="white mx-auto mb-4 py-7" max-width="1130px"> 
          <v-data-table :headers="headers" :items="user.params.feed" :page.sync="page" :items-per-page="itemsPerPage" hide-default-footer class="indigo lighten-5 elevation-1 mt-n5" @page-count="pageCount = $event" >

            <template v-slot:[`item.tipo`]="{ item }">
              <v-chip :color="getColor(item.tipo)" dark> 
                {{ item.tipo }}
              </v-chip>
            </template>
            <template v-slot:[`item.data`]="{ item }">
              <v-list-item class="ml-n4"> 
                {{ new Date(item.data).getDate() + '/' + (new Date(item.data).getMonth()+1) + '/' + new Date(item.data).getFullYear()   + ' ' + new Date(item.data).getHours() + ':' + new Date(item.data).getMinutes() }}
              </v-list-item>
            </template>
            <template v-slot:[`item.actions`]="{ item }">
              <v-icon small color="indigo" @click="delete_item(item)">
                mdi-delete
              </v-icon>
            </template>
          </v-data-table>
          <div class="text-center pt-2">
            <v-pagination v-model="page" :length="pageCount"></v-pagination>
          </div>
        </v-card>
      </v-card>

      <v-dialog v-model="dialogDelete" max-width="500px">
        <v-card>
          <v-card-title class="text-h5">Tem a certeza que pretende eliminar esta operação do registo?</v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="close_delete">Cancelar</v-btn>
            <v-btn color="blue darken-1" text @click="delete_item_confirm">Sim</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

    </v-container>
  </div>
</template>

<script>
import { VContainer, VRow, VCol, VLayout, VImg, VCard, VCardText, VCardTitle, VCardSubtitle, VDivider, VCardActions, VProgressLinear, VList, VListItem, VListItemTitle,  VListItemAction, VListItemContent, VAppBarNavIcon, VToolbarTitle, VMenu, VDataTable, VPagination, VTextField, VChip, VDialog } from 'vuetify/lib'
import NavBar from '../components/NavBar'

export default {
    data: () => ({
      loading: false,
      selection: 1,
      model: null,
      headers: [
          {
            text: 'Operação',
            align: 'start',
            value: 'operacao',
            class: 'indigo--text title'
          },
          { text: 'Quando', value: 'data', class: 'indigo--text title'},
          { text: 'Onde', value: 'tipo', width: '210px', class: 'indigo--text title' },
          { text: 'Nota', value: 'nota', class: 'indigo--text title' },
          { text: 'Ações', value: 'actions', sortable: false, class: 'indigo--text title' },
        ],
      items: [
        { title: 'Editar Conta', path: '/editar-perfil', click() { this.$router.push('/editar-perfil')} },
        { title: 'Editar ePortefolio', path: '/editar-eportefolio', click() { this.$router.push('/editar-eportefolio')} },
        { title: 'Apagar ePortefolio', click() {} },
      ],
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      dialogDelete: false,
      deleteIndex: -1,
      operations: []
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
        VPagination,
        VDataTable,
        VTextField,
        VChip,
        VDialog
    },

    computed: {
        user () {
          console.log("User: "+ JSON.stringify(this.$store.state.users.user));
          this.operations = this.$store.state.users.user.params.feed
          console.log("antes" + this.operations)
          return this.$store.state.users.user;
        },
        date() {
            //console.log(this.user.toISOString().substr(0, 10));
            //return this.user.toISOString().substr(0, 10);
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

      getColor (type) {
        if (type == 'Conta') return 'red'
        else if (type == 'ePortefolio') return 'orange'
        else return 'green'
      },

      delete_item (item) {
        this.deleteIndex = this.operations.indexOf(item)
        this.dialogDelete = true  
      },

      delete_item_confirm () {
        this.operations.splice(this.deleteIndex, 1)
        this.$store.dispatch('users/delete_operation', { feed: this.operations, user: this.user.params} )
        this.close_delete()
      },

      close_delete () {
        this.dialogDelete = false
        this.deleteIndex = -1
      },

    },

    created () {
        this.$store.dispatch('users/getAll');
    }
    
};
</script>