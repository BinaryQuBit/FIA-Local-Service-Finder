import React from 'react';

const DateTime = ({ timestamp }) => {
    const date = new Date(timestamp);
    const formattedDate = date.toISOString().split('T')[0]; // Formats date as YYYY-MM-DD
    const formattedTime = date.toTimeString().split(' ')[0].slice(0, 5); // Formats time as HH:MM

    return (
    <div>
        <p>Date: {formattedDate}, Time: {formattedTime}</p>
    </div>
    );
};

export default DateTime;
