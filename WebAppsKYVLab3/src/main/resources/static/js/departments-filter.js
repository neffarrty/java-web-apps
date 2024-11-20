const filterTable = () => {
    const nameFilter = document.getElementById('filter-name').value.toLowerCase();
    const shortNameFilter = document.getElementById('filter-shortname').value.toLowerCase();
    const codeFilter = document.getElementById('filter-code-building').value;

    document.querySelectorAll('main table tbody tr').forEach((row) => {
        const name = row.querySelector('td:nth-child(1) a')?.textContent.toLowerCase();
        const shortName = row.querySelector('td:nth-child(2)')?.textContent.toLowerCase();
        const codeBuilding = row.querySelector('td:nth-child(3)')?.textContent;

        const matchesName = nameFilter ? name.startsWith(nameFilter) : true;
        const matchesShortName = shortNameFilter ? shortName.startsWith(shortNameFilter) : true;
        const matchesCode = codeFilter ? codeBuilding === codeFilter : true;

        if (matchesName && matchesShortName && matchesCode) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
};

const clearSearch = (form) => {
    form.reset();
    document.querySelectorAll('main table tbody tr').forEach((row) => {
        row.style.display = '';
    });
};