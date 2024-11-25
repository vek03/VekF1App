import { db } from '../config/firebase.js';



class TeamRepository{
    async list(){
        try{
            return await db.collection('teams').get();
        }catch(error){
            console.error('Error getting Teams', error);
            return new Error('Error getting Teams');
        }
    }

    async getById(id){
        try{
            return await db.collection('teams').doc(id).get();
        }catch(error){
            console.error('Error getting Team by id', error);
            return new Error('Error getting Team by id');
        }
    }

    async create(Team){
        try{
            return await db.collection('teams').add(Team);
        }catch(error){
            console.error('Error creating Team', error);
            return new Error('Error creating Team');
        }
    }

    async update(id, Team){
        try{
            return await db.collection('teams').doc(id).update(Team);
        }catch(error){
            console.error('Error updating Team', error);
            return new Error('Error updating Team');
        }
    }

    async delete(id){
        try{
            return await db.collection('teams').doc(id).delete();
        }catch(error){
            console.error('Error deleting Team', error);
            return new Error('Error deleting Team');
        }
    }
}


export default new TeamRepository();