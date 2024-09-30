package br.com.boss.app.bossapi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
import br.csi.util.ParamConverter;
import br.csi.util.SenhaHash;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ParamConverter paramConverter = new ParamConverter();

    public Usuario auth(@NotNull String email, @NotNull String senha) {
        String senhaCriptografada = new SenhaHash().getSHA256Hash(senha);

        Usuario usuario = usuarioDAO.auth(email, senhaCriptografada);

        if (usuario == null){
            throw new IllegalArgumentException("Email ou senha inválidos!");
        }
        else {
            return usuario;
        }
    }

    public ArrayList<Usuario> selectAll(String offset, String limit) {
        Integer offsetNumber = paramConverter.convertStringToInt(offset);
        Integer limitNumber = paramConverter.convertStringToInt(limit);

        offsetNumber = offsetNumber == null ? 0 : offsetNumber;
        limitNumber = limitNumber == null ? 0 : limitNumber;

        return usuarioDAO.selectAll(offsetNumber, limitNumber);
    }

    public Usuario selectUnique(@NotNull String codExterno) {
        if (codExterno.isBlank()){
            throw new IllegalArgumentException("Código externo inválido!");
        }

        return usuarioDAO.selectUnique(new Usuario(codExterno));
    }

    public boolean persist(@NotNull String operacao, String codExterno, @NotNull String nome, @NotNull String email, @NotNull String senha, @NotNull String permissao) {
        validarCampos(operacao, nome, email, senha, permissao);

        String senhaHash = new SenhaHash().getSHA256Hash(senha);

        Usuario usuario = new Usuario(codExterno, nome, email, senhaHash, permissao);

        if (operacao.equals("update")){
            if (codExterno == null || codExterno.isBlank()){
                throw new IllegalArgumentException("Código externo inválido!");
            }
            else{
                return usuarioDAO.update(usuario);
            }
        } else if (operacao.equals("insert")) {
            if (usuarioDAO.existsEmail(email)){
                throw new IllegalArgumentException("Email já cadastrado!");
            }
            return usuarioDAO.insert(usuario);
        }

        return false;
    }

    public boolean delete(@NotNull String codExterno) {
        return usuarioDAO.delete(codExterno);
    }

    private void validarCampos (String operacao, String nome, String email, String senha, String permissao){
        if (!("insert".equals(operacao) || "update".equals(operacao))){
            throw new IllegalArgumentException("Operação inválida!");
        }
        else if (!(permissao.equals("ADMIN") || permissao.equals("USER"))){
            throw new IllegalArgumentException("Permissão inválida!");
        }
        else if (nome.isBlank() || email.isBlank() || senha.isBlank() || permissao.isBlank()){
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos!");
        }
        else if (nome.length() > 50 || email.length() > 50 || senha.length() > 64){
            throw new IllegalArgumentException("Tamanho máximo de caracteres ultrapassado em algum(s) campo(s)!");
        }
        else if (senha.length() < 8){
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres!");
        }
    }
}
