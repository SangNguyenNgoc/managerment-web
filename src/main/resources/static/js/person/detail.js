const params = new URLSearchParams(window.location.search)

const detailErrorToastEl = $('#detail-error-toast');
const detailErrorToast = new bootstrap.Toast(detailErrorToastEl);
const showToast = content => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    detailErrorToast.show();
}

const success = params.get('success')
console.log(success)
if(success) {
    showToast(success)
}

const invalidPay = params.get('invalidPay')
if(invalidPay) {
    showToast("Cần nhập số tiền bồi thường !!!")
}

const successPenalize = params.get('successPenalize')
if(successPenalize) {
    showToast("Lập phiếu phạt thành công, mã phiếu: " + successPenalize)
}
