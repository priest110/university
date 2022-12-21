import Vue from 'vue';
import Router from 'vue-router';
import { authentication } from '../_store/authentication.module';

import HomePage from '../views/HomePage'
import EportfolioPage from '../views/EportfolioPage'
import LoginPage from '../views/LoginPage'
import RegisterPage from '../views/RegisterPage'
import PerfilPage from '../views/PerfilPage'
import CreateEportPage from '../views/CreateEportPage'
import CreatePage from '../views/CreatePage'
import EditPerfilPage from '../views/EditPerfilPage'
import EditEportPage from '../views/EditEportPage'
import LibraryPage from '../views/LibraryPage'
import FeedPage from '../views/FeedPage'
import AccountPage from '../views/AccountPage'
import AboutPage from '../views/AboutPage'
import HelpPage from '../views/HelpPage'

Vue.use(Router);

export const router = new Router({
  mode: 'history',
  routes: [
    { path: '/home', component: HomePage },
    { path: '/eportfolio', component: EportfolioPage },
    { path: '/login', component: LoginPage },
    { path: '/registar', component: RegisterPage },
    { path: '/users/:userId', name: 'users', component: PerfilPage },
    { path: '/criar-eportfolio', component: CreateEportPage },
    { path: '/criar', component: CreatePage },
    { path: '/editar-perfil', component: EditPerfilPage },
    { path: '/editar-eportefolio', component: EditEportPage },
    { path: '/biblioteca', component: LibraryPage },
    { path: '/feed', component: FeedPage },
    { path: '/conta', component: AccountPage },
    { path: '/sobre_nos', component: AboutPage },
    { path: '/ajuda', component: HelpPage },
    // otherwise redirect to home
    { path: '*', redirect: '/home' }
  ]
});

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ['/login', '/registar', '/home', '/ajuda', '/sobre_nos'];
  const authRequired = !publicPages.includes(to.path);
  console.log(authRequired);
  const loggedIn = localStorage.getItem('user');

  if (!authRequired && loggedIn && to.fullPath != '/ajuda' && to.fullPath != '/sobre_nos')
    return next('/eportfolio');
  
  console.log(to.fullPath + " " + from.fullPath)
  if((!authRequired || to.fullPath == '/eportfolio') && from.fullPath == '/criar' && authentication.user != null)
    return next('/criar')

  if((!authRequired || to.fullPath == '/criar') && from.fullPath == '/eportfolio' && authentication.user != null)
    return next('/eportfolio')  

  if (authRequired && !loggedIn) {
    return next('/home');
  }

  next();
})