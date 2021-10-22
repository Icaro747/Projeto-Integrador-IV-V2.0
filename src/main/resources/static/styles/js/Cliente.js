function Confirmacao(){
    var Senha = $("#Senha").val();
    var ConSenha = $("#ConSenha").val();
    var Atuacao = $("#Atuacao").val();
    if (Senha === ConSenha){
        if (Atuacao === ""){
            Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Escolha uma atuação válida!',
            confirmButtonColor: '#0262dc'
        });
        return false; 
        } else{
            return true;
        }
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
