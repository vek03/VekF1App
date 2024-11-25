import isLogged from "../helpers/isLogged.js";



const redirectIfAuth = async (req, res, next) => {
    const { decodedToken, currentUser } = await isLogged(req);

    if (currentUser) {
        res.render("errors/error", {layout: "guest", codError: "403", textError: 'Forbidden!'});
    }

    next();
};


export default redirectIfAuth;