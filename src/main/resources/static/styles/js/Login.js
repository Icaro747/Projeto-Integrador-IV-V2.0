const URL = "http://localhost:8080/api";

function Confirmacao() {
    var Senha = $("#Senha").val();
    var ConSenha = $("#ConSenha").val();
    if (Senha === ConSenha) {
        return true;
    } else {
        MSG('As senhas não estão iguais!');
        return false;
    }
}

function CheckEmail() {
    var Email = $("#Email").val();
    const URL_Email = URL + `/CheckEmail/${Email}`;
    if (Email != "" || Email != null) {
        return result(URL_Email);
    }
}

function CheckCPF() {
    var CPF = $("#CPF").val();
    const URL_CPF = URL + `/CheckCpf/${CPF}`;
    if (CPF != "" || CPF != null) {
        return result(URL_CPF);
    }
}

function result(url) {
    fetch(url, { method: 'GET' }).then(response => {
        response.json().then(data => {
            if (data.sucesso) {
                return true;
            } else {
                MSG(data.mensagem);
                return false;
            }
        });
    });
}

function ConfirmacaoCadasto() {

    var Senha = $("#Senha").val();
    var ConSenha = $("#ConSenha").val();
    var Sexo = $("#Sexo").val();

    try {
        if (Senha === ConSenha) {
            if (Sexo !== '' || Sexo !== null) {
                return true;
            } else {
                MSG("Escolher um sexo");
            }
        } else {
            MSG("As senhas não estão iguais!");
        }
    } catch (e) {
        console.log({ e });
    }
    return false;
}

function MSG(text) {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: text,
        confirmButtonColor: '#0262dc'
    });
}

$(document).ready(function () {
    $("#CPF").mask("999.999.999-99");
});