const now = new Date();
const gmtPlus7Time = new Date(now.getTime() + (7 * 60 * 60 * 1000));
const formattedDateTime = gmtPlus7Time.toISOString().slice(0, 16);
$('#time').val(formattedDateTime)

const successToastEl = $('#booking-error-toast');
const successToast = new bootstrap.Toast(successToastEl);

const showToast = (content) => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    successToast.show();
}

const params = new URLSearchParams(window.location.search)
const failure = params.get('failure')
const penalize = params.get('penalize')

if (failure) {
    showToast("Đặt mượn thất bại, thiết bị đã được đặt hoặc đang được mượn trong thời gian yêu cầu.")
}

if (penalize) {
    showToast("Tài khoản vẫn đang trong hình phạt, không thể đặt mượn thiết bị.")
}
