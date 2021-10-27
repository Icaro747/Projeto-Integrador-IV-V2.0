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

    @GetMapping("/CheckCpfEmail/{cpf}/{email}")
    public ClienteResponse CheckCliente(@PathVariable String cpf, @PathVariable String email) {
        try {
            Cliente cliente = new Cliente();
            cliente.setCpf(cpf);
            cliente.setEmail(email);
            
            if (!ClienteDAO.CheckCPF(cliente)) {
                if (!ClienteDAO.CheckEmail(cliente)) {
                    return new ClienteResponse(true, 0, "Tudo certo");
                } else {
                    return new ClienteResponse(false, 2, "Email já cadastrado");
                }
            } else {
                return new ClienteResponse(false, 1, "CPF já cadastrado");
            }
        } catch (Exception e) {
            return new ClienteResponse(false, 404, ""+e);
        }
    }
}
