import { admin } from "../../app/config/firebase.js";
import { getAuth } from "../../app/config/firebase.js";



const isLogged = async (req) => {
    const access_token = req.cookies.access_token;

    if(access_token && getAuth().currentUser){
        try{
            const decodedToken = await admin.auth().verifyIdToken(access_token);
        
            return { decodedToken, currentUser: getAuth().currentUser };
        }catch(error){
            throw new Error(error);
        }
    }

    return false;
};


export default isLogged;