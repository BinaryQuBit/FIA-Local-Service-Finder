import React from 'react';
import './Header.css';
import FiaLogo from '../Assets/FiaLogo.svg';

function Header() {
    return (
    <div className="header-container">
        <img src={FiaLogo} alt="FIA Logo" className="header-logo" />
        <h2>Your one-stop platform for finding and listing local services</h2>
    </div>
    );
}

export default Header;
