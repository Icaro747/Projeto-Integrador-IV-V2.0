function Confirmacao(){
    var Senha = $("#Senha").val();
    var ConSenha = $("#ConSenha").val();
    if (Senha === ConSenha){
        return true;
    }else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'As senhas não estão iguais!',
            confirmButtonColor: '#0262dc'
        });
        return false;
    }
}

function ConfirmacaoCadasto(){
    var Senha = $("#Senha").val();
    var ConSenha = $("#ConSenha").val();
    var Sexo = $("#Sexo").var();
    if (Senha === ConSenha){
        if (Sexo !== '' || Sexo !== null){
            return true;
        }else{
            Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Escolher um sexo',
            confirmButtonColor: '#0262dc'
        });
        }
    }else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'As senhas não estão iguais!',
            confirmButtonColor: '#0262dc'
        });
    }
    return false;
}

$(document).ready(function(){
    $("#CPF").mask("999.999.999-99");
});