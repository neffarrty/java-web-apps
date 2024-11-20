function getColumnType(value) {
    if (!isNaN(value) && value.trim() !== '') {
        return 'number';
    }

    const date = new Date(value);
    if (!isNaN(date.getTime())) {
        return 'date';
    }

    return 'string';
}

const parseDate = (dateString) => {
    const [day, month, yearAndTime] = dateString.split('.');
    const [year, time] = yearAndTime.split(' ');
    return new Date(`${year}-${month}-${day}T${time}`);
};

function sortTable(index, header) {
    const table = document.getElementById('table');
    const tbody = table.tBodies[0];
    const rows = Array.from(tbody.rows);

    const order = table.dataset.sortOrder === 'asc' ? 'desc' : 'asc';
    table.dataset.sortOrder = order;

    const firstCellValue = rows[0]?.cells[index]?.textContent.trim();
    const columnType = getColumnType(firstCellValue);

    rows.sort((rowA, rowB) => {
        const cellA = rowA.cells[index].textContent.trim();
        const cellB = rowB.cells[index].textContent.trim();

        switch (columnType) {
            case 'number':
                return order === 'asc' ? cellA - cellB : cellB - cellA;
            case 'date':
                return order === 'asc'
                    ? parseDate(cellA) - parseDate(cellB)
                    : parseDate(cellB) - parseDate(cellA);
            default:
                return order === "asc"
                    ? cellA.localeCompare(cellB)
                    : cellB.localeCompare(cellA);
        }
    });

    rows.forEach(row => tbody.appendChild(row));

    document.querySelectorAll("th .sort-indicator").forEach(indicator => {
        indicator.textContent = '';
    });
    header.querySelector('.sort-indicator').textContent = order === 'asc' ? '▲' : '▼';
}