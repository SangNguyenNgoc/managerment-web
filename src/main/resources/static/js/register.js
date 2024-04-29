const params = new URLSearchParams(window.location.search)

const loginErrorToastEl = $('#register-error-toast');
const loginErrorToast = new bootstrap.Toast(loginErrorToastEl);

const showToast = content => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    loginErrorToast.show();
}

document.getElementById('register-form').addEventListener('submit', event => {
    const formControls = $('.form-control');
    const hasNullValue = Array.from(formControls).some(element => {
        element.focus()
        return !element.value;
    });
    if (hasNullValue) {
        event.preventDefault();
        showToast("Vui lòng điền đầy đủ thông tin !!!")
    }
});

const existUser = params.get('existUser')
if(existUser) {
    showToast("Mã thành viên đã được sử dụng.")
}

const invalidConfirm = params.get('invalidConfirm')
if(invalidConfirm) {
    showToast("Xác nhận mật khẩu không đúng, hãy thử lại !!!")
}

const success = params.get('success')
if(success) {
    showToast("Đăng ký thành công.")
    setTimeout(function() {
        window.location.href = "/";
    }, 2000);
}


