import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../AuthContext';
import './Welcome.css';

function Welcome() {
  const navigate = useNavigate();
  const { user, logout } = useContext(AuthContext);
  const [email, setEmail] = useState(user?.email || sessionStorage.getItem('userEmail'));

  useEffect(() => {
    if (!email) {
      navigate('/');
    }
  }, [email, navigate]);

  useEffect(() => {
    console.log('Email in state:', email);
  }, [email]);

  const handleLogout = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/users/logout', {
        method: 'POST',
        credentials: 'include'
      });
      
      if (response.ok) {
        logout();
        navigate('/');
      } else {
        console.error('Failed to logout');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className="welcome-container">
      <h2>Welcome {email}</h2>
      <button onClick={handleLogout} className="logout-button">Logout</button>
    </div>
  );
}

export default Welcome;
