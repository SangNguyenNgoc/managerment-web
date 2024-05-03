const params = new URLSearchParams(window.location.search)

const loginErrorToastEl = $('#create-error-toast');
const loginErrorToast = new bootstrap.Toast(loginErrorToastEl);
const showToast = content => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    loginErrorToast.show();
}

const existUser = params.get('existUser')
console.log(existUser)
if(existUser) {
    showToast("Mã thành viên đã được sử dụng.")
}