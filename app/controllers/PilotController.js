import PilotRepository from '../repositories/PilotRepository.js';
import TeamRepository from '../repositories/TeamRepository.js';



class PilotController {
    async index(req, res) {
        try {
            const pilotsWithTeams = await this.getPilotsWithTeams();
    
            return res.render('pilot/index', { 
                layout: 'main', 
                title: 'Pilotos', 
                pilots: pilotsWithTeams 
            });
    
        } catch (error) {
            console.error('Erro ao consultar pilotos ou equipes:', error);
            req.session.error = 'Erro ao buscar pilotos e equipes!';
            return res.redirect('/');
        }
    }

    async getPilotsWithTeams(){
        // 1. Buscar todos os pilotos
        const pilotosSnapshot = await PilotRepository.list();
                    
        // 2. Verificar se a consulta retornou pilotos
        if (pilotosSnapshot.empty) {
            console.log('Nenhum piloto encontrado');
            return false
        }

        // 3. Criar um array para armazenar os dados dos pilotos com as equipes
        const pilotsWithTeams = [];

        // 4. Iterar sobre os pilotos e buscar a equipe de cada um
        for (const pilotoDoc of pilotosSnapshot.docs) {
            const piloto = pilotoDoc.data();
            const equipeId = piloto.equipeId;
            
            console.log('Piloto:', piloto.nome, 'EquipeId:', equipeId);
            
            // 5. Buscar a equipe correspondente
            const equipeDoc = await TeamRepository.getById(equipeId);
            if (equipeDoc.exists) {
                const equipe = equipeDoc.data();
                console.log(`  Equipe: ${equipe.nome}`);
                console.log(`  Logo: ${equipe.imagem}`);

                // 6. Adicionar a equipe aos dados do piloto
                piloto.equipe = equipe;
            } else {
                console.log('Equipe não encontrada');
                piloto.equipe = {}; // Se não encontrar a equipe, adiciona um objeto vazio
            }

            // 7. Adicionar o piloto com a equipe ao array
            pilotsWithTeams.push(piloto);
        }

        return pilotsWithTeams;
    }
}


export default new PilotController();