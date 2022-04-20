import Vue from 'vue';
import VueRouter from 'vue-router';

import Home from '@/views/home';
// import store from '@/store';
import { about, characters, home, maps, monsters, newCharacter, spells } from '@/routes/breadcrumbs.js';
import { composeGuards, createLangGuard } from '@/routes/guards.js';
import { loadLanguageAsync } from '@/plugins/i18n';

Vue.use(VueRouter);

// const isLoggedIn = createLoginGuard(store);
// const hasAuthorities = createAuthoritiesGuard(store);
const checkLang = createLangGuard(loadLanguageAsync);

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home,
            meta: {
                breadcrumbs: [home],
            },
            beforeEnter: composeGuards(checkLang),
        },
        {
            path: '/about',
            name: 'about',
            component: () => import('@/views/about'),
            meta: {
                breadcrumbs: [about],
            },
            // beforeEnter: composeGuards(isLoggedIn, hasAuthorities('ROLE_ADMIN')),
        },
        {
            path: '/monsters',
            name: 'monsters',
            component: () => import('@/views/monsters'),
            meta: {
                breadcrumbs: [home, monsters],
            },
            beforeEnter: composeGuards(checkLang),
        },
        {
            path: '/characters',
            name: 'characters',
            component: () => import('@/views/characters'),
            meta: {
                breadcrumbs: [home, characters],
            },
            beforeEnter: composeGuards(checkLang),
        },
        {
            path: '/new-character',
            name: 'newCharacter',
            component: () => import('@/views/characters/components/CharacterCreator'),
            meta: {
                breadcrumbs: [home, characters, newCharacter],
            },
            beforeEnter: composeGuards(checkLang),
        },
        {
            path: '/spells',
            name: 'spells',
            component: () => import('@/views/spells'),
            meta: {
                breadcrumbs: [home, spells],
            },
            beforeEnter: composeGuards(checkLang),
        },
        {
            path: '/maps',
            name: 'maps',
            component: () => import('@/views/maps'),
            meta: {
                breadcrumbs: [home, maps],
            },
            beforeEnter: composeGuards(checkLang),
        },
        {
            path: '*',
            component: () => import('@/components/DndPageNotFound.vue'),
        },
    ],
});

export default router;
