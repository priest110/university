<template>
    <div>
        <NavBar/>
        <v-container fluid>
          <v-card flat class="mx-auto my-3" max-width="920">
            <v-toolbar flat class="indigo--text text-h1 display-3" >
              <v-toolbar-title>
                <h2>O meu ePortefolio</h2>
              </v-toolbar-title>
            </v-toolbar>
            <v-divider class="my-1 mx-2"></v-divider>
            <v-card-text class="black--text mt-n2">
              <h6>Mantém o teu ePortefolio atualizado, adiciona novas secções e informação a qualquer altura.</h6>
            </v-card-text>
            <v-card :loading="loading" class="indigo lighten-5 mx-auto my-2" max-width="830">       
              <v-container fluid id="document"> 
                <v-list class="indigo lighten-5" >
                  <v-row> 
                    <v-col >
                      <v-list-item>
                        <v-list-item-avatar size="200">
                          <v-img class="mx-auto" :src="user.params.eportfolios[0].avatar.url.split('/uploads/').join(`${this.strapi_url}/uploads/`)"></v-img>
                        </v-list-item-avatar>
                      </v-list-item>
                    </v-col>
                    <v-col cols="7">
                      <v-list-item >
                        <v-list-item-content>
                          <v-list-item-title class="indigo--text"><h4>Dados pessoais</h4></v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>

                      <v-divider class="mr-16 mt-n4"></v-divider>

                      <v-list-item class="my-n4">
                        <v-list-item-content>
                          <v-list-item-title>
                            <span class="font-weight-light" style="display:inline;">Nome:</span> 
                            <span class="font-weight-bold">{{user.params.eportfolios[0].nome.substr(0, 30)}}</span>
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item> 
                      
                      <v-list-item class="my-n4" v-if="(typeof user.params.eportfolios[0].nacionalidade !== 'undefined')">
                        <v-list-item-content>
                          <v-list-item-title>
                            <span class="font-weight-light" style="display:inline;">País de origem:</span> 
                            <span class="font-weight-bold">{{user.params.eportfolios[0].nacionalidade}}</span>
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item> 
                      
                      <v-list-item class="my-n4" v-if="(typeof user.params.eportfolios[0].genero !== 'undefined')">
                        <v-list-item-content>
                          <v-list-item-title>
                            <span class="font-weight-light" style="display:inline;">Género:</span> 
                            <span class="font-weight-bold">{{user.params.eportfolios[0].genero}}</span>
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item> 
                      
                      <v-list-item class="my-n4">
                        <v-list-item-content>
                          <v-list-item-title>
                            <span class="font-weight-light" style="display:inline;">Data de nascimento:</span> 
                            <span class="font-weight-bold">{{user.params.eportfolios[0].data_nasc.substr(0,10)}}</span>
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item> 
                      
                      <v-list-item class="my-n4" v-if="(typeof user.params.eportfolios[0].profissao !== 'undefined')">
                        <v-list-item-content>
                          <v-list-item-title>
                            <span class="font-weight-light" style="display:inline;">Profissão:</span> 
                            <span class="font-weight-bold">{{user.params.eportfolios[0].profissao}}</span>
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item> 

                      <v-list-item class="mb-n4 mt-4">
                        <v-list-item-content>
                          <v-list-item-title class="indigo--text"><h4>Contacto</h4></v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>  

                      <v-divider class="mr-16 mt-n4"></v-divider> 

                      <v-list-item class="my-n4">
                        <v-list-item-action>
                          <v-icon color="indigo">mdi-email</v-icon>
                        </v-list-item-action>
                        <v-list-item-title class="ml-n6">
                          <span class="font-weight-light" style="display:inline;">Email:</span> 
                          <span class="font-weight-bold">{{user.params.eportfolios[0].email}}</span>
                        </v-list-item-title>
                      </v-list-item>
                      
                      <v-list-item class="my-n4">
                        <v-list-item-action>
                          <v-icon color="indigo">mdi-phone</v-icon>
                        </v-list-item-action>
                        <v-list-item-title class="ml-n6">
                          <span class="font-weight-light" style="display:inline;">Telemóvel:</span> 
                          <span class="font-weight-bold">{{user.params.eportfolios[0].telemovel}}</span>
                        </v-list-item-title>
                      </v-list-item> 

                      
                      <v-list color="indigo lighten-5" v-if="(typeof eport.params.endereco !== 'undefined')">
                        <v-list-item class="mb-n4 mt-4">
                          <v-list-item-content>
                            <v-list-item-title class="indigo--text"><h4>Endereço</h4></v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>  

                        <v-divider class="mr-16 mt-n4"></v-divider> 

                        <v-list-item class="my-n4" v-if="(typeof eport.params.endereco.morada !== 'undefined')">
                          <v-list-item-action>
                            <v-icon color="indigo">mdi-home</v-icon>
                          </v-list-item-action>
                          <v-list-item-title class="ml-n6">
                            <span class="font-weight-light" style="display:inline;">Morada:</span> 
                            <span class="font-weight-bold">{{eport.params.endereco.morada}}</span>
                          </v-list-item-title>
                        </v-list-item>

                        <v-list-item class="my-n4" v-if="(typeof eport.params.endereco.cod_post !== 'undefined')">
                          <v-list-item-action>
                            <v-icon color="indigo">mdi-home-outline</v-icon>
                          </v-list-item-action>
                          <v-list-item-title class="ml-n6">
                            <span class="font-weight-light" style="display:inline;">Código postal:</span> 
                            <span class="font-weight-bold">{{eport.params.endereco.cod_post}}</span>
                          </v-list-item-title>
                        </v-list-item> 

                        <v-list-item class="my-n4" v-if="(typeof eport.params.endereco.cidade !== 'undefined')">
                          <v-list-item-action>
                            <v-icon color="indigo">mdi-city</v-icon>
                          </v-list-item-action>
                          <v-list-item-title class="ml-n6">
                            <span class="font-weight-light" style="display:inline;">Cidade:</span> 
                            <span class="font-weight-bold">{{eport.params.endereco.cidade}}</span>
                          </v-list-item-title>
                        </v-list-item>

                        <v-list-item id="dados_pessoais" class="my-n4" v-if="(typeof eport.params.endereco.pais !== 'undefined')">
                          <v-list-item-action>
                            <v-icon color="indigo">mdi-flag-outline</v-icon>
                          </v-list-item-action>
                          <v-list-item-title class="ml-n6">
                            <span class="font-weight-light" style="display:inline;">País:</span> 
                            <span class="font-weight-bold">{{eport.params.endereco.pais}}</span>
                          </v-list-item-title>
                        </v-list-item>
                      </v-list>
                    </v-col>
                    <v-col data-html2canvas-ignore="true">
                      <v-btn class="edit" icon to="/editar-eportefolio" color="indigo"><v-icon>mdi-pencil</v-icon></v-btn>
                    </v-col>
                  </v-row>
                </v-list>
                
                <div data-html2canvas-ignore="true">
                  <v-checkbox color="indigo" v-model="work_experience" label="Experiência professional"></v-checkbox>
                </div>

                <v-list id="work" v-if="work_experience"  class="indigo lighten-5" >
                  <v-card-title><h3>Experiências profissionais</h3></v-card-title>
                  <v-list v-for="(work, index) in eport.params.trabalhos" :key="index" class="indigo lighten-5">
                    <v-card class="mx-auto" color="indigo" dark rounded>
                      <v-card-title>
                        <v-icon small left class="black--text">
                          mdi-clipboard-file-outline
                        </v-icon>
                        <span class="black--text text-subtitle-1 font-weight-bold" >{{work.funcao}}</span>
                      </v-card-title>
                      <v-card-text v-if="work['em_curso'] == true" class="text-subtitle-2 font-weight-bold my-n3">
                        {{work.ent_empregadora}} | {{ new Date(work['data_int'].inicio).getDate() + '/' + (new Date(work['data_int'].inicio).getMonth()+1) + '/' + new Date(work['data_int'].inicio).getFullYear() }} - EM CURSO
                      </v-card-text>
                      <v-card-text v-else class="text-subtitle-2 font-weight-bold my-n3">
                        {{work.ent_empregadora}} | {{ new Date(work['data_int'].inicio).getDate() + '/' + (new Date(work['data_int'].inicio).getMonth()+1) + '/' + new Date(work['data_int'].inicio).getFullYear() }} - {{ new Date(work['data_int'].fim).getDate() + '/' + (new Date(work['data_int'].fim).getMonth()+1) + '/' + new Date(work['data_int'].fim).getFullYear() }}
                      </v-card-text>
                      <v-card-text class="text-h6 font-weight-bold mt-n7">
                        {{work.responsabilidades}}
                      </v-card-text>
                    </v-card>

                    <v-expansion-panels data-html2canvas-ignore="true"  class="indigo mt-n3" flat dark>
                      <v-expansion-panel class="indigo" flat  dark>
                        <v-expansion-panel-header class="indigo">
                          <v-row>
                            <v-col cols="9"></v-col>
                            <v-col class="white--text">
                              Mais informações
                            </v-col>
                          </v-row>
                        </v-expansion-panel-header>
                        <v-expansion-panel-content>
                          <v-list v-for="(elem, index_elem) in work" :key="index_elem" class="indigo">
                            <v-list-item v-if="work[index_elem] != null && work[index_elem] != '' && index_elem != 'em_curso' && index_elem != 'id' && index_elem != 'eportfolio' && index_elem != 'updatedAt' && index_elem != 'createdAt' &&  index_elem != '_id' && index_elem != 'cidade' && index_elem != 'funcao' && index_elem != 'ent_empregadora' && index_elem != 'responsabilidades' && index_elem != 'endereco' && index_elem != '__v'" class="my-n4">
                              <v-list-item-content v-if="index_elem == 'valido_ate'">
                                <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">{{ (index_elem[0].toUpperCase() + index_elem.slice(1)).replace('_', ' ') }}:</span> 
                                <span class="font-weight-bold">{{ new Date(work[index_elem].inicio).getDate() + '/' + (new Date(work[index_elem].inicio).getMonth()+1) + '/' + new Date(work[index_elem].inicio).getFullYear() }}</span>
                              </v-list-item-content>
                              <v-list-item-content v-else-if="index_elem != 'data_int'">
                                <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">{{ (index_elem[0].toUpperCase() + index_elem.slice(1)).replace('_', ' ') }}:</span> 
                                <span class="font-weight-bold">{{ work[index_elem] }}</span>
                              </v-list-item-content>
                              <v-list-item-content v-else>
                                <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">{{ (index_elem[0].toUpperCase() + index_elem.slice(1)).replace('_', ' ') }}:</span> 
                                <span v-if="work['em_curso'] == true" class="font-weight-bold">{{ new Date(work[index_elem].inicio).getDate() + '/' + (new Date(work[index_elem].inicio).getMonth()+1) + '/' + new Date(work[index_elem].inicio).getFullYear() }} - EM CURSO</span>
                                <span v-else class="font-weight-bold">{{ new Date(work[index_elem].inicio).getDate() + '/' + (new Date(work[index_elem].inicio).getMonth()+1) + '/' + new Date(work[index_elem].inicio).getFullYear() }} - {{ new Date(work[index_elem].fim).getDate() + '/' + (new Date(work[index_elem].fim).getMonth()+1) + '/' + new Date(work[index_elem].fim).getFullYear() }}</span>
                              </v-list-item-content>
                            </v-list-item>
                              <v-divider v-if="work[index_elem] != null && work[index_elem] != '' && index_elem != 'em_curso' && index_elem != 'id' && index_elem != 'eportfolio' && index_elem != 'updatedAt' && index_elem != 'createdAt' && index_elem != '_id' && index_elem != 'cidade' && index_elem != 'funcao' && index_elem != 'ent_empregadora' && index_elem != 'responsabilidades' && index_elem != 'endereco' && index_elem != '__v'" inset></v-divider>
                          </v-list>
                        </v-expansion-panel-content>
                      </v-expansion-panel>
                    </v-expansion-panels>
                  </v-list>
                </v-list>

                <div data-html2canvas-ignore="true">
                  <v-checkbox color="indigo" v-model="education" label="Educação e formação"></v-checkbox>
                </div>

                <v-list id="edu" v-if="education" class="indigo lighten-5" >
                  <v-card-title><h3>Educação e formação</h3></v-card-title>
                  <v-list v-for="(edu, index) in eport.params.educacoes" :key="index" class="indigo lighten-5">
                    <v-card  class="mx-auto" color="indigo" dark rounded>
                      <v-card-title>
                        <v-icon small left class="black--text">
                          mdi-clipboard-file-outline
                        </v-icon>
                        <span class="black--text text-subtitle-1 font-weight-bold" >{{edu.titulo}}</span>
                      </v-card-title>
                      <v-card-text v-if="edu['em_curso'] == true" class="text-subtitle-2 font-weight-bold my-n3">
                        {{edu.organizacao}} | {{ new Date(edu['data_int'].inicio).getDate() + '/' + (new Date(edu['data_int'].inicio).getMonth()+1) + '/' + new Date(edu['data_int'].inicio).getFullYear() }} - EM CURSO
                      </v-card-text>
                      <v-card-text v-else class="text-subtitle-2 font-weight-bold my-n3">
                        {{edu.organizacao}} | {{ new Date(edu['data_int'].inicio).getDate() + '/' + (new Date(edu['data_int'].inicio).getMonth()+1) + '/' + new Date(edu['data_int'].inicio).getFullYear() }} - {{ new Date(edu['data_int'].fim).getDate() + '/' + (new Date(edu['data_int'].fim).getMonth()+1) + '/' + new Date(edu['data_int'].fim).getFullYear() }}
                      </v-card-text>
                      <v-card-text class="text-h6 font-weight-bold mt-n7">
                        {{edu.descricao}}
                      </v-card-text>
                    </v-card>

                    <v-expansion-panels data-html2canvas-ignore="true"   class="indigo mt-n3" flat dark>
                      <v-expansion-panel class="indigo" flat  dark>
                        <v-expansion-panel-header class="indigo">
                          <v-row>
                            <v-col cols="9"></v-col>
                            <v-col class="white--text">
                              Mais informações
                            </v-col>
                          </v-row>
                        </v-expansion-panel-header>
                        <v-expansion-panel-content>
                          <v-list v-for="(elem, index_elem) in edu" :key="index_elem" class="indigo">
                            <v-list-item v-if="edu[index_elem] != null && edu[index_elem] != '' && index_elem != 'em_curso' && index_elem != 'id' && index_elem != 'eportfolio' && index_elem != 'updatedAt' && index_elem != '_id' && index_elem != 'area' && index_elem != 'titulo' && index_elem != 'organizacao' && index_elem != 'descricao' && index_elem != 'endereco' && index_elem != '__v' && index_elem != 'createdAt'" class="my-n4">
                              <v-list-item-content v-if="index_elem != 'data_int'">
                                <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">{{ (index_elem[0].toUpperCase() + index_elem.slice(1)).replace('_', ' ') }}:</span> 
                                <span class="font-weight-bold">{{ edu[index_elem] }}</span>
                              </v-list-item-content>
                              <v-list-item-content v-else>
                                <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">{{ (index_elem[0].toUpperCase() + index_elem.slice(1)).replace('_', ' ') }}:</span> 
                                <span v-if="edu['em_curso'] == true" class="font-weight-bold">{{ new Date(edu[index_elem].inicio).getDate() + '/' + (new Date(edu[index_elem].inicio).getMonth()+1) + '/' + new Date(edu[index_elem].inicio).getFullYear() }} - EM CURSO</span>
                                <span v-else class="font-weight-bold">{{ new Date(edu[index_elem].inicio).getDate() + '/' + (new Date(edu[index_elem].inicio).getMonth()+1) + '/' + new Date(edu[index_elem].inicio).getFullYear() }} - {{ new Date(edu[index_elem].fim).getDate() + '/' + (new Date(edu[index_elem].fim).getMonth()+1) + '/' + new Date(edu[index_elem].fim).getFullYear() }}</span>
                              </v-list-item-content>
                            </v-list-item>
                            <v-divider v-if="edu[index_elem] != null && edu[index_elem] != '' && index_elem != 'em_curso' && index_elem != 'id' && index_elem != 'eportfolio' && index_elem != 'updatedAt'  && index_elem != '_id' && index_elem != 'area' && index_elem != 'titulo' && index_elem != 'organizacao' && index_elem != 'descricao' && index_elem != 'endereco' && index_elem != '__v' && index_elem != 'createdAt'" inset></v-divider>
                          </v-list>
                        </v-expansion-panel-content>
                      </v-expansion-panel>
                    </v-expansion-panels>

                  </v-list>
                </v-list>

                <div data-html2canvas-ignore="true">
                  <v-checkbox color="indigo" v-model="personal_skills" label="Competências pessoais"></v-checkbox>
                </div>

                <v-list id="skills" v-if="personal_skills" class="indigo lighten-5" >
                  <v-card-title><h3>Competências pessoais</h3></v-card-title>
                  <v-card  class="mx-auto" color="indigo" dark rounded>
                    <v-card-title>
                      <v-icon v-if="eport.params.competencias_pessoais.carta_conducao" small left class="black--text">
                        mdi-check
                      </v-icon>
                      <v-icon v-else small left class="black--text">
                        mdi-close
                      </v-icon>
                      <span class="black--text text-subtitle-1 font-weight-bold" >Carta de condução</span>
                    </v-card-title>
                    <v-card-text class="text-h6 font-weight-bold">
                       <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">Línguas maternas:</span> 
                    </v-card-text>
                    <v-list class="indigo mt-n6" v-for="(lm, index_elem) in eport.params.competencias_pessoais['lingua_materna']" :key="index_elem">
                        <v-list-item v-if="lm.nome != ''">
                          <v-list-item-title>- {{lm.nome}}</v-list-item-title>
                        </v-list-item>
                    </v-list>
                    <v-card-text class="text-h6 font-weight-bold mt-n5">
                      <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">Línguas não maternas:</span> 
                    </v-card-text>
                    <v-list class="indigo mt-n6" v-for="(lm, index_ele) in eport.params.competencias_pessoais['outra_lingua']" :key="index_ele">
                        <v-list-item v-if="lm.nome != ''">
                          <v-list-item-title>- {{lm.nome}}</v-list-item-title>
                        </v-list-item>
                    </v-list>
                    <v-card-text class="text-h6 font-weight-bold mt-n5">
                      <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">Competências digitais:</span> 
                    </v-card-text>
                    <v-list class="indigo mt-n6" v-for="(lm, index_el) in eport.params.competencias_pessoais['comp_digital']" :key="index_el">
                        <v-list-item v-if="lm.competencia != ''">
                          <v-list-item-title>- {{lm.competencia}}</v-list-item-title>
                        </v-list-item>
                    </v-list>
                  </v-card>
                </v-list>

                <div data-html2canvas-ignore="true">
                  <v-checkbox color="indigo" v-model="extra" label="Informação extra"></v-checkbox>
                </div>

                <v-list id="extra" v-if="extra" class="indigo lighten-5" >
                  <v-card-title><h3>Informação extra</h3></v-card-title>
                  <v-list v-for="(tipo, index) in eport.params.tipos" :key="index" class="indigo lighten-5">
                    <v-card  class="mx-auto" color="indigo" dark rounded>
                      <v-card-title>
                        <v-icon small left class="black--text">
                          mdi-clipboard-file-outline
                        </v-icon>
                        <span class="black--text text-subtitle-1 font-weight-bold" >{{tipo.nome }}</span>
                      </v-card-title>
                      <v-list v-for="(elem, index_elem) in tipo.campo_int" :key="index_elem" class="indigo">
                            <v-list-item v-if="tipo.campo_int[index_elem].nome != '' && tipo.campo_int[index_elem].valor != ''" class="my-n4">
                              <v-list-item-content>
                                <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">{{ tipo.campo_int[index_elem].nome }}:</span> 
                                <span class="font-weight-bold">{{ tipo.campo_int[index_elem].valor }}</span>
                              </v-list-item-content>
                            </v-list-item>
                              <v-divider v-if="tipo.campo_int[index_elem].nome != '' && tipo.campo_int[index_elem].valor != ''" inset></v-divider>
                          </v-list>
                          <v-list v-for="(elem, index_ele) in tipo.campo_txt" :key="index_ele" class="indigo">
                            <v-list-item v-if="tipo.campo_txt[index_ele].nome != '' && tipo.campo_txt[index_ele].descricao != ''" class="my-n4">
                              <v-list-item-content >
                                <span color="indigo lighten-5" class="font-weight-light" style="display:inline;">{{ tipo.campo_txt[index_ele].nome }}:</span> 
                                <span class="font-weight-bold">{{ tipo.campo_txt[index_ele].descricao }}</span>
                              </v-list-item-content>
                            </v-list-item>
                            <v-divider v-if="tipo.campo_txt[index_ele].nome != '' && tipo.campo_txt[index_ele].descricao != ''" inset></v-divider>
                          </v-list>
                    </v-card>
                  </v-list>
                </v-list>

              </v-container>
              
              <v-divider></v-divider>
              
              <v-card-actions >
                <v-btn color="indigo" plain  @click="exportToPDF"><v-icon left>mdi-download</v-icon>Converter para PDF</v-btn>
              </v-card-actions> 
            </v-card>
          </v-card>
          
        </v-container>
    </div>
</template>

<script>
import { VContainer, VRow, VCol, VLayout, VImg, VCard, VCardText, VCardTitle, VCardSubtitle, VDivider, VCardActions, VProgressLinear, VList, VListItem, VListItemTitle,  VListItemAction, VListItemContent, VAppBarNavIcon, VToolbarTitle, VMenu, VCheckbox, VListItemAvatar, VExpansionPanels, VExpansionPanel, VExpansionPanelHeader, VExpansionPanelContent } from 'vuetify/lib'
import NavBar from '../components/NavBar'
import html2pdf from 'html2pdf.js'

export default {
    data: () => ({
      loading: false,
      selection: 1,
      model: null,
      items: [
        { title: 'Editar Conta', path: '/editar-perfil', click() { this.$router.push('/editar-perfil')} },
        { title: 'Editar ePortefolio', path: '/editar-eportefolio', click() { this.$router.push('/editar-eportefolio')} },
        { title: 'Apagar ePortefolio', click() {} },
      ],
      education: false,
      work_experience: false,
      address: false,
      personal_skills: false,
      extra: false,
      strapi_url: 'http://localhost:1337'
      
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
        VExpansionPanels, 
        VExpansionPanel, 
        VExpansionPanelHeader, 
        VExpansionPanelContent
    },

    computed: {
        user () {
          console.log("User: "+ JSON.stringify(this.$store.state.users.user)) 
          return this.$store.state.users.user
        },
        eport () {
          console.log("Eport: "+ JSON.stringify(this.$store.state.users.eport))
          return this.$store.state.users.eport
        },
        date() {
            //console.log(this.user.toISOString().substr(0, 10))
            //return this.user.toISOString().substr(0, 10)
        },
        users () {
            return this.$store.state.users.all
        },
    },
    
    methods: {
      criar () {
        this.loading = true

        setTimeout(() => (this.loading = false), 2000)
      },

      exportToPDF () {
        this.work_experience = true
        this.education = true
        this.personal_skills = true
        this.extra = true
        var element = document.getElementById('document');
        var opt = {
          margin:1,
          filename:'eportefolio.pdf',
          image: { type: 'jpeg', quality: 0.95 },
          pagebreak: {  after:['#dados_pessoais']},
          html2canvas: { 
            scale:2,
            useCORS: true, 
            y:0, 
            scrollY: 0
          },
          jsPDF: { unit: 'mm', orientation: 'portrait', format:'a4' },
        };
        // New Promise-based usage:
        //html2pdf().set(opt).from(element).save();
        // Old monolithic-style usage:
        html2pdf(element, opt);
		  },


      async selectSection(index) {
        if (this.items[index].path)
          this.items[index].click.call(this)
        else{
          await this.$store.dispatch('users/deleteEport', {user : this.user.params});
          if(this.user.params.eportfolios.length == 0)
            this.$router.push("/criar");
        }
          }
    },

    created () {
        this.$store.dispatch('users/getAll');
    }
    
};
</script>
<style>
    #document label{
      margin-top: 8px;
      color: rgb(63, 81, 181);
    }
    .edit{
      text-decoration: none !important;
    }
</style>  