const successToastEl = $('#success-toast');
const successToast = new bootstrap.Toast(successToastEl);

const showToast = (content) => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    successToast.show();
}

showToast("Đặt mượn thành công !!!")