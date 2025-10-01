window.onload = function() {
    fetch('/api/tickets/')
        .then(response => response.json())
        .then(data => {
            const ticketList = document.querySelector('.ticket-list');
            data.forEach(ticket => {
                const ticketElement = document.createElement('div');
                ticketElement.classList.add('ticket');
                ticketElement.innerHTML = `
                    <p><strong>Ticket ID:</strong> ${ticket.ticketID}</p>
                    <p><strong>Description:</strong> ${ticket.description}</p>
                    <button onclick="deleteTicket('${ticket.ticketID}')">Delete</button>
                    <button onclick="updateTicket('${ticket.ticketID}')">Update</button>
                `;
                ticketList.appendChild(ticketElement);
            });
        });
};

function deleteTicket(ticketID) {
    fetch(`/api/tickets/${ticketID}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                alert('Ticket deleted');
                window.location.reload();
            }
        });
}

function updateTicket(ticketID) {
    const newDescription = prompt('Enter new description:');
    fetch(`/api/tickets/${ticketID}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ description: newDescription }),
    })
        .then(response => response.json())
        .then(data => {
            alert('Ticket updated');
            window.location.reload();
        });
}
