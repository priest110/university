<template>
    <div>
        <v-container fluid  >
            <v-card
            :loading="loading"
            class="mx-auto my-10 indigo lighten-5"
            max-width="374">
                <template slot="progress">
                    <v-progress-linear
                        color="deep-purple"
                        height="10"
                        indeterminate>
                    </v-progress-linear>
                </template>
                <v-img
                    height="250"
                    src="http://localhost:1337/uploads/career_7914a3a967.jpg">
                </v-img>
                <v-card-title class="mb-n3 indigo--text"><h4>Bem vindo {{user.params.username}}</h4></v-card-title>
                <v-card-subtitle class="ml-0 my-n1 black--text"><h5>Ainda não tem um Eportfolio criado.</h5></v-card-subtitle>
                <v-divider class="mx-0 my-n2"></v-divider>
                <v-card-actions>
                    <v-btn
                        to="/criar-eportfolio"
                        color="indigo"
                        text
                        @click="criar">
                        Criar
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </div>
</template>

<script>
import { VContainer, VRow, VCol, VLayout, VImg, VCard, VCardText, VCardTitle, VCardSubtitle, VDivider, VRating, VCardActions, VChip, VChipGroup, VProgressLinear } from 'vuetify/lib'

export default {
    data: () => ({
      loading: false,
      selection: 1,
    }),

    methods: {
      criar () {
        this.loading = true

        setTimeout(() => (this.loading = false), 2000)
      },
    },
    components: {
        VContainer,
        VRow,
        VCol,
        VLayout,
        VImg,
        VCard, 
        VCardText,
        VCardTitle,
        VDivider,
        VRating,
        VCardActions, 
        VChip,
        VChipGroup,
        VProgressLinear,
        VCardSubtitle
    },

    computed: {
        user () {
            return this.$store.state.users.user;
        }
    }
    
};
</script>