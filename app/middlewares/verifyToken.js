import isLogged from "../helpers/isLogged.js";



const verifyToken = async (req, res, next) => {
    try {
        const { decodedToken, currentUser } = await isLogged(req);

        if (!currentUser) {
            throw new Error('Usuário não autenticado');
        }
    
        req.currentUser = currentUser;
        req.decodedToken = decodedToken;
        next();
    } catch (error) {
        console.error('Error verifying token:', error);

        if (error.code === 'auth/id-token-expired') {
            res.clearCookie('access_token');  
            return res.redirect('/login'); 
        }

        res.render("errors/error", {layout: "guest", codError: "403", textError: 'Não Autorizado!'});
    }
};


export default verifyToken;