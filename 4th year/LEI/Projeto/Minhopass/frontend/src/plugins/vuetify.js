import Vue from 'vue'
import Vuetify, {VApp, VToolbar, VBtn, VSpacer, VIcon, VList} from 'vuetify/lib'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify, {
    components: {VApp, VToolbar, VBtn, VSpacer, VIcon, VList},
})

const opts = {}

export default new Vuetify(opts)