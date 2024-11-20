const filterTable = () => {
    const nameFilter = document.getElementById('filter-name').value.toLowerCase();
    const minAge = parseInt(document.getElementById('filter-age-from').value) || 0;
    const maxAge = parseInt(document.getElementById('filter-age-to').value) || Infinity;
    const minArrival = document.getElementById('filter-arrival-from').value
        ? new Date(document.getElementById('filter-arrival-from').value)
        : null;
    const maxArrival = document.getElementById('filter-arrival-to').value
        ? new Date(document.getElementById('filter-arrival-to').value)
        : null;
    const room = parseInt(document.getElementById('filter-room').value);

    document.querySelectorAll('main table tbody tr').forEach((row) => {
        const name = row.cells[0]?.textContent.toLowerCase();
        const age = parseInt(row.cells[1]?.textContent);

        const arrival = row.cells[2]
            ? parseDate(row.cells[2].textContent.trim())
            : null;

        const matchesName = nameFilter ? name.includes(nameFilter) : true;
        const matchesAge = age >= minAge && age <= maxAge;
        const matchesArrival =
            (!minArrival || (arrival >= minArrival)) &&
            (!maxArrival || (arrival <= maxArrival));
        const matchesRoom = !room || room === parseInt(row.cells[3]?.textContent);

        if (matchesName && matchesAge && matchesArrival && matchesRoom) {
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