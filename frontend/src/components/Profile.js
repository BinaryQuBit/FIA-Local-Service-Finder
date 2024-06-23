import React, { useContext } from 'react';
import SignedInHeader from './SignedInHeader';
import { useNavigate, useLocation } from 'react-router-dom';
import './Profile.css';
import { AuthContext } from '../AuthContext';

function Profile() {
  const navigate = useNavigate();
  const location = useLocation();
  const { email } = location.state || {};
  const { logout } = useContext(AuthContext);

  const handleLogout = () => {
    logout();
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
