<template>
        <v-parallax height="985" src="http://localhost:1337/uploads/login_ad0be631d7.jpg"> 
            <v-row class="mt-10" justify="center">
                <v-col cols="4" sm="5">
                    <v-card class="ml-n15" color="rgb(0, 0, 0, 0)" flat>
                        <v-card-title class="indigo--text font-weight-bold text-h1 display-3"><p>FAQ</p></v-card-title>
                        <v-card-subtitle class="indigo--text text--lighten-1 text-h5 display-3">
                            <p>Tens dúvidas relativamente ao funcionamento do gestor Minhopass? Não te preocupes, não és o primeiro.</p>
                        </v-card-subtitle>
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
        { title: 'O que é o Minhopass?', text:'O Minhopass é um conjunto de ferramentas e informações em linha que te ajuda a gerir todas as etapas da tua aprendizagem e carreira profissional. As ferramentas e as informações que encontras no Minhopass ajudam-te a divulgar as tuas competências, qualificações e experiências de forma clara e coerente em toda a região Minho. O Minhopass é uma plataforma desenvolvida por alunos de Engenharia Informática da Universidade do Minho, Braga.'},
        { title: 'Porque devo utilizar o MinhoPass?', text:'O Minhopass disponibiliza ferramentas que te ajudarão a registar as tuas competências, qualificações e experiências e, consequentemente, a procurar novos empregos ou cursos. Podes registar-te para criares o teu perfil e tirar o melhor proveito de todas as ferramentas e informações disponíveis no Minhopass.' },
        { title: 'Quanto custa o registo no Minhopass?', text:'O Minhopass é um conjunto gratuito de ferramentas e informações em linha. Não tem custos ocultos nem encargos adicionais. Chamamos a tua atenção para o facto de não poderes comprar um CV ou qualquer outro serviço do Minhopass. Todos os serviços disponibilizados pelo Minhopass são gratuitos e nunca terás de fornecer quaisquer informações bancárias ou fazer pagamentos.' },
        { title: 'Posso personalizar o meu CV Minhopass?', text:'Se quiseres, com o editor de CV Minhopass, podes acrescentar, suprimir e alterar secções do teu CV. Secções como «Experiência profissional» e «Educação e formação» podem ser deslocadas, alteradas ou suprimidas. Também podes criar secções personalizadas, com títulos teus, em função das tuas necessidades e experiências.' },
         { title: 'Onde posso guardar o meu CV Minhopass?', text:'Podes guardar o teu CV Minhopass das seguintes formas: na tua biblioteca Minhopass ou descarregando-o no teu dispositivo local (computador ou dispositivo móvel)' },
          { title: 'Em que formato posso guardar o meu CV Minhopass?', text:'Atualmente, podes guardar o teu CV Minhopass numa versão Minhopass em formato PDF..' },
      ],
    })

};
</script>