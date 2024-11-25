import TeamRepository from '../repositories/TeamRepository.js';



class TeamController {
    async index(req, res) {
        try{
            const teams = await TeamRepository.list()
            .then((teams) => {
                const teamsData = teams.docs.map((team) => {
                    return {
                        id: team.id,
                        ...team.data()
                    }
                });
    
                return teamsData;
            });
    
            console.log('Equipes:', teams);
            return res.render('Team/index', { layout: 'main', title: 'Equipes', teams: teams });
        }catch(error){
            console.error('Erro ao consultar equipes:', error);
            res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
        }
    }

    async store(req, res) {
        try{
            const { nome, imagem, corPrimaria, corSecundaria } = req.body;
            const Team = await TeamRepository.create({ nome, imagem, corPrimaria, corSecundaria });
    
            if (Team) {
                req.session.success = 'Equipe Cadastrada com Sucesso!';
                return res.redirect('/teams');
            } else {
                throw new Error('Erro ao Cadastrar Equipe!');
            }
        }catch(error){
            console.error('Erro ao cadastrar equipe:', error);
            req.session.error = 'Erro ao Cadastrar Equipe!';
            return res.redirect('/teams/create');
        }
    }

    async edit(req, res) {
        try{
            const team = await TeamRepository.getById(req.params.id).then((team) => {
                return {
                    id: team.id,
                    ...team.data()
                }
            });
        
            return res.render('team/edit', { layout: 'main', title: 'Editar Equipe', team: team });
        }
        catch(error){
            console.error('Erro ao Visualizar Equipe:', error);
            req.session.error = 'Erro ao Visualizar Equipe!';
            return res.redirect('/teams');
        }
    }

    async update(req, res) {
        try{
            const id = req.params.id;
            const { nome, imagem, corPrimaria, corSecundaria } = req.body;
            const Team = await TeamRepository.update(id, { nome, imagem, corPrimaria, corSecundaria });
    
            if (Team) {
                req.session.success = 'Equipe Atualizada com Sucesso!';
                return res.redirect('/teams');
            } else {
                throw new Error('Erro ao Atualizar Equipe!');
            }
        }
        catch(error){
            console.error('Erro ao atualizar equipe:', error);
            req.session.error = 'Erro ao Atualizar Equipe!';
            return res.redirect('/teams/' + id + '/edit');
        }
    }

    async delete(req, res) {
        try{
            const teamPilots = await PilotRepository.listByTeam(req.params.id);

            if (teamPilots.docs.length > 0) {
                req.session.error = 'Não é possível deletar uma equipe com pilotos cadastrados!';
                return res.redirect('/teams');
            }

            const Team = await TeamRepository.delete(req.params.id);

            if (Team) {
                req.session.success = 'Equipe Deletada com Sucesso!';
                return res.redirect('/teams');
            } else {
                throw new Error('Erro ao Deletar Equipe!');
            }
        }
        catch(error){
            console.error('Erro ao Deletar Equipe:', error);
            req.session.error = 'Erro ao Deletar Equipe!';
            return res.redirect('/teams');
        }
    }
}


export default new TeamController();