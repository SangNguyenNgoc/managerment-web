const successToastEl = $('#update-error-toast');
const successToast = new bootstrap.Toast(successToastEl);

const params = new URLSearchParams(window.location.search)
const success = params.get('success')
const updateMailSuccess = params.get('updateMailSuccess')
const invalidOtpOrPass = params.get('invalidOtpOrPass')
const updatePassSuccess = params.get('updatePassSuccess')
const invalidConfirm = params.get('invalidConfirm')
const invalidPass = params.get('invalidPass')

const showToast = (content) => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    successToast.show();
}

if(success) {
    showToast('Cập nhật thông tin thành công.')
}

if(updateMailSuccess) {
    showToast('Cập nhật email thành công.')
}

if(invalidOtpOrPass) {
    showToast('Sai mã OTP hoặc mật khẩu.')
}

if(updatePassSuccess) {
    showToast('Cập nhật mật khẩu thành công.')
}

if(invalidConfirm) {
    showToast('Xác nhận mật khẩu sai.')
}

if(invalidPass) {
    showToast('Sai mật khẩu hiện tại.')
}

document.getElementById('profile-form').addEventListener('submit', event => {
    const formControls = document.querySelectorAll('.profile-form-children');
    const hasNullValue = Array.from(formControls).some(element => {
        element.focus()
        return !element.value;
    });
    if (hasNullValue) {
        event.preventDefault();
        showToast("Vui lòng điền đầy đủ thông tin !!!")
    }

    let hasChanged = false;

    formControls.forEach(input => {
        const defaultValue = input.dataset.defaultValue || '';
        console.log(defaultValue)
        if (input.value !== defaultValue) {
            hasChanged = true;
        }
    });
    if (!hasChanged) {
        console.log("nochange")
        event.preventDefault();
    }
});


const oldEmail = $('#change-email').val()
const mailModal = $('#changeEmailModal')

$("#change-email-btn").click(async (e) => {
    e.preventDefault();
    try {
        const email = $('#change-email').val();
        if(oldEmail !== email) {
            const formData = new FormData();
            formData.append('email', email);

            const response = await fetch('/sendOtp', {
                body: formData,
                method: 'POST'
            });

            if (!response.ok) {
                throw new Error('Email đã được sử dụng');
            } else {
                // Hiển thị modal nếu không có vấn đề gì
                mailModal.modal('show');
            }
        }
    } catch (error) {
        // Xử lý lỗi
        console.error('Lỗi khi gửi OTP:', error);
        showToast(error.message);
    }
});

document.getElementById('change-password-form').addEventListener('submit', event => {
    const oldPassword = $('#old-password')
    const newPassword = $('#new-password')
    const confirmPassword = $('#confirm-password')
    if (!oldPassword.val() || !newPassword.val() || !confirmPassword.val()) {
        event.preventDefault();
        showToast("Vui lòng điền đầy đủ thông tin !!!")
        !oldPassword.val() ? oldPassword.focus() : newPassword.focus()
    }
});



