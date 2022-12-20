<template>
  <div>
    <v-container v-if="!types.loading" fluid >
      <v-row justify="center">
        <v-col cols=7>
          <v-card :loading="loading" class="mx-auto my-10" width="1500px">
            <template slot="progress">
                <v-progress-linear color="white" height="10" indeterminate></v-progress-linear>
            </template>
            <v-toolbar class=" indigo white--text text-h1 display-3" dark>
              <v-toolbar-title>
                <h2>Editar ePortefolio</h2>
              </v-toolbar-title>
            </v-toolbar>

            <v-stepper v-model="step_on">
              <v-stepper-header>
                <v-stepper-step :complete="step_on>1" step="1">Dados pessoais</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step :complete="step_on>2" step="2">Experiência profissional</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step :complete="step_on>3" step="3">Educação e formação</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step :complete="step_on>4" step="4">Competências pessoais</v-stepper-step>
                <v-divider></v-divider>
                <v-stepper-step step="5">Informação extra</v-stepper-step>
              </v-stepper-header>
              <v-stepper-items>
                <v-stepper-content step="1">
                  <v-form ref="form" v-model="valid" lazy-validation class="mx-2 mb-1">
                    <v-card-title class="indigo--text"><h3>Informação pessoal</h3></v-card-title>
                    <v-text-field v-model="eportfolio.nome" prepend-icon="mdi-account" :counter="30" :rules="nameRules" label="Nome" name="nome" required></v-text-field>
                    <v-file-input prepend-icon="mdi-camera" :rules="fileRules" accept="image/png, image/jpeg, image/bmp" type="file" name="avatar" label="Foto" show-size truncate-length="25" @change="onFileSelected"></v-file-input>
                    <v-row>
                      <v-col cols="5">
                        <v-menu v-model="birthDateMenu" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                          <template v-slot:activator="{ on }">
                            <v-text-field disabled label="Data de Nascimento" prepend-icon="mdi-calendar" :rules="[v => !!v || 'Campo obrigatório']" readonly :value="eportfolio.data_nasc.substr(0,10)" v-on="on" name="data_nasc" required></v-text-field>
                          </template>
                          <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="birthDateVal" no-title @input="birthDateMenu = false"></v-date-picker>
                        </v-menu>
                      </v-col>
                      <v-col>
                        <v-radio-group label="Género" v-model="eportfolio.genero" row prepend-icon="mdi-account-group" name="genero">
                          <v-radio label="F"  value="F"></v-radio>
                          <v-radio label="M"  value="M"></v-radio>
                          <v-radio label="Outro"  value="Outro"></v-radio>
                        </v-radio-group>
                      </v-col>
                    </v-row>
                    <v-select v-model="eportfolio.nacionalidade" prepend-icon="mdi-flag" :items="countries" label="Nacionalidade"  name="nacionalidade"></v-select>
                    <v-text-field v-model="eportfolio.profissao" prepend-icon="mdi-briefcase" label="Profissão" name="profissao"></v-text-field>
                    <v-card-subtitle class="indigo--text">Contacto</v-card-subtitle>
                    <v-text-field v-model="eportfolio.email" prepend-icon="mdi-email"  :rules="[emailRules, v => !!v || 'Campo obrigatório']" label="E-mail" name="email" required></v-text-field>
                    <v-text-field v-model="eportfolio.telemovel" prepend-icon="mdi-phone" :counter="9" :rules="phoneRules" label="Telefone"  name="telemovel" required></v-text-field>
                  </v-form>

                  <v-form ref="form_address" v-model="valid" lazy-validation class="mx-2 mb-1">
                    <v-card-subtitle class="indigo--text">Endereço</v-card-subtitle>
                    <v-text-field v-model="eportfolio.endereco.morada" prepend-icon="mdi-home"  label="Morada" name="morada"></v-text-field>
                    <v-row>
                      <v-col>
                        <v-text-field v-model="eportfolio.endereco.cod_post" prepend-icon="mdi-home-outline" label="Código postal"  name="cod_post"></v-text-field>
                      </v-col>
                      <v-col>
                        <v-text-field v-model="eportfolio.endereco.cidade" prepend-icon="mdi-city"  label="Cidade" name="cidade"></v-text-field>
                      </v-col>
                      <v-col>
                        <v-text-field v-model="eportfolio.endereco.pais" prepend-icon="mdi-flag-outline"  :items="countries" label="País"  name="pais"></v-text-field>
                      </v-col>
                    </v-row>
                    <div>
                      <v-btn :disabled="!valid" color="primary" class="mr-4 mb-3" @click="check_validation('form', 'form_address')">Continuar</v-btn>
                      <v-btn color="error" class="mr-4 mb-3" @click="reset_forms('form', null)">Apagar</v-btn>
                      <v-btn color="grey lighten-1" dark class="mr-4 mb-3" @click="back_home">Cancelar</v-btn>
                    </div>
                  </v-form>
                </v-stepper-content>

                <v-stepper-content step="2">
                  <v-list v-for="(we, index) in eportfolio.trabalhos" :key="index">
                    <v-card v-if="index < eportfolio.trabalhos.length && index != edit_we_index" class="mx-auto" color="indigo" dark  rounded>
                      <v-card-title>
                        <v-icon small left>
                          mdi-clipboard-file-outline
                        </v-icon>
                        <span class="text-subtitle-1 font-weight-light" >{{we.funcao}}</span>
                      </v-card-title>
                  
                      <v-card-text class="text-subtitle-2 font-weight-bold my-n3">
                        {{we.ent_empregadora}} | {{address_work[index].cidade}}
                      </v-card-text>

                      <v-card-text class="text-h6 font-weight-bold mt-n7">
                        {{we.responsabilidades}}
                      </v-card-text>
                  
                      <v-card-actions>
                        <v-list-item class="grow">
                          <v-row align="center" justify="end">
                            <v-btn text icon dark class="mr-1 font-weight-bold" color="rgb(197, 203, 233)" @click="edit_we_index = index"> 
                              <v-icon> mdi-lead-pencil </v-icon>
                            </v-btn>
                            <v-btn text icon dark  class="mr-1" color="rgb(197, 203, 233)" @click="remove_we_index(index)"> 
                              <v-icon> mdi-delete </v-icon>
                            </v-btn>
                          </v-row>
                        </v-list-item>
                      </v-card-actions>
                    </v-card>
                  </v-list>
                  <v-form ref="form_work" v-model="valid" lazy-validation class="mx-2 mb-1">
                    <v-list v-for="(we, index_work) in eportfolio.trabalhos" :key="index_work">
                      <v-card-title v-if="edit_we_index == index_work" class="indigo--text"><h3>Experiência profissional</h3></v-card-title>
                      <v-card-subtitle v-if="edit_we_index == index_work" class="indigo--text">Descreve as tuas experiências profissionais. Podes incluir trabalhos pagos, voluntareado, estágios, freelancing e outras atividade.</v-card-subtitle>
                      <v-text-field :id="'funcao' + index_work" v-if="edit_we_index == index_work" v-model="we.funcao" prepend-icon="mdi-briefcase" :rules="[v => !!v || 'Campo obrigatório']" label="Função ou cargo ocupado" name="funcao" required></v-text-field>
                      <v-text-field :id="'funcao' + index_work" v-else v-show="false"   v-model="we.funcao" prepend-icon="mdi-briefcase" :rules="[v => !!v || 'Campo obrigatório']" label="Função ou cargo ocupado" name="funcao" required></v-text-field>
                      <v-text-field :id="'ent_empregadora' + index_work" v-if="edit_we_index == index_work" v-model="we.ent_empregadora" prepend-icon="mdi-briefcase-outline" :rules="[v => !!v || 'Campo obrigatório']" label="Entidade empregadora" name="ent_empregadora" required></v-text-field>
                      <v-text-field :id="'ent_empregadora' + index_work" v-else v-show="false" v-model="we.ent_empregadora" prepend-icon="mdi-briefcase-outline" :rules="[v => !!v || 'Campo obrigatório']" label="Entidade empregadora" name="ent_empregadora" required></v-text-field>
                  <!--<v-text-field v-model="we.cidade" prepend-icon="mdi-city" :rules="[v => !!v || 'Campo obrigatório']" label="Cidade" name="cidade" required></v-text-field>-->
                      <v-row v-if="edit_we_index == index_work">
                        <v-col>
                          <v-menu v-model="fromDateMenu_work" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on }">
                              <v-text-field :id="'data_int' + index_work" label="A partir de"  prepend-icon="mdi-calendar" readonly :value="fromDateDisp_work" v-on="on" name="data_ini"></v-text-field>
                            </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="fromDateVal_work" no-title @input="fromDateMenu_work=false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-menu v-model="toDateMenu_work" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on }">
                              <v-text-field :id="'data_int' + index_work" v-if="we.em_curso" disabled label="Até" prepend-icon="mdi-calendar-outline" readonly v-on="on" name="data_fim"></v-text-field>
                              <v-text-field :id="'data_int' + index_work" v-else label="Até" prepend-icon="mdi-calendar-outline" readonly :value="toDateDisp_work" v-on="on" name="data_fim"></v-text-field>
                              </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="toDateVal_work" no-title @input="toDateMenu_work= false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-switch :id="'em_curso'+ index_work" label="Em curso" name="em_curso" :value="we.em_curso" @click="change_work_em_curso(index_work)"></v-switch> 
                        </v-col>
                      </v-row>
                      <v-row v-else v-show="false">
                        <v-col>
                          <v-menu v-model="fromDateMenu_work  " :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on_menu }">
                              <v-text-field :id="'data_int_work' + index_work" label="A partir de" prepend-icon="mdi-calendar" readonly :value="fromDateDisp_work" v-on="on_menu" name="data_ini"></v-text-field>
                            </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="fromDateVal_work" no-title @input="fromDateMenu_work=false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-menu v-model="toDateMenu_work" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on }">
                              <v-text-field :id="'data_int_work' + index_work" v-if="we.em_curso" disabled label="Até" prepend-icon="mdi-calendar-outline" readonly v-on="on" name="data_fim"></v-text-field>
                              <v-text-field :id="'data_int_work' + index_work" v-else label="Até" prepend-icon="mdi-calendar-outline" readonly :value="toDateDisp_work" v-on="on" name="data_fim"></v-text-field>
                            </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="toDateVal_work" no-title @input="toDateMenu_work= false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-switch :id="'em_curso_work'+ index_work" label="Em curso" name="em_curso" :value="we.em_curso" @click="change_work_em_curso(index_work)"></v-switch> 
                        </v-col>
                      </v-row>
                      <v-textarea :id="'responsabilidades' + index_work" v-if="edit_we_index == index_work" v-model="we.responsabilidades" prepend-icon="mdi-lightbulb" :rules="[v => !!v || 'Campo obrigatório']" label="Principais atividades e responsabilidades" name="responsabilidades" required></v-textarea>
                      <v-textarea :id="'responsabilidades' + index_work" v-else v-show="false" v-model="we.responsabilidades" prepend-icon="mdi-lightbulb" :rules="[v => !!v || 'Campo obrigatório']" label="Principais atividades e responsabilidades" name="responsabilidades" required></v-textarea>

                      <v-checkbox v-if="edit_we_index == index_work" color="indigo" v-model="more_info_work" label="Mais informações"></v-checkbox>

                      <v-text-field :id="'atividade' + index_work" v-if="edit_we_index == index_work && more_info_work" class="ml-10"  v-model="we.atividade_setor" prepend-icon="mdi-clipboard-text"  label="Atividade ou setor" name="atividade_setor"></v-text-field>
                      <v-text-field :id="'atividade' + index_work" v-else v-show="false" class="ml-10"  v-model="we.atividade_setor" prepend-icon="mdi-clipboard-text"  label="Atividade ou setor" name="atividade_setor"></v-text-field>
                      <v-text-field :id="'departamento' + index_work" v-if="edit_we_index == index_work && more_info_work" class="ml-10"  v-model="we.departamento" prepend-icon="mdi-account-details"  label="Departamento" name="departamento"></v-text-field>
                      <v-text-field :id="'departamento' + index_work" v-else v-show="false" class="ml-10"  v-model="we.departamento" prepend-icon="mdi-account-details"  label="Departamento" name="departamento"></v-text-field>
                      <v-text-field :id="'email_organizacao' + index_work" v-if="edit_we_index == index_work && more_info_work" class="ml-10"  v-model="we.email_organizacao" prepend-icon="mdi-email"  :rules="[emailRules]" label="E-mail da organização" name="email_organizacao"></v-text-field>
                      <v-text-field :id="'email_organizacao' + index_work" v-else v-show="false" class="ml-10"  v-model="we.email_organizacao" prepend-icon="mdi-email"  :rules="[emailRules]" label="E-mail da organização" name="email_organizacao"></v-text-field>
                      <v-text-field :id="'sitio_web' + index_work" v-if="edit_we_index == index_work && more_info_work" class="ml-10"  v-model="we.sitio_web" prepend-icon="mdi-file-link"  :rules="siteRules" label="Sítio web" name="sitio_web"></v-text-field>
                      <v-text-field :id="'sitio_web' + index_work" v-else v-show="false" class="ml-10"  v-model="we.sitio_web" prepend-icon="mdi-file-link"  :rules="siteRules" label="Sítio web" name="sitio_web"></v-text-field>
                      <v-text-field :id="'hiper' + index_work" v-if="edit_we_index == index_work && more_info_work" class="ml-10"  v-model="we.hiper" prepend-icon="mdi-link" :rules="siteRules" label="Hiperligação para ficheiro ou vídeo" name="hiper"></v-text-field>
                      <v-text-field :id="'hiper' + index_work" v-else v-show="false" class="ml-10"  v-model="we.hiper" prepend-icon="mdi-link" :rules="siteRules" label="Hiperligação para ficheiro ou vídeo" name="hiper"></v-text-field>
                      <!-- <v-text-field v-if="edit_we_index == index_work"  v-model="we.descricao" prepend-icon="mdi-card-text" :rules="[v => !!v || 'Campo obrigatório']" label="Descrição" name="descricao" required></v-text-field> -->
                    </v-list>
                  </v-form>
                  <v-form ref="form_address_work" v-model="valid" lazy-validation class="mx-2 mb-1">
                    <v-list  v-for="(we, index_address) in address_work" :key="index_address">
                      <v-card-subtitle v-if="edit_we_index == index_address && more_info_work" class="indigo--text ml-10" >Endereço</v-card-subtitle>  
                      <v-text-field :id="'morada_work' + index_address" class="ml-10" v-if="edit_we_index == index_address && more_info_work" v-model="address_work[index_address].morada" prepend-icon="mdi-home"  label="Morada" name="morada"></v-text-field>
                      <v-text-field :id="'morada_work' + index_address" class="ml-10" v-else v-show="false" v-model="address_work[index_address].morada" prepend-icon="mdi-home"  label="Morada" name="morada"></v-text-field>
                 
                      <v-row v-if="edit_we_index == index_address && more_info_work">
                        <v-col>
                          <v-text-field :id="'cod_postal_work' + index_address" class="ml-10"  v-model="address_work[index_address].cod_post" prepend-icon="mdi-home-outline" label="Código postal"  name="cod_post"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'cidade_work' + index_address" class="ml-10" v-model="address_work[index_address].cidade" prepend-icon="mdi-city"  label="Cidade" name="cidade"></v-text-field>
                          </v-col>
                        <v-col>
                          <v-text-field :id="'pais_work' + index_address" class="ml-10" v-model="address_work[index_address].pais" prepend-icon="mdi-flag-outline"  :items="countries" label="País"  name="pais"></v-text-field>
                        </v-col>
                      </v-row>  
                      <v-row v-else v-show="false">
                        <v-col>
                          <v-text-field :id="'cod_postal_work' + index_address" class="ml-10"  v-model="address_work[index_address].cod_post" prepend-icon="mdi-home-outline" label="Código postal"  name="cod_post"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'cidade_work' + index_address" class="ml-10" v-model="address_work[index_address].cidade" prepend-icon="mdi-city"  label="Cidade" name="cidade"></v-text-field>
                          </v-col>
                        <v-col>
                          <v-text-field :id="'pais_work' + index_address" class="ml-10" v-model="address_work[index_address].pais" prepend-icon="mdi-flag-outline"  :items="countries" label="País"  name="pais"></v-text-field>
                        </v-col>
                      </v-row>  

                      <div v-if="edit_we_index == index_address">
                        <template>
                          <v-banner single-line class="mb-3">
                            <v-icon slot="icon"  color="warning" size="36">
                              mdi-alert-box 
                            </v-icon>
                            <template v-slot:actions>
                              <v-btn color="primary" text @click="new_we_form(index_address)"> 
                                Adicionar nova experiência profissional 
                              </v-btn>
                            </template>
                          </v-banner>
                        </template>
                      </div>
                    <v-btn v-if="edit_we_index == index_address" :disabled="!valid_work" color="primary" class="mr-4 mb-3" @click="check_validation('form_work', 'form_address_work')">Continuar</v-btn>
                    <v-btn v-if="edit_we_index == index_address" color="error" class="mr-4 mb-3" @click="reset_forms('form_work', 'form_address_work')">Apagar</v-btn>
                    <v-btn v-if="edit_we_index == index_address" color="grey lighten-1" dark class="mr-4 mb-3" @click="back(1)">Voltar</v-btn>
                    </v-list>  
                  </v-form>
                </v-stepper-content>

                <v-stepper-content step="3">
                  <v-list v-for="(ef, index) in eportfolio.educacoes" :key="index">
                    <v-card v-if="index < eportfolio.educacoes.length && index != edit_ef_index" class="mx-auto" color="indigo" dark  rounded>
                      <v-card-title>
                        <v-icon small left>
                          mdi-clipboard-file-outline
                        </v-icon>
                        <span class="text-subtitle-1 font-weight-light" >{{ef.titulo}}</span>
                      </v-card-title>
                  
                      <v-card-text class="text-subtitle-2 font-weight-bold my-n3">
                        {{ef.organizacao}} | {{address_education[index].cidade}}
                      </v-card-text>

                      <v-card-text class="text-h6 font-weight-bold mt-n7">
                        {{ef.descricao}}
                      </v-card-text>
                  
                      <v-card-actions>
                        <v-list-item class="grow">
                          <v-row align="center" justify="end">
                            <v-btn text icon dark class="mr-1 font-weight-bold" color="rgb(197, 203, 233)" @click="edit_ef_index = index"> 
                              <v-icon> mdi-lead-pencil </v-icon>
                            </v-btn>
                            <v-btn text icon dark  class="mr-1" color="rgb(197, 203, 233)" @click="remove_ef_index(index)"> 
                              <v-icon> mdi-delete </v-icon>
                            </v-btn>
                          </v-row>
                        </v-list-item>
                      </v-card-actions>
                    </v-card>
                  </v-list>
                  <v-form ref="form_education" v-model="valid_education" lazy-validation class="mx-2 mb-1">
                    <v-list v-for="(ef, index_edu) in eportfolio.educacoes" :key="index_edu">  
                      <v-card-title v-if="edit_ef_index == index_edu" class="indigo--text"><h3>Educação e formação</h3></v-card-title>
                      <v-card-subtitle v-if="edit_ef_index == index_edu" class="indigo--text">Descreve as tuas experiências de aprendizagem. Inclui qualquer tipo de experiência de aprendizagem(universidade, cursos online, desenvolvimento profissional).</v-card-subtitle>
                      <v-text-field :id="'titulo' + index_edu" v-if="edit_ef_index == index_edu" v-model="eportfolio.educacoes[index_edu].titulo" prepend-icon="mdi-school" :rules="[v => !!v || 'Campo obrigatório']" label="Título" name="titulo" required></v-text-field>
                      <v-text-field :id="'titulo' + index_edu" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].titulo" prepend-icon="mdi-school" :rules="[v => !!v || 'Campo obrigatório']" label="Título" name="titulo" required></v-text-field>
                      <v-text-field :id="'organizacao' + index_edu" v-if="edit_ef_index == index_edu" v-model="eportfolio.educacoes[index_edu].organizacao" prepend-icon="mdi-school-outline" :rules="[v => !!v || 'Campo obrigatório']" label="Organização" name="organizacao" required></v-text-field>
                      <v-text-field :id="'organizacao' + index_edu" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].organizacao" prepend-icon="mdi-school-outline" :rules="[v => !!v || 'Campo obrigatório']" label="Organização" name="organizacao" required></v-text-field>
                      <v-text-field :id="'sitio_web' + index_edu" v-if="edit_ef_index == index_edu" v-model="eportfolio.educacoes[index_edu].sitio_web" prepend-icon="mdi-file-link"  :rules="siteRules" label="Sítio web" name="sitio_web"></v-text-field>
                      <v-text-field :id="'sitio_web' + index_edu" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].sitio_web" prepend-icon="mdi-file-link"  :rules="siteRules" label="Sítio web" name="sitio_web"></v-text-field>
                      <v-row v-if="edit_ef_index == index_edu">
                        <v-col>
                          <v-menu v-model="fromDateMenu_education" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on }">
                              <v-text-field :id="'data_int_edu' + index_edu" label="A partir de" prepend-icon="mdi-calendar" readonly :value="fromDateDisp_education" v-on="on" name="data_ini"></v-text-field>
                            </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="fromDateVal_education" no-title @input="fromDateMenu_education = false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-menu v-model="toDateMenu_education" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on }">
                              <v-text-field :id="'data_int_edu' + index_edu" v-if="eportfolio.educacoes[index_edu].em_curso" disabled label="Até" prepend-icon="mdi-calendar-outline" readonly v-on="on" name="data_fim"></v-text-field>
                              <v-text-field :id="'data_int_edu' + index_edu" v-else label="Até" prepend-icon="mdi-calendar-outline" readonly :value="toDateDisp_education" v-on="on" name="data_fim"></v-text-field>
                            </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="toDateVal_education" no-title @input="toDateMenu_education = false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-switch :id="'em_curso_edu' + index_edu" label="Em curso" name="em_curso" :value="eportfolio.educacoes[index_edu].em_curso" @click="change_education_em_curso(index_edu)"></v-switch> 
                        </v-col>
                      </v-row>
                      <v-row v-else v-show="false">
                        <v-col>
                          <v-menu v-model="fromDateMenu_education" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on }">
                              <v-text-field :id="'data_int_edu' + index_edu" label="A partir de" prepend-icon="mdi-calendar" readonly :value="fromDateDisp_education" v-on="on" name="data_ini"></v-text-field>
                            </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="fromDateVal_education" no-title @input="fromDateMenu_education = false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-menu v-model="toDateMenu_education" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                            <template v-slot:activator="{ on }">
                              <v-text-field :id="'data_int_edu' + index_edu" v-if="eportfolio.educacoes[index_edu].em_curso" disabled label="Até" prepend-icon="mdi-calendar-outline" readonly v-on="on" name="data_fim"></v-text-field>
                              <v-text-field :id="'data_int_edu' + index_edu" v-else label="Até" prepend-icon="mdi-calendar-outline" readonly :value="toDateDisp_education" v-on="on" name="data_fim"></v-text-field>
                            </template>
                            <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="toDateVal_education" no-title @input="toDateMenu_education = false"></v-date-picker>
                          </v-menu>
                        </v-col>
                        <v-col>
                          <v-switch :id="'em_curso_edu' + index_edu" label="Em curso" name="em_curso" :value="eportfolio.educacoes[index_edu].em_curso" @click="change_education_em_curso(index_edu)"></v-switch> 
                        </v-col>
                      </v-row>

                      <v-checkbox color="indigo" v-if="edit_ef_index == index_edu" v-model="more_info_edu" label="Mais informações"></v-checkbox>

                      <v-text-field :id="'area' + index_edu" class="ml-10" v-if="edit_ef_index == index_edu && more_info_edu" v-model="eportfolio.educacoes[index_edu].area" prepend-icon="mdi-clipboard-text" label="Área de estudo" name="area"></v-text-field>
                      <v-text-field :id="'area' + index_edu" class="ml-10" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].area" prepend-icon="mdi-clipboard-text" label="Área de estudo" name="area"></v-text-field>
                      <v-text-field :id="'tese' + index_edu" class="ml-10" v-if="edit_ef_index == index_edu && more_info_edu" v-model="eportfolio.educacoes[index_edu].tese" prepend-icon="mdi-clipboard-text-outline" label="Tese" name="tese"></v-text-field>
                      <v-text-field :id="'tese' + index_edu" class="ml-10" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].tese" prepend-icon="mdi-clipboard-text-outline" label="Tese" name="tese"></v-text-field>
                      <v-text-field :id="'class_final' + index_edu" class="ml-10" v-if="edit_ef_index == index_edu && more_info_edu" v-model="eportfolio.educacoes[index_edu].class_final" prepend-icon="mdi-star" label="Classificação final" name="class_final"></v-text-field>
                      <v-text-field :id="'class_final' + index_edu" class="ml-10" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].class_final" prepend-icon="mdi-star" label="Classificação final" name="class_final"></v-text-field>
                      <v-text-field :id="'assunto_princ' + index_edu" class="ml-10" v-if="edit_ef_index == index_edu && more_info_edu" v-model="eportfolio.educacoes[index_edu].assunto_princ" prepend-icon="mdi-lightbulb" label="Assunto principal da tese " name="assunto_princ"></v-text-field>
                      <v-text-field :id="'assunto_princ' + index_edu" class="ml-10" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].assunto_princ" prepend-icon="mdi-lightbulb" label="Assunto principal da tese " name="assunto_princ"></v-text-field>
                      
                      <v-row v-if="edit_ef_index == index_edu && more_info_edu">
                        <v-col>
                          <v-select :id="'qeq' + index_edu" class="ml-10"  v-model="eportfolio.educacoes[index_edu].qeq" prepend-icon="mdi-alpha-q-box" :items="qeqs" label="Nível QEQ"  name="qeq"></v-select>
                        </v-col>
                        <v-col>  
                          <v-text-field :id="'class_nacional' + index_edu" class="ml-10"  v-model="eportfolio.educacoes[index_edu].class_nacional" prepend-icon="mdi-star" label="Classificação nacional" name="class_nacional"></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row v-else v-show="false">
                        <v-col>
                          <v-select :id="'qeq' + index_edu" class="ml-10" v-model="eportfolio.educacoes[index_edu].qeq" prepend-icon="mdi-alpha-q-box" :items="qeqs" label="Nível QEQ"  name="qeq"></v-select>
                        </v-col>
                        <v-col>  
                          <v-text-field :id="'class_nacional' + index_edu" class="ml-10"  v-model="eportfolio.educacoes[index_edu].class_nacional" prepend-icon="mdi-star" label="Classificação nacional" name="class_nacional"></v-text-field>
                        </v-col>
                      </v-row>

                      <v-row v-if="edit_ef_index == index_edu && more_info_edu">
                        <v-col>
                          <v-text-field :id="'tipo_creditos' + index_edu" class="ml-10" v-model="eportfolio.educacoes[index_edu].tipo_creditos" prepend-icon="mdi-alpha-t-box"  label="Tipo de créditos" name="tipo_creditos"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'num_creditos' + index_edu" class="ml-10" v-model="eportfolio.educacoes[index_edu].num_creditos" prepend-icon="mdi-alpha-n-box" label="Número de créditos" name="num_creditos"></v-text-field>
                        </v-col>
                      </v-row>
                       <v-row v-else v-show="false">
                        <v-col>
                          <v-text-field :id="'tipo_creditos' + index_edu" class="ml-10" v-model="eportfolio.educacoes[index_edu].tipo_creditos" prepend-icon="mdi-alpha-t-box"  label="Tipo de créditos" name="tipo_creditos"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'num_creditos' + index_edu" class="ml-10" v-model="eportfolio.educacoes[index_edu].num_creditos" prepend-icon="mdi-alpha-n-box" label="Número de créditos" name="num_creditos"></v-text-field>
                        </v-col>
                      </v-row>

                      <v-text-field :id="'hiper' + index_edu" class="ml-10" v-if="edit_ef_index == index_edu && more_info_edu" v-model="eportfolio.educacoes[index_edu].hiper" prepend-icon="mdi-link" :rules="siteRules" label="Hiperligação para ficheiro ou vídeo" name="hiper"></v-text-field>
                      <v-text-field :id="'hiper' + index_edu" class="ml-10" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].hiper" prepend-icon="mdi-link" :rules="siteRules" label="Hiperligação para ficheiro ou vídeo" name="hiper"></v-text-field>
                      
                      <div v-if="edit_ef_index == index_edu && more_info_edu">
                        <v-menu  v-model="validDateMenu" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                          <template v-slot:activator="{ on }">
                            <v-text-field :id="'valido_ate' + index_edu" class="ml-10" v-model="eportfolio.educacoes[index_edu].valido_ate" label="Valido até" prepend-icon="mdi-calendar" readonly :value="validDateDisp" v-on="on" name="data_ini"></v-text-field>
                          </template>
                          <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="validDateVal" no-title @input="validDateMenu = false"></v-date-picker>
                        </v-menu>
                      </div>
                      <div v-else v-show="false">
                        <v-menu v-model="validDateMenu" :close-on-content-click="false" :nudge-right="40" transition="scale-transition" offset-y max-width="290px" min-width="290px">
                          <template v-slot:activator="{ on }">
                            <v-text-field :id="'valido_ate' + index_edu" class="ml-10" v-model="eportfolio.educacoes[index_edu].valido_ate" label="Valido até" prepend-icon="mdi-calendar" readonly :value="validDateDisp" v-on="on" name="data_ini"></v-text-field>
                          </template>
                          <v-date-picker locale="pt-pt" :min="minDate" :max="maxDate" v-model="validDateVal" no-title @input="validDateMenu = false"></v-date-picker>
                        </v-menu>
                      </div>

                      <v-textarea :id="'descricao' + index_edu" class="ml-10" v-if="edit_ef_index == index_edu && more_info_edu" v-model="eportfolio.educacoes[index_edu].descricao" prepend-icon="mdi-lightbulb" label="Skills desenvolvidas" name="descricao"></v-textarea>
                      <v-textarea :id="'descricao' + index_edu" class="ml-10" v-else v-show="false" v-model="eportfolio.educacoes[index_edu].descricao" prepend-icon="mdi-lightbulb" label="Skills desenvolvidas" name="descricao"></v-textarea>
                      <v-card-subtitle class="indigo--text ml-10" v-if="edit_ef_index == index_edu && more_info_edu">Endereço</v-card-subtitle>
                    </v-list>
                  </v-form>
                  <v-form ref="form_address_education" v-model="valid" lazy-validation class="mx-2 mb-1">
                    <v-list v-for="(ef, index_address) in address_education" :key="index_address">
                      <v-text-field :id="'morada_edu' + index_address" class="ml-10" v-if="edit_ef_index == index_address && more_info_edu" v-model="address_education[index_address].morada" prepend-icon="mdi-home"  label="Morada" name="morada"></v-text-field>
                      <v-text-field :id="'morada_edu' + index_address" class="ml-10" v-else v-show="false" v-model="address_education[index_address].morada" prepend-icon="mdi-home"  label="Morada" name="morada"></v-text-field>
                      
                      <v-row v-if="edit_ef_index == index_address && more_info_edu">
                        <v-col>
                          <v-text-field :id="'cod_postal_edu' + index_address" class="ml-10" v-model="address_education[index_address].cod_post" prepend-icon="mdi-home-outline" label="Código postal"  name="cod_postal_edu"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'cidade_edu' + index_address" class="ml-10" v-model="address_education[index_address].cidade" prepend-icon="mdi-city"  label="Cidade" name="cidade"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'pais_edu' + index_address" class="ml-10" v-model="address_education[index_address].pais" prepend-icon="mdi-flag-outline"  :items="countries" label="País"  name="pais"></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row v-else v-show="false">
                        <v-col>
                          <v-text-field :id="'cod_postal_edu' + index_address" class="ml-10" v-model="address_education[index_address].cod_post" prepend-icon="mdi-home-outline" label="Código postal"  name="cod_post"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'cidade_edu' + index_address" class="ml-10" v-model="address_education[index_address].cidade" prepend-icon="mdi-city"  label="Cidade" name="cidade"></v-text-field>
                        </v-col>
                        <v-col>
                          <v-text-field :id="'pai_edu' + index_address" class="ml-10" v-model="address_education[index_address].pais" prepend-icon="mdi-flag-outline"  :items="countries" label="País"  name="pais"></v-text-field>
                        </v-col>
                      </v-row>

                      <div v-if="edit_ef_index == index_address">
                        <template>
                          <v-banner single-line class="mb-3">
                            <v-icon slot="icon"  color="warning" size="36">
                              mdi-alert-box
                            </v-icon>
                            <template v-slot:actions>
                              <v-btn color="primary" text @click="new_ef_form(index_address)"> 
                                Adicionar nova experiência de educação e formação
                              </v-btn>
                            </template>
                          </v-banner>
                        </template>
                      </div>
                      
                      <v-btn v-if="edit_ef_index == index_address" :disabled="!valid_education" color="primary" class="mr-4 mb-3" @click="check_validation('form_education', 'form_address_education')">Continuar</v-btn>
                      <v-btn v-if="edit_ef_index == index_address" color="error" class="mr-4 mb-3" @click="reset_forms('form_education', 'form_address_education')">Apagar</v-btn>
                      <v-btn v-if="edit_ef_index == index_address" color="grey lighten-1" dark class="mr-4 mb-3" @click="back(2)">Voltar</v-btn>
                    </v-list>  
                  </v-form>
                </v-stepper-content>

                <v-stepper-content step="4">
                  <v-form ref="form_skills" v-model="valid_skills" lazy-validation class="mx-2 mb-1">
                    <v-card-title class="indigo--text"><h3>Competências pessoais</h3></v-card-title>
                    <v-card-subtitle class="indigo--text">Descreve as tuas skills relativas a linguagens, carta de condução e competências digitais.</v-card-subtitle>
                    <v-card-title class="black--text"><span class="font-weight-light mt-n2">Línguas maternas atuais: </span><h5>{{eportfolio.competencias_pessoais.lingua_materna.map(el => el.nome).toString().replace(/[|]/g, '')}}</h5></v-card-title>
                    <v-select multiple v-model="eportfolio.competencias_pessoais.lingua_materna" prepend-icon="mdi-flag-variant" :items="countries" label="Linguagem materna" name="lingua_materna" ></v-select>
                    <v-card-title class="black--text"><span class="font-weight-light mt-n2">Línguas não maternas atuais: </span><h5>{{eportfolio.competencias_pessoais.outra_lingua.map(el => el.nome).toString().replace(/[|]/g, '')}}</h5></v-card-title>
                    <v-select multiple v-model="eportfolio.competencias_pessoais.outra_lingua" prepend-icon="mdi-flag-variant" :items="countries" label="Linguagem não materna " name="outra_lingua" ></v-select>
                    
                    <v-switch label="Carta de condução" name="carta_conducao" :value="eportfolio.competencias_pessoais.carta_conducao" @click="change_carta"></v-switch> 

                    <v-card-subtitle class="indigo--text">Competências digitais</v-card-subtitle>
                    
                    <v-list>
                      <v-list-item class="ml-n4" v-for="(cd, index) in eportfolio.competencias_pessoais.comp_digital" :key="index">
                        <v-list-item-action>
                          <v-text-field v-model="cd.competencia" prepend-icon="mdi-laptop" label="Competência digital" name="comp_digital" ></v-text-field>
                        </v-list-item-action>
                      </v-list-item>
                      <v-btn class="ml-8 mt-n8" x-small text color="indigo--text" @click="add">Adicionar outra</v-btn>
                    </v-list>
                    
                    <v-btn :disabled="!valid_skills" color="primary" class="mr-4 mb-3" @click="check_validation('form_skills', null)">Continuar</v-btn>
                    <v-btn color="error" class="mr-4 mb-3" @click="reset_forms('form_skills', null)">Apagar</v-btn>
                    <v-btn color="grey lighten-1" dark class="mr-4 mb-3" @click="back(3)">Voltar</v-btn>
                  </v-form>
                </v-stepper-content>

                <v-stepper-content step="5">
                  <v-list v-for="(ty, index) in eportfolio.tipos" :key="index">
                    <v-card v-if="index < eportfolio.tipos.length && index != edit_ty_index" class="mx-auto" color="indigo" dark  rounded>
                      <v-card-title>
                        <v-icon small left>
                          mdi-clipboard-file-outline
                        </v-icon>
                        <span class="text-subtitle-1 font-weight-light" >{{eportfolio.tipos[index].nome}}</span>
                      </v-card-title>
                  
                      <v-card-actions>
                        <v-list-item class="grow">
                          <v-row align="center" justify="end">
                            <v-btn text icon dark class="mr-1 font-weight-bold" color="rgb(197, 203, 233)" @click="edit_ty_index = index"> 
                              <v-icon> mdi-lead-pencil </v-icon>
                            </v-btn>
                            <v-btn text icon dark  class="mr-1" color="rgb(197, 203, 233)" @click="remove_ty_index(index)"> 
                              <v-icon> mdi-delete </v-icon>
                            </v-btn>
                          </v-row>
                        </v-list-item>
                      </v-card-actions>
                    </v-card>
                  </v-list>
                  <v-form ref="form_extra" v-model="valid_extra" lazy-validation class="mx-2 mb-1">
                    <v-list v-for="(ty, index_extra) in eportfolio.tipos" :key="index_extra">  
                      <v-card-title v-if="edit_ty_index == index_extra" class="indigo--text"><h3>Informação extra</h3></v-card-title>
                      <v-card-subtitle v-if="edit_ty_index == index_extra" class="indigo--text">Adiciona outros tipos de informação ou cria novos.</v-card-subtitle>
                      
                      <v-list-item v-if="edit_ty_index == index_extra">
                        <v-list-item-action>
                          <v-combobox :id="'tipo' + index_extra" v-model="ty.nome" prepend-icon="mdi-alpha-t-box" small-chips :search-input.sync="search" :items="types.params.map(t => t.nome)" label="Tipo" name="tipo" >
                            <template v-slot:no-data>
                              <v-list-item>
                                <v-list-item-content>
                                  <v-list-item-title>
                                    Nenhum resultado para "<strong>{{ search }}</strong>". Pressiona <kbd>enter</kbd> para criar um novo tipo
                                  </v-list-item-title>
                                </v-list-item-content>
                              </v-list-item>
                            </template>
                          </v-combobox>
                        </v-list-item-action>
                      </v-list-item>
                      <v-list-item v-else v-show="false">
                        <v-list-item-action>
                          <v-combobox v-model="ty.nome" prepend-icon="mdi-alpha-t-box" small-chips :search-input.sync="search" :items="types.params.map(t => t.nome)" label="Tipo" name="tipo" >
                            <template v-slot:no-data>
                              <v-list-item>
                                <v-list-item-content>
                                  <v-list-item-title>
                                    Nenhum resultado para "<strong>{{ search }}</strong>". Pressiona <kbd>enter</kbd> para criar um novo tipo
                                  </v-list-item-title>
                                </v-list-item-content>
                              </v-list-item>
                            </template>
                          </v-combobox>
                        </v-list-item-action>
                      </v-list-item>

                      <div v-if="ty.nome && edit_ty_index == index_extra">
                        <div>
                        <v-list v-for="(t, index_extra1) in ty.campo_int" :key="index_extra1">
                          <v-list-item class="ml-4">
                            <v-list-item-action>
                              <v-text-field :id="'campo_int' + index_extra + '-' + index_extra1" v-model="t.nome" prepend-icon="mdi-format-letter-case" label="Nome do campo" name="tipo_int" ></v-text-field>
                            </v-list-item-action>
                            <v-list-item-action>
                              <v-text-field :id="'valor_int' + index_extra + '-' + index_extra1" v-model="t.valor" prepend-icon="mdi-numeric" label="Valor do campo"  ></v-text-field>
                            </v-list-item-action>
                          </v-list-item>
                        </v-list>
                        <v-btn small class="ml-4" text color="indigo--text" @click="add_int(index_extra)">Adicionar campo numérico</v-btn>
                        </div>
                        <div>
                        <v-list v-for="(t, index_extra2) in ty.campo_txt" :key="index_extra2">
                          <v-list-item class="ml-4">
                            <v-list-item-action>
                              <v-text-field :id="'campo_txt' + index_extra + '-' + index_extra2" v-model="t.nome" prepend-icon="mdi-format-letter-case" label="Nome do campo" name="tipo_txt" ></v-text-field>
                            </v-list-item-action>
                            <v-list-item-action>
                              <v-text-field :id="'descricao_txt' + index_extra + '-' + index_extra2" v-model="t.descricao" prepend-icon="mdi-format-text" label="Descrição do campo"  ></v-text-field>
                            </v-list-item-action>
                          </v-list-item>
                        </v-list>
                        <v-btn small class="ml-4" text color="indigo--text" @click="add_text(index_extra)">Adicionar campo textual</v-btn>
                        </div>
                      </div>
                      <div v-else v-show="false">
                        <div>
                          <v-list v-for="(t, index_extra3) in ty.campo_int" :key="index_extra3">
                            <v-list-item class="ml-4">
                              <v-list-item-action>
                                <v-text-field :id="'campo_int' + index_extra + '-' + index_extra3" v-model="t.nome" prepend-icon="mdi-format-letter-case" label="Nome do campo" name="tipo_int" ></v-text-field>
                              </v-list-item-action>
                              <v-list-item-action>
                                <v-text-field :id="'valor_int' + index_extra + '-' + index_extra3" v-model="t.valor" prepend-icon="mdi-numeric" label="Valor do campo"  ></v-text-field>
                              </v-list-item-action>
                            </v-list-item>
                          </v-list>
                          <v-btn small class="ml-4" text color="indigo--text" @click="add_int(index_extra)">Adicionar campo numérico</v-btn>
                        </div>
                        <div>
                          <v-list v-for="(t, index_extra4) in ty.campo_txt" :key="index_extra4">
                            <v-list-item class="ml-4">
                              <v-list-item-action>  
                                <v-text-field :id="'campo_txt' + index_extra + '-' + index_extra4" v-model="t.nome" prepend-icon="mdi-format-letter-case" label="Nome do campo" name="tipo_txt" ></v-text-field>
                              </v-list-item-action>
                              <v-list-item-action>
                                <v-text-field :id="'descricao_txt' + index_extra + '-' + index_extra4" v-model="t.descricao" prepend-icon="mdi-format-text" label="Descrição do campo"  ></v-text-field>
                              </v-list-item-action>
                            </v-list-item>
                          </v-list>
                          <v-btn small class="ml-4" text color="indigo--text" @click="add_text(index_extra)">Adicionar campo textual</v-btn>
                        </div>  
                      </div>
                      
                      <div v-if="edit_ty_index == index_extra">
                        <template>
                          <v-banner single-line class="mb-3">
                            <v-icon slot="icon"  color="warning" size="36">
                              mdi-alert-box
                            </v-icon>
                            <template v-slot:actions>
                              <v-btn color="primary" text @click="new_ty_form(index_extra)"> 
                                Adicionar nova informação extra
                              </v-btn>
                            </template>
                          </v-banner>
                        </template>
                      </div >
                      <v-btn v-if="edit_ty_index == index_extra" :disabled="!valid_extra" color="primary" class="mr-4 mb-3" @click="validate('form_extra')">Continuar</v-btn>
                      <v-btn v-if="edit_ty_index == index_extra" color="error" class="mr-4 mb-3" @click="reset_forms('form_extra', null)">Apagar</v-btn>
                      <v-btn v-if="edit_ty_index == index_extra" color="grey lighten-1" dark class="mr-4 mb-3" @click="back(4)">Voltar</v-btn>
                    </v-list>
                  </v-form>
                </v-stepper-content>
              </v-stepper-items>
            </v-stepper>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { VContainer, VRow, VCol, VLayout, VForm, VTextField, VSelect, VCheckbox, VFileInput, VCard, VCardTitle, VRadio, VRadioGroup, VDivider, VToolbarTitle, VMenu, VDatePicker, VStepper, VStepperHeader, VStepperStep, VStepperItems, VStepperContent, VSwitch, VTextarea, VListItem, VListItemTitle, VListItemAction, VCardSubtitle, VCardText, VCombobox, VListItemGroup, VListItemContent, VBanner, VCardActions, VImg, VListItemAvatar, VListGroup} from 'vuetify/lib'

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
        VDatePicker,
        VStepper,
        VStepperHeader,
        VStepperStep,
        VStepperItems,
        VStepperContent,
        VSwitch,
        VTextarea,
        VListItem,
        VListItemTitle,
        VListItemAction, 
        VCardSubtitle, 
        VCardText, 
        VCombobox,
        VListItemGroup,
        VListItemContent,
        VBanner,
        VCardActions,
        VImg,
        VListItemAvatar,
        VListGroup
    },

    data () {
      return {
        address_education:[],
        address_work: [],
        loading: false,
        submitted: false,
        valid: true,
        valid_work: true,
        valid_education: true,
        valid_skills: true,
        valid_extra: true,
        nameRules: [
          v => !!v || 'Campo obrigatório',
          v => (v && v.length <= 30) || 'No máximo 30 caracteres',
        ],
        emailRules: 
          v => !v || /.+@.+\..+/.test(v) || 'E-mail tem que ser válido',
        fileRules: [
          value => !value || value.size < 1000000 || 'Tamanho da foto no máximo com 1 MB!',
        ],
        siteRules: [
          v => !v || v.match(new RegExp(/[-a-zA-Z0-9@:%_\+.~#?&//=]{2,256}\.[a-z]{2,4}\b(\/[-a-zA-Z0-9@:%_\+.~#?&//=]*)?/gi)) || 'Url tem que ser válido',
        ],
        phoneRules: [
          v => !!v || 'Campo obrigatório',
          v => new RegExp("^(91|92|93|96|97)\\d{7}$").test(v) || 'Telefone tem que ser válido',
        ],
        countries: [],
        qeqs : [],
        checkbox: false,
        birthDateMenu: false,
        birthDateVal: null,
        fromDateMenu_work: false,
        fromDateVal_work: null,
        toDateMenu_work: false,
        toDateVal_work: null,
        fromDateMenu_education: false,
        fromDateVal_education: null,
        toDateMenu_education: false,
        toDateVal_education: null,
        fromDateMenu_skills: false,
        fromDateVal_skills: null,
        toDateMenu_skills: false,
        toDateVal_skills: null,
        validDateMenu: false,
        validDateVal: null,
        minDate: "1930-07-04",
        maxDate: null,
        step_on: 1,
        more_info_work: false,
        more_info_edu: false,
        more_language: false,
        more_types: false,
        search: null,
        formData: new FormData(),
        edit_we_index: 0,
        edit_ef_index: 0,
        edit_cp_index: 0,
        edit_ty_index: 0,
        language: ''
      }
    },


    async created() {
      this.fetchData()

      var curr = new Date()
      var year = curr.getFullYear()
      var month= curr.getMonth() + 1
      if(month < 10)
        month = '0' + month 
      var day = curr.getDate()
      if(day < 10)
        day = '0' + day 
      this.maxDate = year + '-' + month + '-' + day

      this.$store.dispatch('users/get_types')

      for(var t of this.$store.state.users.eport.params.trabalhos){
        console.log(t.endereco)
        await this.$store.dispatch('users/get_address', { id : t.endereco })
        this.address_work.push({
          morada: this.$store.state.users.address.params.morada,
          cod_post: this.$store.state.users.address.params.cod_post,
          cidade: this.$store.state.users.address.params.cidade,
          pais: this.$store.state.users.address.params.pais
        }) 
      }

      for(var e of this.$store.state.users.eport.params.educacoes){
        console.log(e.endereco)
        await this.$store.dispatch('users/get_address', { id : e.endereco })
        this.address_education.push({
          morada: this.$store.state.users.address.params.morada,
          cod_post: this.$store.state.users.address.params.cod_post,
          cidade: this.$store.state.users.address.params.cidade,
          pais: this.$store.state.users.address.params.pais
        }) 
      }

      
    },


    computed: { 
        ...mapState('authentication', ['status']),
        birthDateDisp() {    
            return this.birthDateVal;
        },
        fromDateDisp_work() {    
            return this.fromDateVal_work;
        },
        toDateDisp_work() {    
          if(this.toDateVal_work > this.fromDateVal_work || !this.fromDateVal_work)
            return this.toDateVal_work;
        },
        fromDateDisp_education() {    
            return this.fromDateVal_education;
        },
        toDateDisp_education() {    
          if(this.toDateVal_education > this.fromDateVal_education || !this.fromDateVal_education)
            return this.toDateVal_education;
        },
        validDateDisp() {    
            return this.validDateVal;
        },
        types(){
            return this.$store.state.users.types;
        },
        eportfolio(){
            return this.$store.state.users.eport.params;
        }
    },
    
    methods: {
        onFileSelected(e) {
            this.eportfolio.avatar = e
        },

        check_validation(form, form_address){
          console.log(this.$refs[form])
          console.log(this.$refs[form_address])
          if((this.$refs[form].validate() && form_address == null) || (this.$refs[form].validate() && this.$refs[form_address].validate())){
            this.step_on +=1
            window.scrollTo(0, 0)
          }
        },

        validate_form_list(formElements, form_name){
          let data_work = {}
          let data = {}
          let atual = 0
          for(let i = 0; i < formElements.length; i++){
            const currentElement = formElements[i]
            console.log("current" + currentElement.outerHTML)
            if (!['submit', 'button'].includes(currentElement.type) && typeof currentElement.id !== 'undefined' && currentElement.id.split("-")[0] != 'input'){
              let id = currentElement.id.slice(-1) 
              console.log(id + ' ' + atual)

              if(id == atual + 1 && currentElement.id.split("_")[0] != 'campo' && currentElement.id.split("_")[0] != 'valor' ){
                if(typeof data_work[form_name] === 'undefined')
                  data_work[form_name] = [data]
                else
                  data_work[form_name].push(data)

                atual++
                data = {}
              } 

              console.log("Current: " + currentElement.name)
              if(currentElement.name == 'tipo')
                data[currentElement.name] = { nome : currentElement.value, campo_int : [], campo_txt : []}
              else if(currentElement.name == 'tipo_int'){
                if(typeof data['tipo'].campo_int === 'undefined')
                  data['tipo'].campo_int = [{nome : currentElement.value, valor: formElements[i+1].value}]
                else
                  data['tipo'].campo_int.push({nome : currentElement.value, valor: formElements[i+1].value})
              }
              else if(currentElement.name == 'tipo_txt'){
                if(typeof data['tipo'].campo_txt === 'undefined')
                  data['tipo'].campo_txt = [{'nome' : currentElement.value, 'descricao': formElements[i+1].value}]
                else
                  data['tipo'].campo_txt.push({'nome' : currentElement.value, 'descricao': formElements[i+1].value})
              }  
              else if(currentElement.name == 'data_ini' && typeof data['data_int'] === 'undefined')
                data['data_int'] = { inicio : currentElement.value, fim: '' }
              else if(currentElement.name == 'data_fim')
                data['data_int'].fim = currentElement.value
              else if(currentElement.name == 'qeq')
                data[currentElement.name] = parseInt(currentElement.value.slice(-1), 10)
              else if(currentElement.name == 'class_final' || currentElement.name == 'class_nacional' || currentElement.name == 'num_creditos' )
                data[currentElement.name] = parseInt(currentElement.value, 10)
              else if(currentElement.name)
                data[currentElement.name] = currentElement.value
            }
          }

          if(typeof data_work[form_name] === 'undefined')
            data_work[form_name] = [data]
          else
            data_work[form_name].push(data)

          console.log("FROM_DATA: " + JSON.stringify(data_work))
          console.log("DATA: " + JSON.stringify(data))

          return data_work
        },

        validate_form (formElements){
          let data = {}

          for (let i = 0; i < formElements.length; i++) {
            const currentElement = formElements[i];
            console.log("current" + currentElement.outerHTML)

            if (!['submit', 'file', 'button'].includes(currentElement.type)) {
              if('radio'.includes(currentElement.type)){
                if(currentElement.checked)
                  data[currentElement.name] = currentElement.value;
              }
              else if(currentElement.name == 'lingua_materna' || currentElement.name == 'outra_lingua'){
                var aux = []
                var list = String(currentElement.value).split(",")
                for(let d = 0; d < list.length; d++)
                  aux.push({'nome' : list[d]})
                data[currentElement.name] = aux 
              }
              else if(currentElement.name == 'comp_digital'){
                if(typeof data[currentElement.name] === 'undefined')
                  data[currentElement.name] = [{ "nome" : currentElement.value}]  
                else
                  data[currentElement.name].push({"nome" : currentElement.value})
              }
              else if(currentElement.name)
                  data[currentElement.name] = currentElement.value;
            }

            else if (currentElement.type === 'file') {
              if (currentElement.files.length === 1) {
                  const file = currentElement.files[0];
                  console.log(currentElement.files[0])
                  this.formData.append(`files.${currentElement.name}`, file, file.name);
              } 
              else {
                for (let i = 0; i < currentElement.files.length; i++) {
                    const file = currentElement.files[i];
                    this.formData.append(`files.${currentElement.name}`, file, file.name);
                }
              }
            }
          }
          return data;
        },

        validate (form) {
          console.log("Refs: " + Object.keys(this.$refs))
          console.log("Length: " + Object.keys(this.$refs).length)
            if(this.$refs[form].validate()){
              for(let x = 0; x < Object.keys(this.$refs).length; x++){
                const formElement = this.$refs[Object.keys(this.$refs)[x]].$el
                const formElements = formElement.elements
                let data = {}

                if(Object.keys(this.$refs)[x] == 'form_work' || Object.keys(this.$refs)[x] == 'form_education' || Object.keys(this.$refs)[x] == 'form_address_work' || Object.keys(this.$refs)[x] == 'form_address_education' || Object.keys(this.$refs)[x] == 'form_extra'){
                  data = this.validate_form_list(formElements, Object.keys(this.$refs)[x])
                  this.formData.append(Object.keys(this.$refs)[x], JSON.stringify(data))
                }
                else{
                  data = this.validate_form(formElements)   
                  this.formData.append(Object.keys(this.$refs)[x], JSON.stringify(data))
                } 
              } 

              for (var p of this.formData) {
                  console.log("Elemento do form: " + p);
              }


              var formEport = this.formData
              var formWork = this.formData.get('form_work')
              var formEducation = this.formData.get('form_education')
              var formSkills = this.formData.get('form_skills')
              var formExtra = this.formData.get('form_extra')
              var formAddress = this.formData.get('form_address')
              var formAddressWork = this.formData.get('form_address_work')
              var formAddressEducation = this.formData.get('form_address_education')

              formEport.delete('form_work')
              formEport.delete('form_education')
              formEport.delete('form_extra')
              formEport.delete('form_address')
              formEport.delete('form_address_work')
              formEport.delete('form_address_education')
              formEport.delete('form_skills')

              for (var p of formEport) {
                  console.log("Elemento do form_v1: " + p);
              }

              this.$store.dispatch('users/editEportfolio', { eportfolio: formEport, address: formAddress, work: formWork, address_work: formAddressWork, education: formEducation, address_education: formAddressEducation, skills: formSkills, type: formExtra, user : this.$store.state.users.user.params, id_eport: this.$store.state.users.eport.params.id })
            }
        },
        reset () {
            this.$refs.form.reset()
        },
        reset_forms(form, form_address){
          this.$refs[form].reset()
          if(form_address != null)
            this.$refs[form_address].reset()
        },

        fetchData() { 
          var list = require("../_services/countries")
          this.countries = list.map(a => a.nome)
          this.qeqs = ["Nível QEQ1","Nível QEQ2","Nível QEQ3","Nível QEQ4","Nível QEQ5","Nível QEQ6","Nível QEQ7","Nível QEQ8"]
        
        },

        add(){
          this.eportfolio.competencias_pessoais.comp_digital.push({"nome" : ''})
          console.log(this.eportfolio.competencias_pessoais.comp_digital)
        },

        add_int(index){
          this.eportfolio.tipos[index].campo_int.push({"nome" : '', 'valor' : ''})
          console.log(this.eportfolio.tipos[index].campo_int)
        },

        add_text(index){
          this.eportfolio.tipos[index].campo_txt.push({"nome" : '', 'descricao' : ''})
          console.log(this.eportfolio.tipos[index].campo_txt)
        },

        back_home(){
            this.$router.go(-1)
        },

        back(step){
          this.step_on = step
          window.scroll(0,0) 
        },
        
        change_carta(){
          this.eportfolio.competencias_pessoais.carta_conducao = !this.eportfolio.competencias_pessoais.carta_conducao
        },

        change_work_em_curso(index){
          this.eportfolio.trabalhos[index].em_curso = !this.eportfolio.trabalhos[index].em_curso
        },

        change_education_em_curso(index){
          this.eportfolio.educacoes[index].em_curso = !this.eportfolio.educacoes[index].em_curso
        },

        new_we_form(index){
          var f = document.getElementById('funcao' + index).value
          var e = document.getElementById('ent_empregadora' + index).value
          var r = document.getElementById('responsabilidades' + index).value
          if(f != null && f != '' && e != null && e != '' && r != null && r != ''){
            this.eportfolio.trabalhos.push({
              funcao: '',
              ent_empregadora: '',
              cidade: '',
              data_int: null,
              em_curso: false,
              responsabilidades:'',
              atividade_setor: '',
              departamento: '',
              email_organizacao: '',
              sitio_web: '',
              hiper: '',
            })
            this.address_work.push({
              morada: '',
              cod_post: '',
              cidade: '',
              pais: ''
            })
            this.edit_we_index += 1
          }
        },

        new_ef_form(index){
          var t = document.getElementById('titulo' + index).value
          var o = document.getElementById('organizacao' + index).value
          var d = document.getElementById('descricao' + index).value
          if(t != null && t != '' && o != null && o != '' && d != null && d != ''){
            this.eportfolio.educacoes.push({
                titulo: '',
                organizacao: '',
                em_curso: false,
                area: '',
                class_final: '',
                tese: '',
                assunto_princ: '',
                qeq: '',
                class_nacional: '',
                tipo_creditos: '',
                num_creditos: '',
                valido_ate: '',
                sitio_web: '',
                hiper: '',
                descricao: '',
                data_int: null,
            })
            this.address_education.push({
              morada: '',
              cod_post: '',
              cidade: '',
              pais: ''
            })
            this.edit_ef_index += 1
          }
        },

        new_ty_form(index){
          console.log(this.$refs['form_extra'])
          console.log(document.getElementById('tipo' + index))
          var t = this.eportfolio.tipos[index].nome
          if(t != null && t != ''){
            this.eportfolio.tipos.push({
                nome: '',
                campo_int:[{nome: '', valor: ''}],
                campo_txt:[{nome: '', descricao: ''}]
            })
            this.edit_ty_index += 1
          }
        },

        remove_we_index(index){
          this.eportfolio.trabalhos.splice(index,1)
          if(this.edit_we_index != 0)
            this.edit_we_index -= 1
        },

        remove_ef_index(index){
          this.eportfolio.educacoes.splice(index,1)
          if(this.edit_ef_index != 0)
            this.edit_ef_index -= 1
        },

        remove_ty_index(index){
          this.eportfolio.tipos.splice(index,1)
          if(this.edit_ty_index != 0)
            this.edit_ty_index -= 1
        }
    }
};
</script>
<style scoped>
  .v-input--radio-group >>> legend{
    margin-top: -20px;
  }

  .v-input--radio-group >>> .v-radio{
    margin-top: -5px;
  }
</style>