function pesquisacep() {

    //Nova variável "cep" somente com dígitos.
    var cep = $("#CEP").val().replace(/[^\d]+/g,'');

    //Verifica se campo cep possui valor informado.
    if (cep !== "") {

        //Expressão regular para validar o CEP.
        var validacep = /^[0-9]{8}$/;

        //Valida o formato do CEP.
        if (validacep.test(cep)) {

            //Preenche os campos com "..." enquanto consulta webservice.
            document.getElementById('rua').value = "...";
            document.getElementById('bairro').value = "...";
            document.getElementById('cidade').value = "...";
            document.getElementById('uf').value = "...";


            var url = "http://viacep.com.br/ws/" + cep + "/json/";
            fetch(url, {method: 'GET'}).then(response => {
                response.json().then(data => {
                    meu_callback(data);
                });
            });

        } //end if.
        else {
            //cep é inválido.
            limpa_formulário_cep();
            alert("Formato de CEP inválido.");
        }
    } //end if.
    else {
        //cep sem valor, limpa formulário.
        limpa_formulário_cep();
    }
}

function limpa_formulário_cep() {
    //Limpa valores do formulário de cep.
    document.getElementById('rua').value = ("");
    document.getElementById('bairro').value = ("");
    document.getElementById('cidade').value = ("");
    document.getElementById('uf').value = ("");
}

function meu_callback(conteudo) {
    if (!("erro" in conteudo)) {
        //Atualiza os campos com os valores.
        document.getElementById('rua').value = (conteudo.logradouro);
        document.getElementById('bairro').value = (conteudo.bairro);
        document.getElementById('cidade').value = (conteudo.localidade);
        document.getElementById('uf').value = (conteudo.uf);
        
    } //end if.
    else {
        //CEP não Encontrado.
        limpa_formulário_cep();
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'CEP não encontrado.'
        });
    }
}

$(document).ready(function(){
    $("#CEP").mask("99999-999");
});