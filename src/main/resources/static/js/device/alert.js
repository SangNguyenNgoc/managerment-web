const params = new URLSearchParams(window.location.search)

const alertErrorToastEl = $('#alert-error-toast');
const alertErrorToast = new bootstrap.Toast(alertErrorToastEl);
const showToast = content => {
    console.log(content)
    const toastBody = $('#toast-body')
    toastBody.html(content)
    alertErrorToast.show();
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

$(document).ready(() => {
    loadingEle.hide()
})
const openFilePicker = () => {
    document.getElementById('fileInput').click();
}

const handleFile = async (event) => {
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch('/admin/device/excel', {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            showToast("Import thất bại, hãy kiểm tra định dạng file.")
            throw new Error('Lỗi khi gửi file');

        } else {
            showToast("Import thành công.")
        }

        const data = await response.json();
        console.log('Kết quả từ máy chủ:', data);
    } catch (error) {
        console.error('Lỗi:', error);
        // Xử lý lỗi ở đây
    } finally {
        // Reset giá trị của input file
        document.getElementById('fileInput').value = '';
    }
}