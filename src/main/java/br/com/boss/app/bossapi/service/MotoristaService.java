package br.com.boss.app.bossapi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Motorista;
import br.csi.model.Motorista_Caminhao;
import br.csi.util.ParamConverter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MotoristaService {

    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final Motorista_CaminhaoDAO motorista_caminhaoDAO = new Motorista_CaminhaoDAO();
    private final ParamConverter paramConverter = new ParamConverter();

    public ArrayList<Motorista> selectAll(String offset, String limit) {
        Integer offsetNumber = paramConverter.convertStringToInt(offset);
        Integer limitNumber = paramConverter.convertStringToInt(limit);

        offsetNumber = offsetNumber == null ? 0 : offsetNumber;
        limitNumber = limitNumber == null ? 0 : limitNumber;

        return motoristaDAO.selectAll(offsetNumber, limitNumber);
    }

    public Motorista selectUnique(@NotNull String codMotorista) {
        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);

        if (codMotoristaNumber == null || codMotoristaNumber <= 0){
            return null;
        }
        else{
            Motorista motorista = motoristaDAO.selectUnique(codMotoristaNumber);

            if (motorista != null){
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(motorista.getCod());

                if (relacao != null){
                    motorista.setCaminhao(new CaminhaoDAO().selectUnique(relacao.getCodCaminhao()));
                    motorista.setDataCaminhao(relacao.getDataInicio());
                }

                return motorista;
            }
            else {
                throw new IllegalArgumentException("Motorista não encontrado");
            }
        }
    }

    public boolean persist(@NotNull String operacao, String codMotorista, @NotNull String nome,  String endereco,
                           String telefonePrincipal, @NotNull String telefoneAlternativo, String telefoneAlternativo2,
                           String codCaminhao) {
        validarCampos(operacao,nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);

        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);
        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);
        endereco = paramConverter.convertBlankStringToNull(endereco);
        telefoneAlternativo = paramConverter.convertBlankStringToNull(telefoneAlternativo);
        telefoneAlternativo2 = paramConverter.convertBlankStringToNull(telefoneAlternativo2);

        Motorista motorista = new Motorista(codMotoristaNumber,nome, endereco, telefonePrincipal, telefoneAlternativo, telefoneAlternativo2);

        if (operacao.equals("update")){
            if (motorista.getCod() == null || motoristaDAO.selectUnique(motorista.getCod()) == null){
                throw new IllegalArgumentException("Motorista não encontrado");
            }

            if (motoristaDAO.update(motorista)){
                return gerarRelacionamento(codMotoristaNumber, codCaminhaoNumber);
            }
        }
        else if (operacao.equals("insert")){
            codMotoristaNumber = motoristaDAO.insert(motorista);

            return gerarRelacionamento(codMotoristaNumber, codCaminhaoNumber);
        }

        return false;
    }


    public boolean delete(@NotNull String codMotorista){
        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);

        if (codMotoristaNumber == null || codMotoristaNumber <= 0){
            throw new IllegalArgumentException("Código de motorista inválido");
        }
        else{
            if (motoristaDAO.selectUnique(codMotoristaNumber) == null){
                throw new IllegalArgumentException("Motorista não encontrado");
            }
            else{
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_motorista(codMotoristaNumber);

                if (relacao != null){
                    if (!motorista_caminhaoDAO.delete(relacao)) {
                        throw new IllegalArgumentException("Erro ao deletar relacionamento");
                    }
                }

                return motoristaDAO.delete(codMotoristaNumber);
            }
        }
    }

    private boolean trocarRelacionamento(Integer codMotorista, Integer codCaminhao) {
        Motorista_Caminhao relacaoNova = new Motorista_Caminhao(codMotorista, codCaminhao);
        Motorista_Caminhao relacaoAntiga = motorista_caminhaoDAO.selectByCod_caminhao(codCaminhao);

        if (relacaoAntiga != null){
            if (relacaoAntiga.getCodMotorista() == codMotorista) {
                return true;
            }
            else {
                motorista_caminhaoDAO.delete(relacaoAntiga);
            }
        }

        return motorista_caminhaoDAO.insert(relacaoNova);

    }

    private boolean gerarRelacionamento(Integer codMotorista, Integer codCaminhao){
        if (codCaminhao == null || codCaminhao <= 0){
            return true;
        }
        else{
            return trocarRelacionamento(codMotorista, codCaminhao);
        }
    }

    private void validarCampos(String operacao,String nome, String endereco, String telefonePrincipal, String telefoneAlternativo, String telefoneAlternativo2){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            throw new IllegalArgumentException("Operação inválida");
        }
        else if (nome.isBlank() || telefonePrincipal.isBlank() ){
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos");
        }
        else if (!(endereco.length() <= 75 && telefoneAlternativo.length() <= 15 && telefoneAlternativo2.length() <= 15
                && telefonePrincipal.length() <= 15)){
            throw new IllegalArgumentException("Tamanho máximo de caracteres ultrapassado em algum(s) campo(s)");
        }
    }
}
