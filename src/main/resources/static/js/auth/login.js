const params = new URLSearchParams(window.location.search)
const error = params.get('error')
const userNotFound = params.get('userNotFound')
const resetPass = params.get('resetPass')

const loginErrorToastEl = $('#login-error-toast');
const loginErrorToast = new bootstrap.Toast(loginErrorToastEl);

const showToast = (content) => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    loginErrorToast.show();
}

if(error) {
    showToast("Đăng nhập thất bại, sai tên tài khoảng hoặc mật khẩu !!!")
}

if(userNotFound) {
    showToast("Không tìm thấy người dùng !!!")
}
if (resetPass) {
    showToast("Cấp lại mật khẩu thành công, hãy kiểm tra email")
}
document.getElementById('login-form').addEventListener('submit', event => {
    const username = $('#username')
    const password = $('#password')
    if (!username.val() || !password.val()) {
        event.preventDefault();
        showToast("Vui lòng điền đầy đủ thông tin !!!")
        !username.val() ? username.focus() : password.focus()
    }
});

const exampleModal = document.getElementById('forgot-password-modal')
exampleModal.addEventListener('shown.bs.modal',  (event) => {
    const forgotId = $('#forgot-id')
    forgotId.focus()
})


