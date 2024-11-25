import express from 'express';

//Controllers
import TeamController from '../controllers/TeamController.js';

//Middlewares
import verifyToken from '../middlewares/verifyToken.js';


class TeamRouter {
    constructor() {
        this.router = express.Router();
        this.TeamController = TeamController;
        this.setupRoutes();
    }

    setupRoutes() {
        //INDEX
        this.router.get('/teams', verifyToken, (req, res) => this.TeamController.index(req, res));

        //CREATE
        this.router.get('/teams/create', verifyToken, (req, res) => {
            return res.render('Team/create', { layout: 'main', title: 'Cadastrar Equipe' });
        });
        this.router.post('/teams/store', verifyToken, (req, res) => this.TeamController.store(req, res));

        //UPDATE
        this.router.get('/teams/:id/edit', verifyToken, (req, res) => this.TeamController.edit(req, res));
        this.router.post('/teams/:id/update', verifyToken, (req, res) => this.TeamController.update(req, res));

        //DELETE
        this.router.delete('/teams/:id/delete', verifyToken, (req, res) => this.TeamController.delete(req, res));
    }

    getRouter(){
        return this.router
    }
}

export default new TeamRouter();