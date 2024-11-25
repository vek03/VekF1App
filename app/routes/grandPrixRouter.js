import express from 'express';

//Controllers
import GrandPrixController from '../controllers/GrandPrixController.js';

//Middlewares
import verifyToken from '../middlewares/verifyToken.js';


class GrandPrixRouter {
    constructor() {
        this.router = express.Router();
        this.GrandPrixController = GrandPrixController;
        this.setupRoutes();
    }

    setupRoutes() {
        //INDEX
        this.router.get('/grandPrix', verifyToken, (req, res) => this.GrandPrixController.index(req, res));

        //CREATE
        this.router.get('/grandPrix/create', verifyToken, (req, res) => {
            return res.render('grandPrix/create', { layout: 'main', title: 'Cadastrar Grand Prix' });
        });
        this.router.post('/grandPrix/store', verifyToken, (req, res) => this.GrandPrixController.store(req, res));

        //UPDATE
        this.router.get('/grandPrix/:id/edit', verifyToken, (req, res) => this.GrandPrixController.edit(req, res));
        this.router.post('/grandPrix/:id/update', verifyToken, (req, res) => this.GrandPrixController.update(req, res));

        //DELETE
        this.router.delete('/grandPrix/:id/delete', verifyToken, (req, res) => this.GrandPrixController.delete(req, res));

        //FUNCTIONS
        this.router.post('/grandPrix/getPodio', verifyToken, (req, res) => this.GrandPrixController.getPodio(req, res));
    }

    getRouter(){
        return this.router
    }
}

export default new GrandPrixRouter();