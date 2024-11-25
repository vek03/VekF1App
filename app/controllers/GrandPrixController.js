import GrandPrixRepository from '../repositories/GrandPrixRepository.js';
import PilotController from './PilotController.js';



class GrandPrixController {
    async getPodio(req, res){
        try{
            const gpId = req.body.gpId;

            if(!gpId){
                const pilotos = await PilotController.getPilotsWithTeams();

                return res.status(200).json({status: true, message: 'Pódio retornado com sucesso!', data: pilotos});
            }
            else{
                const gp = await GrandPrixRepository.getById(gpId);
                const podio = gp.data().podio;
                
                return res.status(200).json({status: true, message: 'Pódio retornado com sucesso!', data: pilotosGp});
            }
        }catch(error){
            console.log(error);
            return res.status(500).json({status: false, message: 'Erro ao buscar o pódio!'});
        }
    }

    async index(req, res) {
        try{
            const grandPrixs = await GrandPrixRepository.list()
            .then((grandPrixs) => {
                const grandPrixsData = grandPrixs.docs.map((grandPrix) => {
                    const dataInicioOriginal = grandPrix.data().data_inicio;  // Supondo que o campo de data seja "data"
                    const dataFimOriginal = grandPrix.data().data_fim;  // Supondo que o campo de data seja "data"

                    // Formatar a data para o formato desejado
                    const formattedDataInicio = new Date(dataInicioOriginal).toLocaleDateString('pt-BR', {
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric'
                    });

                    const formattedDataFim = new Date(dataFimOriginal).toLocaleDateString('pt-BR', {
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric'
                    });

                    return {
                        id: grandPrix.id,
                        ...grandPrix.data(),
                        formattedData_inicio: formattedDataInicio,
                        formattedData_fim: formattedDataFim
                    }
                });
    
                return grandPrixsData;
            });
    
            console.log('GPs:', grandPrixs);
            return res.render('GrandPrix/index', { layout: 'main', title: 'GPs', grandPrixs: grandPrixs });
        }catch(error){
            console.error('Erro ao consultar equipes:', error);
            res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
        }
    }

    async store(req, res) {
        try{
            const { nome, pais, cidade, pista, data_inicio, data_fim, podio } = req.body;
            const GrandPrix = await GrandPrixRepository.create({ nome, pais, cidade, pista, data_inicio, data_fim, podio });
    
            if (GrandPrix) {
                req.session.success = 'GP Cadastrada com Sucesso!';
                return res.redirect('/grandPrix');
            } else {
                throw new Error('Erro ao Cadastrar GP!');
            }
        }catch(error){
            console.error('Erro ao cadastrar equipe:', error);
            req.session.error = 'Erro ao Cadastrar GP!';
            return res.redirect('/grandPrix/create');
        }
    }

    async edit(req, res) {
        try{
            const grandPrix = await GrandPrixRepository.getById(req.params.id).then((grandPrix) => {
                return {
                    id: grandPrix.id,
                    ...grandPrix.data()
                }
            });
        
            return res.render('grandPrix/edit', { layout: 'main', title: 'Editar GP', grandPrix: grandPrix });
        }
        catch(error){
            console.error('Erro ao Visualizar GP:', error);
            req.session.error = 'Erro ao Visualizar GP!';
            return res.redirect('/grandPrix');
        }
    }

    async update(req, res) {
        try{
            const id = req.params.id;
            const { nome, pais, cidade, pista, data_inicio, data_fim, podio } = req.body;
            const GrandPrix = await GrandPrixRepository.update(id, { nome, pais, cidade, pista, data_inicio, data_fim, podio });
    
            if (GrandPrix) {
                req.session.success = 'GP Atualizada com Sucesso!';
                return res.redirect('/grandPrix');
            } else {
                throw new Error('Erro ao Atualizar GP!');
            }
        }
        catch(error){
            console.error('Erro ao atualizar equipe:', error);
            req.session.error = 'Erro ao Atualizar GP!';
            return res.redirect('/grandPrix/' + id + '/edit');
        }
    }

    async delete(req, res) {
        try{
            const GrandPrix = await GrandPrixRepository.delete(req.params.id);

            if (GrandPrix) {
                req.session.success = 'GP Deletada com Sucesso!';
                return res.redirect('/grandPrix');
            } else {
                throw new Error('Erro ao Deletar GP!');
            }
        }
        catch(error){
            console.error('Erro ao Deletar GP:', error);
            req.session.error = 'Erro ao Deletar GP!';
            return res.redirect('/grandPrix');
        }
    }
}


export default new GrandPrixController();