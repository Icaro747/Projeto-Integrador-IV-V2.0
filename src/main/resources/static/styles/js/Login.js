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

$(document).ready(function(){
    $("#CEP").mask("99999-999");
});
$(document).ready(function(){
    $("#CPF").mask("999.999.999-99");
});