<template>
        <v-parallax height="985" src="http://localhost:1337/uploads/login_ad0be631d7.jpg"> 
            <v-row class="mt-10" justify="center">
                <v-col cols="4" sm="5">
                    <v-card class="ml-n15" color="rgb(0, 0, 0, 0)" flat>
                        <v-card-title class="indigo--text font-weight-bold text-h1 display-3"><p>Sobre nós</p></v-card-title>
                        <v-card-subtitle class="indigo--text text--lighten-1 text-h5 display-3">
                            <p>Minhopass é um conjunto de ferramentas e informações que te ajuda a gerir
                                todas as etapas da tua carreira profissional e aprendizagem.</p>
                        </v-card-subtitle>
                        <v-card-text class="black--text text-h5 display-1">
                                Estás a estudar? Queres começar a trabalhar? Procuras novos desafios? 
                                O Minhopass permite-te comunicar eficazmente as tuas competências e qualificações. 
                                O Minhopass disponibiliza gratuitamente ferramentas de apoio em linha a alunos, 
                                trabalhadores e candidatos a emprego em toda a região Minho. Antes de mais, consulta as nossas ferramentas:
                        </v-card-text>
                        <v-expansion-panels>
                            <v-expansion-panel v-for="(item, i) in items" :key="i">
                              <v-expansion-panel-header class="indigo lighten-1 text-h5" expand-icon="mdi-plus">
                                {{item.title}}
                              </v-expansion-panel-header>
                              <v-expansion-panel-content class="indigo lighten-1" >
                                <v-divider class="my-3"></v-divider>
                                {{item.text}}
                              </v-expansion-panel-content>
                            </v-expansion-panel>
                        </v-expansion-panels>
                    </v-card>   
                </v-col>
            </v-row>
        </v-parallax>
</template>

<script>
import { VContainer, VRow, VCol, VLayout, VCard, VToolbarTitle, VProgressLinear, VForm, VDivider, VTextField, VParallax, VExpansionPanels, VExpansionPanel, VExpansionPanelHeader, VExpansionPanelContent, VCardTitle, VCardSubtitle, VCardText } from 'vuetify/lib'

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
        VParallax,
        VExpansionPanels, 
        VExpansionPanel, 
        VExpansionPanelHeader, 
        VExpansionPanelContent, 
        VCardTitle, 
        VCardSubtitle, 
        VCardText
    },

    data: () => ({
      items: [
        { title: 'Perfil Minhopass', text:'O teu perfil Minhopass ajuda-te a descrever as tuas competências, a gerir as tuas candidaturas e a redigir CVs.'},
        { title: 'Editor de CV Minhopass', text:'Com o editor de CV Minhopass podes criar e atualizar o teu CV, guardar o mesmo no teu dispositivo e/ou na biblioteca Minhopass.' },
        { title: 'Biblioteca Minhopass', text:'Na tua biblioteca consegues organizar todos os documentos que precisas, desde CVs a certificados e diplomas.' },
        { title: 'Feed de atividade', text:'Podes aceder ao registo de toda a tua atividade a qualquer momento.' },
      ],
    })

};
</script>