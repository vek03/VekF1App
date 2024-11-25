function validateRegister(){
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirm_password = document.getElementById("confirm_password").value;

    if(!email || !password || !confirm_password){
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Campos Incompletos!',
        });
        return false;
    }

    if(password !== confirm_password){
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Senha e Confirmação de Senha não Correspondem!',
        });
        return false;
    }

    return true;
}