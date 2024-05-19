import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './Welcome.css';

function Welcome() {
  const navigate = useNavigate();
  const location = useLocation();
  const { email } = location.state || {};

  const handleLogout = () => {
    navigate('/');
  };

  return (
    <div className="welcome-container">
      <h2>Welcome {email}</h2>
      <button onClick={handleLogout} className="logout-button">Logout</button>
    </div>
  );
}

export default Welcome;
