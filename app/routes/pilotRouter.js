import express from 'express';

//Controllers
import PilotController from '../controllers/PilotController.js';

//Middlewares
import verifyToken from '../middlewares/verifyToken.js';


class PilotRouter {
    constructor() {
        this.router = express.Router();
        this.PilotController = PilotController;
        this.setupRoutes();
    }

    setupRoutes() {
        //INDEX
        this.router.get('/pilots', verifyToken, (req, res) => this.PilotController.index(req, res));
    }

    getRouter(){
        return this.router
    }
}

export default new PilotRouter();