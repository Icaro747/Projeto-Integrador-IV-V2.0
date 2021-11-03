package br.com.process.controller;

import br.com.process.DAO.ClienteDAO;
import br.com.process.entidade.Cliente;
import br.com.process.response.ClienteResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/CheckEmail/{email}")
    public ClienteResponse CheckEmail(@PathVariable String email) {
        try {
            Cliente cliente = new Cliente();
            cliente.setEmail(email);

            if (!ClienteDAO.CheckEmail(cliente)) {
                return new ClienteResponse(true, 0, "Tudo certo");
            } else {
                return new ClienteResponse(false, 2, "Email já cadastrado");
            }
        } catch (Exception e) {
            return new ClienteResponse(false, 404, "" + e);
        }
    }

    @GetMapping("/CheckCpf/{cpf}")
    public ClienteResponse CheckCPF(@PathVariable String cpf) {
        try {
            Cliente cliente = new Cliente();
            cliente.setCpf(cpf);
            if (!ClienteDAO.CheckCPF(cliente)) {
                return new ClienteResponse(true, 0, "Tudo certo");
            } else {
                return new ClienteResponse(false, 1, "CPF já cadastrado");
            }
        } catch (Exception e) {
            return new ClienteResponse(false, 404, "" + e);
        }
    }
    
    @GetMapping("/CheckSenha/{senha}")
    public ClienteResponse CheckSenha(@PathVariable String senha){
        try{
            Cliente cliente = new Cliente();
            cliente.setSenha(senha);
            if(!ClienteDAO.CheckCPF(cliente)){
                return  new ClienteResponse(true,0,"Tudo certo");
            }else{
                return  new ClienteResponse(false,1,"CPF já cadastrado");
            }
        }catch(Exception e){
            return  new ClienteResponse(false,404,"" + e);
        }
    }
}
