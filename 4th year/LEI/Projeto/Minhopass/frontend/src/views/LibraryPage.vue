<template>
    <div>
        <NavBar/>
        <v-container v-if="(typeof user.params !== 'undefined')" fluid>
            <v-card flat class="mx-auto my-3" max-width="1180px">
                <v-toolbar flat class="indigo--text text-h1 display-3" >
                  <v-toolbar-title>
                      <h2>Biblioteca</h2>
                  </v-toolbar-title>
                </v-toolbar>
                <v-divider class="my-1 mx-2"></v-divider>
                <v-card-text class="black--text mt-n2">
                  <h6>Use a biblioteca para armazenar e organizar os seus documentos.</h6>
                </v-card-text>

                <v-list v-for="(card, index_card) in cards" :key="index_card">
                  <v-card :loading="card.is_loading" class="indigo lighten-5 mx-auto mb-4 py-7" max-width="1130px"> 
                    <template slot="progress">
                      <v-progress-linear color="white" height="10" indeterminate></v-progress-linear>
                    </template>
                    <v-card-title class="indigo lighten-5 my-n7 indigo--text">
                      <h4>{{ card.name }}</h4>
                      <v-spacer></v-spacer>
                      <v-btn large color="indigo" dark text class="ma-2 mx-1" @click="card.popup = true">
                        <v-icon color="indigo"> mdi-plus-circle </v-icon> 
                        <span>Adicionar</span>
                      </v-btn>
                      <v-divider v-if="index_card == 0" color="white" class="my-1" vertical></v-divider>
                      <v-btn v-if="index_card == 0" large color="indigo" dark text class="ma-2 mx-1" @click="criar_popup = true">
                        <v-icon color="indigo"> mdi-plus-circle </v-icon> 
                        <span>Criar CV</span>
                      </v-btn>
                    </v-card-title>
                    <v-divider></v-divider>
                    <div v-if="user.params[card.type].length != 0">
                      <v-row class="mt-3"> 
                        <v-col v-for="c in user.params[card.type]" :key="c.id" class="d-flex child-flex" cols="3"> 
                          <v-card class="mx-5" elevation="3">
                            <v-hover>
                              <template v-slot:default="{ hover }">
                               <v-card max-width="225px" elevation="0" class="mb-n4">
                                 <pdf v-if="index_card == 0" :ref="'page' + c.id" :src="c.url.split('/uploads/').join(`${strapi_url}/uploads/`)"></pdf>
                                 <v-img v-else gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="200px" :src="c.url.split('/uploads/').join(`${strapi_url}/uploads/`)"></v-img>
                                  <v-fade-transition>
                                    <v-overlay v-if="hover" class="rounded-0" absolute color="indigo lighten-2">
                                      <v-btn  color="primary"  @click.prevent="downloadFile(c)">DOWNLOAD</v-btn> 
                                    </v-overlay>
                                  </v-fade-transition>
                                </v-card>
                              </template>
                            </v-hover>
                            <v-divider></v-divider>
                            <v-card-text class="mb-n3 mt-n5 mx-2">
                              <span class="ml-n3 d-inline-block text-truncate" style="width: 150px;">{{ c.name.split('.')[0] }}</span>

                              <v-menu offset-y>
                                <template v-slot:activator="{ on, attrs }">
                                  <v-btn class="mt-n3" color="indigo" x-small icon dark v-bind="attrs" v-on="on">
                                    <v-icon> mdi-dots-vertical </v-icon> 
                                  </v-btn>
                                </template>
                                <v-list>
                                  <v-list-item  v-for="(item, index) in items" :key="index" @click="selectSection(index, c, card.type)">
                                    <v-list-item-title :to= "item.path" >{{ item.title }}</v-list-item-title>
                                  </v-list-item>
                                </v-list>
                              </v-menu>
                          
                              <v-btn v-if="card.type != 'certificados'" x-small icon dark class="mt-n3" color="indigo" @click.prevent="$refs['page' + c.id][0].print()"><v-icon>mdi-arrow-expand</v-icon></v-btn>
                              <v-btn v-else x-small icon dark class="mt-n3" color="indigo" @click="open_image(c)"><v-icon>mdi-arrow-expand</v-icon></v-btn>
                            </v-card-text>
                          </v-card>
                        </v-col>
                      </v-row>
                    </div>
                    <div v-else>
                      <v-row align="center" justify="center">
                        <v-icon slot="icon"  color="warning" size="72">
                          mdi-pencil-box-outline
                        </v-icon>
                      </v-row>
                      <v-row align="center" justify="center">
                        <h5>Sem documentos nesta secção.</h5>
                      </v-row>
                    </div>
                  </v-card>
                  
                  <v-dialog v-model="card.popup" max-width="600px">
                    <v-card>
                      <v-card-title class="indigo--text">
                        <h2>{{ card.title }}</h2>
                      </v-card-title>
                      <v-divider class="my-n2"></v-divider>
                      <v-form ref="form" v-model="card.valid" lazy-validation class="mx-2">
                        <v-file-input class="mb-n1" prepend-icon="mdi-file text--indigo" :rules="fileRules" accept="application/pdf, image/png, image/jpeg, image/bmp" label="Adicionar" type="file" name="cv" show-size truncate-length="25" ></v-file-input>
                        <v-btn :disabled="!card.valid" color="indigo" dark @click="validate('form', card.type)" class="mb-2">
                          Carregar
                        </v-btn>
                        <v-btn color="indigo" text @click="card.popup = false" class="mb-2">
                          Cancelar
                        </v-btn>
                      </v-form>
                    </v-card>
                  </v-dialog>

                </v-list>

            <v-dialog v-model="criar_popup" max-width="600px">
              <v-card>
                <v-card-title class="indigo--text">
                  <h2>Criar CV</h2>
                </v-card-title>
                <v-divider class="my-n2"></v-divider>
                <v-card-text class="mt-3">
                  <h5>Pode criar a partir do seu ePortefolio ou criar de raíz.</h5>
                </v-card-text>
                <v-card-actions class="mt-n7">
                  <v-btn class="mx-2" color="indigo" dark  elevation="3" @click="eportToCV_popup = true">
                    Seu ePortefolio
                  </v-btn>
                  <v-btn class="mx-2 criar" color="indigo" text elevation="3" to="/criar-eportfolio">
                    Criar novo
                  </v-btn>
                  <v-btn class="mx-2" color="indigo" text elevation="3" @click="criar_popup = false">
                    Cancelar
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <v-dialog v-model="image_popup" width="400px">
              <v-card v-if="(typeof certificate.url !== 'undefined')" >
                <v-img width="400px" :src="certificate.url.split('/uploads/').join(`${strapi_url}/uploads/`)"></v-img>
                <v-card-title class="indigo--text mt-n3">
                  <h2><span class="font-weight-light">Nome:</span>&nbsp;{{certificate.name.split('.')[0]}}</h2>
                </v-card-title>
                <v-card-title class="indigo--text mt-n7">
                  <h2><span class="font-weight-light">Tipo:</span>&nbsp;{{certificate.mime.split('/')[1]}}</h2>
                </v-card-title>
                <v-card-title class="indigo--text mt-n7">
                  <h2><span class="font-weight-light">Tamanho:</span>&nbsp;{{certificate.size}} KB</h2>
                </v-card-title>
              </v-card>
            </v-dialog>

            <v-dialog v-model="delete_popup" max-width="500px">
              <v-card>
                <v-card-title class="text-h5">Tem a certeza que pretende eliminar este documento da biblioteca?</v-card-title>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="close_delete">Cancelar</v-btn>
                  <v-btn color="blue darken-1" text @click="delete_item_confirm">Sim</v-btn>
                  <v-spacer></v-spacer>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <v-dialog  v-model="eportToCV_popup" max-width="850">
              <v-card color="indigo lighten-5" max-width="830">     
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
                    </v-row>
                  </v-list>

                  <v-list id="work" class="indigo lighten-5" >
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
                    </v-list>
                  </v-list>

                  <v-list id="edu" class="indigo lighten-5" >
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
                    </v-list>
                  </v-list>

                  <v-list id="skills" class="indigo lighten-5" >
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

                  <v-list id="extra" class="indigo lighten-5" >
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
                  <v-btn color="indigo" plain  @click="exportToPDF"><v-icon left>mdi-download</v-icon>Criar CV</v-btn>
                  <v-btn color="indigo" plain  @click="eportToCV_popup = false"><v-icon left>mdi-close</v-icon>Cancelar</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

          </v-card>
        </v-container>
    </div>  
</template>

<script>
import { VContainer, VRow, VCol, VLayout, VImg, VCard, VCardText, VCardTitle, VCardSubtitle, VDivider, VCardActions, VProgressLinear, VList, VListItem, VListItemTitle,  VListItemAction, VListItemContent, VAppBarNavIcon, VToolbarTitle, VMenu, VProgressCircular, VFileInput, VDialog, VItem, VHover, VOverlay, VFadeTransition, VForm, VListItemAvatar } from 'vuetify/lib'
import NavBar from '../components/NavBar'
import pdf from 'vue-pdf'
import axios from 'axios'
import html2pdf from 'html2pdf.js'

export default {
    data: () => ({
      selection: 1,
      model: null,
      items: [
        { title: 'Editar nome', path: '/editar-perfil', click() { this.$router.push('/editar-perfil')} },
        { title: 'Apagar documento', click() {} },
      ],
      cards: [
        { 'name' : 'CVs', title: 'Carregar CV', is_loading: false, type: 'cvs', popup: false, valid: false },
        { 'name' : 'Certicados/diplomas', title: 'Carregar certificado/diploma', is_loading: false, type: 'certificados', popup: false, valid: false },
        { 'name' : 'Cartas de apresentação', title: 'Carregar carta de apresentação', is_loading: false, type: 'cartas', popup: false, valid: false },
        { 'name' : 'Outros documentos', title: 'Carregar documento', is_loading: false, type: 'outros', popup: false, valid: false },
      ],
      fileRules: [
        value => !value || value.size < 2000000 || 'Tamanho da foto no máximo com 2 MB!',
      ],
      pdf: null,
      adicionar_popup: false,
      adicionar_popup_cert: false,
      eportToCV_popup: false,
      criar_popup: false,
      image_popup: false,
      strapi_url: 'http://localhost:1337',
      currentPage: 0,
      pageCount: 0,
      valid : true,
      certificate: {},
      delete_popup: false,
      delete_index: -1,
      delete_type: '',
      docs: {}
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
        VProgressCircular,
        VFileInput,
        VDialog,
        pdf,
        VItem,
        VHover,
        VOverlay,
        VFadeTransition,
        VForm,
        VListItemAvatar
    },

    computed: {
        user () {
          console.log("User: "+ JSON.stringify(this.$store.state.users.user));
          return this.$store.state.users.user;
        },
        date() {
            //console.log(this.user.toISOString().substr(0, 10));
            //return this.user.toISOString().substr(0, 10);
        },
        users () {
            return this.$store.state.users.all;
        },
        eport () {
          console.log("Eport: "+ JSON.stringify(this.$store.state.users.eport))
          return this.$store.state.users.eport
        },
    },
    
    methods: {
      criar () {
        this.loading = true
        setTimeout(() => (this.loading = false), 2000)
      },

      downloadFile (cv) {
        axios.get(cv.url.split('/uploads/').join(`${this.strapi_url}/uploads/`), { responseType: 'blob' })
          .then(response => {
            const blob = new Blob([response.data])
            const link = document.createElement('a')
            link.href = URL.createObjectURL(blob)
            link.download = cv.name
            link.click()
            URL.revokeObjectURL(link.href)
          }).catch(console.error)
      },

      async selectSection(index, doc, type) {
        if (this.items[index].path)
          this.items[index].click.call(this)
        else{
          this.docs = this.user.params[type]
          this.delete_index = this.user.params[type].indexOf(doc)
          this.delete_type  = type
          this.delete_popup = true
        }
      },

      validate (form, type_form) {
        console.log(this.$refs[form][0])
        if(this.$refs[form][0].validate()){
          const formElement = this.$refs[form][0].$el
          const formData = new FormData();
          const formElements = formElement.elements;
          for (let i = 0; i < formElements.length; i++) {
            const currentElement = formElements[i];
            console.log(currentElement.type)
            if (currentElement.type === 'file') {
              if (currentElement.files.length === 1) {
                const file = currentElement.files[0];
                console.log(currentElement.files[0])
                formData.append(`files`, currentElement.files[0], file.name);
              } else {
                for (let i = 0; i < currentElement.files.length; i++) {
                  const file = currentElement.files[i];
                  formData.append(`files.${currentElement.name}`, currentElement.files[0], file.name);
                }
              }
            }
          }
          for (var p of formData) {
            console.log("Elemento do form: " + p);
          }
          console.log("FROMDATA" + JSON.stringify(Object.fromEntries(formData)))
          this.$store.dispatch('users/put_file', { user : this.$store.state.users.user.params, file : formData, tipo: type_form})

          for(var card of this.cards)
            card.popup = false
        }
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
        }
        var self = this
        // New Promise-based usage:
        html2pdf().set(opt).from(element).save().outputPdf().then(function(pdf) { 
          var aux = btoa(pdf)

          // helper function: generate a new file from base64 String
          const dataURLtoFile = (dataurl, filename) => {
            console.log(pdf)
            console.log(aux)
            const bstr = atob(dataurl)
            let n = bstr.length
            const u8arr = new Uint8Array(n)
            while (n) {
              u8arr[n - 1] = bstr.charCodeAt(n - 1)
              n -= 1 // to make eslint happy
            }
            return new File([u8arr], filename, { type: 'application/pdf' })
          }

          // generate file from base64 string
          const file = dataURLtoFile(aux, 'eportefolio.pdf')
          // put file into form data
          const data = new FormData()
          data.append('files', file, file.name)

          self.$store.dispatch('users/put_file', { user : self.$store.state.users.user.params, file : data, tipo: 'cvs'})
        })
		  },

      open_image(cert){
        console.log(cert)
        this.certificate = cert
        this.image_popup = true
      },

      delete_item_confirm () {
        this.docs.splice(this.delete_index, 1)
        this.$store.dispatch('users/delete_document', { docs: this.docs, user: this.user.params, type: this.delete_type })
        this.close_delete()
      },

      close_delete () {
        this.delete_popup = false
        this.delete_index = -1
      },
      
    },

    created () {
        this.$store.dispatch('users/getAll');
    }
    
};
</script>
<style scoped>
  .criar{
    text-decoration: none;
  }
</style>