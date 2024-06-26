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

const success = params.get('success')
if(success) {
    showToast(success)
}

$('#openFile').click(() => openFilePicker())
$('#fileInput').on('change', e => handleFile(e))
const openFilePicker = () => {
    document.getElementById('fileInput').click();
}

const handleFile = async (event) => {
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append('file', file);
    console.log('send file')
    try {
        const response = await fetch('/admin/person/excel', {
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