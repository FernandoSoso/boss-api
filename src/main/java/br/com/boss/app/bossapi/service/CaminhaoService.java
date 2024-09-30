package br.com.boss.app.bossapi.service;

import br.csi.dao.CaminhaoDAO;
import br.csi.dao.MotoristaDAO;
import br.csi.dao.Motorista_CaminhaoDAO;
import br.csi.model.Caminhao;
import br.csi.model.Motorista_Caminhao;
import br.csi.util.ParamConverter;
import br.csi.util.Round;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CaminhaoService {
    private final CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
    private final Motorista_CaminhaoDAO motorista_caminhaoDAO = new Motorista_CaminhaoDAO();

    private final ParamConverter paramConverter = new ParamConverter();

    public ArrayList<Caminhao> selectAll(String offset, String limit) {
        Integer offsetNumber = paramConverter.convertStringToInt(offset);
        Integer limitNumber = paramConverter.convertStringToInt(limit);

        offsetNumber = offsetNumber == null ? 0 : offsetNumber;
        limitNumber = limitNumber == null ? 0 : limitNumber;

        return caminhaoDAO.selectAll(offsetNumber, limitNumber);
    }

    public Caminhao selectUnique(@NotNull String codCaminhao) {
        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);

        if (codCaminhaoNumber == null || codCaminhaoNumber <= 0){
            throw new IllegalArgumentException("Código de caminhão inválido");
        }
        else {
            Caminhao caminhao = caminhaoDAO.selectUnique(codCaminhaoNumber);

            if (caminhao != null){
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(caminhao.getCod());
                caminhao.setPercentualMotorista(caminhao.getPercentualMotorista() * 100);

                if (relacao != null){
                    caminhao.setMotorista(new MotoristaDAO().selectUnique(relacao.getCodMotorista()));
                    caminhao.setDataMotorista(relacao.getDataInicio());
                }

                return caminhao;
            }
            else {
                throw new IllegalArgumentException("Caminhão não encontrado");
            }
        }
    }

    public boolean persist(@NotNull String operacao, String codCaminhao,@NotNull String placa, String marca, String modelo,
                           String ano, String capacidade, @NotNull String percentualMotorista, String codMotorista) {
        validarCampos(operacao,placa, marca, modelo, ano, capacidade, percentualMotorista);

        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);
        Integer codMotoristaNumber = paramConverter.convertStringToInt(codMotorista);
        Integer anoNumber = paramConverter.convertStringToInt(ano);
        Integer capacidadeNumber = paramConverter.convertStringToInt(capacidade);
        Double percentualMotoristaNumber = Round.roundUp(paramConverter.convertStringToDouble(percentualMotorista)/100, 2);
        marca = paramConverter.convertBlankStringToNull(marca);
        modelo = paramConverter.convertBlankStringToNull(modelo);

        Caminhao caminhao = new Caminhao(codCaminhaoNumber, placa, marca, modelo, anoNumber, capacidadeNumber, percentualMotoristaNumber);

        if (operacao.equals("update")){
            Caminhao caminhaoAntigo = caminhaoDAO.selectUnique(caminhao.getCod());
            if (caminhao.getCod() == null || caminhaoAntigo == null){
                throw new IllegalArgumentException("Caminhão não encontrado");
            }
            else{
                if (!(caminhaoAntigo.getPlaca().equals(caminhao.getPlaca()))){
                    if (caminhaoDAO.existsPlaca(caminhao.getPlaca())){
                        throw new IllegalArgumentException("Placa já cadastrada");
                    }
                }

                if (!caminhaoDAO.update(caminhao)){
                    return gerarRelacionamento(codCaminhaoNumber, codMotoristaNumber);
                }
            }
        }
        else if (operacao.equals("insert")){
            if (caminhaoDAO.existsPlaca(placa)){
                throw new IllegalArgumentException("Placa já cadastrada");
            }
            else{
                codCaminhaoNumber = caminhaoDAO.insert(caminhao);
            }

            return gerarRelacionamento(codCaminhaoNumber, codMotoristaNumber);
        }

        return false;
    }



    public boolean delete(@NotNull String codCaminhao){
        Integer codCaminhaoNumber = paramConverter.convertStringToInt(codCaminhao);

        if (codCaminhaoNumber == null || codCaminhaoNumber <= 0){
            throw new IllegalArgumentException("Código de caminhão inválido");
        }
        else {
            if (caminhaoDAO.selectUnique(codCaminhaoNumber) == null){
                throw new IllegalArgumentException("Caminhão não encontrado");
            }
            else{
                Motorista_Caminhao relacao = motorista_caminhaoDAO.selectByCod_caminhao(codCaminhaoNumber);

                if (relacao != null){
                    if (!motorista_caminhaoDAO.delete(relacao)) {
                        throw new IllegalArgumentException("Erro ao deletar relacionamento");
                    }
                }

                return caminhaoDAO.delete(codCaminhaoNumber);
            }
        }
    }

    private boolean trocarRelacionamento(Integer codMotorista, Integer codCaminhao) {
        Motorista_Caminhao relacaoNova = new Motorista_Caminhao(codMotorista, codCaminhao);
        Motorista_Caminhao relacaoAntiga = motorista_caminhaoDAO.selectByCod_motorista(codMotorista);

        if (relacaoAntiga != null){
            if (relacaoAntiga.getCodCaminhao() == codCaminhao){
                return true;
            }
            else {
                motorista_caminhaoDAO.delete(relacaoAntiga);
            }
        }

        return motorista_caminhaoDAO.insert(relacaoNova);
    }

    private Boolean gerarRelacionamento(Integer codCaminhao, Integer codMotorista) {
        if (codMotorista == null || codMotorista <= 0){
            return true;
        }
        else{
            return trocarRelacionamento(codMotorista, codCaminhao);
        }
    }

    private void validarCampos(String operacao,String placa, String marca, String modelo, String ano, String capacidade, String percentualMotorista){
        if (!(operacao.equals("insert") || operacao.equals("update"))) {
            throw new IllegalArgumentException("Operação inválida");
        }
        else if (placa.isBlank()){
            throw new IllegalArgumentException("Placa inválida");
        }
        else if (!(placa.length() == 11 && marca.length() <= 25 && modelo.length() <= 25 && ano.length() <= 4 && capacidade.length() <= 5
                    && percentualMotorista.length() <= 5)){

            throw new IllegalArgumentException("Tamanho máximo de caracteres ultrapassado em algum(s) campo(s)");
        }
    }
}
