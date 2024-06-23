import React from 'react';
import SignedInHeader from './SignedInHeader';
import { useNavigate, useLocation } from 'react-router-dom';
import './Profile.css';

function Profile() {
    const navigate = useNavigate();
    const location = useLocation();
    const { email } = location.state || {};

    const handleLogout = () => {
        navigate('/');
    };
    
    return (
        <div>
        <SignedInHeader />
        <div className="content">
            <h1>{email} Profile</h1>
            <button onClick={handleLogout} className="logout-button">Logout</button>
        </div>
        </div>
    );
}

export default Profile;
