import express from 'express';

//Controllers
import AuthController from '../controllers/AuthController.js';

//Middlewares
import verifyToken from '../middlewares/verifyToken.js';
import redirectIfAuth from '../middlewares/redirectIfAuth.js';


class AuthRouter {
    constructor() {
        this.router = express.Router();
        this.AuthController = AuthController;
        this.setupRoutes();
    }

    setupRoutes() {
        //LOGIN
        this.router.get('/login', redirectIfAuth, (req, res) => {
            return res.render('auth/login', { layout: 'guest', title: 'Login' });
        });
        this.router.post('/login', redirectIfAuth, (req, res) => this.AuthController.login(req, res));


        //REGISTER
        this.router.get('/register', redirectIfAuth, (req, res) => {
            return res.render('auth/register', { layout: 'guest', title: 'Cadastro' });
        });
        this.router.post('/register', redirectIfAuth, (req, res) => this.AuthController.register(req, res));


        //DASHBOARD
        this.router.get('/dashboard', verifyToken, (req, res) => {
            return res.render('dashboard', { layout: 'main', title: 'Dashboard' });
        });


        //PROFILE
        this.router.get('/profile', verifyToken, (req, res) => {
            return res.render('auth/profile', { layout: 'main', title: 'Perfil' });
        });

        this.router.post('/profile/alterPassword', verifyToken, (req, res) => this.AuthController.alterPassword(req, res));

        this.router.post('/profile/resetPassword', verifyToken, (req, res) => this.AuthController.resetPassword(req, res));


        //LOGOUT
        this.router.post('/logout', verifyToken, (req, res) => this.AuthController.logout(req, res));
    }

    getRouter(){
        return this.router
    }
}

export default new AuthRouter();