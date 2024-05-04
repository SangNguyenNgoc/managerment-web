$(document).ready( () => {
    $('#example').DataTable({
        searching: false,
        lengthChange: false,
    });
});

$('#search-input').on("keypress", event => {
    if (event.key === "Enter") {
        const searchValue = event.target.value;
        window.location.href = "/?filter=" + searchValue;
    }
});




