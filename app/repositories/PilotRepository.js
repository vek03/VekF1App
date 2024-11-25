import { db } from '../config/firebase.js';



class PilotRepository{
    async list(){
        try{
            const Pilots = await db.collection('pilots').get();
            return Pilots;
        }catch(error){
            console.error('Error getting Pilots', error);
        }
    }

    async listByTeam(teamId){
        try{
            return await db.collection('pilots').where('equipeId', '==', teamId).get();
        }catch(error){
            console.error('Error getting Pilots', error);
        }
    }

    async getById(id){
        try{
            return await db.collection('pilots').doc(id).get();
        }catch(error){
            console.error('Error getting Pilot by id', error);
        }
    }

    async create(Pilot){
        try{
            const response = await db.collection('pilots').add(Pilot);
            return response;
        }catch(error){
            console.error('Error creating Pilot', error);
        }
    }

    async update(id, Pilot){
        try{
            await db.collection('pilots').doc(id).update(Pilot);
            return true;
        }catch(error){
            console.error('Error updating Pilot', error);
        }
    }

    async delete(id){
        try{
            await db.collection('pilots').doc(id).delete();
            return true;
        }catch(error){
            console.error('Error deleting Pilot', error);
        }
    }
}


export default new PilotRepository();