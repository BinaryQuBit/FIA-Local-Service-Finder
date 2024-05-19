import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

function Register() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [showAlert, setShowAlert] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setError('Passwords do not match!');
      return;
    }

    const accounts = JSON.parse(localStorage.getItem('accounts')) || {};

    if (accounts[email]) {
      setError('Account already exists with this email!');
      return;
    }

    accounts[email] = { password };
    localStorage.setItem('accounts', JSON.stringify(accounts));

    setShowAlert(true);

    setTimeout(() => {
      setShowAlert(false);
      navigate('/');
    }, 2000);
  };

  return (
    <div className="register-container">
      <h2>Register</h2>
      {error && <div className="error-message">{error}</div>}
      {showAlert && (
        <div className="custom-alert">
          Your account is successfully created!
        </div>
      )}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div>
          <label>Confirm Password:</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
        </div>
        <button type="submit">Register</button>
      </form>
      <button onClick={() => navigate('/')} className="back-button">BACK</button>
    </div>
  );
}

export default Register;
