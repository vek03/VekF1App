//Iniciando Bibliotecas
import express from 'express';
import session from 'express-session';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';
import handlebars from "express-handlebars";
import helpers from "handlebars-helpers";
import dotenv from 'dotenv';

//Helpers
import isLogged from './app/helpers/isLogged.js';

//Routers
import AuthRouter from "./app/routes/authRouter.js";
import grandPrixRouter from './app/routes/grandPrixRouter.js';
import pilotRouter from './app/routes/pilotRouter.js';
import teamRouter from './app/routes/teamRouter.js';

dotenv.config();
const PORT = process.env.PORT || 8080;

const app = express();

//Definindo Atalhos
app.use('/bootstrap', express.static('node_modules/bootstrap/dist'));
app.use('/public', express.static('assets/public'));
app.use('/jquery', express.static('node_modules/jquery/dist'));
app.use('/inputmask', express.static('node_modules/inputmask/dist'));
app.use('/sweetalert2', express.static('node_modules/sweetalert2/dist'));

//Definindo layout das páginas
app.engine("handlebars", handlebars.engine({ defaultLayout: "main", helpers: helpers }));
app.set("view engine", "handlebars");

app.set('views', './assets/views');

//Iniciando Middleware que recolhe os dados do formulário via POST
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

//Iniciando Sessions
app.use(session({
    secret: 'key',
    resave: true,
    saveUninitialized: true
}));

// Middleware para passar informações do usuário para as views
app.use((req, res, next) => {
    res.locals.user = req.session.user;
    next();
});

//Iniciando Cookies Auth e Router
app.use(express.json());
app.use(cookieParser());


//ROTAS
//AUTH
app.use('/', AuthRouter.getRouter());

//GRANDPRIX
app.use('/', grandPrixRouter.getRouter());

//PILOT
app.use('/', pilotRouter.getRouter());

//TEAM
app.use('/', teamRouter.getRouter());


//INDEX
app.get('/', async (req, res) => {
    try{
        if(await isLogged(req)){
            return res.redirect("/dashboard");
        }
        else{
            return res.redirect("/login");
        }
    }catch(error){
        console.log('Erro ao Redirecionar: ', error);
        return res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
    }
});



//Verificar Erro
app.get('/getError', (req, res) => {
    const error = req.session.error;
    delete req.session.error;

    if (error) {
        res.json({ error });
    } else {
        res.json({});
    }
});



//Verificar Sucesso
app.get('/getSuccess', (req, res) => {
    const success = req.session.success;
    delete req.session.success;

    if (success) {
        res.json({ success });
    } else {
        res.json({});
    }
});



//FALLBACK
app.use((req, res) => {
    return res.render("errors/error", {layout: "guest", codError: "404", textError: 'Página Não Encontrada!'});
})



//Iniciando Servidor
app.listen(PORT, () => {
    console.log(`Servidor Ativo na Porta ${PORT}!`);
});