const params = new URLSearchParams(window.location.search)

const loginErrorToastEl = $('#aler-error-toast');
const loginErrorToast = new bootstrap.Toast(loginErrorToastEl);
const showToast = content => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    loginErrorToast.show();
}

const success = params.get('success')
console.log(success)
if(success) {
    showToast(success)
}

const error = params.get('existDevice')
console.log(error)
if(error) {
    showToast(error)
}