function Confirmacao() {
    var Senha = $("#Senha").val();
    var ConSenha = $("#ConSenha").val();
    if (Senha === ConSenha) {
        return true;
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'As senhas não estão iguais!',
            confirmButtonColor: '#0262dc'
        });
        return false;
    }
}

function ConfirmacaoCadasto() {

    var Senha = $("#Senha").val();
    var ConSenha = $("#ConSenha").val();
    var Sexo = $("#Sexo").val();
    var CPF = $("#CPF").val();
    var Email = $("#Email").val();
    var msg = "";
    var URL = "http://localhost:8080/api/CheckCpfEmail";

    try {
        if (Senha === ConSenha) {
            if (Sexo !== '' || Sexo !== null) {
                URL += `/${CPF}/${Email}`;
                fetch(URL, { method: 'GET' }).then(response => {
                    response.json().then(data => {
                        if (data.sucesso) {
                            return true;
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: data.mensagem,
                                confirmButtonColor: '#0262dc'
                            });
                        }
                    });
                });
            } else {
                msg = "Escolher um sexo";
            }
        } else {
            msg = "As senhas não estão iguais!";
        }
    } catch (e) {
        console.log({ e });
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: msg,
            confirmButtonColor: '#0262dc'
        });
    }
    return false;
}

$(document).ready(function () {
    $("#CPF").mask("999.999.999-99");
});