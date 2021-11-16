function calculaCep(URL){
    console.log('http://localhost:8080'+URL);
    $.ajax('http://localhost:8080'+URL).done(function(){ 
        location.reload();
    });
}
