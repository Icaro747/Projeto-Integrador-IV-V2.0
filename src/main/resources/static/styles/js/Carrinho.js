function Confirmacao(URL, QTD, acao){
    if (acao == "-" && QTD == 1) {
        alert("Ação inválida");
    }else{
        $.ajax(URL).done(function(){
            location.reload();
        });
    }
}
